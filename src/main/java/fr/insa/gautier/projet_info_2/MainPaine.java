/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.gautier.projet_info_2;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

/**
 *
 * @author gserouart01
 */
public class MainPaine extends BorderPane {
    

    private Label lEtage;
    private Button bEtageHaut;
    private Button bEtageBas;
    
    private Label lNombreEtages;
    private Button bNouvelEtage;
    private Button bNouvellePiece;
    private Button bDevis;
    private RadioButton rbPrecision;
    private Button bSauvegarder;
    private Button bCharger;
    
    private DessinCanvas cDessin ;
    private Controleur controleur ;
    
    public MainPaine() {
        
        this.lNombreEtages = new Label("nombre d'étages : 0");
        this.lEtage = new Label("étage actuel : 0");
        this.bEtageBas = new Button("<");
        this.bEtageBas.setOnAction(event ->{
            changementEtage(-1);
        });
        this.bEtageHaut = new Button(">");
        this.bEtageHaut.setOnAction(event ->{
            changementEtage(1);
        });
        
        
        this.cDessin = new DessinCanvas();
        
        
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
        this.rbPrecision = new RadioButton("activer précision");
        

        this.bSauvegarder = new Button("Sauvegarder");
        this.bSauvegarder.setOnAction(event ->{
            bcSauvegarder();
        });this.bCharger = new Button("Charger");
        this.bCharger.setOnAction(event ->{
            bcCharger();
        });
        VBox vbDroite = new VBox(this.lNombreEtages,this.rbPrecision,this.bNouvelEtage,this.bNouvellePiece,this.bDevis,this.bSauvegarder,this.bCharger);
        vbDroite.setSpacing(10);
        
        HBox hbHaut = new HBox(this.bEtageBas,this.lEtage,this.bEtageHaut);
        hbHaut.setAlignment(Pos.CENTER);
        
        this.controleur = new Controleur(this.cDessin,this.lEtage,this.lNombreEtages,this.rbPrecision);
        
        
        
        
        this.setTop(hbHaut);
        this.setRight(vbDroite);
        this.setCenter(this.cDessin);
                
    }
    
    public void changementEtage(int i) {
        this.controleur.changementEtage(i);
    }
    public void bcNouvelEtage(){
        this.controleur.nouvelEtage();
    }
    public void bcNouvellePiece(){
        this.controleur.dessinPiece();
    }
    public void bcDevis(){
        this.controleur.calculDevis(this.controleur.getBatiments(),this.controleur.getBatiments().getEtages());
    }
    public void bcFin(){
        System.out.println("des crêpes");
    }
    public void bcSauvegarder(){

        
        
        ArrayList<Etage> Etages = new ArrayList();
        Etages.add(new Etage(0,2));
        
        ArrayList<Batiment> Batiments = new ArrayList();
        Batiments.add(new Batiment(0));
       
        ArrayList<Coin> Coins = new ArrayList();
        Coins.add(new Coin(0,1,2,Etages.get(0)));
        Coins.add(new Coin(1,3,5,Etages.get(0)));
        
        ArrayList<Mur> Murs = new ArrayList();
        Murs.add(new Mur(Coins.get(0),Coins.get(1)));
        
        ArrayList<Pièce> Pieces = new ArrayList();
        Pieces.add(new Pièce(Coins,new Revetement(1,"nn",true, true, true,3)));
        
        //this.controleur.Sauvegarde(this.controleur.getCoins(),this.controleur.getBatiments().getEtages(),this.controleur.getBatiments(),Murs,Pieces);

    }
    public void bcCharger(){
        System.out.println("Chargement...");
            this.controleur.LectureSauvegarde();
        
    }
}
