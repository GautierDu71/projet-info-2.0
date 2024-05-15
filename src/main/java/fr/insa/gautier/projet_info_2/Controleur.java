/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.gautier.projet_info_2;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;




/**
 *
 * @author gserouart01
 */
public class Controleur {
    
    private Etage etage = new Etage(2,2);
    private DessinCanvas canvas;
    //états: 1:dessinpièce
    private int etat = 0;
    
    
    public Controleur(DessinCanvas canvas) {
        
        this.canvas = canvas ;
        ArrayList<Coin> coins = new ArrayList<Coin>() ;
                
        this.canvas.setOnMouseClicked(o->{
            
           double x = o.getX();
           double y = o.getY();
           
           switch (etat) {
               case 0:
               break;
               
               case 1:
               canvas.contexte.setLineWidth(5);
                     
               if (coins.isEmpty()){
               coins.add(new Coin(coins.size(),x,y,etage));
               }  else if(coinProche(coins,x,y)) {
               canvas.contexte.strokeLine(coins.get(coins.size()-1).getX(),coins.get(coins.size()-1).getY() , coins.get(0).getX(), coins.get(0).getY());
               this.etat = 0;
               } else {
               canvas.contexte.strokeLine(coins.get(coins.size()-1).getX(),coins.get(coins.size()-1).getY() , x, y);
               coins.add(new Coin(coins.size(),x,y,etage));
               } break ;
           
           }
        });
    }    
    
    
    public boolean coinProche(ArrayList<Coin> coins, double x, double y){
        int i;
        boolean result = false;
        for(i=0;i<coins.size();i++){
            if((Math.abs(x-coins.get(i).getX())<5)&&(Math.abs(y-coins.get(i).getY())<5)){
                result = true ;
            }
        }
        return result;
    }
    
        public void dessinPiece() {        
        this.etat = 1 ;        
    }
    
}
