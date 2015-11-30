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
public class MeasureGet {

	//@XmlTransient
	private String idMeasureHistory;

	private  String type;

	private String value;

	//@XmlTransient
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private Date timestamp;

	public MeasureGet() {}

	public MeasureGet(String idMeasureHistory, String type, String value, Date timestamp) {
		this.idMeasureHistory = idMeasureHistory;
		this.type = type;
		this.value = value;
		this.timestamp = timestamp;
	}

	public String getIdMeasureHistory() {
		return idMeasureHistory;
	}

	public void setIdMeasureHistory(String idMeasureHistory) {
		this.idMeasureHistory = idMeasureHistory;
	}

	public String getMeasure() {
		return type;
	}

	public void setMeasure(String type) {
		this.type = type;
	}

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
