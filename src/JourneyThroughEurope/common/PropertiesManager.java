package JourneyThroughEurope.common;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import JourneyThroughEurope.common.InvalidXMLFileFormatException;
import JourneyThroughEurope.common.XMLUtilities;

/**
 * This class is used for loading properties from XML files that can
 * then be used throughout an application. Note that this class is
 * a singleton, and so can be accessed from anywhere. To get the
 * singleton properties manager, just use the static accessor:
 * 
 * PropertiesManager props = PropertiesManager.getPropertiesManager();
 * 
 * Now you can access all the properties it currently stores using
 * the getProperty method. Note that the properties_schema.xsd file
 * specifies how these files are to be constructed.
 * 
 * @author Richard McKenna
 */
public class PropertiesManager
{    
    // THIS CLASS IS A SINGLETON, AND HERE IS THE ONLY OBJECT
    private static PropertiesManager singleton = null;

    // WE'LL STORE PROPERTIES HERE
    private HashMap<String, String> properties;
    
    // LISTS OF PROPERTY OPTIONS CAN BE STORED HERE
    private HashMap<String, HashMap<String,ArrayList<String>>> propertyOptionsLists;
    
    // THIS WILL LOAD THE XML FOR US
    private XMLUtilities xmlUtil;
    
    // THESE CONSTANTS ARE USED FOR LOADING PROPERTIES AS THEY ARE
    // THE ESSENTIAL ELEMENTS AND ATTRIBUTES
    public static final String PROPERTY_ELEMENT                 = "property";
    public static final String PROPERTY_LIST_ELEMENT            = "property_list";
    public static final String PROPERTY_OPTIONS_LIST_ELEMENT    = "routes";
    public static final String PROPERTY_OPTIONS_ELEMENT         = "city";    
    public static final String OPTION_ELEMENT                   = "sea";
    public static final String OPTION_ELEMENT2                  = "land";
    public static final String NAME_ATT                         = "name";
    public static final String CITY_ATT                        = "city";
    public static final String DATA_PATH_PROPERTY               = "DATA_PATH";

    /**
     * The constructor is private because this is a singleton.
     */
    private PropertiesManager() 
    {
        properties = new HashMap<String, String>();
        propertyOptionsLists = new HashMap<String, HashMap<String, ArrayList<String>>>();
        xmlUtil = new XMLUtilities();
    }

    /**
     * This is the static accessor for the singleton.
     * 
     * @return The singleton properties manager object.
     */
    public static PropertiesManager getPropertiesManager()
    {
        // IF IT'S NEVER BEEN RETRIEVED BEFORE THEN
        // FIRST WE MUST CONSTRUCT IT
        if (singleton == null)
        {
            // WE CAN CALL THE PRIVATE CONSTRCTOR FROM WITHIN THE CLASS
            singleton = new PropertiesManager();
        }
        // RETURN THE SINGLETON
        return singleton;
    }

    /**
     * This function adds the (property, value) tuple to the 
     * properties manager.
     * 
     * @param property Key, i.e. property type for this pair.
     * 
     * @param value The data for this pair.
     */
    public void addProperty(Object property, String value)
    {
        String propName = property.toString();
        properties.put(propName, value);
    }
 
    /**
     * Accessor method for getting a property from this manager.
     * 
     * @param propType The key for getting a property.
     * 
     * @return The value associated with the key.
     */
    public String getProperty(Object propType)
    {
        String propName = propType.toString();
        return properties.get(propName);
    }

    /**
     * Accessor method for getting a property options list associated
     * with the property key.
     * 
     * @param property The key for accessing the property options list.
     * 
     * @return The property options list associated with the key.
     */
    public HashMap<String, ArrayList<String>> getPropertyOptionsList(Object property)
    {
        String propName = property.toString();
        return propertyOptionsLists.get(propName);
    }

    /**
     * This function loads the xmlDataFile in this property manager, first
     * make sure it's a well formed document according to the rules specified
     * in the xmlSchemaFile.
     * 
     * @param xmlDataFile XML document to load.
     * 
     * @param xmlSchemaFile Schema that the XML document should conform to.
     * 
     * @throws InvalidXMLFileFormatException This is thrown if the XML file
     * is invalid.
     */
    public void loadProperties(String xmlDataFile, String xmlSchemaFile)
            throws InvalidXMLFileFormatException
    {

        String dataPath = getProperty(DATA_PATH_PROPERTY);
 
        // ADD THE DATA PATH
        xmlDataFile = dataPath + xmlDataFile;
        xmlSchemaFile = dataPath + xmlSchemaFile;
        // FIRST LOAD THE FILE
        Document doc = xmlUtil.loadXMLDocument(xmlDataFile, xmlSchemaFile);
        Node propertyOptionsListNode = xmlUtil.getNodeWithName(doc, PROPERTY_OPTIONS_LIST_ELEMENT);
        if (propertyOptionsListNode != null)
        {
            ArrayList<Node> propertyOptionsNodes = xmlUtil.getChildNodesWithName(propertyOptionsListNode, PROPERTY_OPTIONS_ELEMENT);
            for (Node n : propertyOptionsNodes)
            {
              
        	ArrayList<Node> optionsNode = xmlUtil.getChildNodesWithName(n, NAME_ATT);
        	String name = optionsNode.get(0).getTextContent().toString();
                ArrayList<String> options1 = new ArrayList();
                ArrayList<String> options2 = new ArrayList();
                HashMap<String, ArrayList<String>> nei = new HashMap();
                nei.put("sea", options1);
                nei.put("land", options2);
                propertyOptionsLists.put(name, nei);
                Node sea = xmlUtil.getChildNodesWithName(n, OPTION_ELEMENT).get(0);
                ArrayList<Node> optionsNodes = xmlUtil.getChildNodesWithName(sea, CITY_ATT);
                for (Node oNode : optionsNodes)
                {
                    String option = oNode.getTextContent();
                    options1.add(option);
                }
                Node land = xmlUtil.getChildNodesWithName(n, OPTION_ELEMENT2).get(0);
                ArrayList<Node> optionsNodes2 = xmlUtil.getChildNodesWithName(land, CITY_ATT);
                for (Node oNode : optionsNodes2)
                {
                    String option = oNode.getTextContent();
                    options2.add(option);
                }
            }
        }
    }
}