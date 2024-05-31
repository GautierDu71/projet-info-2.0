package fr.insa.gautier.projet_info_2;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
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
    
    public MainPaineRevetements(Controleur controleur_, Label lEtage_){       
        this.controleur = controleur_;
        this.Revetements = this.controleur.getRevetements();
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
        int decalage = 0;
        
        for(int i=0 ; i<etageActuel.getPieces().size() ; i++){
            decalage = 0;
            ChoiceBox piecei = new ChoiceBox<>();
            Pièce pieceI = etageActuel.getPiece(i);
            piecei.setOnMouseEntered(event -> {                
                this.controleur.showPiece(pieceI);
            });
            piecei.setOnMouseExited(event ->{
                this.controleur.drawEtage(this.controleur.getEtageActuel());
            });            
            for(int j=0 ; j<this.Revetements.size() ; j++){
                if(this.Revetements.get(j).isPourSol()) {
                    piecei.getItems().add(this.Revetements.get(j).getNom());
                    
                    if(this.Revetements.get(j) == etageActuel.getPiece(i).getSol()){
                        System.out.println("decalage : "+decalage+"j: "+j);
                        piecei.getSelectionModel().select(piecei.getItems().get(j-decalage));
                    }
                    
                } else {decalage++;}
            }
            
            piecei.getSelectionModel().selectedItemProperty().addListener(o->{                
                for(int k = 0 ; k<this.Revetements.size() ; k++){                    
                    if(this.Revetements.get(k).getNom().equals(piecei.getSelectionModel().selectedItemProperty().getValue())){
                        pieceI.setSol(this.Revetements.get(k));
                        break;
                    }
                }
            });
            
            Label lPiecei = new Label("pièce n°" + (i+1) + ": ");
            HBox hBoxPiecei = new HBox(lPiecei,piecei);
            menusPiece.add(hBoxPiecei);
            
        }    
            
            
        
        this.vbox.getChildren().add(new Label("Pièces :"));
        this.vbox.getChildren().addAll(menusPiece);
        this.vbox.getChildren().add(new Label("Murs :"));
        
        ArrayList<HBox> menusMurs = new ArrayList();        
        for(int i=0 ; i<etageActuel.getMurs().size() ; i++){
            Mur murI = etageActuel.getMur(i);
            decalage = 0;
            ChoiceBox muriRev1 = new ChoiceBox<>();
            muriRev1.setOnMouseEntered(event -> {                
                this.controleur.showMur(murI);
            });
            muriRev1.setOnMouseExited(event ->{
                this.controleur.drawEtage(this.controleur.getEtageActuel());
            });
            for(int j=0 ; j<this.Revetements.size() ; j++){
                if(this.Revetements.get(j).isPourMur()) {
                    muriRev1.getItems().add(this.Revetements.get(j).getNom());
                    if(this.Revetements.get(j) == etageActuel.getMur(i).getRev1()){
                        muriRev1.getSelectionModel().select(muriRev1.getItems().get(j-decalage));
                    }
                    
                    
                } else {decalage++;}
            }
            
            muriRev1.getSelectionModel().selectedItemProperty().addListener(o->{                
                for(int k = 0 ; k<this.Revetements.size() ; k++){                    
                    if(this.Revetements.get(k).getNom().equals(muriRev1.getSelectionModel().selectedItemProperty().getValue())){
                        murI.setRev1(this.Revetements.get(k));
                        break;
                    }
                }
            });
            
            decalage = 0;
            ChoiceBox muriRev2 = new ChoiceBox<>();
            muriRev2.setOnMouseEntered(event -> {                
                this.controleur.showMur(murI);
            });
            muriRev2.setOnMouseExited(event ->{
                this.controleur.drawEtage(this.controleur.getEtageActuel());
            });
            for(int j=0 ; j<this.Revetements.size() ; j++){
                if(this.Revetements.get(j).isPourMur()) {
                    muriRev2.getItems().add(this.Revetements.get(j).getNom());
                     if(this.Revetements.get(j) == etageActuel.getMur(i).getRev2()){
                        muriRev2.getSelectionModel().select(muriRev2.getItems().get(j-decalage));
                    }
                } else {decalage++;}
            }
            
            muriRev2.getSelectionModel().selectedItemProperty().addListener(o->{                
                for(int k = 0 ; k<this.Revetements.size() ; k++){                    
                    if(this.Revetements.get(k).getNom().equals(muriRev2.getSelectionModel().selectedItemProperty().getValue())){
                        murI.setRev2(this.Revetements.get(k));
                        break;
                    }
                }
            });
            
            Label lMuri = new Label("mur n°" + (i+1) + ": ");
            HBox hBoxMuri = new HBox(lMuri,muriRev1,muriRev2);
            menusMurs.add(hBoxMuri);
            
        }    
         this.vbox.getChildren().addAll(menusMurs);
    }
}


