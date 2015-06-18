//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.06.18 at 12:30:50 PM SAMT 
//


package xmlstate.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="redentity" type="{}red" maxOccurs="unbounded"/>
 *         &lt;element name="grayentity" type="{}gray" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "redentity",
    "grayentity"
})
@XmlRootElement(name = "entities")
public class Entities {

    @XmlElement(required = true)
    protected List<Red> redentity;
    @XmlElement(required = true)
    protected List<Gray> grayentity;

    /**
     * Gets the value of the redentity property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the redentity property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRedentity().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Red }
     * 
     * 
     */
    public List<Red> getRedentity() {
        if (redentity == null) {
            redentity = new ArrayList<Red>();
        }
        return this.redentity;
    }

    /**
     * Gets the value of the grayentity property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the grayentity property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGrayentity().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Gray }
     * 
     * 
     */
    public List<Gray> getGrayentity() {
        if (grayentity == null) {
            grayentity = new ArrayList<Gray>();
        }
        return this.grayentity;
    }

}
