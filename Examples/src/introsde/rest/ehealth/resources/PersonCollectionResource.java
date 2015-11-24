package introsde.rest.ehealth.resources;
import introsde.rest.ehealth.model.Person;
import introsde.rest.ehealth.dao.LifeCoachDao;
import introsde.rest.ehealth.model.LifeStatus;
import introsde.rest.ehealth.model.HealthMeasureHistory;
import introsde.rest.ehealth.model.MeasureDefinition;

import java.io.IOException;
import java.util.List;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.UriBuilder;
import java.util.ArrayList;
import java.util.Calendar;

@Stateless // will work only inside a Java EE application
@LocalBean // will work only inside a Java EE application
@Path("/person")
public class PersonCollectionResource {

    // Allows to insert contextual objects into the class,
    // e.g. ServletContext, Request, Response, UriInfo
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    // will work only inside a Java EE application
    @PersistenceUnit(unitName="introsde-jpa")
    EntityManager entityManager;

    // will work only inside a Java EE application
    @PersistenceContext(unitName = "introsde-jpa",type=PersistenceContextType.TRANSACTION)
    private EntityManagerFactory entityManagerFactory;

    // Return the list of people to the user in the browser


    @GET
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    public List<Person> getPersons() {
        System.out.println("Getting list of people...");
        List<Person> people = Person.getAll();
        return people;
}

    // retuns the number of people
    // to get the total number of records
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCount() {
        System.out.println("Getting count...");
        List<Person> people = Person.getAll();
        int count = people.size();
        return String.valueOf(count);
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML})
    public Person newPerson(Person person) throws IOException {
        System.out.println("Creating new person...");
        //List<LifeStatus> lf = person.getLifeStatus();
        if(person.getLifeStatus() == null){
    		return Person.savePerson(person);
    	}else{
    		//removes the life statuses in the persons and puts them in another variable
            List<LifeStatus> lfCopy = new ArrayList<LifeStatus>();
            for(int i=0; i<person.getLifeStatus().size(); i++){
                lfCopy.add(person.getLifeStatus().get(i));
            }
    		person.setLifeStatus(null);

    		Person p = Person.savePerson(person);
    		int pid = p.getIdPerson();

    		Calendar today = Calendar.getInstance();

    		for(int i=0; i<lfCopy.size(); i++){
    			lfCopy.get(i).setPerson(p);
    			HealthMeasureHistory story = new HealthMeasureHistory();

    			String measureName = lfCopy.get(i).getMeasureDefinition().getMeasureName();
    			MeasureDefinition existingDefinition = new MeasureDefinition();
    			existingDefinition = MeasureDefinition.getMeasureDefinitionByName(measureName);

    			if (existingDefinition != null){
    				lfCopy.get(i).setMeasureDefinition(existingDefinition);
    				story.setMeasureDefinition(existingDefinition);
    				story.setPerson(p);
    				story.setValue(lfCopy.get(i).getValue());
    				story.setTimestamp(today.getTime());
    				LifeStatus.saveLifeStatus(lfCopy.get(i));  //saves lifestatus in the db
    				HealthMeasureHistory.saveHealthMeasureHistory(story);
    			}
    		}
    		Person per = Person.getPersonById(pid);
            return per;
    	}
    }


    // Defines that the next path parameter after the base url is
    // treated as a parameter and passed to the PersonResources
    // Allows to type http://localhost:599/base_url/1
    // 1 will be treaded as parameter todo and passed to PersonResource
    @Path("{personId}")
    public PersonResource getPerson(@PathParam("personId") int id) {
        System.out.println("PersonResource is being called");
        return new PersonResource(uriInfo, request, id);
    }
}
