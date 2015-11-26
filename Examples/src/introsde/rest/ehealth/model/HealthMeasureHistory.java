package introsde.rest.ehealth.model;

import introsde.rest.ehealth.dao.LifeCoachDao;
import introsde.rest.ehealth.model.Person;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import javax.persistence.TypedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;



/**
 * The persistent class for the "HealthMeasureHistory" database table.
 *
 */
	@Entity
	@Table(name="HealthMeasureHistory")
	@NamedQueries({
		@NamedQuery(name="HealthMeasureHistory.findAll", query="SELECT h FROM HealthMeasureHistory h"),
		@NamedQuery(name="HealthMeasureHistory.findByMeasureAndPerson", query="SELECT hmh FROM HealthMeasureHistory hmh WHERE hmh.person = ?1 AND hmh.measureDefinition = ?2"),
		@NamedQuery(name="HealthMeasureHistory.findByPidAndMid", query="SELECT hmh FROM HealthMeasureHistory hmh WHERE hmh.person = ?1 AND hmh.idMeasureHistory = ?2"),
		@NamedQuery(name="HealthMeasureHistory.findMeasureByDate", query="SELECT hmh FROM HealthMeasureHistory hmh WHERE hmh.person = ?1 AND hmh.measureDefinition = ?2 "
		+ "AND hmh.timestamp BETWEEN ?4 AND ?3")
	})
@XmlRootElement(name="measure")
	public class HealthMeasureHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_mhistory")
	@TableGenerator(name="sqlite_mhistory", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="HealthMeasureHistory")
	@Column(name="idMeasureHistory")
	private int idMeasureHistory;

	@Temporal(TemporalType.DATE)
	@Column(name="timestamp")
	private Date timestamp;

	@Column(name="value")
	private String value;

	@ManyToOne
	@JoinColumn(name = "idMeasureDef", referencedColumnName = "idMeasureDef")
	private MeasureDefinition measureDefinition;

	// notice that we haven't included a reference to the history in Person
	// this means that we don't have to make this attribute XmlTransient
	@ManyToOne
	@JoinColumn(name = "idPerson", referencedColumnName = "idPerson")
	private Person person;

	public HealthMeasureHistory() {
	}

	public int getIdMeasureHistory() {
		return this.idMeasureHistory;
	}

	public void setIdMeasureHistory(int idMeasureHistory) {
		this.idMeasureHistory = idMeasureHistory;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@XmlTransient
	public MeasureDefinition getMeasureDefinition() {
	    return measureDefinition;
	}

	public void setMeasureDefinition(MeasureDefinition param) {
	    this.measureDefinition = param;
	}

	@XmlTransient
	public Person getPerson() {
	    return person;
	}

	public void setPerson(Person param) {
	    this.person = param;
	}

	// database operations
	public static HealthMeasureHistory getHealthMeasureHistoryById(int id) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		HealthMeasureHistory p = em.find(HealthMeasureHistory.class, id);
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}

	public static HealthMeasureHistory getHealthMeasureHistoryByPidAndMid(Person pid, int mid) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		System.out.println("SONO QUI");
		TypedQuery<HealthMeasureHistory> query = em.createNamedQuery("HealthMeasureHistory.findByPidAndMid", HealthMeasureHistory.class);
		System.out.println("SONO ANCHE QUI");
		query.setParameter(1, pid);
		query.setParameter(2, mid);
		System.out.println("SONO ANCHE ANCHE QUI");
		List<HealthMeasureHistory> hmh = query.getResultList();
		if(hmh==null){
			System.out.println("SONONULLO");
			return null;
		}
		LifeCoachDao.instance.closeConnections(em);

		return hmh.get(0);
	}

	public static List<HealthMeasureHistory> getByPersonMeasureNameAndPerson(Person person, MeasureDefinition mdef) {
	EntityManager em = LifeCoachDao.instance.createEntityManager();
	TypedQuery<HealthMeasureHistory> query = em.createNamedQuery("HealthMeasureHistory.findByMeasureAndPerson", HealthMeasureHistory.class);
	query.setParameter(1, person);
	query.setParameter(2, mdef);
	List<HealthMeasureHistory> list = query.getResultList();
	LifeCoachDao.instance.closeConnections(em);
	return list;
}

public static List<HealthMeasureHistory> getMeasureByDate(MeasureDefinition mdef, Person p, Calendar from, Calendar to) {
	EntityManager em = LifeCoachDao.instance.createEntityManager();
	TypedQuery<HealthMeasureHistory> query = em.createNamedQuery("HealthMeasureHistory.findMeasureByDate", HealthMeasureHistory.class);
	query.setParameter(1, p);
	query.setParameter(2, mdef);
	query.setParameter(3, from.getTime());
	query.setParameter(4, to.getTime());

	List<HealthMeasureHistory> hmhList = query.getResultList();
		LifeCoachDao.instance.closeConnections(em);
	return hmhList;

}

	public static List<HealthMeasureHistory> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    List<HealthMeasureHistory> list = em.createNamedQuery("HealthMeasureHistory.findAll", HealthMeasureHistory.class).getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}

	public static HealthMeasureHistory saveHealthMeasureHistory(HealthMeasureHistory p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	  LifeCoachDao.instance.closeConnections(em);
	  return p;
	}

	public static HealthMeasureHistory updateHealthMeasureHistory(HealthMeasureHistory p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}


	public static void removeHealthMeasureHistory(HealthMeasureHistory p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	}
}
