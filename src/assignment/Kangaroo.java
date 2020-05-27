package assignment;

public class Kangaroo {

    private Point startPoint;
    //Added currentPoint/endPoint for kangaroo
    private Point currentPoint;
    private int noOfFood;
    private String gender;
    private int maxPouchFood;
    private boolean canTravelTo;

    //The arguments need to change from int noOfFood --> int maxPouchFood    
    public Kangaroo(Point startPoint, int maxPouchFood, String gender) {
        this.startPoint = startPoint;
        this.maxPouchFood = maxPouchFood;
        this.gender = gender;
        this.currentPoint = startPoint;
        this.noOfFood = 0;
    }

    public Point getCurrentPoint() {
        return currentPoint;
    }

    public void setCurrentPoint(Point currentPoint) {
        this.currentPoint = currentPoint;
    }

    public int getMaxPouchFood() {
        return maxPouchFood;
    }

    public void setMaxPouchFood(int maxPouchFood) {
        this.maxPouchFood = maxPouchFood;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public int getNoOfFood() {
        return noOfFood;
    }

    public void setNoOfFood(int noOfFood) {
        this.noOfFood = noOfFood;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void canTravelTo(Point p) {

    }

}
