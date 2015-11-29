package introsde.rest.client.helpers;

import javax.ws.rs.core.MediaType;

// THIS CLASS CONTAINS ONLY CONSTANTS STRING USED FROM THE CLIENT;

public class PrettyStrings {

  public static final String URI_SERVER = "http://127.0.1.1:5700/sdelab";
  public static final String ERROR_E = "ERROR";
  public static final String OK_OK = "OK";
  public static final String APP_JSON = MediaType.APPLICATION_JSON;
  public static final String APP_XML  = MediaType.APPLICATION_XML;
	public static final String GET_STRING = "GET";
	public static final String PUT_STRING = "PUT";
	public static final String POST_STRING = "POST";
	public static final String DELETE_STRING = "DELETE";

	public static String PUT_OUTPUT = "Request #%d: %s %s Accept: %s Content-type: %s\n"
								 +"\t=> Result: %s\n"
								 +"\t=> HTTP Status: %d\n\n"
								 +"%s\n";

	public static String GET_OUTPUT = "Request #%d: %s %s Accept: %s\n"
								 +"\t=> Result: %s\n"
								 +"\t=> HTTP Status: %d\n\n"
								 +"%s\n";

	public static String DEL_OUTPUT = "Request #%d: %s %s\n"
								 +"\t=> Result: %s\n"
								 +"\t=> HTTP Status: %d\n\n";

	public static String TYPE_HEADER = "\n###################################################################\n"+
									"#####\n"+
									"###   Running tests with Accept and Content-type: %s\n"+
									"#\n";

	public static String HEADER = "\n\t\t\t##############\n"+
							   							 "\t\t\t## TEST %s ##\n"+
							   					 		 "\t\t\t##############\n";
}
