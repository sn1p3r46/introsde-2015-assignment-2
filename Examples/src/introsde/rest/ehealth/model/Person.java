package introsde.rest.ehealth.model;

import introsde.rest.ehealth.dao.LifeCoachDao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Locale;


import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity  // indicates that this class is an entity to persist in DB
@Table(name="Person") // to whole table must be persisted
@NamedQueries({
	@NamedQuery(name="Person.findAll", query="SELECT p FROM  Person p"),
    @NamedQuery(name="Person.findByMeasureNameAndMinMax",
				query="SELECT p FROM Person p INNER JOIN p.lifeStatus l WHERE l.measureDefinition = ?1 AND CAST(l.value NUMERIC(10,2)) BETWEEN ?2 AND ?3")
})

@XmlRootElement
@XmlType(propOrder={"idPerson", "name", "lastname" , "birthdate", "lifeStatus"})
@JsonPropertyOrder({ "idPerson", "firstname", "lastname" , "birthdate", "lifeStatus"})


public class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id // defines this attributed as the one that identifies the entity
    @GeneratedValue(generator="sqlite_person")
    @TableGenerator(name="sqlite_person", table="sqlite_sequence",
        pkColumnName="name", valueColumnName="seq",
        pkColumnValue="Person")
    @Column(name="idPerson")
    private int idPerson;
    @Column(name="lastname")
    private String lastname;

    @Column(name="name")
    private String name;
    //@Column(name="username")
    //private String username;
    @Temporal(TemporalType.DATE) // defines the precision of the date attribute
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @Column(name="birthdate")
    private Date birthdate;
    //@Column(name="email")
    //private String email;

    // mappedBy must be equal to the name of the attribute in LifeStatus that maps this relation
    @OneToMany(mappedBy="person",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private List<LifeStatus> lifeStatus;

    @XmlElementWrapper(name = "healthprofile")
    @XmlElement(name="measureType")
    @JsonProperty("healthprofile")
    public List<LifeStatus> getLifeStatus() {
        return lifeStatus;
    }
    // add below all the getters and setters of all the private attributes

    // getters
    //@XmlTransient
    public int getIdPerson(){
        return idPerson;
    }

    public String getLastname(){
        return lastname;
    }

    @XmlElement(name="firstname")
    public String getName(){
        return name;
    }
    /*public String getUsername(){
        return username;
    }*/

    public Date getBirthdate(){
    return birthdate;
}
/*    public String getBirthdate(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        // Get the date today using Calendar object.
        return df.format(birthdate);
    }

    public String getEmail(){
        return email;
    }
*/
    // setters
    public void setLifeStatus(List<LifeStatus> lifeStatus){
        this.lifeStatus = lifeStatus;
    }

    public void setIdPerson(int idPerson){
        this.idPerson = idPerson;
    }
    public void setLastname(String lastname){
        this.lastname = lastname;
    }
    public void setName(String name){
        this.name = name;
    }
    /*
    public void setUsername(String username){
        this.username = username;
    }*/
    public void setBirthdate(Date birthdate){
        this.birthdate = birthdate;
    }
/*    public void setEmail(String email){
        this.email = email;
    }
*/
    public static Person getPersonById(int personId) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        Person p = em.find(Person.class, personId);
        LifeCoachDao.instance.closeConnections(em);
        if (p==null){
            System.out.println("The given ID is NOT in our database" + personId);
        }
        return p;
    }

    public static List<Person> getAll() {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        List<Person> list = em.createNamedQuery("Person.findAll", Person.class)
            .getResultList();
        LifeCoachDao.instance.closeConnections(em);
        return list;
    }

    public static Person savePerson(Person p) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
        return p;
    }

    public static Person updatePerson(Person p) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
        return p;
    }

    public static void removePerson(Person p) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        em.remove(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
    }

    public static List<Person> getByMeasureNameAndMinMax(MeasureDefinition md, Double min, Double max) {
    EntityManager em = LifeCoachDao.instance.createEntityManager();
    em.getEntityManagerFactory().getCache().evictAll();
    TypedQuery<Person> query = em.createNamedQuery("Person.findByMeasureNameAndMinMax", Person.class);
    query.setParameter(1, md);
    query.setParameter(2, min);
    query.setParameter(3, max);

    List<Person> list = query.getResultList();
    LifeCoachDao.instance.closeConnections(em);
    return list;
}

}
