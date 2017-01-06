package JourneyThroughEurope.common;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import JourneyThroughEurope.bean.Card;
import JourneyThroughEurope.bean.Instruction;

public class Cards {
    private static Cards singleton = null;
    private HashMap<String, Card> cards;
    private XMLUtilities xmlUtil;
    static String DATA_PATH = "./data/";

    private Cards() {
	cards = new HashMap<String, Card>();
	xmlUtil = new XMLUtilities();
    }


    public static Cards getCardsManager() {

	if (singleton == null) {
	    // WE CAN CALL THE PRIVATE CONSTRCTOR FROM WITHIN THE CLASS
	    singleton = new Cards();
	}
	// RETURN THE SINGLETON
	return singleton;
    }

    public Card getCard(Object propType) {
	String propName = propType.toString();
	return cards.get(propName);
    }

    public HashMap<String, Card> getCards() {
	return this.cards;
    }

    public void loadProperties(String xmlDataFile, String xmlSchemaFile)
	    throws InvalidXMLFileFormatException {
	String dataPath = DATA_PATH;
	xmlDataFile = dataPath + xmlDataFile;
	xmlSchemaFile = dataPath + xmlSchemaFile;
	Document doc = xmlUtil.loadXMLDocument(xmlDataFile, xmlSchemaFile);
	loadColorCards(doc, "red");
	loadColorCards(doc, "yellow");
	loadColorCards(doc, "green");

    }

    private void loadColorCards(Document doc, String color) {
	Node propertyOptionsListNode = xmlUtil.getNodeWithName(doc, color);
	if (propertyOptionsListNode != null) {
	    ArrayList<Node> propertyOptionsNodes = xmlUtil
		    .getChildNodesWithName(propertyOptionsListNode, "card");
	    for (Node n : propertyOptionsNodes) {
		NodeList childNodes = n.getChildNodes();
		String cardName = xmlUtil.getChildNodeWithName(n, "name").getTextContent().trim();
		Node region =  xmlUtil.getChildNodeWithName(n, "region");
		String re_h = xmlUtil.getChildNodeWithName(region, "h").getTextContent().trim();
		String re_v =  xmlUtil.getChildNodeWithName(region, "v").getTextContent().trim();
		
		String instr = xmlUtil.getChildNodeWithName(n, "instr").getTextContent().trim();
		HashMap<String, HashMap<String,String>> insMap = null;
		if (instr.equals("true")) {
		    Node instr_list = xmlUtil.getChildNodeWithName(n, "instr_list");
		    ArrayList<Node> instrsOfCard = xmlUtil
			    .getChildNodesWithName(instr_list, "instruction");
		   insMap = new  HashMap<String, HashMap<String,String>>();
		    for (Node ins : instrsOfCard) {
			String type = xmlUtil.getChildNodeWithName(ins, "type").getTextContent().trim();
			Instructions instructions = Instructions.getInstructionsManager();
			Instruction in = instructions.getInstructions().get(type);
			HashMap<String,String> instr_Op = new HashMap<String,String>();
			for(int i = 0; i < in.getAttr_list().size(); i++){
			    String value =  xmlUtil.getChildNodeWithName(ins, in.getAttr_list().get(i)).getTextContent().trim();
			    instr_Op.put(in.getAttr_list().get(i), value);
			}
			insMap.put(type, instr_Op);
		    }
		}
		this.cards.put(cardName, new Card(cardName,re_h,re_v,color,insMap));
	    }
	}
    }
}