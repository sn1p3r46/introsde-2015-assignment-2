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
@XmlRootElement(name="measure")
public class MeasurePost{
  //public String measure;
  public String value;

	@Temporal(TemporalType.DATE)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
  public Date timestamp;

  public MeasurePost(){}

  public MeasurePost(String value, Date timestamp){
    // this.measure = measure;
    this.value = value;
		this.timestamp = timestamp;
	}

//  public MeasurePost(String measure,String value, Date timestamp){
//    this.measure = measure;
//    this.value = value;
//		this.timestamp = timestamp;
//}
//	public String getMeasure() {
//		return measure;
//	}
//
//	public void setMeasure(String measure) {
//		this.measure = measure;
//	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
