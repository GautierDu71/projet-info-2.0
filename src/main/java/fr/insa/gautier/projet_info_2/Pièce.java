/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.gautier.projet_info_2;
import java.util.ArrayList ;

public class Pièce {
    private ArrayList<Coin> coins ;
    private Revetement sol ;

    public Pièce(ArrayList<Coin> coins, Revetement sol) {
        this.coins = coins;
        this.sol = sol;
    }
    //donne le prix de la piece (non utilise)
    public double prix() {        
        return this.sol.getPrixUnitaire()*surface();
    }
    //ajoute un point à la liste
    public void addCoin(Coin a) {
    this.coins.add(a) ;
    }
    //calcule la surface de la piece quelle que soit sa forme en utilisant une formule necessitant que les points soient places dans l'ordre
    public double surface() {
        double surface = 0 ;
        int i ;
        
        for (i=0 ; i<coins.size()-1 ; i++) {            
        surface += (this.coins.get(i).getX()* this.coins.get(i+1).getY() - this.coins.get(i+1).getX()* this.coins.get(i).getY());    
        }
        //la formule entre le dernier et le premier point
        surface += (this.coins.get(coins.size()-1).getX()* this.coins.get(0).getY() - this.coins.get(0).getX()* this.coins.get(coins.size()-1).getY());
        
    surface = surface/(2*40*40); // le 40 au carre est pour s'alligner au systeme de coordonnees
    return Math.abs(surface);
    } 
    //getters et setters
    @Override
    public String toString() {
        return "Pi\u00e8ce{" + "coins=" + coins + '}';
    }

    public ArrayList<Coin> getCoins() {
        return coins;
    }
    
        public Coin getCoin(int i) {
        return coins.get(i);
    }

    public void setCoins(ArrayList<Coin> coins) {
        this.coins = coins;
    }

    public Revetement getSol() {
        return sol;
    }

    public void setSol(Revetement sol) {
        this.sol = sol;
    }
    
    
            
}
