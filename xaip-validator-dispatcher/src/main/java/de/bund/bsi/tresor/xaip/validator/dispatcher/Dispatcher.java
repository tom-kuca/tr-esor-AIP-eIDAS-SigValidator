package de.bund.bsi.tresor.xaip.validator.dispatcher;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import javax.xml.bind.JAXB;

import de.bund.bsi.tresor.xaip.validator.api.boundary.ProtocolAssembler;
import de.bund.bsi.tresor.xaip.validator.api.boundary.SignatureFinder;
import de.bund.bsi.tresor.xaip.validator.api.boundary.SignatureVerifier;
import de.bund.bsi.tresor.xaip.validator.api.boundary.SyntaxValidator;
import de.bund.bsi.tresor.xaip.validator.api.boundary.ValidatorModule;
import de.bund.bsi.tresor.xaip.validator.api.control.ModuleLogger;
import de.bund.bsi.tresor.xaip.validator.api.entity.SyntaxValidationResult;
import de.bund.bsi.tresor.xaip.validator.api.entity.XAIPValidatorException;
import oasis.names.tc.dss._1_0.core.schema.SignatureObject;
import oasis.names.tc.dss_x._1_0.profiles.verificationreport.schema_.IndividualReportType;
import oasis.names.tc.dss_x._1_0.profiles.verificationreport.schema_.VerificationReportType;

/**
 * Dispatcher element of the XAIPValidator. This is the control segment of the validator which implements the outer API functions of the
 * validator. Those API calls will be prepared and redirected to the designated modules in a specific order to ensure a correct validation
 * of the XAIP.
 * 
 * @author wolffs
 */
public enum Dispatcher
{
    INSTANCE;
    
    private SignatureFinder   sigFinder;
    private SignatureVerifier sigVerifier;
    
    private SyntaxValidator   syntaxValidator;
    private ProtocolAssembler protocolAssembler;
    
    /**
     * Triggering and managing the XAIP validation by requesting the modules in a specific order and processing their responses. The
     * {@link DispatcherArguments} are being used to configure and provide informations to the dispatcher which are being used by the
     * dispatcher itself or being relayed to a specific module.
     * 
     * @param args
     *            the dispatcher arguments
     */
    public void dispatch( DispatcherArguments args )
    {
        ModuleLogger.initConfig( args.isVerbose(), args.getLog() );
        loadModules();
        
        SyntaxValidationResult syntaxResult = syntaxValidator.validateSyntax( args.getInput() );
        ModuleLogger.log( "finished syntax validation" );
        
        IndividualReportType syntaxReport = syntaxResult.getSyntaxReport();
        
        List<IndividualReportType> reportParts = new ArrayList<>();
        reportParts.add( syntaxReport );
        
        if ( args.isVerify() )
        {
            syntaxResult.getXaip().ifPresent( xaip -> {
                List<SignatureObject> signatures = sigFinder.findSignatures( xaip );
                ModuleLogger.log( signatures.size() + " signatures found" );
                ModuleLogger.log( "finished signature search" );
                
                // TODO verify
                ModuleLogger.log( "finished signature verification" );
                reportParts.addAll( sigVerifier.verify( signatures ) );
            } );
        }
        
        VerificationReportType verificationReport = protocolAssembler.assembleProtocols( reportParts );
        ModuleLogger.log( "finished protocol assembling" );
        
        JAXB.marshal( verificationReport, args.getOutput() );
    }
    
    /**
     * Loading all validator modules.
     */
    void loadModules()
    {
        sigFinder = loadModule( SignatureFinder.class );
        sigVerifier = loadModule( SignatureVerifier.class );
        
        syntaxValidator = loadModule( SyntaxValidator.class );
        protocolAssembler = loadModule( ProtocolAssembler.class );
    }
    
    /**
     * Loads a module implementation of the provided moduleClass.
     * 
     * @param <T>
     *            type of the module
     * @param moduleClass
     *            class of the module interface
     * @return the loaded module implementation
     */
    <T extends ValidatorModule> T loadModule( Class<T> moduleClass )
    {
        String moduleName = moduleClass.getSimpleName();
        T module = ServiceLoader.load( moduleClass )
                .findFirst()
                .orElseThrow( () -> new XAIPValidatorException( "no provider found for module " + moduleName ) );
        
        String vendor = module.getVendor();
        String version = module.getVersion();
        ModuleLogger.log( MessageFormat.format( "loaded {0} by {1} in version {2}", moduleName, vendor, version ) );
        
        return module;
    }
}
