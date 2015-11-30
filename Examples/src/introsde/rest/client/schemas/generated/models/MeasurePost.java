package introsde.rest.client.schemas.generated.models;


import java.util.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.fasterxml.jackson.annotation.JsonFormat;

@XmlAccessorType (XmlAccessType.FIELD)
@XmlRootElement(name="Measure")
public class MeasurePost{
  public String measure;
  public String value;

  public MeasurePost(){}
  public MeasurePost(String measure,String value){
    this.measure = measure;
    this.value = value;
  }
  public String getMeasure(){
    return this.measure;
  }
  public String getValue(){
    return this.value;
  }
}
