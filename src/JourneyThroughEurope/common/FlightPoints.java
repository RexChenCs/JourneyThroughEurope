package JourneyThroughEurope.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

import JourneyThroughEurope.bean.PlaneCity;
import JourneyThroughEurope.bean.Point;

public class FlightPoints {
    HashMap<String, PlaneCity> points;
    private static FlightPoints singleton = null;

    private FlightPoints(String path) {
	this.points = new HashMap<String, PlaneCity>();
	inital(path);
    }

    public HashMap<String, PlaneCity> getPoints() {
	return this.points;
    }

    public static FlightPoints getPointsManager() {
	// IF IT'S NEVER BEEN RETRIEVED BEFORE THEN
	// FIRST WE MUST CONSTRUCT IT
	if (singleton == null) {
	    // WE CAN CALL THE PRIVATE CONSTRCTOR FROM WITHIN THE CLASS
	    singleton = new FlightPoints("./data/flights.csv");
	}
	// RETURN THE SINGLETON
	return singleton;
    }

    public void inital(String fileNameAndPath) {
	try {
	    File csv = new File(fileNameAndPath);
	    BufferedReader br = new BufferedReader(new FileReader(csv));

	    String line = "";
	    line = br.readLine();
	    while ((line = br.readLine()) != null) {
		StringTokenizer st = new StringTokenizer(line, "	");
		String name = st.nextToken().trim();
		name = name.replace(" ", "_");
		int index = Integer.parseInt(st.nextToken());
		int x = Integer.parseInt(st.nextToken());
		int y = Integer.parseInt(st.nextToken());
		PlaneCity c = new PlaneCity(name, index, 0, new Point(x, y));
		points.put(name, c);
	    }
	    br.close();

	} catch (FileNotFoundException e) {

	    e.printStackTrace();
	} catch (IOException e) {

	    e.printStackTrace();
	}
    }
    public PlaneCity getSelect(Point p){
	for(String cn:this.points.keySet()){
	    if(points.get(cn).getP().isClose(p)){
		return points.get(cn);
	    }
	}
	return null;
	
    }
}
