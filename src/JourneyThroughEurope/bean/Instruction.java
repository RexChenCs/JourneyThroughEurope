package JourneyThroughEurope.bean;

import java.util.ArrayList;

public class Instruction {
    String type;
    String description;
    ArrayList<String> attr_list;
    public Instruction(String type, String description,
	    ArrayList<String> attr_list) {
	this.type = type;
	this.description = description;
	this.attr_list = attr_list;
    }
    /**
     * @return the type
     */
    public String getType() {
        return type;
    }
    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return the attr_list
     */
    public ArrayList<String> getAttr_list() {
        return attr_list;
    }
    /**
     * @param attr_list the attr_list to set
     */
    public void setAttr_list(ArrayList<String> attr_list) {
        this.attr_list = attr_list;
    }

}
