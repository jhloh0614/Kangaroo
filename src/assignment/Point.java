/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import java.util.LinkedList;

public class Point {

    private int food, size, path;
    private String id;
    private LinkedList<Point> link = new LinkedList<>();
    private LinkedList<String> pathid = new LinkedList<>();
    private int count = 0;

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
        return count == path;
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
}
