package introsde.rest.client.helpers;

import introsde.rest.client.helpers.XmlHelper;
import introsde.rest.client.helpers.JsonHelper;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StringHelper {

    public String formatXml(String unformattedXml) {
    	Transformer transformer;
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			transformer = transformerFactory.newTransformer();

		} catch (TransformerConfigurationException | TransformerFactoryConfigurationError e1) {
			return unformattedXml;
		}

    	transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    	transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
    	transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

    	StreamResult result = new StreamResult(new StringWriter());
    	StreamSource source = new StreamSource(new StringReader(unformattedXml));

    	try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			return unformattedXml;
		}

    	return result.getWriter().toString();
    }

    public String formatJson(String unformattedJson) {
    	ObjectMapper mapper = new ObjectMapper();
    	Object json = new Object();

    	try {
			json = mapper.readValue(unformattedJson, Object.class);
		} catch (IOException e) {
			return unformattedJson;
		}

    	try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
		} catch (JsonProcessingException e) {
			return unformattedJson;
		}
    }

    public static void prettyResultPrinter(int reqNumber, String reqMethod, String path, String acceptType, String statusString, Response r){
    	int statusCode = r.getStatus();
    	String result = r.readEntity(String.class);
    	String prettyPrint = "";
    	if(result != null && result.length() !=0){
    		prettyPrint = (r.getMediaType().toString().equals(MediaType.APPLICATION_XML))? XmlHelper.prettyXML(result, 5): JsonHelper.prettyJSON(result, 5);
    		}
    	if(reqMethod.equals(PrettyStrings.GET_STRING)){
    		System.out.println(String.format(PrettyStrings.GET_OUTPUT, reqNumber, reqMethod, path , acceptType, statusString, statusCode, prettyPrint));
    	} else if(reqMethod.equals(PrettyStrings.PUT_STRING) || reqMethod.equals("POST")){
    		System.out.println(String.format(PrettyStrings.PUT_OUTPUT, reqNumber, reqMethod, path , acceptType, r.getMediaType().toString(), statusString, statusCode, prettyPrint));
    	} else if(reqMethod.equals(PrettyStrings.DELETE_STRING)){
    		System.out.println(String.format(PrettyStrings.DEL_OUTPUT, reqNumber, reqMethod, path, statusString, statusCode));
    		}
    	}
}
