/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.gautier.projet_info_2;
import java.util.* ;

/**
 *
 * @author gserouart01
 */
public class Etage {
    private int id;
    private double hauteur;
    private ArrayList<Pièce> pieces;



    public Etage(int id, double hauteur) {
        this.id = id;
        this.hauteur = hauteur;
        this.pieces = new ArrayList();
    }
    
    public ArrayList<Pièce> getPieces() {
        return pieces;
    }

    public void Ajouter(Pièce p) {
        this.pieces.add(p);
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getHauteur() {
        return hauteur;
    }

    public void setHauteur(double hauteur) {
        this.hauteur = hauteur;
    }
    
    

    @Override
    public String toString() {
        return "Etage{" + "id=" + id + ", hauteur=" + hauteur + ", pieces=" + pieces + '}';
    }
    
    
}
