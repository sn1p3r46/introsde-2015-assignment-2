package introsde.rest.ehealth.model;

import introsde.rest.ehealth.dao.LifeCoachDao;
import introsde.rest.ehealth.model.MeasureDefinition;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlElement;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import javax.persistence.TypedQuery;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "LifeStatus")
@NamedQueries({
	@NamedQuery(name = "LifeStatus.findAll", query = "SELECT l FROM LifeStatus l"),
	@NamedQuery(name="LifeStatus.findLifeStatusByMeasureDefinitionAndPerson", query="SELECT ls FROM LifeStatus ls WHERE ls.person = ?1 AND ls.measureDefinition = ?2")
})
@XmlRootElement(name="Measure")
public class LifeStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_lifestatus")
	@TableGenerator(name="sqlite_lifestatus", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="LifeStatus")
	@Column(name = "idMeasure")
	private int idMeasure;

	@Column(name = "value")
	private String value;

	@OneToOne
	@JoinColumn(name = "idMeasureDef", referencedColumnName = "idMeasureDef", insertable = true, updatable = true)
	private MeasureDefinition measureDefinition;

	@ManyToOne
	@JoinColumn(name="idPerson",referencedColumnName="idPerson")
	private Person person;

	public LifeStatus() {
	}

	@XmlTransient
	public int getIdMeasure() {
		return this.idMeasure;
	}

	public void setIdMeasure(int idMeasure) {
		this.idMeasure = idMeasure;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@XmlElement(name = "measure")
	public MeasureDefinition getMeasureDefinition() {
		return measureDefinition;
	}

	public void setMeasureDefinition(MeasureDefinition param) {
		this.measureDefinition = param;
	}

	// we make this transient for JAXB to avoid and infinite loop on serialization
	@XmlTransient
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}


	// Database operations
	// Notice that, for this example, we create and destroy and entityManager on each operation.
	// How would you change the DAO to not having to create the entity manager every time?
	public static LifeStatus getLifeStatusById(int lifestatusId) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		LifeStatus p = em.find(LifeStatus.class, lifestatusId);
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}

	public static LifeStatus getLifeStatusByMeasureDefinitionAndPerson(MeasureDefinition md, Person p){
	EntityManager em = LifeCoachDao.instance.createEntityManager();
	try{
		TypedQuery<LifeStatus> query = em.createNamedQuery("LifeStatus.findLifeStatusByMeasureDefinitionAndPerson", LifeStatus.class);
		query.setParameter(1, p);
		query.setParameter(2, md);
		LifeStatus ls = query.getSingleResult();
		LifeCoachDao.instance.closeConnections(em);
		return ls;
	}
	catch(Exception e){
		return null;
	}
}

	public static List<LifeStatus> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    List<LifeStatus> list = em.createNamedQuery("LifeStatus.findAll", LifeStatus.class).getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}

	public static LifeStatus saveLifeStatus(LifeStatus p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	  LifeCoachDao.instance.closeConnections(em);
	  return p;
	}

	public static LifeStatus updateLifeStatus(LifeStatus p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
	  LifeCoachDao.instance.closeConnections(em);
	  return p;
	}

	public static void removeLifeStatus(LifeStatus p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	  p=em.merge(p);
	  em.remove(p);
	  tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	}
}
