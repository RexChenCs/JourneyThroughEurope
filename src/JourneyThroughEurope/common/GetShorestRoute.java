package JourneyThroughEurope.common;

import java.util.ArrayList;
import java.util.HashMap;

public class GetShorestRoute {
    ArrayList<String> cities = new ArrayList<String>();
    ArrayList<String> resultCities = new ArrayList<String>();
    int shortest = 10000000;
    int steps = 0;
    int flag = 0;
    PropertiesManager props = PropertiesManager.getPropertiesManager();

    public GetShorestRoute(String root, String des1, String des2) {
	super();
	this.root = root;
	this.des1 = des1;
	this.des2 = des2;
    }

    String root;
    String des1;
    String des2;

    public boolean completed() {
	if (this.cities.contains(des1) && this.cities.contains(des2)) {
	    return true;
	} else {
	    return false;
	}

    }

    public void storeRoute() {
	this.resultCities.clear();
	for (String c : this.cities) {
	    this.resultCities.add(c);
	}
	this.cities.clear();
	this.shortest = this.steps;
	this.steps = 0;
    }

    public boolean isSaved(String name) {
	if (this.cities.contains(name)) {
	    return true;
	} else {
	    return false;
	}
    }

    public void getRoute(String root, int level) {
	if(level == 1){
	    this.steps = 0;
	    this.cities.clear();
	}
	if (root.equals(this.root)) {
	    if (this.completed()) {
		this.cities.add(root);
		if (this.steps < this.shortest) {
		    storeRoute();
		}
		return;
	    }
	   if(level!=0){
		return;
	    }

	} else {
	    if (!this.isSaved(root)) {
		if (this.steps >= this.shortest) {
		    return;
		}
		this.cities.add(root);
	    } else {
		return;
	    }
	}
	HashMap<String, ArrayList<String>> rootMap = props
		.getPropertyOptionsList(root);
	ArrayList<String> land = rootMap.get("land");
	for (String lcity : land) {
	    this.steps++;
	    getRoute(lcity,level+1);
	}
	ArrayList<String> sea = rootMap.get("sea");
	for (String scity : sea) {
	    this.steps = this.steps + 1;
	    getRoute(scity,level+1);
	}
    }

    public void print() {
	for (String lcity : this.resultCities) {
	    System.out.println(lcity);
	}
    }

    public ArrayList<String> getRouteList() {
	return this.resultCities;
    }

}
