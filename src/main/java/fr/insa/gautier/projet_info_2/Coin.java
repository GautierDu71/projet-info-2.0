/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.gautier.projet_info_2;

/**
 *
 * @author gserouart01
 */
public class Coin {
    
    private int intersections;
    private int id;
    private double x, y;
    private Etage Etage;

    public Coin(int id, double x, double y, Etage Etage) {
        intersections = 0;
        this.id = id;
        this.x = x;
        this.y = y;
        this.Etage = Etage;
    }

    public int getIntersections() {
        return intersections;
    }

    public void setIntersections(int intersections) {
        this.intersections = intersections;
    }

    @Override
    public String toString() {
        return "Coin{" + "id=" + id + ", x=" + x + ", y=" + y + '}';
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Etage getEtage() {
        return Etage;
    }

    public void setEtage(Etage Etage) {
        this.Etage = Etage;
    }




    
    
       
}
