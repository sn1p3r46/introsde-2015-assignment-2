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
import javax.ws.rs.QueryParam;

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
    public List<Person> getPersonsByMinAndMax(@QueryParam("measureType") String measureName,@QueryParam("max") Double max, @QueryParam("min") Double min) {

        List<Person> personList = new ArrayList<Person>();
        if(measureName == null && min == null && max == null)
        	return Person.getAll();
        else{
        	MeasureDefinition mdef = MeasureDefinition.getMeasureDefinitionByName(measureName);
        	return Person.getByMeasureNameAndMinMax(mdef, min, max);
        }
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
    // checks if person includes life statuses, in other words a 'healthprofile'
    if(person.getLifeStatus() == null){
        return Person.savePerson(person);
    }else{
        //removes the life statuses in the persons and puts them in another variable
        //System.out.println()
        ArrayList<LifeStatus> list_lifeStatus = new ArrayList<>();
        list_lifeStatus.addAll(person.getLifeStatus());
        person.setLifeStatus(null);

        //saves the person in the database and retrieve the id
        Person p = Person.savePerson(person);
        int id_person = p.getIdPerson();

        //creates the today date
        Calendar today = Calendar.getInstance();

        //the use of this list avoid the insertion of the same measure multiple time
        //the list stores progressively the measure already inserted
        ArrayList<Integer> control = new ArrayList<>();

        //iterates on all 'lifestatus' the client wants to insert
        for(int i=0; i<list_lifeStatus.size(); i++){
            //associates the 'lifestatus' with the person
            list_lifeStatus.get(i).setPerson(p);
            HealthMeasureHistory history_element = new HealthMeasureHistory();

            //retrieves the name of the measures inserted by the client (e.g. weight)
            String measureName = list_lifeStatus.get(i).getMeasureDefinition().getMeasureName();

            //searches the measure definition associated with the name of the measure
            MeasureDefinition temp = new MeasureDefinition();
            temp = MeasureDefinition.getMeasureDefinitionByName(measureName);

            if (temp != null && !control.contains(temp.getIdMeasureDef())){
                control.add(temp.getIdMeasureDef());
                //associates the lifestatus with the corresponding measureDefinition
                list_lifeStatus.get(i).setMeasureDefinition(temp);
                //saves the new measure value of the lifestatus also in the history
                history_element.setMeasureDefinition(temp);
                history_element.setPerson(p);
                history_element.setValue(list_lifeStatus.get(i).getValue());
                history_element.setTimestamp(today.getTime());
                LifeStatus.saveLifeStatus(list_lifeStatus.get(i));  //saves lifestatus in the db
                HealthMeasureHistory.saveHealthMeasureHistory(history_element);
            }
        }
        return Person.getPersonById(id_person);
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
