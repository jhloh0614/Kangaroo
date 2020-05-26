/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

/**
 *
 * @author Owner
 */
public class Kangaroo {
    private Point point;
    private char gender;
    private int pouchFoodLimit;
    private int currentPouchFood;
    private int colony;

    public Kangaroo(Point point, char gender, int pouchFoodLimit) {
        this.point = point;
        this.gender = gender;
        this.pouchFoodLimit = pouchFoodLimit;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getPouchFoodLimit() {
        return pouchFoodLimit;
    }

    public void setPouchFoodLimit(int pouchFoodLimit) {
        this.pouchFoodLimit = pouchFoodLimit;
    }

    public int getCurrentPouchFood() {
        return currentPouchFood;
    }

    public void setCurrentPouchFood(int currentPouchFood) {
        this.currentPouchFood = currentPouchFood;
    }

    public int getColony() {
        return colony;
    }

    public void setColony(int colony) {
        this.colony = colony;
    }

   
    
    
}
