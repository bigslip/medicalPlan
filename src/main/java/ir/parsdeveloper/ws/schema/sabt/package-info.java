/**
 * @author h.tayebi
 */
@XmlSchema(
        namespace = "http://tempuri.org/",
        location = "http://tempuri.org/",
        elementFormDefault = XmlNsForm.QUALIFIED,
        xmlns = {
                @XmlNs(prefix = "inq", namespaceURI = "http://tempuri.org/")
        }
)
package ir.parsdeveloper.ws.schema.sabt;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;