package introsde.rest.client.schemas.generated.models;

import introsde.rest.client.schemas.generated.models.MeasureGet;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType (XmlAccessType.FIELD)
@XmlRootElement(name="healthMeasureHistories")
public class MeasureGetList{

  @XmlElement(name = "measure")
  private List<MeasureGet> measure;

  public MeasureGetList(){
    this.measure = new ArrayList<MeasureGet>();
  }

  public MeasureGetList(List<MeasureGet> measure){
    this.measure = measure;
  }

  public List<MeasureGet> getMeasureList(){
    return measure;
  }

  public void setMeasureList(List<MeasureGet> measure){
    this.measure = measure;
  }

  public void addMeasure(MeasureGet mg){
    measure.add(mg);
  }
}
