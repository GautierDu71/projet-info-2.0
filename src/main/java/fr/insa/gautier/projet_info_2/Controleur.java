/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.gautier.projet_info_2;

import java.util.ArrayList;

/**
 *
 * @author gserouart01
 */
public class Controleur {
    
    private ArrayList<Coin> coins = new ArrayList<Coin>() ;
    private Etage etage = new Etage(2,2);
    
    
    public void dessinPiece(DessinCanvas canvas) {
        
        canvas.contexte.setLineWidth(20);
        
        canvas.setOnMouseClicked((o)->{
           double x = o.getX();
           double y = o.getY();
           this.coins.add(new Coin(coins.size(),x,y,etage));
           canvas.contexte.strokeLine(x+20, y+20, x, y);
        });
    }
}
