package introsde.rest.client.helpers;

import java.io.IOException;
import java.io.StringReader;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.transform.TransformerFactory;
import org.xml.sax.SAXException;
import java.io.ByteArrayInputStream;
import org.xml.sax.InputSource;
import org.w3c.dom.Node;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.Transformer;
import java.io.StringWriter;

public class XmlHelper {

	Document doc;
	XPath xpath;

	public XmlHelper() {
		 XPathFactory factory = XPathFactory.newInstance();
	     xpath = factory.newXPath();
	}

    public void loadXML(String xml) {

        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true);

        try {
        	DocumentBuilder builder = domFactory.newDocumentBuilder();
        	InputSource is = new InputSource(new StringReader(xml));
        	doc = builder.parse(is);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			doc = null;
			}
    }

    public NodeList getNodes(String xpathExpr) {
      try {

					XPathExpression expr = xpath.compile(xpathExpr);
					return (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
				}
			catch (XPathExpressionException e) {
					return null;
			}
    }

		public static String prettyXML(String xml, int indent) {
		try {
				// Turn xml string into a document
				Document document = DocumentBuilderFactory.newInstance()
								.newDocumentBuilder()
								.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));

				// Remove whitespaces outside tags
				XPath xPath = XPathFactory.newInstance().newXPath();
				NodeList nodeList = (NodeList) xPath.evaluate("//text()[normalize-space()='']",
																											document,
																											XPathConstants.NODESET);

				for (int i = 0; i < nodeList.getLength(); ++i) {
						Node node = nodeList.item(i);
						node.getParentNode().removeChild(node);
				}
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				transformerFactory.setAttribute("indent-number", indent);
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");

				StringWriter stringWriter = new StringWriter();
				transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
				return stringWriter.toString();
		} catch (Exception e) {
				throw new RuntimeException(e);
		}
	}
}
