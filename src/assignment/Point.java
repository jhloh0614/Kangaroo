package assignment;

import java.util.LinkedList;

public class Point {

    private int food, size, path;
    private String id;
    private LinkedList<Point> link = new LinkedList<>();
    private LinkedList<String> pathid = new LinkedList<>();
    private boolean bidirection = false;
    private boolean isColonised = false;
    private int count = 0;
    private int femaleKangaroo = 0;
    private int maleKangaroo = 0;
    private int currentKangarooNumber = 0;
    private int x = 0;
    private int y = 0;
    private double layoutX, layoutY;

    public Point(Point point, String pathid) {
        this.pathid.add(pathid);
        link.add(point);

    }

    public Point(String source, String destination, String pathid) {
        this.id = source;
        link.add(new Point(destination));
        this.pathid.add(pathid);

    }

    public Point(String id) {
        this.id = id;
    }

    public Point(String id, int food, int size, int path) {
        this.id = id;
        this.food = food;
        this.size = size;
        this.path = path;
    }

    public boolean isIsColonised() {
        return isColonised;
    }

    public void setIsColonised(boolean isColonised) {
        this.isColonised = isColonised;
    }
        
    public int getFemaleKangaroo() {
        return femaleKangaroo;
    }

    public int getCurrentKangarooNumber() {
        return femaleKangaroo + maleKangaroo;
    }
                    
    public void setFemaleKangaroo(int femaleKangaroo) {
        this.femaleKangaroo = femaleKangaroo;
    }

    public int getMaleKangaroo() {
        return maleKangaroo;
    }

    public void setMaleKangaroo(int maleKangaroo) {
        this.maleKangaroo = maleKangaroo;
    }

    public double getLayoutX() {
        return layoutX;
    }

    public void setLayoutX(double layoutX) {
        this.layoutX = layoutX;
    }

    public double getLayoutY() {
        return layoutY;
    }

    public void setLayoutY(double layoutY) {
        this.layoutY = layoutY;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public boolean isBidirection() {
        return bidirection;
    }

    public void setBidirection(boolean bidirection) {
        this.bidirection = bidirection;
    }

    public LinkedList<Point> getLink() {
        return link;
    }

    public void setLink(LinkedList<Point> link) {
        this.link = link;
    }

    public LinkedList<String> getPathid() {
        return pathid;
    }

    public void setPathid(LinkedList<String> pathid) {
        this.pathid = pathid;
    }

    public void setLink(Point destination, String pathid) {
        if (!linkFull()) {
            link.add(destination);
            this.pathid.add(pathid);
        } else {
            System.out.println("The path is full");
        }
    }

    public void setLink(String destination) {
        if (!linkFull()) {
            link.add(new Point(destination));
        }
    }

    public void setLink(Point p) {
        if (!linkFull()) {
            link.add(p);
        }
    }

    public void setLink(String source, String destination, String pathid) {
        this.id = source;
        if (!linkFull()) {
            link.add(new Point(destination));
            this.pathid.add(pathid);
            count++;
        } else {
            System.out.println("The path is full");
        }

    }

//    public void setLink(Point point, String pathid){
//        if(!linkFull()){
//            link[count] = point;
//            this.pathid[count] = pathid;
//            count++;
//        }
//        else{
//            System.out.println("The path is full");
//        }
//    }
    public boolean linkFull() {
        return count == path || pathid.size() == path || link.size() == path;
    }

    public String toString() {
        return "ID : " + id
                + "\nNo. of Food : " + food
                + "\nMax no. of kangaroo : " + size
                + "\nNo. of path : " + path;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPath() {
        return path;
    }

    public void setPath(int path) {
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
