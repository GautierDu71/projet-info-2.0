/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.gautier.projet_info_2;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author gserouart01
 */
public class DessinCanvas extends Pane{
    
    private Canvas realCanvas ;
    
    public DessinCanvas() {
        this.realCanvas = new Canvas(this.getWidth(),this.getHeight());
        redrawAll();
    }
    
    public void redrawAll() {
        
        GraphicsContext contexte = this.realCanvas.getGraphicsContext2D();
        contexte.setFill(Color.RED);
        contexte.fillRect(0, 0, this.getWidth(), this.getHeight());
        
    }
    
}
