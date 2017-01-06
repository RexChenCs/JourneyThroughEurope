package Application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import JourneyThroughEurope.bean.Instruction;
import JourneyThroughEurope.common.Cards;
import JourneyThroughEurope.common.GetShorestRoute;
import JourneyThroughEurope.common.Instructions;
import JourneyThroughEurope.common.InvalidXMLFileFormatException;
import JourneyThroughEurope.common.PropertiesManager;
import JourneyThroughEurope.common.SaveLoad;
import JourneyThroughEurope.ui.Splash;

public class Main {

    static String CITIES_XML = "cities.xml";
    static String CARDS_XML = "Cards.xml";
    static String INSTRUCTIONS_XML = "instructions.xml";
    static String CITIES_CSV = "cities.csv";
    static String DATA_PATH = "./data/";

    public static void main(String[] args) {

	try {
	    PropertiesManager props = PropertiesManager.getPropertiesManager();
	    props.addProperty("DATA_PATH", DATA_PATH);
	    props.loadProperties(CITIES_XML, null);
	    Instructions ins = Instructions.getInstructionsManager();
	    ins.loadProperties(INSTRUCTIONS_XML, null);
	    Cards cards = Cards.getCardsManager();
	    cards.loadProperties(CARDS_XML, null);
	    Splash t = new Splash();
	} catch (InvalidXMLFileFormatException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	try {
	    SaveLoad.getTownInfo();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	/*GetShorestRoute  gsr = new GetShorestRoute("BOLOGNA","BELFAST","MAGDEBURG");
	gsr.getRoute("BOLOGNA",0);
	gsr.print();*/
    }
}
