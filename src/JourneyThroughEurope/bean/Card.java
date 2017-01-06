package JourneyThroughEurope.bean;

import java.util.HashMap;

public class Card {
    String name;
    String h;
    String v;
    String color;
    HashMap<String, HashMap<String,String>> insMap;
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the h
     */
    public String getH() {
        return h;
    }
    /**
     * @param h the h to set
     */
    public void setH(String h) {
        this.h = h;
    }
    /**
     * @return the v
     */
    public String getV() {
        return v;
    }
    /**
     * @param v the v to set
     */
    public void setV(String v) {
        this.v = v;
    }
    /**
     * @return the insMap
     */
    public HashMap<String, HashMap<String, String>> getInsMap() {
        return insMap;
    }
    /**
     * @param insMap the insMap to set
     */
    public void setInsMap(HashMap<String, HashMap<String, String>> insMap) {
        this.insMap = insMap;
    }
 
    public Card(String name, String h, String v,String color,
	    HashMap<String, HashMap<String, String>> insMap) {
	super();
	this.name = name;
	this.h = h;
	this.v = v;
	this.color = color;
	this.insMap = insMap;
    }
    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }
    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

}
