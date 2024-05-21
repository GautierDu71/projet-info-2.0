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
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;





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
   
    
    
    //private DessinCanvas canvas;
    //états: -1:début 0:défaut 10:dessinpièce
    private int etat = 0;
    
    
    public Controleur(DessinCanvas canvas_, Label lEtage_, Label lNombreEtages_) {
        
        this.lNombreEtages = lNombreEtages_;
        this.lEtage = lEtage_;
        this.canvas = canvas_;
        this.Batiments = new Batiment(0);
        ArrayList<Revetement> Revetements = new ArrayList();
        LectureRevetements(Revetements);
        
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
           Coin coinproche = null ;
           
           System.out.println(this.etat);
           
           switch (this.etat) {
               
               case -1:
                   System.out.println("pas d'étages");
               break;
               
               case 0:
               break;
               
               case 10:
               this.canvas.contexte.setLineWidth(5);
               
               

               for(i=0 ; i<this.Batiments.getEtage(this.etageActuel).getPieces().size() ; i++ ){
                   coinproche = coinProche(this.Batiments.getEtage(this.etageActuel).getPiece(i).getCoins(),x,y);              
               }
               if (coinproche != null){
                   coinproche.setIntersections(coinproche.getIntersections()+1);
               }
               
               
               if (this.Coins.isEmpty()){
                   if (coinproche == null) {
                       if(this.Batiments.getEtage(this.etageActuel).getPieces().size()==0){
                       this.Coins.add(new Coin(this.Coins.size(),x,y,this.Batiments.getEtage(this.etageActuel)));
                       }
                   } else {
                       this.Coins.add(coinproche);
                   }
               }  else if(coinProche(this.Coins,x,y) != null) {
               this.canvas.contexte.strokeLine(this.Coins.get(this.Coins.size()-1).getX(),this.Coins.get(this.Coins.size()-1).getY() , this.Coins.get(0).getX(), this.Coins.get(0).getY());
               this.Batiments.getEtage(this.etageActuel).AjouterP(new Pièce(new ArrayList<Coin>(this.Coins),Revetements.get(0)));
               this.Coins.clear();
               this.etat = 0;
               } else {
                   this.canvas.contexte.strokeLine(this.Coins.get(this.Coins.size()-1).getX(),this.Coins.get(this.Coins.size()-1).getY() , x, y);
                   if (coinproche == null){
                        this.Coins.add(new Coin(this.Coins.size(),x,y,this.Batiments.getEtage(this.etageActuel)));                        
                   } else {
                        this.Coins.add(coinproche);                      
                   }
               }
               
               break ;
           
           }
        });
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
            ArrayList<Pièce> piecesEtageDessous = this.Batiments.getEtage(nEtage-1).getPieces();
            System.out.println(this.Batiments.getEtage(nEtage-1).toString());
            for (i=0 ; i< piecesEtageDessous.size() ; i++) {
                ArrayList<Coin> coinsPiecei = piecesEtageDessous.get(i).getCoins();
                for(j=1 ; j<coinsPiecei.size() ; j++) {
                    this.canvas.contexte.strokeLine(coinsPiecei.get(j-1).getX(), coinsPiecei.get(j-1).getY(), coinsPiecei.get(j).getX(), coinsPiecei.get(j).getY());
                }
                this.canvas.contexte.strokeLine(coinsPiecei.get(0).getX(), coinsPiecei.get(0).getY(), coinsPiecei.get(coinsPiecei.size()-1).getX(), coinsPiecei.get(coinsPiecei.size()-1).getY());
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
        /*
        public void Sauvegarde(ArrayList<Coin> Coins,ArrayList<Etage> Etages,Batiment Batiments,ArrayList<Mur> Murs,ArrayList<Pièce> Pieces) {
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
    } */
    
    
    
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
    
    
}
