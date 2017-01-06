package JourneyThroughEurope.bean;

public class PlaneCity {

    String name;
    int index;
    int selected;
    Point P;
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
     * @return the index
     */
    public int getIndex() {
        return index;
    }
    /**
     * @param index the index to set
     */
    public void setIndex(int index) {
        this.index = index;
    }
    /**
     * @return the selected
     */
    public int getSelected() {
        return selected;
    }
    /**
     * @param selected the selected to set
     */
    public void setSelected(int selected) {
        this.selected = selected;
    }
    /**
     * @return the p
     */
    public Point getP() {
        return P;
    }
    /**
     * @param p the p to set
     */
    public void setP(Point p) {
        P = p;
    }
    public PlaneCity(String name, int index, int selected, Point p) {
	super();
	this.name = name;
	this.index = index;
	this.selected = selected;
	P = p;
    }
}
