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
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;





/**
 *
 * @author gserouart01
 */
public class Controleur {
    
     
    private Label lEtage;
    private Label lNombreEtages;
    private Batiment Batiments ;
    private DessinCanvas canvas;
    private int etageActuel = 0;
    private ArrayList<Coin> Coins = new ArrayList();
    private ArrayList<Coin> AllCoins = new ArrayList();
    private boolean precision;
    private ArrayList<Mur> Murs = new ArrayList();
    private ArrayList<Revetement> Revetements;
   
    
    
    //private DessinCanvas canvas;
    //états: -1:début 0:défaut 10:dessinpièce
    private int etat = 0;
    
    
    public Controleur(DessinCanvas canvas_, Label lEtage_, Label lNombreEtages_, RadioButton rbPrecision_) {
        
        
        this.lNombreEtages = lNombreEtages_;
        this.lEtage = lEtage_;
        this.canvas = canvas_;
        this.Batiments = new Batiment(0);
        this.Revetements = new ArrayList();
        LectureRevetements(Revetements);
        
        Revetement revDefaultSol = revetementDefautSol();
        Revetement revDefaultMur = revetementDefautMur();
        
        this.canvas.heightProperty().addListener((o)->{
            if(this.Batiments.getEtages().size()!=0){drawEtage(this.etageActuel);} 
        });
        this.canvas.widthProperty().addListener((o)->{
            if(this.Batiments.getEtages().size()!=0){drawEtage(this.etageActuel);}
        });
                
        
        //this.canvas = canvas_ ;
        this.canvas.setOnMouseClicked(o->{
           
           int i;
           double x = o.getX();
           double y = o.getY();
           
           if(rbPrecision_.isSelected()){
               x = Math.round(x/20)*20;
               y = Math.round(y/20)*20;
               System.out.println(x+" "+y);
           }
           
           
           Coin coinproche = null ;
           
           System.out.println(this.etat);
           for(i=0 ; i<this.Coins.size() ; i++ ){
                     coinproche = coinProche(this.Coins,x,y);              
                }
           
           if (coinDedans(x,y,this.etageActuel) || coinproche != null){
           switch (this.etat) {
               
               case -1:
                   System.out.println("pas d'étages");
               break;
               
               case 0:
               break;
               
               case 10:
                this.canvas.contexte.setLineWidth(5);
                
                Pièce pieceActuelle = null;
               
               
                if (coinproche == null) {
                    if(this.Batiments.getEtage(this.etageActuel).getPieces().size()==0){
                    this.Batiments.getEtage(this.etageActuel).AjouterP(new Pièce(new ArrayList<Coin>(),revDefaultSol));
                    pieceActuelle = this.Batiments.getEtage(this.etageActuel).getPiece(this.Batiments.getEtage(this.etageActuel).getPieces().size()-1);
                    this.Coins.add(new Coin(this.Coins.size(),x,y,this.Batiments.getEtage(this.etageActuel)));
                    System.out.println("taille Coins = "+this.Coins.size());
                    this.Batiments.getEtage(this.etageActuel).getPiece(this.Batiments.getEtage(this.etageActuel).getPieces().size()-1).addCoin(this.Coins.get(this.Coins.size()-1));
                    System.out.println("taille Coins = "+this.Coins.size());
                    
                    this.etat = 11;
                    }
                } else {
                    this.Batiments.getEtage(this.etageActuel).AjouterP(new Pièce(new ArrayList<Coin>(),revDefaultSol));
                    pieceActuelle = this.Batiments.getEtage(this.etageActuel).getPiece(this.Batiments.getEtage(this.etageActuel).getPieces().size()-1);
                    this.Coins.add(coinproche);
                    this.Batiments.getEtage(this.etageActuel).getPiece(this.Batiments.getEtage(this.etageActuel).getPieces().size()-1).addCoin(this.Coins.get(this.Coins.size()-1));                    
                    this.etat = 11;
                }
               
               
               break;
               
               case 11:
               pieceActuelle = this.Batiments.getEtage(this.etageActuel).getPiece(this.Batiments.getEtage(this.etageActuel).getPieces().size()-1);

               /*this.Coins.get(this.Batiments.getEtage(this.etageActuel).getPiece(this.Batiments.getEtage(this.etageActuel).getPieces().size()-1).getCoin(0).getX())*/
               if(coinProche(pieceActuelle.getCoins(),x,y) != null) {
                    
                    this.canvas.contexte.strokeLine(pieceActuelle.getCoin(pieceActuelle.getCoins().size()-1).getX(),pieceActuelle.getCoin(pieceActuelle.getCoins().size()-1).getY(), pieceActuelle.getCoin(0).getX(), pieceActuelle.getCoin(0).getY());
                    Mur murExistant = MurExistant(pieceActuelle.getCoin(pieceActuelle.getCoins().size()-1),pieceActuelle.getCoin(0));

                    if (murExistant == null) {
                       this.Batiments.getEtage(this.etageActuel).ajoutMur(new Mur(pieceActuelle.getCoin(pieceActuelle.getCoins().size()-1) ,pieceActuelle.getCoin(0),revDefaultMur,revDefaultMur));
                   } else {
                       murExistant.setExt(false);
                   }                              
                    //this.AllCoins.addAll(Coins);
                    //this.Coins.clear();
                    this.etat = 0;
                    actualiserLabelEtage();
               } else {
                   this.canvas.contexte.strokeLine(pieceActuelle.getCoin(pieceActuelle.getCoins().size()-1).getX(),pieceActuelle.getCoin(pieceActuelle.getCoins().size()-1).getY() , x, y);
                   
                   if (coinproche == null){
                        this.Coins.add(new Coin(this.Coins.size(),x,y,this.Batiments.getEtage(this.etageActuel))); 
                        this.Batiments.getEtage(this.etageActuel).getPiece(this.Batiments.getEtage(this.etageActuel).getPieces().size()-1).addCoin(this.Coins.get(this.Coins.size()-1));
                   } else {
                        this.Batiments.getEtage(this.etageActuel).getPiece(this.Batiments.getEtage(this.etageActuel).getPieces().size()-1).addCoin(coinproche);
                   }
                   Mur murExistant = MurExistant(pieceActuelle.getCoin(pieceActuelle.getCoins().size()-1),pieceActuelle.getCoin(pieceActuelle.getCoins().size()-2));

                   if (murExistant == null) {
                       this.Batiments.getEtage(this.etageActuel).ajoutMur(new Mur(pieceActuelle.getCoin(pieceActuelle.getCoins().size()-1) ,pieceActuelle.getCoin(pieceActuelle.getCoins().size()-2),revDefaultMur,revDefaultMur));
                   } else {
                       murExistant.setExt(false);
                   }
               }
               
               break ;
           }
           }
        });
    }

