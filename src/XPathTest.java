/**
 * @author	Tiziano Antico
 * In this class, some functions, for the management of the information inside the xml file, have
 * been coded.
 * Throw some XPath Expressions, it is possible to extrapolate information like: the weight of a specific
 * person with a specific ID, the height of a specific person with a specific ID, etc...
 */

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XPathTest{

    Document doc;
    XPath xpath;

    /**
     * Load the xml file
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public void loadXML() throws ParserConfigurationException, SAXException, IOException {

    	// A new instance of DocumentBuilderFactory has been created
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true);
        DocumentBuilder builder = domFactory.newDocumentBuilder();
        //The document which has to be parsed, it has been given as parameter to the function "parse"
        doc = builder.parse("people.xml");

        //The XPath object has been created
        getXPathObj();
    }

    /**
     * Return an object of type "XPath"
     * @return
     */
    public XPath getXPathObj() {

        XPathFactory factory = XPathFactory.newInstance();
        xpath = factory.newXPath();
        return xpath;
    }

    /**
     * Return the Weight of a specific id person
     * @param id
     * @return
     * @throws XPathExpressionException
     */
    public Node getWeightById(Long id) throws XPathExpressionException{

    	//The expression written into the "compile" method allows you to get the information required for this function
    	XPathExpression expr = xpath.compile("/people/person[@id='" + id + "']/healthprofile/weight/text()");
        //The previous expression is evaluated
    	Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
        
        return node;
    }
    
    
    /**
     * Return the Height of a specific id person
     * @param id
     * @return
     * @throws XPathExpressionException
     */
    public Node getHeightById(Long id) throws XPathExpressionException{

    	XPathExpression expr = xpath.compile("/people/person[@id='" + id + "']/healthprofile/height/text()");
        Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
        
        return node;
    }
    
    /**
     * Print all the people in the xml file
     * @throws XPathExpressionException
     */
    public void getAllPeople() throws XPathExpressionException{
    	
    	//Expression which allows to print all the people written into "people.xml" file
    	XPathExpression expr = xpath.compile("//person");

	    Object result = expr.evaluate(doc, XPathConstants.NODESET);
	    NodeList nodes = (NodeList) result;
	    for (int i = 0; i < nodes.getLength(); i++) {
	    	System.out.println("Person# "+(i+1));
	        System.out.println(nodes.item(i).getTextContent());
	    }

    }
    
    
    /**
     * Print the healthprofile of person with a specific id
     * @param id
     * @throws XPathExpressionException
     */
    public void getHealthProfileById (Long id) throws XPathExpressionException {

        XPathExpression expr = xpath.compile("//person[@id='" + id +"']/healthprofile");
        Object result = expr.evaluate(doc, XPathConstants.NODESET);
	    NodeList nodes = (NodeList) result;

    	System.out.println("Person# "+(id)+"- healthprofile");
	    for (int i = 0; i < nodes.getLength(); i++) {
	        System.out.println(nodes.item(i).getTextContent());
	    }

    }
    
    /**
     * Return people's weight under a certain condition
     * @param weight
     * @param condition
     * @return
     * @throws XPathExpressionException
     */
    public void getWeightByCondition(double weight, String condition) throws XPathExpressionException {

        XPathExpression expr = xpath.compile("//parent::person[healthprofile/weight" + condition + "'" + weight + "']");
        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        
        System.out.println("Person who match with the condition: weight "+condition+' '+weight+" Kg");
        for(int i = 0; i < nodes.getLength(); i++){
        	System.out.println(nodes.item(i).getTextContent());
        }

    }
    
    /**
	 * The XPathTest  gets information from the command line about
	 * name of function, number (which can be either the id or the weight) and the condition.
	 * It calculates the function's result and return it.
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws ParserConfigurationException, SAXException,
    IOException, XPathExpressionException {
		
		XPathTest test = new XPathTest();
        test.loadXML();
        
		int argCount = args.length;
		if (argCount == 0) {
			//If nothing is specified, it appears an error message to let the user knows about the his mistake
			System.out.println("Please specify the name of one method");
		}
		else {
			String method = args[0];
			//printing all the people in the xml file
			if (method.equals("getAllPeople")) {
				test.getAllPeople();
			}
			//getting weight by id person
			else if (method.equals("getWeightById")) {
				Long personId = Long.parseLong(args[1]);
				Node weight = test.getWeightById(personId);
				System.out.println("Weight: " + weight.getTextContent());			
			}
			//getting height by id person
			else if (method.equals("getHeightById")) {
				Long personId = Long.parseLong(args[1]);
				Node height = test.getHeightById(personId);
				System.out.println("Height: " + height.getTextContent());
			}
			//printing the healthprofile of a person with a specific id
			else if (method.equals("getHealthProfileById")) {
				Long personId = Long.parseLong(args[1]);
				test.getHealthProfileById(personId);
			}
			//printing the people who fulfill a condition
			else if (method.equals("getWeightByCondition")) {
				String condition = args[2];
				double weight = Double.parseDouble(args[1]);
				test.getWeightByCondition(weight,condition);
			}
			else {
				//If the user does not type a valid function, it will be shown this message
				System.out.println("The system did not find the method '"+method+"'");
			}
		}
	}
}

