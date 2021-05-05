
package it.eng.la.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for freeform complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="freeform">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codefornerp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="datadoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numdoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="q1" type="{http://webservices.servlet.web.snam.lucystar.idoq.it/}q1" minOccurs="0"/>
 *         &lt;element name="q3" type="{http://webservices.servlet.web.snam.lucystar.idoq.it/}q3" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
/**
 * Modifica XML per fatturazione elettronica MASSIMO CARUSO
 * 
*@XmlAccessorType(XmlAccessType.FIELD)
*@XmlType(name = "freeform", propOrder = {
*    "codefornerp",
*    "datadoc",
*    "numdoc",
*    "q1",
*    "q3"
*})
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "freeform", propOrder = {
    "proformaId",
    "q1",
    "q3"
})
public class Freeform {

	/**
	 * Rimozione dei campi inutilizzati per fatturazione elettronica
	 * MASSIMO CARUSO
	 */
    //protected String codefornerp;
    //protected String datadoc;
    //protected String numdoc;
	protected String proformaId;
    protected Q1 q1;
    protected Q3 q3;

    /**
     * Gets the value of the codefornerp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
//    public String getCodefornerp() {
//        return codefornerp;
//    }

    /**
     * Sets the value of the codefornerp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
//    public void setCodefornerp(String value) {
//        this.codefornerp = value;
//    }

    /**
     * Gets the value of the datadoc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
//    public String getDatadoc() {
//        return datadoc;
//    }

    /**
     * Sets the value of the datadoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
//    public void setDatadoc(String value) {
//        this.datadoc = value;
//    }

    /**
     * Gets the value of the numdoc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
//    public String getNumdoc() {
//        return numdoc;
//    }

    /**
     * Sets the value of the proformaId property.
     * @author MASSIMO CARUSO
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProformaId(String value) {
        this.proformaId = value;
    }

    
    /**
     * Gets the value of the proformaId property.
     * @author MASSIMO CARUSO
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProformaId() {
        return proformaId;
    }

    /**
     * Sets the value of the numdoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
//    public void setNumdoc(String value) {
//        this.numdoc = value;
//    }
    
    
    /**
     * Gets the value of the q1 property.
     * 
     * @return
     *     possible object is
     *     {@link Q1 }
     *     
     */
    public Q1 getQ1() {
        return q1;
    }

    /**
     * Sets the value of the q1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Q1 }
     *     
     */
    public void setQ1(Q1 value) {
        this.q1 = value;
    }

    /**
     * Gets the value of the q3 property.
     * 
     * @return
     *     possible object is
     *     {@link Q3 }
     *     
     */
    public Q3 getQ3() {
        return q3;
    }

    /**
     * Sets the value of the q3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Q3 }
     *     
     */
    public void setQ3(Q3 value) {
        this.q3 = value;
    }

}
