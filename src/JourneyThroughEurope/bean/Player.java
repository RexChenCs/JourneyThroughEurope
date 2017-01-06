package JourneyThroughEurope.bean;

import java.util.ArrayList;
import java.util.HashMap;

import JourneyThroughEurope.common.CVSPoints;
import JourneyThroughEurope.common.GetShorestRoute;
import JourneyThroughEurope.common.PropertiesManager;

public class Player {
    public final static int HUMAN = 0;
    public final static int COMPUTER = 1;
    ArrayList<String> route = new ArrayList<String>();
    int isDie = 0;
    int getOneMoreDice = 0;
    int anotherGoes = 0;
    int firstFlag;
    int harborWaited = 0;
    /**
     * @return the route
     */
    public ArrayList<String> getRoute() {
        return route;
    }
    /**
     * @param route the route to set
     */
    public void setRoute(ArrayList<String> route) {
        this.route = route;
    }

    int number;
    int planeFlag = 0;

    public void setRoute(){
	GetShorestRoute  gsr = new GetShorestRoute(this.desCities[0].getName(),this.desCities[1].getName(),this.desCities[2].getName());
	gsr.getRoute(this.desCities[0].getName(),0);
	this.route = gsr.getRouteList();
	//gsr.print();
    }
    /**
     * @return the harborWaited
     */
    public int getHarborWaited() {
	return harborWaited;
    }

    /**
     * @param harborWaited
     *            the harborWaited to set
     */
    public void setHarborWaited(int harborWaited) {
	this.harborWaited = harborWaited;
    }

    int type;
    String name;
    City current;
    City first;
    City[] desCities;
    int reachedFlag[];
    ArrayList<City> historyCities;

    /**
     * @return the firstFlag
     */
    public int getFirstFlag() {
	return firstFlag;
    }

    /**
     * @param firstFlag
     *            the firstFlag to set
     */
    public void setFirstFlag(int firstFlag) {
	this.firstFlag = firstFlag;
    }

