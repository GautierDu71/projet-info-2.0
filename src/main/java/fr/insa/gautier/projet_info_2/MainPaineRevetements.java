package fr.insa.gautier.projet_info_2;

import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
    private ArrayList<Revetement> Revetements;
    
    public MainPaineRevetements(Controleur controleur_, Label lEtage_, ArrayList<Revetement> Revetements_){
        this.Revetements = Revetements_;
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
        ArrayList<HBox> menusPiece = new ArrayList();
        for(int i=0 ; i<etageActuel.getPieces().size() ; i++){
            ChoiceBox piecei = new ChoiceBox<>();
            for(int j=0 ; j<this.Revetements.size() ; j++){
                if(this.Revetements.get(j).isPourSol()) {
                    piecei.getItems().add(this.Revetements.get(j).getNom());
                    System.out.println(etageActuel.getPiece(i).getSol().toString());
                    System.out.println(this.Revetements.get(j));
                    System.out.println("000");
                    if(this.Revetements.get(j) == etageActuel.getPiece(i).getSol()){
                        piecei.getSelectionModel().select(this.Revetements.get(j));
                        System.out.println("lol"+this.Revetements.get(j).toString());
                    }
                }
                
            }
            Label lPiecei = new Label("pièce n°" + (i+1) + ": ");
            HBox hBoxPiecei = new HBox(lPiecei,piecei);
            menusPiece.add(hBoxPiecei);
            
        }    
            
            
        
        this.vbox.getChildren().add(new Label("Pièces :"));
        this.vbox.getChildren().addAll(menusPiece);
        this.vbox.getChildren().add(new Label("Murs :"));
        
        ArrayList<HBox> menusMurs = new ArrayList();        
        for(int i=0 ; i<etageActuel.getMurs().size() ; i++){            
            ChoiceBox muriRev1 = new ChoiceBox<>();
            for(int j=0 ; j<this.Revetements.size() ; j++){
                if(this.Revetements.get(j).isPourMur()) {
                    muriRev1.getItems().add(this.Revetements.get(j).getNom());
                    
                }
            }
            ChoiceBox muriRev2 = new ChoiceBox<>();
            for(int j=0 ; j<this.Revetements.size() ; j++){
                if(this.Revetements.get(j).isPourMur()) {
                    muriRev2.getItems().add(this.Revetements.get(j).getNom());
                }
            }
            Label lMuri = new Label("mur n°" + (i+1) + ": ");
            HBox hBoxMuri = new HBox(lMuri,muriRev1,muriRev2);
            menusMurs.add(hBoxMuri);
            
        }    
         this.vbox.getChildren().addAll(menusMurs);
    }
}


