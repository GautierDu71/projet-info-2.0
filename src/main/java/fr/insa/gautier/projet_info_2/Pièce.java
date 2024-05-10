/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.gautier.projet_info_2;
import java.util.ArrayList ;

/**
 *
 * @author gserouart01
 */
public class Pièce {
    private ArrayList<Coin> coins ;
    private Revetement sol ;

    public Pièce(Revetement sol) {
        this.coins = new ArrayList() ;
        this.sol = sol;
    }

   
    public double prix() {        
        return this.sol.getPrixUnitaire()*surface();
    }
    
    
    
    
    public void addCoin(Coin a) {
    this.coins.add(a) ;
    }
    public double surface() {
        double surface = 0 ;
        int i ;
        
        for (i=0 ; i<coins.size()-1 ; i++) {            
        surface = surface + (this.coins.get(i).getX()* this.coins.get(i+1).getY() - this.coins.get(i+1).getX()* this.coins.get(i).getY());    
        }
        surface = surface + (this.coins.get(coins.size()-1).getX()* this.coins.get(0).getY() - this.coins.get(0).getX()* this.coins.get(coins.size()-1).getY());
        
    surface = surface/2 ;
    return surface;
    } 

    @Override
    public String toString() {
        return "Pi\u00e8ce{" + "coins=" + coins + '}';
    }
            
}
