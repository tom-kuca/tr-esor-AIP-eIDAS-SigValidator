package de.bund.bsi.tresor.xaip.validator.api.control;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import de.bund.bsi.tr_esor.xaip._1.XAIPType;

/**
 * @author wolffs
 */
public class XAIPMarshaller
{
    public static final QName XAIP_QNAME = new QName( "http://www.bsi.bund.de/tr-esor/xaip/1.2", "XAIP" );
    
    public static JAXBElement<XAIPType> element( XAIPType xaip )
    {
        return new JAXBElement<XAIPType>( XAIP_QNAME, XAIPType.class, xaip );
    }
}
