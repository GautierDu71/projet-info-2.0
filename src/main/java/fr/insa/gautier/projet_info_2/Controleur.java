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
import java.text.DecimalFormat;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Controleur {
    
    private Label lEtage;
    private Label lNombreEtages;
    private Batiment Batiments ;
    private DessinCanvas canvas;
    private int etageActuel = 0;
    private ArrayList<Coin> Coins = new ArrayList();
    private boolean precision;
    private ArrayList<Mur> Murs = new ArrayList();
    private ArrayList<Revetement> Revetements;

    //private DessinCanvas canvas;
    //états: -1:début 0:défaut 10:dessinpièce
    private int etat = 0;
    //constructeur
    public Controleur(DessinCanvas canvas_, Label lEtage_, Label lNombreEtages_, RadioButton rbPrecision_) {
        
        
        this.lNombreEtages = lNombreEtages_;
        this.lEtage = lEtage_;
        this.canvas = canvas_;
        this.Batiments = new Batiment(0);
        this.Revetements = new ArrayList();
        LectureRevetements(Revetements); //on cree la liiste de revetements
        //creation de revetements par defaut
        Revetement revDefaultSol = revetementDefautSol();
        Revetement revDefaultMur = revetementDefautMur();
        
        this.canvas.heightProperty().addListener((o)->{
            if(this.Batiments.getEtages().size()!=0){drawEtage(this.etageActuel);} 
        });
        this.canvas.widthProperty().addListener((o)->{
            if(this.Batiments.getEtages().size()!=0){drawEtage(this.etageActuel);}
        });
                
        
        //quand on clique , on recupere les coordonnees du clic
        this.canvas.setOnMouseClicked(o->{
           
            int i;
            double x = o.getX();
            double y = o.getY();
            //si on est en mode precision, on recupere les points a coordonnees entieres selon notre systeme de coordonnees
            if(rbPrecision_.isSelected()){
                x = Math.round(x/20)*20;
                y = Math.round(y/20)*20;
                System.out.println(x+" "+y);
            }
            
            Coin coinproche = null ;
            
            System.out.println(this.etat);
            //on recupere si un coin est confondu ou tres proche de l'endroit clique
            for(i=0 ; i<this.Coins.size() ; i++ ){
                    coinproche = coinProche(this.Coins,x,y);              
                }
            //si le point ne depasse pas et qu'on a bien un coin proche qui a ete trouve
            if (coinDedans(x,y,this.etageActuel) || coinproche != null){
                switch (this.etat) {
                    case -1: //si on n'a aps encore d'etage, il faut en creer un avant toute chose
                        System.out.println("pas d'étages");
                        break;
                    case 0: //etat normal
                        break;
                    case 10: //on commence de creer une piece
                        this.canvas.contexte.setLineWidth(5);
                        Pièce pieceActuelle = null;

                        if (coinproche == null) { //si on n'a pas clique sur un point deja existant ou juste a cote
                            if(this.Batiments.getEtage(this.etageActuel).getPieces().isEmpty()){ //et si on cree le premier point de l'etage
                                this.Batiments.getEtage(this.etageActuel).AjouterP(new Pièce(new ArrayList<Coin>(),revDefaultSol)); //on cree une nouvelle piece
                                pieceActuelle = this.Batiments.getEtage(this.etageActuel).getPiece(this.Batiments.getEtage(this.etageActuel).getPieces().size()-1); //qu'on nomme piece actuelle
                                this.Coins.add(new Coin(this.Coins.size(),x,y,this.Batiments.getEtage(this.etageActuel))); //on ajoute le premier point a la liste de coins
                                //System.out.println("taille Coins = "+this.Coins.size());
                                this.Batiments.getEtage(this.etageActuel).getPiece(this.Batiments.getEtage(this.etageActuel).getPieces().size()-1).addCoin(this.Coins.get(this.Coins.size()-1));//et on l'ajoute a la piece
                                //System.out.println("taille Coins = "+this.Coins.size());
                                
                                this.etat = 11;
                            }
                        } else { //si on cliqye sur un point existant on fait la meme chose mais sans creer de point
                            this.Batiments.getEtage(this.etageActuel).AjouterP(new Pièce(new ArrayList<Coin>(),revDefaultSol)); 
                            pieceActuelle = this.Batiments.getEtage(this.etageActuel).getPiece(this.Batiments.getEtage(this.etageActuel).getPieces().size()-1);
                            this.Coins.add(coinproche);
                            this.Batiments.getEtage(this.etageActuel).getPiece(this.Batiments.getEtage(this.etageActuel).getPieces().size()-1).addCoin(this.Coins.get(this.Coins.size()-1));                    
                            this.etat = 11;
                        }
                    break;

                    case 11: //pendant la creation de piece
                    pieceActuelle = this.Batiments.getEtage(this.etageActuel).getPiece(this.Batiments.getEtage(this.etageActuel).getPieces().size()-1);

                    if(coinProche(pieceActuelle.getCoins(),x,y) != null) { //si on a clique sur un point existant
                        this.canvas.contexte.strokeLine(pieceActuelle.getCoin(pieceActuelle.getCoins().size()-1).getX(),pieceActuelle.getCoin(pieceActuelle.getCoins().size()-1).getY(), pieceActuelle.getCoin(0).getX(), pieceActuelle.getCoin(0).getY()); // on dessine lemur entre ce point et le precedent
                        Mur murExistant = MurExistant(pieceActuelle.getCoin(pieceActuelle.getCoins().size()-1),pieceActuelle.getCoin(0)); //on teste si le mur existe entre le clic et le point precedent

                        if (murExistant == null) { //si non on cree le mur
                            this.Batiments.getEtage(this.etageActuel).ajoutMur(new Mur(this.Batiments.getEtage(this.etageActuel).getMurs().size(),pieceActuelle.getCoin(pieceActuelle.getCoins().size()-1) ,pieceActuelle.getCoin(0),revDefaultMur,revDefaultMur));
                        } else { //si non, on dit que le mur est exterieur et on n'en cree pas
                            murExistant.setExt(false);
                        }                               

                        this.etat = 0; //on retourne e l'etat par defaut : on a fini de creer la piece
                        actualiserLabelEtage(); //on affiche l'etage sur lequel on est
                    } else { 
                        this.canvas.contexte.strokeLine(pieceActuelle.getCoin(pieceActuelle.getCoins().size()-1).getX(),pieceActuelle.getCoin(pieceActuelle.getCoins().size()-1).getY() , x, y);

                        if (coinproche == null){ //si on clique pour creer un nouveau point : on cree le point et on l'ajoute dans les listes
                            this.Coins.add(new Coin(this.Coins.size(),x,y,this.Batiments.getEtage(this.etageActuel))); 
                            this.Batiments.getEtage(this.etageActuel).getPiece(this.Batiments.getEtage(this.etageActuel).getPieces().size()-1).addCoin(this.Coins.get(this.Coins.size()-1));
                        } else { //on ajoute le point clique dans les listes
                            this.Batiments.getEtage(this.etageActuel).getPiece(this.Batiments.getEtage(this.etageActuel).getPieces().size()-1).addCoin(coinproche);
                        }
                        Mur murExistant = MurExistant(pieceActuelle.getCoin(pieceActuelle.getCoins().size()-1),pieceActuelle.getCoin(pieceActuelle.getCoins().size()-2));
                        if (murExistant == null) { //si le mur est nouveau, on le cree dans la bonne liste
                           this.Batiments.getEtage(this.etageActuel).ajoutMur(new Mur(this.Batiments.getEtage(this.etageActuel).getMurs().size(),pieceActuelle.getCoin(pieceActuelle.getCoins().size()-1) ,pieceActuelle.getCoin(pieceActuelle.getCoins().size()-2),revDefaultMur,revDefaultMur));
                        } else { 
                           murExistant.setExt(false);
                        }
                    }

                   break ;
                }
            }
        });
    }
    //on recupere le premier revetement pour sol
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
    //on recupere le premier revetement pour mur
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
    //on surligne la piece
    public void showPiece(Pièce piecei){        
        ArrayList<Coin> coinsPiecei = piecei.getCoins();
        this.canvas.contexte.setStroke(Color.web("FFBC1F"));
        this.canvas.contexte.setLineWidth(6);
                for(int j=1 ; j<coinsPiecei.size() ; j++) {
                    this.canvas.contexte.strokeLine(coinsPiecei.get(j-1).getX(), coinsPiecei.get(j-1).getY(), coinsPiecei.get(j).getX(), coinsPiecei.get(j).getY());
                }
                this.canvas.contexte.strokeLine(coinsPiecei.get(0).getX(), coinsPiecei.get(0).getY(), coinsPiecei.get(coinsPiecei.size()-1).getX(), coinsPiecei.get(coinsPiecei.size()-1).getY());
        
    }
    //on surligne le mur
    public void showMur(Mur muri){
        this.canvas.contexte.setStroke(Color.web("FFBC1F"));
        this.canvas.contexte.setLineWidth(6);
        this.canvas.contexte.strokeLine(muri.getPt1().getX(), muri.getPt1().getY(), muri.getPt2().getX(), muri.getPt2().getY());
        
    }
    //lorsqu'on change d'etage, on l'affiche
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
    //on affiche l'etage sur lequel on est
    public void actualiserLabelEtage(){
        this.lEtage.setText("étage actuel : "+this.etageActuel);
    }
    //on affiche le numero de l'etage
    public void actualiserLabelNombreEtage(){
        this.lNombreEtages.setText("nombre d'étages : "+this.Batiments.getEtages().size());
    }
    //on redessine la page
    public void drawEtage(int nEtage){
        int i;
        int j;
        this.canvas.redrawAll();
        this.canvas.contexte.setLineWidth(5);
        this.canvas.contexte.setStroke(Color.GREY);
        if (nEtage-1 >= 0){ //si il existe un etage au dessous, on le dessine en gris poir voir les limites a ne pas depasser
            System.out.println(nEtage-1);
            ArrayList<Mur> mursEtageDessous = this.Batiments.getEtage(nEtage-1).getMurs();
            System.out.println(this.Batiments.getEtage(nEtage-1).toString());
            for (i=0 ; i< mursEtageDessous.size() ; i++) {
                if (mursEtageDessous.get(i).getExt()){
                    this.canvas.contexte.strokeLine(mursEtageDessous.get(i).getPt1().getX(), mursEtageDessous.get(i).getPt1().getY(), mursEtageDessous.get(i).getPt2().getX(), mursEtageDessous.get(i).getPt2().getY());
                    }

            }
        }
        //on dessine les murs de l'etage actuel en noir
        this.canvas.contexte.setStroke(Color.BLACK);
        ArrayList<Pièce> piecesEtage = this.Batiments.getEtage(nEtage).getPieces();
        if (piecesEtage.size() > 0) {
            System.out.println("il y a bien des pieces");
        }
        for (i=0 ; i< piecesEtage.size() ; i++) {
            ArrayList<Coin> coinsPiecei = piecesEtage.get(i).getCoins();
            if (coinsPiecei.size() > 0) {
                System.out.println("il y a bien des coins dans la piece");
            }
            for(j=1 ; j<coinsPiecei.size() ; j++) {
                this.canvas.contexte.strokeLine(coinsPiecei.get(j-1).getX(), coinsPiecei.get(j-1).getY(), coinsPiecei.get(j).getX(), coinsPiecei.get(j).getY());
            }
            this.canvas.contexte.strokeLine(coinsPiecei.get(0).getX(), coinsPiecei.get(0).getY(), coinsPiecei.get(coinsPiecei.size()-1).getX(), coinsPiecei.get(coinsPiecei.size()-1).getY());
        }
    }
    //methode pour determiner si la ou on a clique est proche d'un point existant
    public Coin coinProche(ArrayList<Coin> Coins, double x, double y){
        int i;
        Coin result = null;
        for(i=0;i<Coins.size();i++){ //on regarde si le clic est a moins de 5px d'un point
            if((Math.abs(x-Coins.get(i).getX())<5)&&(Math.abs(y-Coins.get(i).getY())<5)){
                result = Coins.get(i) ;
            }
        }
        return result;
    }
    //on commence le dessin de la piece
    public void dessinPiece() {        
        this.etat = 10 ;        
    }
    public void nouvelEtage() { //on cree un nouvel etage et on l'affiche
        this.Batiments.ajoutEtage();
        this.actualiserLabelNombreEtage();
    }
    public void devis() {        
        this.etat = 10 ;        
    }
    
    //getters et setters
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

    //la rpocedure qui lit le fichier des revetements
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
    //procedure qui fait a savegarde
    public void Sauvegarde() {
        //on cree un nouveau fichier si il n'existe pas
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
    
    try { //on initialise le writer
        FileWriter myWriter = new FileWriter("Sauvegarde.txt");
        BufferedWriter buffer = new BufferedWriter(myWriter);
        //on efface la sauvegarde d'avant
        buffer.flush();
        
        //on fait la liste des batiments
        buffer.write("BATIMENTS"+'\r');
        buffer.write(this.Batiments.getIdBatiment()+","+this.Batiments.getEtages().size()+'\r');
        //buffer.write('\r');
        //dans le premeir batiment
        buffer.write("** Batiment "+Batiments.getIdBatiment()+" **"+'\r' + '\r');
        //on lit par etage
        for (int e = 0; e < this.Batiments.getEtages().size(); e++) {
            buffer.write("ETAGE "+this.Batiments.getEtage(e).getId()+'\r');
            buffer.write(this.Batiments.getEtage(e).getId()+","+this.Batiments.getEtage(e).getHauteur()+'\r'); //on affiche les donnees de l'etage necessaires a sa creation
            buffer.write("Points de l'etage :" + '\r');
            for (int p = 0; p < this.Batiments.getEtage(e).getPieces().size(); p++) { //pour chaque piece, on affiche les donnees necessaires a la creation des points de celle-ci 
                Pièce piece = this.Batiments.getEtage(e).getPiece(p);
                for (int c = 0; c < piece.getCoins().size(); c++) {
                    buffer.write(piece.getCoin(c).getId() + "," + piece.getCoin(c).getX() + "," + piece.getCoin(c).getY() + "," + piece.getCoin(c).getEtage().getId() + '\r');
                }
            }                
            buffer.write("Pieces de l'etage :" + '\r'); //on affiche les donnees necessaires a la creation de chauqe piece
            for (int p = 0; p < this.Batiments.getEtage(e).getPieces().size(); p++) {
                Pièce piece = this.Batiments.getEtage(e).getPiece(p);
                buffer.write(p + "," + piece.getCoins().size() + ",");
                for (int c = 0; c < piece.getCoins().size()-1; c++) {
                    buffer.write(piece.getCoin(c).getId() + "-");
                }
                buffer.write(piece.getCoin(piece.getCoins().size()-1).getId() + "," + piece.getSol().getIdRev()+ '\r');
            }                
            buffer.write("Murs de l'etage :" + '\r'); //on affiche les donnees necessaires a la creation de chaque mur
            for (int m = 0; m < this.Batiments.getEtage(e).getMurs().size(); m++) {
                Mur mur = this.Batiments.getEtage(e).getMur(m);
                buffer.write(mur.getId() + "," + mur.getPt1().getId() + "," + mur.getPt2().getId() + "," + mur.getRev1().getIdRev() + "," + mur.getRev2().getIdRev() + "," + (mur.getExt() ? 1 : 0) + "," + mur.getFenetres() + "," + mur.getPortes() + '\r');
            }
            //buffer.write('\r');
        }
        buffer.write("FIN avec un e");
                
        buffer.close();
        System.out.println("Sauvegarde terminee.");
        } catch (IOException e) {
        System.out.println("Erreur.");
        e.printStackTrace();
    }
}
    
    //la lecture de sauvegarde utilisait un buffer reader, ce qui n'est pas optimal donc on a decide de se focaliser sur d'autres 
    //fonctions plutot que de se battre avec un system qui n'est pas optimal. en connaissance de cause , il aurait fallu utiliser un scanner
    public void LectureSauvegarde() {
        String cheminFichier = "Sauvegarde.txt";
        this.Batiments.clear();
        try {
            // Crée un objet Scanner pour lire le fichier
            Scanner scanner = new Scanner(new File(cheminFichier));
            int etatDonnee = 0;
            ArrayList<Coin> Points = new ArrayList();
            int comptPoints = -1;
            int comptPieces = -1;
            // Lit le contenu du fichier ligne par ligne
            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();
                if (ligne.contains("BATIMENTS")) {
                    etatDonnee = 10;
                } else if (ligne.contains("ETAGE") || (etatDonnee == 20 && !(ligne.contains("Points de l'etage :")))) {
                    etatDonnee = 20;
                } else if (ligne.contains("Points de l'etage :") || (etatDonnee == 30 && !(ligne.contains("Pieces de l'etage :")))) {
                    etatDonnee = 30;            
                    comptPoints++;
                } else if (ligne.contains("Pieces de l'etage :") || (etatDonnee == 40 && !(ligne.contains("Murs de l'etage :")))) {
                    etatDonnee = 40;
                    comptPieces++;
                } else if (ligne.contains("Murs de l'etage :") || (etatDonnee == 50 && !(ligne.contains("Points de l'etage :")))) {
                    etatDonnee = 50;
                } else {
                }
                double[] temp = new double[8];
                int[] ptsPiece = new int[40];
                    
                String[] donnees = ligne.split(","); // Sépare les données par des virgules
                for (int d = 0; d < donnees.length; d++) {
                    //System.out.println(donnee);
                    //System.out.println(etatDonnee);
                    if (donnees[d].contains("-")) {
                        String[] pointsDeLaPiece = donnees[d].split("-");
                        for (int p = 0; p < pointsDeLaPiece.length; p++) {
                            int idPoint = (int)Double.parseDouble(pointsDeLaPiece[p].trim());
                            ptsPiece[p] = idPoint;
                        }
                    } else if (!(donnees[d].contains("E") || donnees[d].contains("e") || etatDonnee == 10)) {
                        double valeur = Double.parseDouble(donnees[d].trim());
                        temp[d] = valeur;
                    } else {
                        temp[0] = 200;
                    }
                }
                switch (etatDonnee) {
                    case 10:
                        break;
                    case 20:
                        if (!(temp[0]==200)) {
                            this.Batiments.ajoutEtage();
                            System.out.println("l'etage est cree yay   ");
                        }
                        break;
                    case 30:
                        if ((int)temp[0] == comptPoints-1) {
                            Points.add(new Coin((int)temp[0], temp[1], temp[2],this.Batiments.getEtage(this.Batiments.getEtages().size()-1)));
                            System.out.println("le point est cree yay   ");
                        }
                        break;
                    case 40:
                        ArrayList<Coin> tempCoins = new ArrayList();
                        for (int c = 0; c < temp[1]; c++) {
                            tempCoins.add(getCoin(Points, ptsPiece[c]));
                        }
                        tempCoins.add(getCoin(Points, ptsPiece[0]));
                        Revetement tempRev = getRev(Revetements, (int)temp[2]);
                        this.Batiments.getEtage(this.Batiments.getEtages().size()-1).AjouterP(new Pièce(tempCoins, tempRev));
                        System.out.println("la piece est cree yay   ");
                        break;
                    case 50:
                        this.Batiments.getEtage(this.Batiments.getEtages().size()-1).AjouterM(new Mur((int)temp[0], getCoin(Points, (int)temp[1]), getCoin(Points, (int)temp[2]), getRev(Revetements, (int)temp[3]), getRev(Revetements, (int)temp[4]), temp[5]==1, (int)temp[6], (int)temp[7]));
                        System.out.println("le mur est cree yay   ");
                        break;
                }
            }

            // Ferme le scanner
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Le fichier n'a pas été trouvé : " + e.getMessage());
        }
        actualiserLabelNombreEtage();
        drawEtage(0);
    }

    
    //detection d'n mur existant selon ses coordonnees
    public Mur MurExistant(Coin coin1, Coin coin2) {
        Mur mur = null;
        ArrayList<Mur> murs = this.Batiments.getEtage(this.etageActuel).getMurs();
        System.out.println(coin1);
        System.out.println(coin2);
        
        if (murs.isEmpty()) {
            System.out.println("La liste de murs est vide.");
            return mur;
        }
        
        //System.out.println(this.Murs.size());
        for (int indexMurs = 0; indexMurs < murs.size(); indexMurs++) {
            if (!murs.isEmpty()) {
                if ((murs.get(indexMurs).getPt1() == coin1 && murs.get(indexMurs).getPt2() == coin2) || (murs.get(indexMurs).getPt1() == coin2 && murs.get(indexMurs).getPt2() == coin1)){ //on regarde dans la liste des murs si chacun des murs a come points les deux points qu'on cherche, dans les deux sens
                    //System.out.println("le mur existe deja");
                    mur = murs.get(indexMurs);
                    break;
                }
            }
            //System.out.println(indexMurs);
        }
        return mur;
    }
    
    public void creationPaneRev() { //la creation d'une nouvelle fenetre pour les revetements
        Stage sRevetements = new Stage();
        sRevetements.setTitle("Revêtements");
        var scene = new Scene(new MainPaineRevetements(this,this.lEtage),800,400);
        sRevetements.setScene(scene);
        sRevetements.setX(2.0);
        sRevetements.show();
    }

    public void calculDevis(Batiment Batiment,ArrayList<Etage> Etages) { //calcul et creation du devis
        try (PrintWriter out = new PrintWriter("Devis.txt")) { //on cree le ficirer si il n'existe pas
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
        
        try { //on initialise le buffer writer
            FileWriter myWriter = new FileWriter("Devis.txt");
            BufferedWriter buffer = new BufferedWriter(myWriter);
            
            double[] Tot = new double[this.Revetements.size()];
            for (int r = 0; r < this.Revetements.size(); r++) {
                Tot[r] = 0;
            }
            buffer.flush();
            
            buffer.write(" " + '\r');
            buffer.write("DEVIS DES PRIX DES REVETEMENTS"+'\r');
            buffer.write(" " + '\r');
            
            buffer.write("-------------------------------------"+'\r');
            buffer.write("** Batiment 1"+" **"+'\r'); //on fait le devis par batiment
            for (int i = 0; i < Etages.size(); i++) { //on regarde pour chaque etage, pour chaque mur, pour chaque revetement
                for (int m = 0; m < Etages.get(i).getMurs().size(); m++) {
                    for (int r = 0; r < this.Revetements.size(); r++) {
                        if (Etages.get(i).getMurs().get(m).getRev1() == null) { //pour chaque revetement du mur, on regarde si c'est le meme que le revetement actuel.
                            Tot[r] += 0; //si non, on ne faot rien
                        } else if(Etages.get(i).getMurs().get(m).getRev1().equals(this.Revetements.get(r))) {
                            Tot[r] += Etages.get(i).getMurs().get(m).getSurface(1)*this.Revetements.get(r).getPrixUnitaire(); //si oui, on calcul le prix
                        }
                        if (Etages.get(i).getMurs().get(m).getRev2() == null) { //idem
                            Tot[r] += 0;
                        } else if(Etages.get(i).getMurs().get(m).getRev2().equals(this.Revetements.get(r))) {
                            Tot[r] += Etages.get(i).getMurs().get(m).getSurface(2)*this.Revetements.get(r).getPrixUnitaire();
                        }
                        if (Etages.get(i).getMurs().get(m).getRev3() == null) { //idem
                            Tot[r] += 0;
                        } else if(Etages.get(i).getMurs().get(m).getRev3().equals(this.Revetements.get(r))) {
                            Tot[r] += Etages.get(i).getMurs().get(m).getSurface(3)*this.Revetements.get(r).getPrixUnitaire();
                        }
                    }
                }
                for (int p = 0; p< Etages.get(i).getPieces().size(); p++) { //pour chauqe piece, on faoit la meme chose
                    for (int r = 0; r < this.Revetements.size(); r++) {
                       if (Etages.get(i).getPiece(p).getSol()== null) {
                            Tot[r] += 0;
                        } else if(Etages.get(i).getPiece(p).getSol().equals(this.Revetements.get(r))) {
                            Tot[r] += Etages.get(i).getPiece(p).surface()*this.Revetements.get(r).getPrixUnitaire();
                        }
                    } 
                }
            }
            DecimalFormat numberFormat = new DecimalFormat("0.000");
            buffer.write("Revetement : "+ '\t'+ '\t'+'\t' + "prix unitaire : "+ '\t'+ "surface : " + '\t' + '\t' + "prix total :" + '\r'); //entete du devis
            for (int r = 0; r < this.Revetements.size(); r++) { //pour chauqe revetement, on ecrit les donnees de nom, prix au metre carre, surface, cout
                Revetement rev = this.Revetements.get(r);
                buffer.write(rev.getNom()+" : "+'\t'); //ajustement si le nom es trop long
                if (!(r==6)) {
                    buffer.write(" "+'\t' + '\t');
                }
                buffer.write(rev.getPrixUnitaire() + " Eur" + '\t'+ '\t'+"S = " + numberFormat.format(Tot[r]/rev.getPrixUnitaire())+ " m^2" + '\t' + '\t' +  numberFormat.format(Tot[r]) + " Eur" + '\r');
            }
            double total = 0;
            for (int t = 0; t < Tot.length; t++) {
                total += Tot[t];
            }
            buffer.write(" " + '\t' + '\t' + '\t' + '\t' + '\t' + '\t' + '\t' + "TOTAL = " + numberFormat.format(total) + " EUR");
            buffer.close();
            System.out.println("Devis termine.");
            } catch (IOException e) {
            System.out.println("Erreur.");
            e.printStackTrace();
        }
    }
    //verification que le point place n'est pas dans le vide. dans cette methode,mieux expliquee dans le compte rendu avec un shema, on trace une demi droite verticale et on compte le nombre de fois ou elle intersecte un mur exterieue. si c'est un nombre impair ette est dedans, sinon non.
    public boolean coinDedans(double x, double y, int k){
        boolean coinDedans = false;
        if (k > 0){
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
        } else {
            coinDedans=true;
        }
        return coinDedans; 
    }
    public Revetement getRev(ArrayList<Revetement> Revetements, int n) {
        Revetement tempRev = revetementDefautSol();        
        for (int r = 0; r < Revetements.size(); r++) {
            if (Revetements.get(r).getIdRev() == n) {
                tempRev = Revetements.get(r);
            }
        }
        return tempRev;
    }
    public Coin getCoin (ArrayList<Coin> Coins, int n) {
        Coin tempCoin = null;        
        for (int r = 0; r < Coins.size(); r++) {
            if (Coins.get(r).getId() == n) {
                tempCoin = Coins.get(r);
            }
        }
        return tempCoin;
    }
    //getters et setetrs
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
