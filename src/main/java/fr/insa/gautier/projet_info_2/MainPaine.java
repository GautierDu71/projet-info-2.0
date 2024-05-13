/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.gautier.projet_info_2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.lang.Character;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author gserouart01
 */
public class MainPaine extends BorderPane {
    
    private Button bNouvelEtage;
    private Button bNouvellePiece;
    private Button bDevis;
    private Button bFin;
    
    private DessinCanvas cDessin;
    
    public MainPaine() {
        
        this.bNouvelEtage = new Button("Nouvel étage");
        this.bNouvelEtage.setOnAction(event ->{
            bcNouvelEtage();
        });
        this.bNouvellePiece = new Button("Nouvelle pièce");
        this.bNouvellePiece.setOnAction(event ->{
            bcNouvellePiece();
        });
        this.bDevis = new Button("Devis");
        this.bDevis.setOnAction(event ->{
            bcDevis();
        });
        this.bFin = new Button("Fin");
        this.bFin.setOnAction(event ->{
            bcFin();
        });
        VBox vbDroite = new VBox(this.bNouvelEtage,this.bNouvellePiece,this.bDevis,this.bFin);
        vbDroite.setSpacing(10);
        
        
        
        this.cDessin = new DessinCanvas() ;
        
        
        
        this.setRight(vbDroite);
        this.setCenter(this.cDessin);
                
    }
    
    public void bcNouvelEtage(){
        System.out.println("fpsdojfpjospdojfpsojdfs");
    }
    public void bcNouvellePiece(){
        System.out.println("suzette");
    }
    public void bcDevis(){
        System.out.println("fais moi");
    }
    public void bcFin(){
        System.out.println("des crêpes");
    }
    
}
