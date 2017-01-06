package JourneyThroughEurope.common;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import JourneyThroughEurope.bean.Instruction;

public class Instructions
{    
    // THIS CLASS IS A SINGLETON, AND HERE IS THE ONLY OBJECT
    private static Instructions singleton = null;

    private HashMap<String, Instruction> instructions;
    private XMLUtilities xmlUtil; 
    static String DATA_PATH = "./data/";
    /**
     * The constructor is private because this is a singleton.
     */
    private Instructions() 
    {
	instructions = new HashMap<String, Instruction>();
        xmlUtil = new XMLUtilities();
    }

    /**
     * This is the static accessor for the singleton.
     * 
     * @return The singleton properties manager object.
     */
    public static Instructions getInstructionsManager()
    {
     
        if (singleton == null)
        {
            // WE CAN CALL THE PRIVATE CONSTRCTOR FROM WITHIN THE CLASS
            singleton = new Instructions();
        }
        // RETURN THE SINGLETON
        return singleton;
    }

 
 
    public Instruction getInstruction(Object propType)
    {
        String propName = propType.toString();
        return instructions.get(propName);
    }

    public HashMap<String, Instruction> getInstructions()
    {
       return this.instructions;
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

        String dataPath =  DATA_PATH;
 
        // ADD THE DATA PATH
        xmlDataFile = dataPath + xmlDataFile;
        xmlSchemaFile = dataPath + xmlSchemaFile;
        // FIRST LOAD THE FILE
        Document doc = xmlUtil.loadXMLDocument(xmlDataFile, xmlSchemaFile);
        Node propertyOptionsListNode = xmlUtil.getNodeWithName(doc, "instruction_types");
        if (propertyOptionsListNode != null)
        {
            ArrayList<Node> propertyOptionsNodes = xmlUtil.getChildNodesWithName(propertyOptionsListNode, "instruction");
            for (Node n : propertyOptionsNodes)
            {
              
        	ArrayList<Node> typeNode = xmlUtil.getChildNodesWithName(n, "type");
        	String type = typeNode.get(0).getTextContent().toString();
        	ArrayList<Node> desNode = xmlUtil.getChildNodesWithName(n, "description");
        	String dis = desNode.get(0).getTextContent().toString();
        	
                ArrayList<String> attr_list = new ArrayList<String>();
                Node attr_lists = xmlUtil.getChildNodesWithName(n, "attr_list").get(0);
                ArrayList<Node> attrNodes = xmlUtil.getChildNodesWithName(attr_lists, "attr");
                for (Node oNode : attrNodes)
                {
                    String option = oNode.getTextContent();
                    attr_list.add(option);
                }
                Instruction i = new Instruction(type,dis,attr_list);
                this.instructions.put(type, i);
            
            }
        }
    }
}