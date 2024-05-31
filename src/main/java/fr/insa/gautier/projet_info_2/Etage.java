/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.gautier.projet_info_2;
import java.util.* ;

public class Etage {
    private int id;
    private double hauteur;
    private ArrayList<Pièce> pieces;
    private ArrayList<Mur> Murs;
    private ArrayList<Coin> Coins = new ArrayList();

    public Etage(int id, double hauteur) {
        this.Murs = new ArrayList();
        this.id = id;
        this.hauteur = hauteur;
        this.pieces = new ArrayList();
    }
    //pour gerer les listes 
    public void AjouterP(Pièce p) {
        this.pieces.add(p);
    }
    
    public void AjouterM(Mur M) {
        this.Murs.add(M);
    }
    //getters et setters
    public ArrayList<Mur> getMurs() {
        return Murs;
    }
    
    public Mur getMur(int i) {
        return Murs.get(i);
    }
    
    public void setMurs(ArrayList<Mur> Murs) {
        this.Murs = Murs;
    }
    
    public void ajoutMur (Mur mur){
        this.Murs.add(mur);
    }

    public ArrayList<Pièce> getPieces() {
        return pieces;
    }
    
    public Pièce getPiece(int i) {
        return pieces.get(i);
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
