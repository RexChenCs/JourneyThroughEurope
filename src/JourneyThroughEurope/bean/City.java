package JourneyThroughEurope.bean;

import java.util.ArrayList;

import JourneyThroughEurope.common.CVSPoints;
import JourneyThroughEurope.common.PropertiesManager;

public class City {
    int selected = 0;

    /**
     * @return the selected
     */
    public int getSelected() {
	return selected;
    }

    /**
     * @param selected
     *            the selected to set
     */
    public void setSelected(int selected) {
	this.selected = selected;
    }

    int quarter;
    String color;
    Point p;

    public City(String name, String color, int quarter, Point p) {
	super();
	this.quarter = quarter;
	this.color = color;
	this.p = p;
	this.name = name;
    }

    public static City copy(City other) {
	return new City(other.getName(), other.getColor(), other.getQuarter(),
		other.getP());
    }

    String name;

    /**
     * @return the quarter
     */
    public int getQuarter() {
	return quarter;
    }

    /**
     * @param quarter
     *            the quarter to set
     */
    public void setQuarter(int quarter) {
	this.quarter = quarter;
    }

    /**
     * @return the color
     */
    public String getColor() {
	return color;
    }

    /**
     * @param color
     *            the color to set
     */
    public void setColor(String color) {
	this.color = color;
    }

    /**
     * @return the p
     */
    public Point getP() {
	return p;
    }

    /**
     * @param p
     *            the p to set
     */
    public void setP(Point p) {
	this.p = p;
    }

    /**
     * @return the name
     */
    public String getName() {
	return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
	this.name = name;
    }

}
