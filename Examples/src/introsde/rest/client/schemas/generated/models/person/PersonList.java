package introsde.rest.client.schemas.generated.models.person;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "people")
@XmlAccessorType (XmlAccessType.FIELD)
public class PersonList
{
    @XmlElement(name = "person")
    private List<Person> people = null;

    public List<Person> getpeople() {
        return people;
    }

    public void setpeople(List<Person> people) {
        this.people = people;
    }
}
