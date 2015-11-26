/*package client;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.client.ClientConfig;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class TestClient {

	public static final String uriServer = "introsde-assignment-2-galloni.herokuapp.com/sdelab";
	public static String mediaType = null;

	private Client client = null;
	private WebTarget service = null;
	private ClientConfig clientConfig = null;

	private String first_person_id = null;
	private String last_person_id = null;
	private ArrayList<String> measure_types=new ArrayList<>();
	private String measure_id = null;
	private String measureType = null;

	private String measure_id_person;

	public TestClient(){
		clientConfig = new ClientConfig();
		client = ClientBuilder.newClient(clientConfig);
		service = client.target(getBaseURI(uriServer));
	}

	public void reloadUri(){
		service = null;
		service = client.target(getBaseURI(uriServer));
	}


public static void main(String[] args) {

    uriServer = "https://enigmatic-sierra-2066.herokuapp.com/sdelab";


    //sets the media type (XML or JSON)
    if(args[1].equals("JSON"))
      mediaType = MediaType.APPLICATION_JSON;
    else
      mediaType = MediaType.APPLICATION_XML;

    System.out.println("Server URL : " + uriServer);
    System.out.println("MediaType  : " + mediaType);

    try {
      //starts the client
      TestClient jerseyClient = new TestClient();
      jerseyClient.getPeople(); //Step 3.1
      jerseyClient.getPerson(); //Step 3.2
      jerseyClient.putPerson(); //Step 3.3
      //post person returns the id of the new person
      String person_id = jerseyClient.postPerson(); //Step 3.4
      jerseyClient.deletePerson(person_id); //Step 3.5
      jerseyClient.getMeasureTypes(); //Step 3.6
      jerseyClient.getPersonHistoryByMeasureType(); // Step 3.7
      jerseyClient.getMeasureHistoryById(); // Step 3.8
      jerseyClient.postMeasureValue(); //Step 3.9
      jerseyClient.putHealthHistory(); //Step 3.10
      jerseyClient.getPersonHistoryByDate(); //Step 3.11
      jerseyClient.getPersonHistoryByValue(); //Step 3.12

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}*/