    public Revetement revetementDefautSol() {
        Revetement sol = null;
        for(int i=0 ; i<this.Revetements.size() ;i++){
            if(Revetements.get(i).isPourSol()){
                sol = Revetements.get(i);
                break;
            }
        }
        return sol;
    }
    
    public Revetement revetementDefautMur() {
        Revetement mur = null;
        for(int i=0 ; i<this.Revetements.size() ;i++){
            if(Revetements.get(i).isPourMur()){
                mur = Revetements.get(i);
                break;
            }
        }
        return mur;
    }
    
    public void showPiece(Pièce piecei){
        ArrayList<Coin> coinsPiecei = piecei.getCoins();
        this.canvas.contexte.setStroke(Color.LIGHTCORAL);
                for(int j=1 ; j<coinsPiecei.size() ; j++) {
                    this.canvas.contexte.strokeLine(coinsPiecei.get(j-1).getX(), coinsPiecei.get(j-1).getY(), coinsPiecei.get(j).getX(), coinsPiecei.get(j).getY());
                }
                this.canvas.contexte.strokeLine(coinsPiecei.get(0).getX(), coinsPiecei.get(0).getY(), coinsPiecei.get(coinsPiecei.size()-1).getX(), coinsPiecei.get(coinsPiecei.size()-1).getY());
    }
    
    public void showMur(Mur muri){
        this.canvas.contexte.setStroke(Color.LIGHTCORAL);
        this.canvas.contexte.strokeLine(muri.getPt1().getX(), muri.getPt1().getY(), muri.getPt2().getX(), muri.getPt2().getY());
    }
    
    
    public void changementEtage(int i){
    int etageSuivant = this.etageActuel+i;
        if (this.etat == 0){
            if(etageSuivant >= 0 && etageSuivant < this.Batiments.getEtages().size()){
            this.etageActuel = this.etageActuel+i;
            actualiserLabelEtage();
            drawEtage(etageSuivant);
            }
        }
    }
    
    public void actualiserLabelEtage(){
        this.lEtage.setText("étage actuel : "+this.etageActuel);
    }
    
    public void actualiserLabelNombreEtage(){
        this.lNombreEtages.setText("nombre d'étages : "+this.Batiments.getEtages().size());
    }
    
