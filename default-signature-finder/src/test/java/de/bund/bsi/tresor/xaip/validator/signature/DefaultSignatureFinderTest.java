package de.bund.bsi.tresor.xaip.validator.signature;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.w3._2000._09.xmldsig_.SignatureType;

import de.bund.bsi.tr_esor.xaip._1.CredentialType;
import de.bund.bsi.tr_esor.xaip._1.CredentialsSectionType;
import de.bund.bsi.tr_esor.xaip._1.DataObjectType;
import de.bund.bsi.tr_esor.xaip._1.DataObjectType.BinaryData;
import de.bund.bsi.tr_esor.xaip._1.DataObjectsSectionType;
import de.bund.bsi.tr_esor.xaip._1.XAIPType;
import de.bund.bsi.tresor.xaip.validator.api.control.ModuleLogger;
import oasis.names.tc.dss._1_0.core.schema.SignatureObject;

/**
 * @author bendlera
 *
 */
class DefaultSignatureFinderTest
{
    
    private static byte[] signedPdfFile;
    
    /**
     * Loads test data.
     * 
     * @throws java.lang.Exception
     *             loading fails
     */
    @BeforeAll
    static void setUpBeforeClass() throws Exception
    {
        signedPdfFile = Files
                .readAllBytes( Paths.get( DefaultSignatureFinderTest.class.getResource( "/signed.pdf" ).toURI() ) );
    }
    
    /**
     * Test method for
     * {@link de.bund.bsi.tresor.xaip.validator.signature.DefaultSignatureFinder#findSignatures(de.bund.bsi.tr_esor.xaip._1.XAIPType)}.
     */
    @Test
    void testFindSignatures()
    {
        String credentialsSectionId = "01234";
        String dataObjectsSectionId = "56789";
        
        ByteArrayOutputStream loggerOutput = new ByteArrayOutputStream();
        ModuleLogger.initConfig( true, loggerOutput );
        
        DataObjectsSectionType dataObjectsSectionType = null;
        try
        {
            dataObjectsSectionType = createDataObjectsSectionType( dataObjectsSectionId,
                    Optional.ofNullable( Paths.get( DefaultSignatureFinderTest.class.getResource( "/signed.pdf" ).toURI() ).toFile() ) );
        }
        catch ( URISyntaxException e )
        {
            fail( "data could not loaded", e );
        }
        
        XAIPType xaipType = new XAIPType();
        xaipType.setCredentialsSection( createCredentialsSectionType( credentialsSectionId ) );
        xaipType.setDataObjectsSection( dataObjectsSectionType );
        
        DefaultSignatureFinder defaultSignatureFinder = new DefaultSignatureFinder();
        
        List<SignatureObject> signatures = defaultSignatureFinder.findSignatures( xaipType );
        
        assertEquals( credentialsSectionId, signatures.get( 0 ).getSignature().getId() );
        assertArrayEquals( signedPdfFile, (byte[]) signatures.get( 1 ).getSignaturePtr().getWhichDocument() );
    }
    
    /**
     * Test method for
     * {@link de.bund.bsi.tresor.xaip.validator.signature.DefaultSignatureFinder#fromCredentialSection(de.bund.bsi.tr_esor.xaip._1.CredentialsSectionType)}.
     */
    @Test
    void testFromCredentialSection()
    {
        String id = "12345";
        
        DefaultSignatureFinder defaultSignatureFinder = new DefaultSignatureFinder();
        
        List<SignatureObject> result = defaultSignatureFinder.fromCredentialSection( createCredentialsSectionType( id ) );
        
        assertEquals( id, result.get( 0 ).getSignature().getId() );
    }
    
    /**
     * Creates {@link CredentialsSectionType}.
     * 
     * @param id
     *            SignatureType-ID
     * @return new instance
     */
    CredentialsSectionType createCredentialsSectionType( String id )
    {
        SignatureType signatureType = new SignatureType();
        signatureType.setId( id );
        
        SignatureObject signatureObject = new SignatureObject();
        signatureObject.setSignature( signatureType );
        
        CredentialType credentialType = new CredentialType();
        credentialType.setSignatureObject( signatureObject );
        
        CredentialsSectionType credentialsSectionType = new CredentialsSectionType();
        credentialsSectionType.getCredential().add( credentialType );
        
        return credentialsSectionType;
    }
    
