package introsde.rest.client;

import introsde.rest.client.helpers.XmlHelper;

import introsde.rest.client.helpers.XmlHelper;
import introsde.rest.client.helpers.JsonHelper;
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

  static final String appJson = MediaType.APPLICATION_JSON;
	static final String appXml  = MediaType.APPLICATION_XML;
	static String outputFormatPUT = "Request #%d: %s %s Accept: %s Content-type: %s\n"
								 +"\t=> Result: %s\n"
								 +"\t=> HTTP Status: %d\n\n"
								 +"%s\n";

	static String outputFormatGET = "Request #%d: %s %s Accept: %s\n"
								 +"\t=> Result: %s\n"
								 +"\t=> HTTP Status: %d\n\n"
								 +"%s\n";

	static String outputFormatDELETE = "Request #%d: %s %s\n"
								 +"\t=> Result: %s\n"
								 +"\t=> HTTP Status: %d\n\n";

	static String mediaTypeHeader = "\n###################################################################\n"+
									"#####\n"+
									"###   Running tests with Accept and Content-type: %s\n"+
									"#\n";

	static String testHeader = "\n##############\n"+
							   							 "## TEST %s ##\n"+
							   					 		 "##############\n";

	public static final String uriServer = "http://127.0.1.1:5700/sdelab";
  public static final String error_E = "ERROR";
  public static final String oK_ok = "OK";

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
		System.out.println("\n  Client Started.. \n  Server URI: " + uriServer);

		pressAnyKeyToContinue();

		ClientConfig clientConfig = new ClientConfig();
		//Long timeout in case heroku server is sleeping
		clientConfig.property(ClientProperties.CONNECT_TIMEOUT, 60*1000);
		Client client = ClientBuilder.newClient(clientConfig);
		service = client.target(uriServer);

		JAXBContext jaxbContext = JAXBContext.newInstance(PersonList.class);
    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

    //We had written this file in marshalling example
		File f = new File("peopleTest.xml");
		String file_str = FileUtils.readFileToString(f);
    PersonList people = (PersonList) jaxbUnmarshaller.unmarshal( f );
		for (Person p : people.getpeople()) {
		System.out.println(p.getFirstname());
		}

		test3_1(appXml);
		test3_1(appJson);

		return;
	}

	private static void test3_1(String mediaType){
	System.out.println(String.format(testHeader, "3.1"));
	Response r = testGeneric("/person", "GET", mediaType, null);
	PersonList people;
	if(mediaType==appXml){
			people = r.readEntity(new GenericType<PersonList>(){});
	}else{
		List<Person> plist = r.readEntity(new GenericType<List<Person>>(){});
		people = new PersonList();
		people.setpeople(plist);
	}

	String status = (people.getpeople().size()>1)? "OK" : "ERROR";
	printResult(1, "GET", "/person", mediaType, status, r);

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

	private static void pressAnyKeyToContinue(){
         System.out.println("\n \n  Press any key to continue... \n \n");
         try{System.in.read();}
         catch(Exception e){}}

	private static Response testGeneric(String URIPath,  String method, String mediaType, Object requestBody){
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

	if(method.equals("GET")){
		response = builder.get();
	} else if(method.equals("POST")){
		response = builder.post(body);
	} else if(method.equals("PUT")){
		response = builder.put(body);
	} else if(method.equals("DELETE")){
		response = builder.delete();
	} else {
		throw new RuntimeException("Unexpected HTTP method: "+method);
	}

	response.bufferEntity();
	return response;
}

private static void printResult(int reqNumber, String reqMethod, String path, String acceptType, String statusString, Response r){
	int statusCode = r.getStatus();
	String result = r.readEntity(String.class);
	String prettyPrint = "";
	if(result != null && result.length() !=0){
		prettyPrint = (r.getMediaType().toString().equals(MediaType.APPLICATION_XML))? XmlHelper.prettyXML(result, 5): JsonHelper.prettyJSON(result, 5);
		}
	if(reqMethod.equals("GET")){
		System.out.println(String.format(outputFormatGET, reqNumber, reqMethod, path , acceptType, statusString, statusCode, prettyPrint));
	} else if(reqMethod.equals("PUT") || reqMethod.equals("POST")){
		System.out.println(String.format(outputFormatPUT, reqNumber, reqMethod, path , acceptType, r.getMediaType().toString(), statusString, statusCode, prettyPrint));
	} else if(reqMethod.equals("DELETE")){
		System.out.println(String.format(outputFormatDELETE, reqNumber, reqMethod, path, statusString, statusCode));
		}
	}
}
