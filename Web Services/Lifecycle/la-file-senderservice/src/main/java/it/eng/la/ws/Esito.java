
package it.eng.la.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per esito complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="esito">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="filePDF" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="result" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resultDetails" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "esito", propOrder = {
    "filePDF",
    "result",
    "resultDetails"
})
public class Esito {

    protected byte[] filePDF;
    protected String result;
    protected String resultDetails;

    /**
     * Recupera il valore della proprietà filePDF.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getFilePDF() {
        return filePDF;
    }

    /**
     * Imposta il valore della proprietà filePDF.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setFilePDF(byte[] value) {
        this.filePDF = value;
    }

    /**
     * Recupera il valore della proprietà result.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResult() {
        return result;
    }

    /**
     * Imposta il valore della proprietà result.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResult(String value) {
        this.result = value;
    }

    /**
     * Recupera il valore della proprietà resultDetails.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultDetails() {
        return resultDetails;
    }

    /**
     * Imposta il valore della proprietà resultDetails.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultDetails(String value) {
        this.resultDetails = value;
    }

}
