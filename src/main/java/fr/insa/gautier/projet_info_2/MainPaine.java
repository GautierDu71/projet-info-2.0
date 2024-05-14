/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.gautier.projet_info_2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
    
    private Controleur controleur = new Controleur();
    
    private Button bNouvelEtage;
    private Button bNouvellePiece;
    private Button bDevis;
    private Button bFin;
    private Button bSauvegarder;
    private Button bCharger;
    
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
        this.bSauvegarder = new Button("Sauvegarder");
        this.bSauvegarder.setOnAction(event ->{
            bcSauvegarder();
        });this.bCharger = new Button("Charger");
        this.bCharger.setOnAction(event ->{
            bcCharger();
        });
        VBox vbDroite = new VBox(this.bNouvelEtage,this.bNouvellePiece,this.bDevis,this.bFin,this.bSauvegarder,this.bCharger);
        vbDroite.setSpacing(10);
        
        
        
        this.cDessin = new DessinCanvas() ;
        
        
        
        this.setRight(vbDroite);
        this.setCenter(this.cDessin);
                
    }
    
    public void bcNouvelEtage(){
        System.out.println("fpsdojfpjospdojfpsojdfs");
    }
    public void bcNouvellePiece(){
        this.controleur.dessinPiece(cDessin);
    }
    public void bcDevis(){
        System.out.println("fais moi");
    }
    public void bcFin(){
        System.out.println("des crêpes");
    }
    public void bcSauvegarder(){
        System.out.println("des crêpes");
        
        
        ArrayList<Etage> Etages = new ArrayList();
        Etages.add(new Etage(0,2));
        
        ArrayList<Batiment> Batiments = new ArrayList();
        Batiments.add(new Batiment(0,Etages));
       
        ArrayList<Coin> Coins = new ArrayList();
        Coins.add(new Coin(0,1,2,Etages.get(0)));
        Coins.add(new Coin(1,3,5,Etages.get(0)));
        
        ArrayList<Mur> Murs = new ArrayList();
        Murs.add(new Mur(Coins.get(0),Coins.get(1)));
        
        ArrayList<Pièce> Pieces = new ArrayList();
        Pieces.add(new Pièce(Coins,new Revetement(3222)));
        
        Sauvegarde(Coins,Etages,Batiments,Murs,Pieces);

    }
    public void bcCharger(){
        System.out.println("Chargement...");
        LectureSauvegarde();
        
    }
    public void Sauvegarde(ArrayList<Coin> Coins,ArrayList<Etage> Etages,ArrayList<Batiment> Batiments,ArrayList<Mur> Murs,ArrayList<Pièce> Pieces) {
        try (PrintWriter out = new PrintWriter("Sauvegarde.txt")) {
            File myObj = new File("Sauvegarde.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        try {
            FileWriter myWriter = new FileWriter("Sauvegarde.txt");
            BufferedWriter buffer = new BufferedWriter(myWriter);
            
            buffer.flush();
            
            buffer.write("BATIMENTS"+'\r');
            for (int b = 0; b < Batiments.size(); b++) {
                buffer.write(Batiments.get(b).getIdBatiment()+","+Batiments.get(b).getEtages().size()+'\r');
            }
            buffer.write('\r');
            for (int b = 0; b < Batiments.size(); b++) {
                buffer.write("** Batiment "+Batiments.get(b).getIdBatiment()+" **"+'\r'+'\r');
                buffer.write("ETAGES"+'\r');
                for (int i = 0; i < Etages.size(); i++) {
                    buffer.write(Etages.get(i).getId()+","+Etages.get(i).getHauteur()+'\r');
                }
            
                buffer.write('\r'+"COINS"+'\r');
                for (int i = 0; i < Coins.size(); i++){
                    buffer.write(Coins.get(i).getId()+","+Coins.get(i).getX()+","+Coins.get(i).getY()+","+Coins.get(i).getEtage().getId()+'\r');
                }
                
                buffer.write('\r'+"MURS"+'\r');
                for (int i = 0; i < Murs.size(); i++){
                    buffer.write(Murs.get(i).getPt1().getId()+","+Murs.get(i).getPt1().getId()+","+Murs.get(i).getRev1()+","+Murs.get(i).getRev2()+","+Murs.get(i).getRev3()+","+
                            Murs.get(i).getSeparation()+","+Murs.get(i).getPortes()+","+Murs.get(i).getFenetres()+'\r');
                }
                
                buffer.write('\r'+"PIECES"+'\r');
                for (int i = 0; i < Pieces.size(); i++){
                    buffer.write(Pieces.get(i).getSol().getIdRev());
                    buffer.write("ggggg");
                    for (int j = 0; j < Pieces.get(i).getCoins().size(); j++) {
                        buffer.write(","+Pieces.get(i).getCoins().get(j).getId());
                    }
                    buffer.write('\r');
                }
                
                
            }
            buffer.write('\r'+"FIN");
                
            buffer.close();
            System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    
    public void LectureSauvegarde() {
	try {
            // Création d'un fileReader pour lire le fichier
            FileReader fileReader = new FileReader("Sauvegarde.txt");
            
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
                String tempNom = new String(tempChar);
                
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
                // manoeuvre d'arrondis pour eviter les imprecisions a 0.00000001 pres
                tempPrix = (Math.round(tempPrix*100));
                double prix = tempPrix/100;
                
                // ajout le revetement a une Array List
                
                // lecture de la prochaine ligne
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
