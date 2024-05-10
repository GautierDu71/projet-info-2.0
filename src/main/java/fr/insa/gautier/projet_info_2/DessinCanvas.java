/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.gautier.projet_info_2;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author gserouart01
 */
public class DessinCanvas extends Canvas{
    
    public DessinCanvas(double largeur, double hauteur) {
        super(largeur,hauteur);
        GraphicsContext contexte = this.getGraphicsContext2D();
        
    }
    
}