    public void drawEtage(int nEtage){
        int i;
        int j;
        this.canvas.redrawAll();
        
        
        this.canvas.contexte.setStroke(Color.GREY);
        if (nEtage-1 >= 0){
            System.out.println(nEtage-1);
            ArrayList<Mur> mursEtageDessous = this.Batiments.getEtage(nEtage-1).getMurs();
            System.out.println(this.Batiments.getEtage(nEtage-1).toString());
            for (i=0 ; i< mursEtageDessous.size() ; i++) {
                if (mursEtageDessous.get(i).getExt()){
                    this.canvas.contexte.strokeLine(mursEtageDessous.get(i).getPt1().getX(), mursEtageDessous.get(i).getPt1().getY(), mursEtageDessous.get(i).getPt2().getX(), mursEtageDessous.get(i).getPt2().getY());
                    }

            }
        }
        
        this.canvas.contexte.setStroke(Color.BLACK);
        ArrayList<Pièce> piecesEtage = this.Batiments.getEtage(nEtage).getPieces();
            for (i=0 ; i< piecesEtage.size() ; i++) {
                ArrayList<Coin> coinsPiecei = piecesEtage.get(i).getCoins();
                for(j=1 ; j<coinsPiecei.size() ; j++) {
                    this.canvas.contexte.strokeLine(coinsPiecei.get(j-1).getX(), coinsPiecei.get(j-1).getY(), coinsPiecei.get(j).getX(), coinsPiecei.get(j).getY());
                }
                this.canvas.contexte.strokeLine(coinsPiecei.get(0).getX(), coinsPiecei.get(0).getY(), coinsPiecei.get(coinsPiecei.size()-1).getX(), coinsPiecei.get(coinsPiecei.size()-1).getY());
            }
    }
    
    public Coin coinProche(ArrayList<Coin> Coins, double x, double y){
        int i;
        Coin result = null;
        for(i=0;i<Coins.size();i++){
            if((Math.abs(x-Coins.get(i).getX())<5)&&(Math.abs(y-Coins.get(i).getY())<5)){
                result = Coins.get(i) ;
            }
        }
        return result;
    }
    
        public void dessinPiece() {        
        this.etat = 10 ;        
    }
        public void nouvelEtage() {        
        this.Batiments.ajoutEtage();
        this.actualiserLabelNombreEtage();
    }
        public void devis() {        
        this.etat = 10 ;        
    }

    public Batiment getBatiments() {
        return Batiments;
    }

    public void setBatiments(Batiment Batiments) {
        this.Batiments = Batiments;
    }

    public ArrayList<Coin> getCoins() {
        return Coins;
    }

