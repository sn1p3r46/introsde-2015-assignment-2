package introsde.rest.ehealth.resources;

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
@Path("/measureTypes")
public class MeasureDefinitionResource {
  private List<MeasureDefinition> md;


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

  @GET
  @Produces({MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML})
  public MeasureTypeCollection getMeasureTypes() {
      MeasureTypeCollection list = new MeasureTypeCollection();
    return list;
  }
}
