package introsde.rest.ehealth.resources;

import introsde.rest.ehealth.model.MeasureDefinition;

import java.util.List;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * POJO for getting the list of valid Measure Types
 */
@XmlRootElement(name="measureTypes")
@Entity
public class MeasureTypeCollection {

	private List<MeasureDefinition> measures;

	public MeasureTypeCollection(){
		measures = MeasureDefinition.getAll();
	}

	@XmlElement(name="measureType")
	public List<MeasureDefinition> getMeasureTypes(){
		return measures;
	}
}
