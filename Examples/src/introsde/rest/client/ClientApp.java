package introsde.rest.client;

import introsde.rest.client.helpers.XmlHelper;

import introsde.rest.client.helpers.XmlHelper;
import introsde.rest.client.helpers.JsonHelper;
import introsde.rest.client.helpers.PrettyStrings;
import introsde.rest.client.helpers.StringHelper;

import introsde.rest.client.schemas.generated.models.person.Person;
import introsde.rest.client.schemas.generated.models.person.PersonList;
import introsde.rest.client.schemas.generated.models.person.PersonObjectFactory;
import introsde.rest.client.schemas.generated.models.SMeasureType;
import introsde.rest.client.schemas.generated.models.MeasureTypeList;
import introsde.rest.client.schemas.generated.models.HealthMeasureHistory;
import introsde.rest.client.schemas.generated.models.HealthMeasure;

import introsde.rest.client.schemas.generated.models.MeasureGet;
import introsde.rest.client.schemas.generated.models.MeasureGetList;
import introsde.rest.client.schemas.generated.models.MeasurePost;
import introsde.rest.client.schemas.generated.models.MeasurePut;

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
import javax.xml.bind.Marshaller;
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
import java.util.Calendar;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;


  public class ClientApp {


	public static String mediaType = null;

	//private static Client client;
	private static WebTarget service;
	private static ClientConfig clientConfig ;
	private static String first_person_id;
	private static String last_person_id;
	private static ArrayList<String> measure_types=new ArrayList<>();
	private static int measure_id = 0;
	private static String measure_Type = null;
	private static Person myPerson = null;
	private static Person firstPerson = null;
	private static Person lastPerson = null;
	private static Person createdPerson = null;
  private static MeasureTypeList msc = null;
	private String measure_id_person;


	public static void main(String[] args) throws JAXBException, IOException {
		System.out.println("\n  ASSIGNMENT 2\n  STUDENT Andrea Galloni\n  E-MAIL: andrea[dot]galloni[at]studenti[dot]unitn[dot]it");
		System.out.println("\n  Client Started.. \n  Server URI: " + PrettyStrings.URI_SERVER);


		ClientConfig clientConfig = new ClientConfig();
		clientConfig.property(ClientProperties.CONNECT_TIMEOUT, 60*1000);
		Client client = ClientBuilder.newClient(clientConfig);
		service = client.target(PrettyStrings.URI_SERVER);

		if(args[0].equals("xml")){

			pressAnyKeyToContinue();
			test3_1(PrettyStrings.APP_XML);
			pressAnyKeyToContinue();
			test3_2(PrettyStrings.APP_XML);
			pressAnyKeyToContinue();
			test3_3(PrettyStrings.APP_XML);
			pressAnyKeyToContinue();
			test3_4(PrettyStrings.APP_XML);
			pressAnyKeyToContinue();
			test3_5(PrettyStrings.APP_XML);
			pressAnyKeyToContinue();
			test3_6(PrettyStrings.APP_XML);
			pressAnyKeyToContinue();
			test3_7(PrettyStrings.APP_XML);
			pressAnyKeyToContinue();
			test3_8(PrettyStrings.APP_XML);
			pressAnyKeyToContinue();
			test3_9(PrettyStrings.APP_XML);
			pressAnyKeyToContinue();
			test3_10(PrettyStrings.APP_XML);
			pressAnyKeyToContinue();
			test3_11(PrettyStrings.APP_XML);
			pressAnyKeyToContinue();
			test3_12(PrettyStrings.APP_XML);

		}else{

			pressAnyKeyToContinue();
			test3_1(PrettyStrings.APP_JSON);
			pressAnyKeyToContinue();
			test3_2(PrettyStrings.APP_JSON);
			pressAnyKeyToContinue();
			test3_3(PrettyStrings.APP_JSON);
			pressAnyKeyToContinue();
			test3_4(PrettyStrings.APP_JSON);
			pressAnyKeyToContinue();
			test3_5(PrettyStrings.APP_JSON);
			pressAnyKeyToContinue();
			test3_6(PrettyStrings.APP_JSON);
			pressAnyKeyToContinue();
			test3_7(PrettyStrings.APP_JSON);
			pressAnyKeyToContinue();
			test3_8(PrettyStrings.APP_JSON);
			pressAnyKeyToContinue();
			test3_9(PrettyStrings.APP_JSON);
			pressAnyKeyToContinue();
			test3_10(PrettyStrings.APP_JSON);
			pressAnyKeyToContinue();
			test3_11(PrettyStrings.APP_JSON);
			pressAnyKeyToContinue();
			test3_12(PrettyStrings.APP_JSON);

		}


		System.out.println("Client Test Completed!");
		pressAnyKeyToExit();
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
		//System.out.println(firstPerson.getFirstname());
		//System.out.println(lastPerson.getLastname());
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

private static void test3_3(String mediaType){
	// Little trick to make it work always without writing two times the same function
	// And without changing parameters.. ;)
	String newName = "Jooooooooohn";
	System.out.println(String.format(PrettyStrings.HEADER, "3.3"));
	Response r = testMe("/person/"+firstPerson.getIdPerson(), PrettyStrings.PUT_STRING, mediaType, firstPerson);
	Person changedNamePerson = r.readEntity(Person.class);
	if(changedNamePerson==null){System.out.println("SonoNullo");}
	newName = (changedNamePerson.getFirstname().equals(newName))? "NAAAAAAASH" : newName;
	firstPerson.setFirstname(newName);
	firstPerson.setHealtProfile(null);
	r = testMe("/person/"+firstPerson.getIdPerson(), PrettyStrings.PUT_STRING, mediaType, firstPerson);
	changedNamePerson = r.readEntity(Person.class);
	String status = (changedNamePerson.getFirstname().equals(newName))? PrettyStrings.OK_OK : PrettyStrings.ERROR_E;
	StringHelper.prettyResultPrinter(3, PrettyStrings.PUT_STRING, "/person/"+firstPerson.getIdPerson(), mediaType, status, r);
}

private static void test3_4(String mediaType){
	System.out.println(String.format(PrettyStrings.HEADER, "3.4"));

	Person p = new Person();
	p.setFirstname("Chuck");
	p.setLastname("Norris");
	Calendar cal = Calendar.getInstance();
	cal.set(Calendar.YEAR, 1945);
	cal.set(Calendar.MONTH, Calendar.JANUARY);
	cal.set(Calendar.DAY_OF_MONTH, 1);
	p.setBirthdate(cal.getTime());
	p.createHealthProfile();
	p.addMeasure("weight", "78.9");
	p.addMeasure("height", "172");

	Response r = testMe("/person", PrettyStrings.POST_STRING, mediaType, p);

	Person returnedPerson = r.readEntity(Person.class);
	String status = (returnedPerson.getIdPerson() != 0 && (r.getStatus() == 200 || r.getStatus() == 201 || r.getStatus() == 202))? PrettyStrings.OK_OK : PrettyStrings.ERROR_E;

	StringHelper.prettyResultPrinter(4, PrettyStrings.POST_STRING, "/person", PrettyStrings.APP_XML, status, r);

	createdPerson = returnedPerson;

}

private static void test3_5(String mediaType){
	System.out.println(String.format(PrettyStrings.HEADER, "3.5"));

	Response r = testMe("/person/"+createdPerson.getIdPerson(), PrettyStrings.DELETE_STRING, mediaType, null);
	String status = (r.getStatus() == 204)? PrettyStrings.OK_OK : PrettyStrings.ERROR_E;
	StringHelper.prettyResultPrinter(5, PrettyStrings.DELETE_STRING, "/person/"+createdPerson.getIdPerson(), mediaType, status, r);

	r = testMe("/person/"+createdPerson.getIdPerson(), PrettyStrings.GET_STRING, mediaType, null);
	status = (r.getStatus() == 404)? PrettyStrings.OK_OK : PrettyStrings.ERROR_E;
	if (r.getStatus()==404){
		System.out.println("Request #5: GET /person/"+createdPerson.getIdPerson()+"\n\t=> Result: OK\n"+"\t=> HTTP Status: 404\n");
		System.out.println("THE PERSON HAVE BEEN DELETED CORRECTLY");
	}
	//StringHelper.prettyResultPrinter(2, PrettyStrings.GET_STRING, "/person/"+createdPerson.getIdPerson(), mediaType, status, r);
}

private static void test3_6(String mediaType){
	System.out.println(String.format(PrettyStrings.HEADER, "3.6"));

	Response r = testMe("/measureTypes", PrettyStrings.GET_STRING, mediaType, null);

	MeasureTypeList m = null;
	//if(mediaType.equals(MediaType.APPLICATION_XML)){
	m = r.readEntity(MeasureTypeList.class);
	//} else if (mediaType.equals(MediaType.APPLICATION_JSON)){
	//	MeasureTypeList li = r.readEntity(MeasureTypeList.class);
	//	m = new MeasureTypeList();
	//		m.setMeasureTypes(new ArrayList<String>());
	//	for(SMeasureType sMT: li ){
	//		m.getMeasureTypes().add(sMT.value);
	//	}
	//	}

	String status = (m.getMeasureTypes().size()>2)? PrettyStrings.OK_OK : PrettyStrings.ERROR_E;
	StringHelper.prettyResultPrinter(9, PrettyStrings.GET_STRING, "/measureTypes", mediaType, status, r);

	msc = m;
}
private static void test3_7(String mediaType){
	System.out.println(String.format(PrettyStrings.HEADER, "3.7"));
	String status = PrettyStrings.ERROR_E;

	List<Person> pList = new ArrayList<Person>();
	pList.add(firstPerson);
	pList.add(lastPerson);
	HealthMeasureHistory m = null;
	if(mediaType.equals(PrettyStrings.APP_JSON)){
		m = new HealthMeasureHistory();
	}
	Response r = null;
	for(Person pIterator:pList){
		for(String mName:msc.getMeasureTypes()){
			r = testMe("/person/"+pIterator.getIdPerson()+"/"+mName, PrettyStrings.GET_STRING, mediaType, null);
			if(mediaType.equals(PrettyStrings.APP_XML)){
			m = r.readEntity(HealthMeasureHistory.class);
		} else{
			List<HealthMeasure> measure = r.readEntity(new GenericType<List<HealthMeasure>>(){});
			m.setHealthMeasure(measure);
		}
			if(m!=null && m.getHealthMeasure().size()>0){
				status = PrettyStrings.OK_OK;
				ClientApp.measure_id = m.getHealthMeasure().get(0).getIdMeasureHistory();
				ClientApp.measure_Type = mName;
				ClientApp.myPerson = pIterator;
			}
		}
	}
StringHelper.prettyResultPrinter(9, PrettyStrings.GET_STRING, "/person/"+ClientApp.myPerson.getIdPerson()+"/"+ClientApp.measure_Type, mediaType, status, r);
}

private static void test3_8(String mediaType){
	System.out.println(String.format(PrettyStrings.HEADER, "3.8"));
	Response r = testMe("/person/" + ClientApp.myPerson.getIdPerson()+"/" + ClientApp.measure_Type + "/" + ClientApp.measure_id, PrettyStrings.GET_STRING, mediaType, null);
	String status = (r.getStatus() == 200) ? PrettyStrings.OK_OK : PrettyStrings.ERROR_E;
	StringHelper.prettyResultPrinter(7, PrettyStrings.GET_STRING, "/person/" + ClientApp.myPerson.getIdPerson()+"/" + ClientApp.measure_Type + "/" + ClientApp.measure_id, mediaType, status, r);
}


private static void test3_10(String mediaType){
	System.out.println(String.format(PrettyStrings.HEADER, "E10"));
	System.out.println("\n\n NOW WE ARE GOING TO UPDATE SOME VALUES\n\n EXTRA POINTS 10\n");
	Response r = testMe("/person/" + ClientApp.myPerson.getIdPerson()+"/" + ClientApp.measure_Type + "/" + ClientApp.measure_id, PrettyStrings.GET_STRING, mediaType, null);
	String status = (r.getStatus() == 200) ? PrettyStrings.OK_OK : PrettyStrings.ERROR_E;

	MeasureGet m;
	m = r.readEntity(MeasureGet.class);
	String old_val = m.getValue();

	StringHelper.prettyResultPrinter(7, PrettyStrings.GET_STRING, "/person/" + ClientApp.myPerson.getIdPerson()+"/" + ClientApp.measure_Type + "/" + ClientApp.measure_id, mediaType, status, r);
	System.out.println("\n\n NOW WE ARE GOING TO SEND THE PUT REQUEST \n\n");

	Calendar cal = Calendar.getInstance();
	cal.set(Calendar.YEAR, 1945);
	cal.set(Calendar.MONTH, Calendar.JANUARY);
	cal.set(Calendar.DAY_OF_MONTH, 1);


	Object mp;
	if(mediaType.equals(PrettyStrings.APP_JSON)){
	mp = "{\"value\":\""+ StringHelper.randIntString(1,1000)+"\"}";
	//System.out.println(mp.toString());
} else{
	mp = new MeasurePut(StringHelper.randIntString(1,1000),cal.getTime());
}

	r = testMe("/person/" + ClientApp.myPerson.getIdPerson()+"/" + ClientApp.measure_Type + "/" + ClientApp.measure_id, PrettyStrings.PUT_STRING, mediaType, mp);


	m = r.readEntity(MeasureGet.class);

	if(m==null)
		System.out.println("\n\n\n BENE R NULLO \n\n\n");

	String new_val = m.getValue();
	StringHelper.prettyResultPrinter(10, PrettyStrings.PUT_STRING, "/person/"+firstPerson.getIdPerson(), mediaType, status, r);

	System.out.println("\n\nTHE MEASURE VALUE IS CHANGED FROM "+ old_val +" TO "+ new_val + "\n");
}


private static void test3_11(String mediaType){

	System.out.println(String.format(PrettyStrings.HEADER, "E11"));

	System.out.println("\n\n NOW WE ARE GOING TO FILTER BY DATE A VALUE: BMI \n\n EXTRA POINTS 11\n");
	System.out.println("\n\n FIRST WE PRINT ALL HISTORY OF BMI MEASUREMENTS:\n");

	Response r = testMe("/person/10000/bmi", PrettyStrings.GET_STRING, mediaType, null);
	StringHelper.prettyResultPrinter(11, PrettyStrings.GET_STRING, "/person/10000/bmi", mediaType, PrettyStrings.OK_OK, r);

	System.out.println("\n\n THEN WE PRINT FILTERED RESULTS BY DATE (SEE URL):\n");

	ClientConfig clientConfig1 = new ClientConfig();
	Client client = ClientBuilder.newClient(clientConfig1);
	URI uri = UriBuilder.fromUri(PrettyStrings.URI_SERVER).build();
	service = client.target(uri);
	Response response = service.path("/person/10000/bmi")
			.queryParam("before", "2015-11-11").queryParam("after", "2011-12-08")
			.request(mediaType).get(Response.class);

	StringHelper.prettyResultPrinter(11, PrettyStrings.GET_STRING, "/person/10000/bmi?after=2011-12-08&before=2015-11-11", mediaType, PrettyStrings.OK_OK, response);

}

private static void test3_12(String mediaType){

	System.out.println(String.format(PrettyStrings.HEADER, "E12"));

	System.out.println("\n\n NOW WE ARE GOING TO FILTER PEOPLE BY THE VALUE (MIN MAX) OF A GIVEN MEASURE \n\n EXTRA POINTS 12\n");
	System.out.println("\n\n FIRST WE PRINT ALL HISTORY OF BMI MEASUREMENTS:\n");

	Response r = testMe("/person", PrettyStrings.GET_STRING, mediaType, null);
	StringHelper.prettyResultPrinter(11, PrettyStrings.GET_STRING, "/person/10000/bmi", mediaType, PrettyStrings.OK_OK, r);

	System.out.println("\n\n THEN WE PRINT FILTERED RESULTS BY VALUES (SEE URL):\n");

	ClientConfig clientConfig1 = new ClientConfig();
	Client client = ClientBuilder.newClient(clientConfig1);
	URI uri = UriBuilder.fromUri(PrettyStrings.URI_SERVER).build();
	service = client.target(uri);
	Response response = service.path("/person")
			.queryParam("measureType", "bmi")
			.queryParam("min", "72").queryParam("max", "74")
			.request(mediaType).get(Response.class);

	StringHelper.prettyResultPrinter(11, PrettyStrings.GET_STRING, "/person/10000/bmi?after=2011-12-08&before=2015-11-11", mediaType, PrettyStrings.OK_OK, response);

}

 private static void pressAnyKeyToContinue(){
   System.out.println("\n \n  Press any key to continue... \n \n");
   try{System.in.read();}
   catch(Exception e){}
	}

	private static void pressAnyKeyToExit(){
    System.out.println("\n \n  Press any key to exit... \n \n");
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

	private static void test3_9(String mediaType) throws JAXBException, JsonProcessingException {
		System.out.println(String.format(PrettyStrings.HEADER, "3.9"));
		String status;
		MeasureGetList mgl = new MeasureGetList();
		MeasureGet mg1 = new MeasureGet(null, "height", "172", null);	//Calendar.getInstance().getTime());
		MeasureGet mg2 = new MeasureGet(null, "weight", "72", null);		//Calendar.getInstance().getTime());

		Response r = testMe("/person/" + ClientApp.firstPerson.getIdPerson()+"/" + ClientApp.measure_Type, PrettyStrings.GET_STRING, mediaType, null);
		MeasureGetList m;
		if(mediaType.equals(PrettyStrings.APP_XML)){
		m = r.readEntity(MeasureGetList.class);
	} else{
		List<MeasureGet> measure = r.readEntity(new GenericType<List<MeasureGet>>(){});
		m = new MeasureGetList(measure);
	}
		int oldCount = m.getMeasureList().size();
		status = (r.getStatus() == 200 || r.getStatus() == 201 || r.getStatus() == 202)? PrettyStrings.OK_OK : PrettyStrings.ERROR_E;
		System.out.println("Request #6: GET /person/" + ClientApp.firstPerson.getIdPerson() + "/" + ClientApp.measure_Type + "/"
			+ " Accept: " + mediaType
			+ "\n\t=> Result: " + status +"\n"
			+ "\t=> HTTP Status: 200\n" + "\n\nTHE NUMBER OF MEASUREMENTS IS: " + oldCount);
			System.out.println("\n\nNOW I AM GOING TO MAKE THE POST REQUEST\n\n");

			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, 2013);
			cal.set(Calendar.MONTH, Calendar.JANUARY);
			cal.set(Calendar.DAY_OF_MONTH, 1);

			MeasurePost mp = new MeasurePost("72",cal.getTime());
			java.io.StringWriter sw = new StringWriter();
			JAXBContext jaxbContext1 = JAXBContext.newInstance(MeasurePost.class);
			Marshaller jaxbMarshaller1 = jaxbContext1.createMarshaller();
			jaxbMarshaller1.marshal(mp, sw);

			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(mp);

			String body;
			if(mediaType.equals(MediaType.APPLICATION_XML)){
				 body = sw.toString();
				 System.out.println(XmlHelper.prettyXML(body,1));
			} else {
				 body =	jsonInString;
				 System.out.println(JsonHelper.prettyJSON(body,1));
			}
			Response postR = testMe("/person/" + ClientApp.firstPerson.getIdPerson()+"/" + ClientApp.measure_Type, PrettyStrings.POST_STRING, mediaType, body);
			status = (postR.getStatus() == 200 || postR.getStatus() == 201 || postR.getStatus() == 202)? PrettyStrings.OK_OK : PrettyStrings.ERROR_E;
			StringHelper.prettyResultPrinter(9, PrettyStrings.POST_STRING, "/person/" + ClientApp.firstPerson.getIdPerson()+"/" + ClientApp.measure_Type, mediaType, status, postR);
			System.out.println("\n\nNOW I AM GOING TO MAKE AGAIN THE GET REQUEST\n");
			r = testMe("/person/" + ClientApp.firstPerson.getIdPerson()+"/" + ClientApp.measure_Type, PrettyStrings.GET_STRING, mediaType, null);
			if(mediaType.equals(PrettyStrings.APP_XML)){
			m = r.readEntity(MeasureGetList.class);
		} else{
			List<MeasureGet> measure = r.readEntity(new GenericType<List<MeasureGet>>(){});
			m = new MeasureGetList(measure);
		}

			status = (r.getStatus() == 200 || r.getStatus() == 201 || r.getStatus() == 202)? PrettyStrings.OK_OK : PrettyStrings.ERROR_E;
			System.out.println("Request #6: GET /person/" + ClientApp.myPerson.getIdPerson()+"/" + ClientApp.measure_Type + "/"
				+" Accept: " + mediaType
				+"\n\t=> Result: " + status +"\n"
				+"\t=> HTTP Status: 200\n");


			if(mediaType.equals(PrettyStrings.APP_XML)){
				m = r.readEntity(MeasureGetList.class);
			} else{
				List<MeasureGet> measure = r.readEntity(new GenericType<List<MeasureGet>>(){});
				m = new MeasureGetList(measure);
			}
			int newCount = m.getMeasureList().size();
			System.out.println("\n\nTHE NEW NUMBER OF MEASUREMENTS IS: "+ newCount +"\n\n");
			status = (oldCount<newCount)? PrettyStrings.OK_OK : PrettyStrings.ERROR_E;
	}

}
