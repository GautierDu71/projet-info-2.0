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
        ArrayList<Revetement> Revetements = new ArrayList();
        LectureRevetements(Revetements);
        for (int i = 0; i<19; i++) {
            System.out.println(Revetements.get(i).toString());
        }
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
			
            String line = reader.readLine();
                       
            while (line != null) {
                // creation de variables temporaires
                int separateurs[] = new int[5];
                int indPtVirgule = 0;
                int tempId = 0;
                double tempPrix = 0;
                boolean tempPourSol, tempPourMur,tempPourPlafond;
                char ptVirgule = ';';
                int lecteur = 0;
                int nombre;
                String tempNom;
                char tempChar[] = new char[25];
                
                // affichage de la ligne
                System.out.println(line);
                
                while (lecteur < line.length()) {
                    char iEmeChar = line.charAt(lecteur);
                    if (iEmeChar == ptVirgule) {
                        //on crée un tabeau avec les emplacements des ";"
                        separateurs[indPtVirgule]=lecteur;
                        indPtVirgule++;
                        //System.out.println(separateurs[0]);
                        //System.out.println(indPtVirgule);
                        iEmeChar++;
                    }
                    lecteur++;
                }
                
                // On recupere le nom du revetement
                line.getChars(separateurs[0] + 1, separateurs[1], tempChar, 0);
                tempNom = Arrays.toString(tempChar);
                
                // On recupere els valeurs booleennes du revetement
                tempPourMur = (1 == Character.getNumericValue(line.charAt(separateurs[1]+1)));
                tempPourSol = (1 == Character.getNumericValue(line.charAt(separateurs[2]+1)));
                tempPourPlafond = (1 == Character.getNumericValue(line.charAt(separateurs[3]+1)));
                
                // on recupere les valeurs numeriques :
                for (int i=0; i<line.length(); i++){
                    if (!(";".equals(line.charAt(i)))) {
                        
                        nombre = Character.getNumericValue(line.charAt(i));
                        
                        if (i < separateurs[0]) {
                            // on ajoute le nombre multiplie a la puissance de 10 correspondant
                            // a son rang dans la valeur temporaire
                            tempId += nombre * Math.pow(10, separateurs[0]-i-1);
                        }
                        
                        // idem mais on doit prendre en compte la virgule et l'ignorer
                        if ((i > separateurs[4]) && (i < line.length() - 3)) {
                            if (!(nombre == -1)) {
                                tempPrix += nombre * Math.pow(10, separateurs[4]-(i-2));
                            }
                        }
                        if (i > line.length() - 3) {
                            if (!(nombre == -1)) {
                                tempPrix += nombre * Math.pow(10, separateurs[4]-(i-3));
                            }
                        }
                    }
                }
                
                // ajout le revetement a une Array List
                Revetements.add(new Revetement(tempId, tempNom, tempPourPlafond, tempPourSol, tempPourMur, tempPrix));
                
                // lecture de la prochaine ligne
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
