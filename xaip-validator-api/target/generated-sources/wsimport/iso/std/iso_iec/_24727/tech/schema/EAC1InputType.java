
package iso.std.iso_iec._24727.tech.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für EAC1InputType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="EAC1InputType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{urn:iso:std:iso-iec:24727:tech:schema}DIDAuthenticationDataType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Certificate" type="{http://www.w3.org/2001/XMLSchema}hexBinary" maxOccurs="unbounded"/&gt;
 *         &lt;element name="CertificateDescription" type="{http://www.w3.org/2001/XMLSchema}hexBinary" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="ProviderInfo" type="{http://www.w3.org/2001/XMLSchema}hexBinary" minOccurs="0"/&gt;
 *         &lt;element name="RequiredCHAT" type="{http://www.w3.org/2001/XMLSchema}hexBinary" minOccurs="0"/&gt;
 *         &lt;element name="OptionalCHAT" type="{http://www.w3.org/2001/XMLSchema}hexBinary" minOccurs="0"/&gt;
 *         &lt;element name="AuthenticatedAuxiliaryData" type="{http://www.w3.org/2001/XMLSchema}hexBinary" minOccurs="0"/&gt;
 *         &lt;element name="TransactionInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EAC1InputType")
public class EAC1InputType
    extends DIDAuthenticationDataType
{


}
