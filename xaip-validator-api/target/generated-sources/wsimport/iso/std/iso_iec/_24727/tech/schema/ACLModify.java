
package iso.std.iso_iec._24727.tech.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für anonymous complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{urn:iso:std:iso-iec:24727:tech:schema}RequestType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ConnectionHandle" type="{urn:iso:std:iso-iec:24727:tech:schema}ConnectionHandleType"/&gt;
 *         &lt;element name="TargetName" type="{urn:iso:std:iso-iec:24727:tech:schema}TargetNameType"/&gt;
 *         &lt;element name="CardApplicationServiceName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ActionName" type="{urn:iso:std:iso-iec:24727:tech:schema}ActionNameType"/&gt;
 *         &lt;element name="SecurityCondition" type="{urn:iso:std:iso-iec:24727:tech:schema}SecurityConditionType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "connectionHandle",
    "targetName",
    "cardApplicationServiceName",
    "actionName",
    "securityCondition"
})
@XmlRootElement(name = "ACLModify")
public class ACLModify
    extends RequestType
{

    @XmlElement(name = "ConnectionHandle", required = true)
    protected ConnectionHandleType connectionHandle;
    @XmlElement(name = "TargetName", required = true)
    protected TargetNameType targetName;
    @XmlElement(name = "CardApplicationServiceName", required = true)
    protected String cardApplicationServiceName;
    @XmlElement(name = "ActionName", required = true)
    protected ActionNameType actionName;
    @XmlElement(name = "SecurityCondition", required = true)
    protected SecurityConditionType securityCondition;

    /**
     * Ruft den Wert der connectionHandle-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ConnectionHandleType }
     *     
     */
    public ConnectionHandleType getConnectionHandle() {
        return connectionHandle;
    }

    /**
     * Legt den Wert der connectionHandle-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ConnectionHandleType }
     *     
     */
    public void setConnectionHandle(ConnectionHandleType value) {
        this.connectionHandle = value;
    }

    /**
     * Ruft den Wert der targetName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TargetNameType }
     *     
     */
    public TargetNameType getTargetName() {
        return targetName;
    }

    /**
     * Legt den Wert der targetName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TargetNameType }
     *     
     */
    public void setTargetName(TargetNameType value) {
        this.targetName = value;
    }

    /**
     * Ruft den Wert der cardApplicationServiceName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardApplicationServiceName() {
        return cardApplicationServiceName;
    }

    /**
     * Legt den Wert der cardApplicationServiceName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardApplicationServiceName(String value) {
        this.cardApplicationServiceName = value;
    }

    /**
     * Ruft den Wert der actionName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ActionNameType }
     *     
     */
    public ActionNameType getActionName() {
        return actionName;
    }

    /**
     * Legt den Wert der actionName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ActionNameType }
     *     
     */
    public void setActionName(ActionNameType value) {
        this.actionName = value;
    }

    /**
     * Ruft den Wert der securityCondition-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link SecurityConditionType }
     *     
     */
    public SecurityConditionType getSecurityCondition() {
        return securityCondition;
    }

    /**
     * Legt den Wert der securityCondition-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link SecurityConditionType }
     *     
     */
    public void setSecurityCondition(SecurityConditionType value) {
        this.securityCondition = value;
    }

}
