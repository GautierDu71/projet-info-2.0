/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.gautier.projet_info_2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;




/**
 *
 * @author gserouart01
 */
public class Controleur {
    
    private Etage etage = new Etage(2,2);
    private DessinCanvas canvas;
    //états: 1:dessinpièce
    private int etat = 0;
    
    
    public Controleur(DessinCanvas canvas) {
        
        ArrayList<Revetement> Revetements = new ArrayList();
        LectureRevetements(Revetements);
        
        this.canvas = canvas ;
        ArrayList<Coin> coins = new ArrayList<Coin>() ;
                
        this.canvas.setOnMouseClicked(o->{
            
           double x = o.getX();
           double y = o.getY();
           
           switch (etat) {
               case 0:
               break;
               
               case 1:
               canvas.contexte.setLineWidth(5);
                     
               if (coins.isEmpty()){
               coins.add(new Coin(coins.size(),x,y,etage));
               }  else if(coinProche(coins,x,y)) {
               canvas.contexte.strokeLine(coins.get(coins.size()-1).getX(),coins.get(coins.size()-1).getY() , coins.get(0).getX(), coins.get(0).getY());
               this.etat = 0;
               } else {
               canvas.contexte.strokeLine(coins.get(coins.size()-1).getX(),coins.get(coins.size()-1).getY() , x, y);
               coins.add(new Coin(coins.size(),x,y,etage));
               } break ;
           
           }
        });
    }    
    
    
    public boolean coinProche(ArrayList<Coin> coins, double x, double y){
        int i;
        boolean result = false;
        for(i=0;i<coins.size();i++){
            if((Math.abs(x-coins.get(i).getX())<5)&&(Math.abs(y-coins.get(i).getY())<5)){
                result = true ;
            }
        }
        return result;
    }
    
        public void dessinPiece() {        
        this.etat = 1 ;        
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
    
}
