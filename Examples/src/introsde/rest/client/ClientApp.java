package introsde.rest.client;

import introsde.rest.client.helpers.XmlHelper;

import introsde.rest.client.helpers.XmlHelper;
import introsde.rest.client.helpers.JsonHelper;
import introsde.rest.client.helpers.PrettyStrings;
import introsde.rest.client.helpers.StringHelper;

import introsde.rest.client.schemas.generated.models.person.Person;
import introsde.rest.client.schemas.generated.models.person.PersonList;
import introsde.rest.client.schemas.generated.models.person.PersonObjectFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


  public class ClientApp {


	public static String mediaType = null;

	//private static Client client;
	private static WebTarget service;
	private static ClientConfig clientConfig ;
	private static String first_person_id;
	private static String last_person_id;
	private static ArrayList<String> measure_types=new ArrayList<>();
	private static String measure_id = null;
	private static String measureType = null;
	private static Person firstPerson = null;
	private static Person lastPerson = null;

	private String measure_id_person;


	public static void main(String[] args) throws JAXBException, IOException {
		System.out.println("\n  ASSIGNMENT 2\n  STUDENT Andrea Galloni\n  E-MAIL: andrea[dot]galloni[at]studenti[dot]unitn[dot]it");
		System.out.println("\n  Client Started.. \n  Server URI: " + PrettyStrings.URI_SERVER);

		pressAnyKeyToContinue();

		ClientConfig clientConfig = new ClientConfig();
		clientConfig.property(ClientProperties.CONNECT_TIMEOUT, 60*1000);
		Client client = ClientBuilder.newClient(clientConfig);
		service = client.target(PrettyStrings.URI_SERVER);

		JAXBContext jaxbContext = JAXBContext.newInstance(PersonList.class);
    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();


		test3_1(PrettyStrings.APP_XML);
		test3_1(PrettyStrings.APP_JSON);

		return;
	}

	private static void test3_1(String mediaType){
	System.out.println(String.format(PrettyStrings.HEADER, "3.1"));
	Response r = testMe("/person", PrettyStrings.GET_STRING, mediaType, null);
	PersonList people;

	if(mediaType==PrettyStrings.APP_XML){
			people = r.readEntity(new GenericType<PersonList>(){});
	}else{
		List<Person> plist = r.readEntity(new GenericType<List<Person>>(){});
		people = new PersonList();
		people.setpeople(plist);
	}

	String status = (people.getpeople().size()>1)? PrettyStrings.OK_OK : PrettyStrings.ERROR_E;
	StringHelper.prettyResultPrinter(1, PrettyStrings.GET_STRING, "/person", mediaType, status, r);

	if(people.getpeople().size()>0){
		firstPerson = people.getpeople().get(0);
		lastPerson = people.getpeople().get(people.getpeople().size()-1);
		System.out.println(firstPerson.getFirstname());
		System.out.println(lastPerson.getLastname());
	} else {
		System.err.println("Error nobody is here in my DB...");
		System.exit(0);
	}
}

private static void test3_2(String mediaType){
	System.out.println(String.format(PrettyStrings.HEADER, "3.2"));
	Response r = testMe("/person/"+firstPerson.getIdPerson(), PrettyStrings.GET_STRING, mediaType, null);
	String status = (r.getStatus() == 200 || r.getStatus() == 202)? PrettyStrings.OK_OK : PrettyStrings.ERROR_E;
	StringHelper.prettyResultPrinter(2, PrettyStrings.GET_STRING, "/person/" + firstPerson.getIdPerson(), mediaType, status, r);
}

	private static void pressAnyKeyToContinue(){
   System.out.println("\n \n  Press any key to continue... \n \n");
   try{System.in.read();}
   catch(Exception e){}
	}

	private static Response testMe(String URIPath,  String method, String mediaType, Object requestBody){
	Response response = null;
	Invocation.Builder builder = service.path(URIPath)
			.request()
			.accept(mediaType)
			.header("Content-Type", mediaType);

	Entity<Object> body = null;
	if(requestBody != null){
		if(mediaType.equals(MediaType.APPLICATION_XML)){
			body = Entity.xml(requestBody);
		} else if (mediaType.equals(MediaType.APPLICATION_JSON)){
			body = Entity.json(requestBody);
		}
	}

	if(method.equals(PrettyStrings.GET_STRING)){
		response = builder.get();
	} else if(method.equals("POST")){
		response = builder.post(body);
	} else if(method.equals(PrettyStrings.PUT_STRING)){
		response = builder.put(body);
	} else if(method.equals(PrettyStrings.DELETE_STRING)){
		response = builder.delete();
	} else {
		throw new RuntimeException("Unexpected HTTP method: "+method);
	}

	response.bufferEntity();
	return response;
	}

}
