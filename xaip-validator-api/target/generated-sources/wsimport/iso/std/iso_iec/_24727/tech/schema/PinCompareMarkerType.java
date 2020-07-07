
package iso.std.iso_iec._24727.tech.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für PinCompareMarkerType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="PinCompareMarkerType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{urn:iso:std:iso-iec:24727:tech:schema}DIDAbstractMarkerType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PinRef" type="{urn:iso:std:iso-iec:24727:tech:schema}KeyRefType"/&gt;
 *         &lt;element name="PinValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PasswordAttributes" type="{urn:iso:std:iso-iec:24727:tech:schema}PasswordAttributesType" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:iso:std:iso-iec:24727:tech:schema}StateInfo" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PinCompareMarkerType")
public class PinCompareMarkerType
    extends DIDAbstractMarkerType
{


}
