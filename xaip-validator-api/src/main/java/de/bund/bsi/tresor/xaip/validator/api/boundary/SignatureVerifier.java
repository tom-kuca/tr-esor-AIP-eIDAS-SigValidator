package de.bund.bsi.tresor.xaip.validator.api.boundary;

import java.util.List;
import java.util.Map;
import java.util.Set;

import de.bund.bsi.tr_esor.vr._1.CredentialValidityType;
import de.bund.bsi.tr_esor.xaip._1.CredentialType;
import de.bund.bsi.tr_esor.xaip._1.DataObjectType;
import de.bund.bsi.tr_esor.xaip._1.XAIPType;
import oasis.names.tc.dss_x._1_0.profiles.verificationreport.schema_.IndividualReportType;

/**
 * API for the SignatureVerificationModule.<br>
 * This module is being used to verify all signatures found by the {@link SignatureFinder} implementation.
 * 
 * @author wolffs
 */
public interface SignatureVerifier extends ValidatorModule
{
    /**
     * Resolving the {@link DataObjectType} and {@link CredentialType} by the provided ids from the {@link SignatureFinder} and verifying
     * the resolved signatures. Each signature should result into an {@link CredentialValidityType} with consideration of the supported
     * signatures of the verifier.
     * 
     * @param xaip
     *            the xaip to validate
     * @param credIdsByDataId
     *            a set of credentialIds as value mapped by the dataObjectId as key which contain a signature.</br>
     *            The entries can contain any permutation of:</br>
     *            <ul>
     *            <li>dataObjectId without any credentialIds</li>
     *            <ul>
     *            <li>when the dataObject contains an embedded signature</li>
     *            </ul>
     *            <li>dataObjectId with credentialId(s)</li>
     *            <ul>
     *            <li>when the dataObject contains an embedded signature and the credential holds a pointer to those data</li>
     *            <li>when the dataObject contains a detached signature which is hold by the credential</li>
     *            <li>when the credential holds a timestamp</li>
     *            </ul>
     *            <li>credentialId(s) mapped to the key <code>null</code></li>
     *            <ul>
     *            <li>when the credential contains data with an embedded signature without any dataObject reference</li>
     *            </ul>
     *            </ul>
     * @return the verification result in form of an {@link IndividualReportType}s
     */
    public List<CredentialValidityType> verify( XAIPType xaip, Map<String, Set<String>> credIdsByDataId );
}
