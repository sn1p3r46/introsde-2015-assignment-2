package introsde.rest.client;

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

public class ClientApp {

	public static final String uriServer = "http://127.0.1.1:5700/sdelab";
  public static final String error_E = "ERROR";
  public static final String OK_ok = "OK";

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

	public void TestClient(){
		clientConfig = new ClientConfig();
		client = ClientBuilder.newClient(clientConfig);
		service = client.target(uriServer);
	}

	public void reloadUri(){
		service = null;
		service = client.target(uriServer);
	}

public static void main(String[] args) {

  if (args.length < 2)
    System.out.println("Error: insert {myServer, partnerServer} and {xml, json}");
  else{
    //sets the media type (XML or JSON)
    if(args[0].equals("JSON"))
      mediaType = MediaType.APPLICATION_JSON;
    else
      mediaType = MediaType.APPLICATION_XML;

    System.out.println("Server URL : " + uriServer);
    System.out.println("MediaType  : " + mediaType);

    try {
      ClientApp clientApp = new ClientApp();

      /*
      jerseyClient.getPeople(); //Step 3.1
      //starts the client
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
      */
    } catch (Exception e) {
      e.printStackTrace();
      }
    }
  }
}
