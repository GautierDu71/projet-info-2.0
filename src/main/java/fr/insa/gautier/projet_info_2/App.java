package fr.insa.gautier.projet_info_2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();
        
        // On recupere du fichier tous les revetements
        ArrayList<Revetement> Revetements = new ArrayList();
        LectureRevetements(Revetements);
        
        var controleur = new Controleur();
        var scene = new Scene(new MainPaine());
        stage.setTitle("projet info S2");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
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