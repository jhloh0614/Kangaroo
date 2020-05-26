
package assignment;

public class Kangaroo {
       
    private Point startPoint;
    private int noOfFood;
    private String gender;
    private boolean canTravelTo;
    
    public Kangaroo(Point startPoint, int noOfFood, String gender){
        this.startPoint = startPoint;
        this.noOfFood = noOfFood;
        this.gender = gender;
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

    public void canTravelTo(Point p){
        
    }
    
}