    public void setCoins(ArrayList<Coin> Coins) {
        this.Coins = Coins;
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
                Revetements.add(new Revetement(tempId, tempNom, tempPourPlafond, tempPourSol, tempPourMur, prix));
                
                // lecture de la prochaine ligne
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        
    public void Sauvegarde() {
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
        buffer.write(this.Batiments.getIdBatiment()+","+this.Batiments.getEtages().size()+'\r');
        buffer.write('\r');
        
        buffer.write("** Batiment "+Batiments.getIdBatiment()+" **"+'\r' + '\r');
        
        for (int e = 0; e < this.Batiments.getEtages().size(); e++) {
            buffer.write("ETAGE "+this.Batiments.getEtage(e).getId()+'\r');
            buffer.write(this.Batiments.getEtage(e).getId()+","+this.Batiments.getEtage(e).getHauteur()+'\r'); 
            buffer.write("Points de l'etage :" + '\r');
            for (int p = 0; p < this.Batiments.getEtage(e).getPieces().size(); p++) {
                Pièce piece = this.Batiments.getEtage(e).getPiece(p);
                for (int c = 0; c < piece.getCoins().size(); c++) {
                    buffer.write(piece.getCoin(c).getId() + "," + piece.getCoin(c).getX() + "," + piece.getCoin(c).getY() + "," + piece.getCoin(c).getEtage().getId() + '\r');
                }
            }                
            buffer.write("Pieces de l'etage :" + '\r');
            for (int p = 0; p < this.Batiments.getEtage(e).getPieces().size(); p++) {
                Pièce piece = this.Batiments.getEtage(e).getPiece(p);
                buffer.write(p + "," + piece.getCoins().size() + "," + piece.getSol().getNom() + '\r');
            }                
            buffer.write('\r' + "Murs de l'etage :" + '\r');
            for (int m = 0; m < this.Batiments.getEtage(e).getMurs().size(); m++) {
                Mur mur = this.Batiments.getEtage(e).getMur(m);
                buffer.write(mur.getId() + "," + mur.getPt1().getId() + "," + mur.getPt2().getId() + "," + mur.getRev1().getIdRev() + "," + mur.getRev2().getIdRev() + "," + mur.getExt() + "," + mur.getFenetres() + "," + mur.getPortes() + '\r');
            }
            buffer.write('\r');

        }

        
        buffer.write('\r'+"FIN");
                
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
                int indVirgules[] = new int[7];
                int comptVirgules = 0;
                // affichage de la ligne
                System.out.println(line);
                
                switch (line) {
                    case "BATIMENTS":
                        lnDepuisChangement = 0;
                        etat = 1;
                        break;
                    case "ETAGES":
                        lnDepuisChangement = 0;
                        etat = 2;
                        break;
                    case "COINS":
                        lnDepuisChangement = 0;
                        etat = 3;
                        break;
                    case "MURS":
                        lnDepuisChangement = 0;
                        etat = 4;
                        break;
                    case "PIECES":
                        lnDepuisChangement = 0;
                        etat = 5;
                        break;
                    case ".":
                        break;
                        
                    default:
                        lnDepuisChangement++;
                        break;
                }
                
                while (lecteur < line.length()) {
                    char iEmeChar = line.charAt(lecteur);
                    if (iEmeChar == virgule) {
                        //on crée un tabeau avec les emplacements des ";"
                        indVirgules[comptVirgules] = lecteur;
                        comptVirgules++;
                    }
                    lecteur++;
                }
                
                if (etat == 0 && lnDepuisChangement > 0) {
                    
                } else if(etat == 1 && lnDepuisChangement > 0) {

                } else if(etat == 2 && lnDepuisChangement > 0) {
                    Etages.add(new Etage(calculInt(line,0,indVirgules[0]), Double.parseDouble(line.substring(indVirgules[0]+1, line.length()))));
                    //System.out.println(Etages.get(0));
                } else if(etat == 3 && lnDepuisChangement > 0) {
                
                } else if(etat == 4 && lnDepuisChangement > 0) {
                
                } else if(etat == 5 && lnDepuisChangement > 0) {
                
                }
                
                // manoeuvre d'arrondis pour eviter les imprecisions a 0.00000001 pres
                
                
                // ajout le revetement a une Array List
                
                // lecture de la prochaine ligne
                comptVirgules = 0;
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public int calculInt(String line, int debut, int fin) {
        int res = 0;
        for (int i = debut; i < fin; i++){
            res += Character.getNumericValue(line.charAt(i)) * Math.pow(10, fin - debut - i -1);
        }
        System.out.println(res);
        return res;
    }

    public Mur MurExistant(Coin coin1, Coin coin2) {
        Mur mur = null;
        ArrayList<Mur> murs = this.Batiments.getEtage(this.etageActuel).getMurs();
        System.out.println(coin1);
        System.out.println(coin2);
        
            if (murs.isEmpty()) {
        System.out.println("La liste de murs est vide.");
        return mur;
    }
        
        System.out.println(this.Murs.size());
        for (int indexMurs = 0; indexMurs < murs.size(); indexMurs++) {
            
            if (!murs.isEmpty()) {
                
            if ((murs.get(indexMurs).getPt1() == coin1 && murs.get(indexMurs).getPt2() == coin2) || 
                    (murs.get(indexMurs).getPt1() == coin2 && murs.get(indexMurs).getPt2() == coin1)){
                System.out.println("le mur existe deja");
                mur = murs.get(indexMurs);
                break;
            }}
            //System.out.println(indexMurs);
        }
        return mur;
    }
    
    
     public void creationPaneRev() {
       Stage sRevetements = new Stage();
            sRevetements.setTitle("Revêtements");
            var scene = new Scene(new MainPaineRevetements(this,this.lEtage),400,400);
            sRevetements.setScene(scene);
            sRevetements.setX(2.0);
           sRevetements.show();
    }


    

    public void calculDevis(Batiment Batiment,ArrayList<Etage> Etages) {
        try (PrintWriter out = new PrintWriter("Devis.txt")) {
            File myObj = new File("Devis.txt");
            if (myObj.createNewFile()) {
                System.out.println("Fichier cree: " + myObj.getName());
            } else {
                System.out.println("Fichier Devis trouve.");
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        try {
            FileWriter myWriter = new FileWriter("Devis.txt");
            BufferedWriter buffer = new BufferedWriter(myWriter);
            
            double[] Tot = new double[this.Revetements.size()];
            for (int r = 0; r < this.Revetements.size(); r++) {
                Tot[r] = 0;
            }
            buffer.flush();
            
            buffer.write('\r'+'\r');
            buffer.write("DEVIS DES PRIX DES REVETEMENTS"+'\r');
            buffer.write('\r'+'\r');
            
            buffer.write("-------------------------------------"+'\r');
            buffer.write("** Batiment 1"+" **"+'\r');
            for (int i = 0; i < Etages.size(); i++) {
                for (int m = 0; m < Etages.get(i).getMurs().size(); m++) {
                    for (int r = 0; r < this.Revetements.size(); r++) {
                        if (Etages.get(i).getMurs().get(m).getRev1() == null) {
                            Tot[r] += 0;
                        } else if(Etages.get(i).getMurs().get(m).getRev1().equals(this.Revetements.get(r))) {
                            Tot[r] += Etages.get(i).getMurs().get(m).getSurface(1)*this.Revetements.get(r).getPrixUnitaire();
                        }
                        if (Etages.get(i).getMurs().get(m).getRev2() == null) {
                            Tot[r] += 0;
                        } else if(Etages.get(i).getMurs().get(m).getRev2().equals(this.Revetements.get(r))) {
                            Tot[r] += Etages.get(i).getMurs().get(m).getSurface(2)*this.Revetements.get(r).getPrixUnitaire();
                        }
                        if (Etages.get(i).getMurs().get(m).getRev3() == null) {
                            Tot[r] += 0;
                        } else if(Etages.get(i).getMurs().get(m).getRev3().equals(this.Revetements.get(r))) {
                            Tot[r] += Etages.get(i).getMurs().get(m).getSurface(3)*this.Revetements.get(r).getPrixUnitaire();
                        }
                    }
                }
                for (int p = 0; p< Etages.get(i).getPieces().size(); p++) {
                    for (int r = 0; r < this.Revetements.size(); r++) {
                       if (Etages.get(i).getPiece(p).getSol()== null) {
                            Tot[r] += 0;
                        } else if(Etages.get(i).getPiece(p).getSol().equals(this.Revetements.get(r))) {
                            Tot[r] += Etages.get(i).getPiece(p).surface()*this.Revetements.get(r).getPrixUnitaire();
                        }
                    } 
                }
            }
            buffer.write("Revetement : "+ '\t'+ '\t'+'\t' + "prix unitaire : "+ '\t'+'\t' + "surface : " + '\t' +'\t' + "prix total :" + '\r');
                for (int r = 0; r < this.Revetements.size(); r++) {
                Revetement rev = this.Revetements.get(r);
                buffer.write(rev.getNom()+" : "+'\t');
                if (!(r==6)) {
                    buffer.write(" "+'\t' + '\t');
                }
                buffer.write(rev.getPrixUnitaire() + " Eur" + '\t'+ '\t'+ '\t'+"surface = " + Tot[r]/rev.getPrixUnitaire()+ " m^2" + '\t' + Tot[r] + " Eur" + '\r');
            }
                
            buffer.close();
            System.out.println("Devis termine.");
            } catch (IOException e) {
            System.out.println("Erreur.");
            e.printStackTrace();
        }
    }
    public boolean coinDedans(double x, double y, int k){
        
        boolean coinDedans = false;
        if(k>0){
        ArrayList<Mur> murs = this.Batiments.getEtage(k-1).getMurs();
        int i ;
        int nbmurs = 0;
        for(i=0 ; i<murs.size() ; i++){
            Coin c1 = null;
            Coin c2 = null;
            
            if(murs.get(i).getExt() == true){
            if(murs.get(i).getPt1().getX() != murs.get(i).getPt2().getX()){
                
                if(murs.get(i).getPt1().getX() <= murs.get(i).getPt2().getX()){
                    c1 = murs.get(i).getPt1();
                    c2 = murs.get(i).getPt2();
                } else {
                    c1 = murs.get(i).getPt2();
                    c2 = murs.get(i).getPt1();
                } 
                
                if(c1.getX() != c2.getX()){
                if( (c1.getX() <= x) && (c2.getX() > x) ){
                        if( ((c1.getY() - c2.getY())/(c1.getX() - c2.getX()))*(x-c1.getX())+c1.getY() > y){
                            System.out.println((((c1.getY() - c2.getY())/(c1.getX() - c2.getX()))*(x-c1.getX())+c1.getY())+"est Fxc");
                            nbmurs++;
                        }
                } 
                } 
                
                
                
            }
            }
                
            }
           if(nbmurs % 2 != 0){
            coinDedans = true;            
        }
        } else {coinDedans=true;}
        return coinDedans; 
        }
    
        public Batiment getBatiment() {
            return this.Batiments;
        }
        
        public int getEtageActuel(){
            return this.etageActuel ;
        }
        
        public ArrayList<Revetement> getRevetements(){
            return this.Revetements;
        }

   
}
