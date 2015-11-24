package introsde.rest.ehealth.resources;

import introsde.rest.ehealth.model.Person;
import introsde.rest.ehealth.model.HealthMeasureHistory;
import introsde.rest.ehealth.model.MeasureDefinition;
import java.util.*;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PathParam;
import javax.ws.rs.Path;


@Stateless // only used if the the application is deployed in a Java EE container
@LocalBean // only used if the the application is deployed in a Java EE container
public class PersonResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    int id;

    EntityManager entityManager; // only used if the application is deployed in a Java EE container

    public PersonResource(UriInfo uriInfo, Request request,int id, EntityManager em) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
        this.entityManager = em;
    }

    public PersonResource(UriInfo uriInfo, Request request,int id) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
    }

    // Application integration
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Person getPerson() {
        Person person = this.getPersonById(id);
        // if person is null the response code will be 404 Not Found
        // according to http://stackoverflow.com/a/20924573
        // and RFC 3986, section 3.4: http://tools.ietf.org/html/rfc3986#section-3.4
        if (person == null){
            Response res = Response.status(404).entity("404 Not Found").build();
            throw new NotFoundException("Get: Person with " + id + " not found",res);
            //return Response.status(404).entity(yourMessage).type( getAcceptType()).build();
        }
        return person;
    }

    // for the browser
    @GET
    @Produces(MediaType.TEXT_XML)
    public Person getPersonHTML() {
        Person person = this.getPersonById(id);
        if (person == null){
            Response res = Response.status(404).build();
            throw new NotFoundException("Get: Person with " + id + " not found",res);
            //return Response.status(404).entity(yourMessage).type( getAcceptType()).build();
        }
        System.out.println("Returning person... " + person.getIdPerson());
        return person;
    }

    @PUT
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response putPerson(Person person) {
        Response res;
        Person existing = getPersonById(this.id);

        // if the person is not in the db it will create a new one with the
        // given ID
        if (existing == null) {
            System.out.println("--> The given ID is not in our DB " + id);
            System.out.println("--> A new person will be created with this ID: " + id);
            // we set the ID that the client provided from the URI
            person.setIdPerson(id);
            // force the life status to null to be consistent with the DB
            person.setLifeStatus(null);
            //Maybe also Person.savePerson(person) could be ok?
            Person.savePerson(person);
            // actually i've putted the response to "created" because a new entity is just created
            // in the existing case the response will be a generic OK response
            res = Response.created(uriInfo.getAbsolutePath()).build();
            // in my opinion this was not very correct: res = Response.noContent().build();
        } else {
            System.out.println("--> Updating Person... " +this.id);
            System.out.println("--> "+person.toString());
            person.setIdPerson(this.id);
            person.setLifeStatus(existing.getLifeStatus());

            // We handle the case that we would like to change only some
            // properties, so we check if some "fields" are null, in a positive
            // case the old properties will be setted up to the "new" person object

            if(person.getName()==null){
                person.setName(existing.getName());
            }
            if(person.getLastname()==null){
                person.setLastname(existing.getLastname());
            }
            if(person.getBirthdate()==null){
                person.setBirthdate(existing.getBirthdate());
            }
            Person.updatePerson(person);
            // We create an "ok" response because the request have been well accomplished
            res = Response.ok().build();
        }
        return res;
    }


    @DELETE
    public Response deletePerson() {
        Person person = getPersonById(id);
        if (person != null){
            Person.removePerson(person);
        }
        return Response.ok().build();
    }

	public Person getPersonById(int personId) {
		System.out.println("Reading person from DB with id: "+personId);
		//Person person = entityManager.find(Person.class, personId);

		Person person = Person.getPersonById(personId);
		//I've added this if in order to not break things if the given ID is not in the DB
		if (person!=null){
	   		System.out.println("Person: "+person.toString());
		}
		return person;
	}

    @GET
    @Path("{measureType}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<HealthMeasureHistory> getMeasureType(@PathParam("measureType") String measureName){

    	//searches the measure definition associated with the name of the measure
    	MeasureDefinition mdef = new MeasureDefinition();
    	mdef = MeasureDefinition.getMeasureDefinitionByName(measureName);
    	Person person = this.getPersonById(id);
    	List<HealthMeasureHistory> hmh = new ArrayList<HealthMeasureHistory>();
		hmh = HealthMeasureHistory.getByPersonMeasureNameAndPerson(person, mdef);
    	if (hmh == null)
    		throw new RuntimeException("Get: History for person " + id + " not found");
    	return hmh;
    }

    @GET
    @Path("{measureType}/{mid}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public HealthMeasureHistory getMeasureTypePidAndMid(@PathParam("measureType") String measureName, @PathParam("mid") String mid){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
        Person p = Person.getPersonById(this.id);
        HealthMeasureHistory hmh  = HealthMeasureHistory.getHealthMeasureHistoryByPidAndMid(p,Integer.parseInt(mid));
        System.out.println("\n\n\n\n\nDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD\n\n\n\n\n\n");
        if (hmh == null)
            throw new RuntimeException("Get: History for person " + id + " not found");
        return hmh;
    }
}