    /**
     * @param number
     *            the number to set
     */
    public void setNumber(int number) {
	this.number = number;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(int type) {
	this.type = type;
    }

    /**
     * @param historyCities
     *            the historyCities to set
     */
    public void setHistoryCities(ArrayList<City> historyCities) {
	this.historyCities = historyCities;
    }

    /**
     * @return the reachedNum
     */
    public boolean isAllReached() {
	GameHistory game = GameHistory.getGameHistory();
	if (this.reachedFlag[1] == 1 && this.reachedFlag[2] == 1
		&& game.getCurrentCity().getName().equals(this.first.getName())) {
	    return true;
	} else {
	    return false;
	}
    }

    /**
     * @param reachedNum
     *            the reachedNum to set
     */
    public void setReachedNum(int reachedNum) {
	this.reachedFlag[reachedNum] = 1;
    }

    public int getReachedNum(int reachedNum) {
	return this.reachedFlag[reachedNum];
    }

    public boolean isCompleted(City c) {
	if (isAllReached() && c.getName().equals(this.first.getName())) {
	    return true;
	} else {
	    return false;
	}
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

    public boolean hasReached(City c) {
	for (City hiscity : this.historyCities) {
	    if (c.getName().equals(hiscity.getName())) {
		return true;
	    }
	}
	return false;
    }

    /**
     * @return the historyCities
     */
    public ArrayList<City> getHistoryCities() {
	return historyCities;
    }

    /**
     * @param historyCities
     *            the historyCities to set
     */
    public void addHistoryCities(City c) {

	if (this.historyCities == null) {
	    this.historyCities = new ArrayList<City>();
	}
	if (c.getName().equals(first.getName())) {
	    return;
	}
	for (City city : historyCities) {
	    if (city.getName().equals(c.getName())) {
		return;
	    }
	}
	this.historyCities.add(c);
    }

    /**
     * @return the current
     */
    public City getCurrent() {
	return current;
    }

    /**
     * @param current
     *            the current to set
     */
    public void setCurrent(City current) {
	this.current = current;
    }

    /**
     * @return the first
     */
    public City getFirst() {
	return first;
    }

    /**
     * @param first
     *            the first to set
     */
    public void setFirst(City first) {
	this.first = first;
    }

    /**
     * @return the cities
     */
    public City[] getDesCities() {
	return desCities;
    }

    /**
     * @param cities
     *            the cities to set
     */
    public void setDesCities(City[] cities) {
	this.desCities = cities;
    }

    /**
     * @return the number
     */
    public int getNumber() {
	return number;
    }

    /**
     * @return the type
     */
    public int getType() {
	return type;
    }

    /**
     * @return the getOneMoreDice
     */
    public int getGetOneMoreDice() {
	return getOneMoreDice;
    }

    /**
     * @param getOneMoreDice
     *            the getOneMoreDice to set
     */
    public void setGetOneMoreDice(int getOneMoreDice) {
	this.getOneMoreDice = getOneMoreDice;
    }

    /**
     * @return the anotherGoes
     */
    public int getAnotherGoes() {
	return anotherGoes;
    }

    /**
     * @param anotherGoes
     *            the anotherGoes to set
     */
    public void setAnotherGoes(int anotherGoes) {
	this.anotherGoes = anotherGoes;
    }

    public String toString() {
	String restr = this.name + ";" + this.type + ";" + this.number + ";"
		+ this.firstFlag + ";" + this.anotherGoes + ";"
		+ this.getOneMoreDice + ";" + this.current.getName() + ";"
		+ this.getFirst().getName() + ";";
	for (int i = 0; i < this.reachedFlag.length; i++) {
	    String add = ",";
	    if (i == this.reachedFlag.length - 1) {
		add = ";";
	    }
	    restr = restr + this.reachedFlag[i] + add;
	}
	for (int i = 0; i < desCities.length; i++) {
	    String add = ",";
	    if (i == desCities.length - 1) {
		add = ";";
	    }
	    restr = restr + desCities[i].getName() + add;
	}
	for (int i = 0; i < this.historyCities.size(); i++) {
	    String add = ",";
	    if (i == this.historyCities.size() - 1) {
		add = "";
	    }
	    restr = restr + historyCities.get(i).getName() + add;
	}
	return restr + ";" + this.isDie + ";" + this.harborWaited + ";"
		+ this.planeFlag;
    }

    public Player(String playerInfo) {
	HashMap<String, City> points = CVSPoints.getPointsManager().getPoints();
	String info[] = playerInfo.split(";");
	String name = info[0];
	int type = Integer.parseInt(info[1]);
	int num = Integer.parseInt(info[2]);
	int firstMoveFlag = Integer.parseInt(info[3]);
	int diceFlag = Integer.parseInt(info[5]);
	int anotherGoes = Integer.parseInt(info[4]);
	String curCity = info[6];
	String firstCity = info[7];
	String rearchedFlag[] = info[8].split(",");
	String desCities[] = info[9].split(",");
	String hisCities[] = info[10].split(",");
	this.isDie = Integer.parseInt(info[11]);
	this.harborWaited = Integer.parseInt(info[12]);
	this.planeFlag = Integer.parseInt(info[13]);

	this.firstFlag = firstMoveFlag;
	this.number = num;
	this.type = type;
	this.name = name;
	this.anotherGoes = anotherGoes;
	this.getOneMoreDice = diceFlag;
	this.setCurrent(points.get(curCity));
	this.setFirst(points.get(firstCity));
	this.reachedFlag = new int[3];
	reachedFlag[0] = Integer.parseInt(rearchedFlag[0]);
	reachedFlag[1] = Integer.parseInt(rearchedFlag[1]);
	reachedFlag[2] = Integer.parseInt(rearchedFlag[2]);
	this.desCities = new City[desCities.length];
	for (int i = 0; i < desCities.length; i++) {
	    this.desCities[i] = points.get(desCities[i]);
	}
	for (int i = 0; i < hisCities.length; i++) {
	    this.addHistoryCities(points.get(hisCities[i]));
	}

    }

    /**
     * @return the planeFlag
     */
    public int getPlaneFlag() {
	return planeFlag;
    }

    /**
     * @param planeFlag
     *            the planeFlag to set
     */
    public void setPlaneFlag(int planeFlag) {
	this.planeFlag = planeFlag;
    }

    /**
     * @return the isDie
     */
    public int getIsDie() {
	return isDie;
    }

    /**
     * @param isDie
     *            the isDie to set
     */
    public void setIsDie(int isDie) {
	this.isDie = isDie;
    }

    public Player(int number, int type, String name, int flag) {
	this.firstFlag = flag;
	this.number = number;
	this.type = type;
	this.name = name;
	this.reachedFlag = new int[3];
	reachedFlag[0] = 0;
	reachedFlag[1] = 0;
	reachedFlag[2] = 0;
    }

    public ArrayList<City> getReachableCitys(String type) {
	ArrayList<City> reachableCities = new ArrayList<City>();
	CVSPoints cityPoints = CVSPoints.getPointsManager();
	City city = this.getCurrent();
	PropertiesManager p = PropertiesManager.getPropertiesManager();
	ArrayList<String> cities = p.getPropertyOptionsList(city.getName())
		.get(type);
	for (String cString : cities) {
	    City c = cityPoints.getPoints().get(cString);
	    if (this.hasReached(c) == false) {
		reachableCities.add(c);
	    }
	}
	return reachableCities;
    }

    /**
     * 
     * @param clickedPoint
     * @param type
     *            : "sea" or "land"
     * @return
     */
    public City getNei(Point clickedPoint, String type) {
	CVSPoints cityPoints = CVSPoints.getPointsManager();
	PropertiesManager p1 = PropertiesManager.getPropertiesManager();
	ArrayList<String> cities = p1.getPropertyOptionsList(
		this.getCurrent().getName()).get(type);
	for (String city : cities) {
	    City c = cityPoints.getPoints().get(city);
	    Point neiCity = c.getP().small(4);
	    if (neiCity.isNear(clickedPoint)) {
		return c;
	    }
	}
	return null;
    }

    public City getAnotherDes(String name) {
	if (this.desCities[1].getName().equals(name)) {
	    return this.desCities[2];
	} else {
	    return this.desCities[1];
	}
    }
    public  void setNextCityofComputer(){
	CVSPoints cps = CVSPoints.getPointsManager();
	if(this.current.name.equals(this.first.getName())){
	   this.setCurrent(cps.getPoints().get(this.getRoute().get(0)));
	}else{
	    int nextIndex = this.route.indexOf(this.current.getName()) + 1;
	    this.setCurrent(cps.getPoints().get(this.getRoute().get(nextIndex)));
	}
    }
}
