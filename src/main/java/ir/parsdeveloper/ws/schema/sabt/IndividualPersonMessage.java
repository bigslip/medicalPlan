package ir.parsdeveloper.ws.schema.sabt;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;


@XmlType
@XmlAccessorType(XmlAccessType.PROPERTY)
public class IndividualPersonMessage {

    List<String> string;

    @XmlElement(name = "string")
    public List<String> getString() {
        return string;
    }

    public void setString(List<String> string) {
        this.string = string;
    }
}
