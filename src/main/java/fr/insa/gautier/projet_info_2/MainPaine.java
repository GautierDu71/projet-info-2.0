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
        ArrayList<Revetement> Revetements = new ArrayList();
        LectureRevetements(Revetements);
        System.out.println(Revetements.get(0).toString());
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
    
    public void LectureRevetements(ArrayList<Revetement> Revetements) {
	try {
            // Création d'un fileReader pour lire le fichier
            FileReader fileReader = new FileReader("CatalogueRevetements.txt");
            
            // Création d'un bufferedReader qui utilise le fileReader
            BufferedReader reader = new BufferedReader(fileReader);
			
            // une fonction à essayer pouvant générer une erreur
            String line = reader.readLine();
            
            int separateurs[] = new int[5];
            int compt = 0;
            int tempId = 0;
            double tempPrix = 0;
            boolean tempPourSol, tempPourMur,tempPourPlafond;
            String tempNom;
            char tempChar[] = new char[20];
            int faireUnNouveau = 0;
            int indice = 0;
                        
            while (line != null) {
                // affichage de la ligne
                System.out.println(line);
                
                for (int i=0; i<line.length(); i++) {
                    if (line.charAt(i)).equals(";")) {
                        //on crée un tabeau avec les emplacements des ";"
                        separateurs[compt]=i;
                        compt++;
                        System.out.println(separateurs[0]);
                    }
                }
                line.getChars(separateurs[0], separateurs[1], tempChar, 0);
                tempNom = Arrays.toString(tempChar);
                tempPourMur = ("1".equals(line.charAt(separateurs[1]+1)));
                tempPourSol = ("1".equals(line.charAt(separateurs[2]+1)));
                tempPourPlafond = ("1".equals(line.charAt(separateurs[3]+1)));
                
                for (int i=0; i<line.length(); i++){
                    if (!(";".equals(line.charAt(i)))) {
                        if (i < separateurs[0]) {
                            tempId += line.charAt(i) * Math.pow(10, separateurs[0]-i);
                        }
                        if (i > separateurs[4]) {
                            tempPrix +=line.charAt(i) * Math.pow(10, separateurs[4]-(i+2));
                        }
                    }
                }
                for (int i = 0; i < indice; i++){
                    if (!(Revetements.get(i).getIdRev()==tempId)) {
                        faireUnNouveau = 1;
                    } else {
                        faireUnNouveau = 0;
                    }
                }
                
                Revetements.add(new Revetement(tempId, tempNom, tempPourPlafond, tempPourSol, tempPourMur, tempPrix));
                
                // lecture de la prochaine ligne
                line = reader.readLine();
                indice++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
