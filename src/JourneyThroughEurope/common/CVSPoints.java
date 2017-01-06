package JourneyThroughEurope.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

import JourneyThroughEurope.bean.City;
import JourneyThroughEurope.bean.Point;

public class CVSPoints {
    HashMap<String, City> points1;
    HashMap<String, City> points2;
    HashMap<String, City> points3;
    HashMap<String, City> points4;
    HashMap<String, City> pointsRed;
    HashMap<String, City> pointsGreen;
    HashMap<String, City> pointsYellow;
    HashMap<String,City> points;
    private static CVSPoints singleton = null;

    private CVSPoints(String path) {
	this.points1 = new HashMap<String, City>();
	this.points2 = new HashMap<String, City>();
	this.points3 = new HashMap<String, City>();
	this.points4 = new HashMap<String, City>();
	this.points = new HashMap<String, City>();
	this.pointsRed = new HashMap<String, City>();
	this.pointsGreen = new HashMap<String, City>();
	this.pointsYellow = new HashMap<String, City>();
	inital(path);
    }

    /**
     * @return the points1
     */
    public HashMap<String, City> getPoints(int i) {
	if (i == 1) {
	    return points1;
	} else if (i == 2) {
	    return points2;
	} else if (i == 3) {
	    return points3;
	} else {
	    return points4;
	}
    }
    public HashMap<String, City> getPoints(String color) {
   	if (color.equals("red")) {
   	    return pointsRed;
   	} else if (color.equals("green")) {
   	    return pointsGreen;
   	} else if (color.equals("yellow")) {
   	    return pointsYellow;
   	}
	return null; 
       }
    public HashMap<String, City> getPoints() {
   	return this.points;
       }
  
  
    public static CVSPoints getPointsManager() {
	// IF IT'S NEVER BEEN RETRIEVED BEFORE THEN
	// FIRST WE MUST CONSTRUCT IT
	if (singleton == null) {
	    // WE CAN CALL THE PRIVATE CONSTRCTOR FROM WITHIN THE CLASS
	    singleton = new CVSPoints("./data/cities.csv");
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

		String name = st.nextToken();
		name = name.replace(" ", "_");
		String color = st.nextToken();
		int quarter = Integer.parseInt(st.nextToken());
		int x = Integer.parseInt(st.nextToken());
		int y = Integer.parseInt(st.nextToken());
		City c = new City(name, color, quarter, new Point(x, y));
		points.put(name, c);
		if (quarter == 1) {
		    points1.put(name, c);
		} else if (quarter == 2) {
		    points2.put(name, c);
		} else if (quarter == 3) {
		    points3.put(name, c);
		} else {
		    points4.put(name, c);
		}
		if(color.equals("red")){
		    pointsRed.put(name, c);
		}else if(color.equals("yellow")){
		    pointsYellow.put(name, c);
		}else if(color.equals("green")){
		    pointsGreen.put(name, c);
		}

	    }
	    br.close();

	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