    /**
     * Test method for
     * {@link de.bund.bsi.tresor.xaip.validator.signature.DefaultSignatureFinder#fromDataObjectsSection(de.bund.bsi.tr_esor.xaip._1.DataObjectsSectionType)}.
     */
    @Test
    void testFromDataObjectsSection()
    {
        String dataObjectId = "12345";
        
        ByteArrayOutputStream loggerOutput = new ByteArrayOutputStream();
        ModuleLogger.initConfig( true, loggerOutput );
        
        DefaultSignatureFinder defaultSignatureFinder = new DefaultSignatureFinder();
        
        assertEquals( Collections.emptyList(), defaultSignatureFinder.fromDataObjectsSection( null ) );
        
        assertEquals( Collections.emptyList(), defaultSignatureFinder.fromDataObjectsSection( new DataObjectsSectionType() ) );
        
        DataObjectsSectionType dataObjectsSectionType = createDataObjectsSectionType( dataObjectId, Optional.empty() );
        
        assertEquals( Collections.emptyList(), defaultSignatureFinder.fromDataObjectsSection( dataObjectsSectionType ) );
        
        try
        {
            dataObjectsSectionType = createDataObjectsSectionType( dataObjectId,
                    Optional.ofNullable( Paths.get( DefaultSignatureFinderTest.class.getResource( "/text.txt" ).toURI() ).toFile() ) );
        }
        catch ( URISyntaxException e )
        {
            fail( "data could not loaded", e );
        }
        
        assertEquals( Collections.emptyList(), defaultSignatureFinder.fromDataObjectsSection( dataObjectsSectionType ) );
        
        try
        {
            dataObjectsSectionType = createDataObjectsSectionType( dataObjectId,
                    Optional.ofNullable( Paths.get( DefaultSignatureFinderTest.class.getResource( "/signed.pdf" ).toURI() ).toFile() ) );
        }
        catch ( URISyntaxException e )
        {
            fail( "data could not loaded", e );
        }
        
        assertEquals( 1, defaultSignatureFinder.fromDataObjectsSection( dataObjectsSectionType ).size() );
    }
    
    /**
     * Creates {@link DataObjectsSectionType}.
     * 
     * @param dataObjectId
     *            id
     * @param signedData
     *            signed data
     * @return new instance
     */
    private DataObjectsSectionType createDataObjectsSectionType( String dataObjectId, Optional<File> signedData )
    {
        DataObjectsSectionType dataObjectsSectionType = new DataObjectsSectionType();
        
        BinaryData signedPdf = new BinaryData();
        
        if ( signedData.isPresent() )
        {
            signedPdf.setMimeType( "application/octet-stream" );
            signedPdf.setValue( new DataHandler( new FileDataSource( signedData.get() ) ) );
        }
        
        DataObjectType dataObjectType;
        dataObjectType = new DataObjectType();
        dataObjectType.setDataObjectID( dataObjectId );
        dataObjectType.setBinaryData( signedPdf );
        
        dataObjectsSectionType.getDataObject().add( dataObjectType );
        
        return dataObjectsSectionType;
    }
    
    /**
     * Test method for {@link de.bund.bsi.tresor.xaip.validator.signature.DefaultSignatureFinder#convert(byte[])}.
     */
    @Test
    void testConvert()
    {
        byte[] testData = "data".getBytes();
        DefaultSignatureFinder defaultSignatureFinder = new DefaultSignatureFinder();
        SignatureObject signatureObject = defaultSignatureFinder.convert( testData );
        
        assertEquals( testData, signatureObject.getSignaturePtr().getWhichDocument() );
    }
    
    /**
     * Test method for {@link de.bund.bsi.tresor.xaip.validator.signature.DefaultSignatureFinder#asData(javax.activation.DataHandler)}.
     */
    @Test
    void testAsData()
    {
        DataHandler dataHandler = null;
        try
        {
            dataHandler = new DataHandler( new FileDataSource(
                    Paths.get( DefaultSignatureFinderTest.class.getResource( "/text.txt" ).toURI() ).toFile() ) );
        }
        catch ( URISyntaxException e )
        {
            fail( "data could not loaded", e );
        }
        
        DefaultSignatureFinder defaultSignatureFinder = new DefaultSignatureFinder();
        
        assertArrayEquals( "content".getBytes(), defaultSignatureFinder.asData( dataHandler ) );
    }
    
    /**
     * Test method for {@link de.bund.bsi.tresor.xaip.validator.signature.DefaultSignatureFinder#getVendor()}.
     */
    @Test
    void testGetVendor()
    {
        assertEquals( "BSI", new DefaultSignatureFinder().getVendor() );
    }
    
    /**
     * Test method for {@link de.bund.bsi.tresor.xaip.validator.signature.DefaultSignatureFinder#getVersion()}.
     */
    @Test
    void testGetVersion()
    {
        assertEquals( "1.0.0", new DefaultSignatureFinder().getVersion() );
    }
    
}
