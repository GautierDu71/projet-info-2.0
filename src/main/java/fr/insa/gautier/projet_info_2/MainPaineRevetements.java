package fr.insa.gautier.projet_info_2;

import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author serou
 */
public class MainPaineRevetements extends BorderPane{
    
    private Controleur controleur;
    private Label lEtage;
    private Button bEtageHaut;
    private Button bEtageBas;
    private VBox vbox ;
    ArrayList<Button> boutonsPiece;
    ArrayList<Button> boutonsMurs;
    
    public MainPaineRevetements(Controleur controleur_, Label lEtage_){
        this.controleur = controleur_;
        this.vbox = new VBox();        
        
        this.lEtage = new Label();
        lEtage.textProperty().bind(lEtage_.textProperty());
        lEtage.textProperty().addListener(cl ->{
            menuPrincipal();
        });
        
        this.bEtageBas = new Button("<");
        this.bEtageBas.setOnAction(event ->{
            changementEtage(-1);
        });
        this.bEtageHaut = new Button(">");
        this.bEtageHaut.setOnAction(event ->{
            changementEtage(1);
        });
        
        HBox hbHaut = new HBox(this.bEtageBas,this.lEtage,this.bEtageHaut);
        hbHaut.setAlignment(Pos.CENTER);
        
        this.vbox.setAlignment(Pos.CENTER);
        this.vbox.setSpacing(10.0);
        
        this.setTop(hbHaut);
        this.setCenter(this.vbox);
        
        menuPrincipal();
        
        
    }
    public void changementEtage(int i) {
        this.controleur.changementEtage(i);
        
    }
    
    public void menuPrincipal() {
        this.vbox.getChildren().clear();
        Etage etageActuel = this.controleur.getBatiment().getEtage(this.controleur.getEtageActuel());
        ArrayList<Button> boutonsPiece = new ArrayList();
        for(int i=0 ; i<etageActuel.getPieces().size() ; i++){
            boutonsPiece.add(new Button("Pièce n°"+(i+1)));          
        }
        this.vbox.getChildren().add(new Label("Pièces :"));
        this.vbox.getChildren().addAll(boutonsPiece);
        this.vbox.getChildren().add(new Label("Murs :"));
        
        ArrayList<Button> boutonsMurs = new ArrayList();
        for(int j=0 ; j<etageActuel.getMurs().size() ; j++){
            boutonsMurs.add(new Button("Mur n°"+(j+1)));          
        }
        this.vbox.getChildren().addAll(boutonsMurs);
        
    }
}


