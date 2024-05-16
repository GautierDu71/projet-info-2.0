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
    private Button bSauvegarder;
    private Button bCharger;
    
    private DessinCanvas cDessin ;
    private Controleur controleur ;
    
    public MainPaine() {
        
        this.cDessin = new DessinCanvas();
        this.controleur = new Controleur(this.cDessin);
        
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
        
        
        
        
        
        
        
        this.setRight(vbDroite);
        this.setCenter(this.cDessin);
                
    }
    
    public void bcNouvelEtage(){
        
    }
    public void bcNouvellePiece(){
        this.controleur.dessinPiece();
    }
    public void bcDevis(){
        
    }
    public void bcFin(){
        System.out.println("des crêpes");
    }
    public void bcSauvegarder(){

        
        
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
        Pieces.add(new Pièce(Coins,new Revetement(1,"nn",true, true, true,3)));
        
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
                System.out.println("Fichier cree: " + myObj.getName());
            } else {
                System.out.println("Fichier sauvegarde trouve.");
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
            buffer.write("."+'\r');
            for (int b = 0; b < Batiments.size(); b++) {
                buffer.write("** Batiment "+Batiments.get(b).getIdBatiment()+" **"+'\r'+"."+'\r');
                buffer.write("ETAGES"+'\r');
                for (int i = 0; i < Etages.size(); i++) {
                    buffer.write(Etages.get(i).getId()+","+Etages.get(i).getHauteur()+'\r');
                }
            
                buffer.write("."+'\r'+"COINS"+'\r');
                for (int i = 0; i < Coins.size(); i++){
                    buffer.write(Coins.get(i).getId()+","+Coins.get(i).getX()+","+Coins.get(i).getY()+","+Coins.get(i).getEtage().getId()+'\r');
                }
                
                buffer.write("."+'\r'+"MURS"+'\r');
                for (int i = 0; i < Murs.size(); i++){
                    buffer.write(Murs.get(i).getPt1().getId()+","+Murs.get(i).getPt1().getId()+","+Murs.get(i).getRev1()+","+Murs.get(i).getRev2()+","+Murs.get(i).getRev3()+","+
                            Murs.get(i).getSeparation()+","+Murs.get(i).getPortes()+","+Murs.get(i).getFenetres()+'\r');
                }
                
                buffer.write("."+'\r'+"PIECES"+'\r');
                for (int i = 0; i < Pieces.size(); i++){
                    buffer.write(Pieces.get(i).getSol().getNom());
                    for (int j = 0; j < Pieces.get(i).getCoins().size(); j++) {
                        buffer.write(","+Pieces.get(i).getCoins().get(j).getId());
                    }
                    buffer.write('\r');
                }
                
                
            }
            //buffer.write("."+'\r'+"FIN");
                
            buffer.close();
            System.out.println("Sauvegarde terminee.");
            } catch (IOException e) {
            System.out.println("Erreur.");
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
            
            char tempChar[] = new char[10];
            int etat = 0;
            int batNo = 0;
            int lnDepuisChangement = 0;
            char virgule = ',';
            
            ArrayList<Batiment> Batiments = new ArrayList();
            ArrayList<Etage> Etages = new ArrayList();
            ArrayList<Coin> Coins = new ArrayList();
            ArrayList<Mur> Murs = new ArrayList();
            ArrayList<Pièce> Pieces = new ArrayList();


            while (line != null) {
                // creation de variables temporaires
                int lecteur = 0;
                int indVirgules[];
                int comptVirgules = 0;
                // affichage de la ligne
                System.out.println(line);
                
                switch (line) {
                    case "BATIMENTS":
                        lnDepuisChangement = 0;
                        etat = 1;
                        indVirgules = new int[1];
                        break;
                    case "ETAGES":
                        lnDepuisChangement = 0;
                        etat = 2;
                        indVirgules = new int[1];
                        break;
                    case "COINS":
                        lnDepuisChangement = 0;
                        etat = 3;
                        indVirgules = new int[3];
                        break;
                    case "MURS":
                        lnDepuisChangement = 0;
                        etat = 4;
                        indVirgules = new int[6];
                        break;
                    case "PIECES":
                        lnDepuisChangement = 0;
                        etat = 5;
                        indVirgules = new int[2];
                        break;
                    default:
                        lnDepuisChangement++;
                        indVirgules = new int[1];
                        break;
                }
                System.out.println("taille indVirg = "+indVirgules.length);
                
                while (lecteur < line.length()) {
                    char iEmeChar = line.charAt(lecteur);
                    if (iEmeChar == virgule) {
                        //on crée un tabeau avec les emplacements des ";"
                        System.out.println("lecteur = "+lecteur+" + iemechar = "+iEmeChar+" + comptVirgules = "+comptVirgules);
                        indVirgules[comptVirgules] = lecteur;
                        comptVirgules++;
                    }
                    lecteur++;
                }
                
                if (etat == 0 && lnDepuisChangement > 0) {
                    
                } else if(etat == 1 && lnDepuisChangement > 0) {

                } else if(etat == 2 && lnDepuisChangement > 0) {
                    Etages.add(new Etage(calculInt(line,0,indVirgules[0]),calculDouble(line,indVirgules[0]+1,line.length())));
                } else if(etat == 3 && lnDepuisChangement > 0) {
                
                } else if(etat == 4 && lnDepuisChangement > 0) {
                
                } else if(etat == 5 && lnDepuisChangement > 0) {
                
                }
                
                // manoeuvre d'arrondis pour eviter les imprecisions a 0.00000001 pres
                
                
                // ajout le revetement a une Array List
                
                // lecture de la prochaine ligne
                comptVirgules = 0;
                line = reader.readLine();
                System.out.println("etat = "+etat);
                System.out.println("lnDepuisChangement = "+lnDepuisChangement);
                System.out.println("comptVirgules = "+comptVirgules);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public int calculInt(String line, int debut, int fin) {
        int res = 0;
        for (int i = debut; i < fin; i++){
            res += Character.getNumericValue(line.charAt(i)) * Math.pow(10, fin - debut - i);
        }
        return res;
    }
    public double calculDouble(String line, int debut, int fin) {
        double res = 0;
        int lecteur = 0;
        char point = ',';
        int indPoints = 0;
        int comptPoints = 0;
        
        for (int i = 0; i< line.length(); i++) {
            char iEmeChar = line.charAt(i);
            if (iEmeChar == point) {
                //on crée un tabeau avec les emplacements des ";"
                indPoints = i;
                comptPoints++;
            }            
        }
        res += calculInt(line, debut, fin);
    }
}
