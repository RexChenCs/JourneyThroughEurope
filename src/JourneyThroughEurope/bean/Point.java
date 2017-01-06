package JourneyThroughEurope.bean;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
	this.setX(x);
	this.setY(y);
    }

    public boolean isNear(Point other) {
	if (Math.abs(this.getX() - other.getX()) <= 30
		&& Math.abs(this.getY() - other.getY()) <= 30) {
	    return true;
	} else {
	    return false;
	}
    }
    public boolean isClose(Point other) {
  	if (Math.abs(this.getX() - other.getX()) <= 8
  		&& Math.abs(this.getY() - other.getY()) <= 8) {
  	    return true;
  	} else {
  	    return false;
  	}
      }
    public Point small(int n) {
	return new Point(this.getX()/n, this.getY()/n);
	
    }

    public int getX() {
	return x;
    }

    public void setX(int x) {
	this.x = x;
    }

    public int getY() {
	return y;
    }

    public void setY(int y) {
	this.y = y;
    }
}