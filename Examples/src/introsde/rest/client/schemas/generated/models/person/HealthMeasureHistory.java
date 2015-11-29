package introsde.rest.client.schemas.generated.models;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonValue;
import introsde.rest.client.schemas.generated.models.HealthMeasure;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="healthMeasureHistories")
public class HealthMeasureHistory{

  @XmlElement(name="measure")
  public List<HealthMeasure> healthMeasure;


	public List<HealthMeasure> getHealthMeasure() {
		return healthMeasure;
	}

	public void setHealthMeasure(List<HealthMeasure> healthMeasure) {
		this.healthMeasure = healthMeasure;
	}
}
