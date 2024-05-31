/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.gautier.projet_info_2;

public class Mur {
    
    private int id, portes, fenetres;
    private double separation;
    private Coin pt1, pt2;
    private boolean ext;
    private Revetement rev1, rev2, rev3;
    
    double largeurFenetre = 1.225;
    double hauteurFenetre = 1.65;
    double surfaceFenetre = largeurFenetre * hauteurFenetre;
    
    double largeurPorte = 0.78;
    double hauteurPorte = 2.04;
    double surfacePorte = largeurPorte * hauteurPorte;
    
    public Mur(int id, Coin pt1, Coin pt2,Revetement rev1,Revetement rev2) {
        this.ext = true ;
        this.pt1 = pt1;
        this.pt2 = pt2;
        this.separation = 0;
        this.fenetres = 0;
        this.portes = 0;
        this.rev1 = rev1;
        this.rev2 = rev2;   
        this.id = id;
    }
    //donne la longueur du mur
    public double Longueur(){
        double longueur;
        longueur = Math.sqrt((this.pt1.getX()-this.pt2.getX())*(this.pt1.getX()-this.pt2.getX()) + (this.pt1.getY()-this.pt2.getY())*(this.pt1.getY()-this.pt2.getY()));
        //System.out.println(longueur);
        return longueur / 40;
    }
    //donne la surface du dessous de la separation
    public double Surface(){
        double surface;
        if (this.separation == 0) {
            surface = this.Longueur() * this.pt1.getEtage().getHauteur();
        } else {
            surface = this.Longueur() * this.separation;
        }
    return surface;
    }
    //donne la surface au dessus de la separation si elle existe, sinon donne 0
    public double SurfaceHaut(){
        double surface;
        if (this.separation == 0) {
            surface = 0;
        } else {
        surface = this.Longueur() * (this.pt1.getEtage().getHauteur() - this.separation);
        }
    return surface;
    }
    //donne le prix du mur (non utilise)
    public double prix() {
    double prix = 0;    
    double s = this.Surface();
    double sh = this.SurfaceHaut();
    
    prix += s*this.rev1.getPrixUnitaire()+sh*this.rev2.getPrixUnitaire();
    if (this.ext) {prix += (s+sh)*this.rev3.getPrixUnitaire();}
    return prix ;
    }
    //donne la surface du mur selon quelle zone on souhaite
    public double getSurface(int idSurface) {
        
        switch (idSurface){
            case 1: //surface au dessous de la separation
                return this.Surface() - surfaceFenetre*this.fenetres - surfacePorte*this.portes;
            case 2: //surface au dessus de la separation
                return this.SurfaceHaut() - surfaceFenetre*this.fenetres - surfacePorte*this.portes;
            case 3: //surface totale
                return this.Surface() + this.SurfaceHaut() - surfaceFenetre*this.fenetres - surfacePorte*this.portes;
            default:
                return 0;
        }
    }
    //ajout de portes et fenetres
    public void addPorte() {
        if ((this.Longueur() - this.portes*largeurPorte - this.fenetres*largeurFenetre) > largeurPorte) {
            this.portes ++;
        } else {
            System.out.println("pas assez de largeur pour une porte");
        }
    }
    public void addFenetre() {
        if ((this.Longueur() - this.portes*largeurPorte - this.fenetres*largeurFenetre) > largeurFenetre) {
            this.fenetres ++;
        } else {
            System.out.println("pas assez de largeur pour une fenetre");
        }
    }
    public void removePorte() {
        if (this.portes > 0) {
            this.portes --;
        }
    }
    public void remove() {
        if (this.fenetres > 0) {
            this.fenetres --;
        }
    }
    //getters et setters
    public boolean getExt(){
        return this.ext;
    }
    
    public void setExt(boolean ext) {
        this.ext = ext;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPortes() {
        return portes;
    }

    public void setPortes(int portes) {
        this.portes = portes;
    }

    public int getFenetres() {
        return fenetres;
    }

    public void setFenetres(int fenetres) {
        this.fenetres = fenetres;
    }

    public double getSeparation() {
        return separation;
    }

    public void setSeparation(double separation) {
        this.separation = separation;
    }

    public Coin getPt1() {
        return pt1;
    }

    public void setPt1(Coin pt1) {
        this.pt1 = pt1;
    }

    public Coin getPt2() {
        return pt2;
    }

    public void setPt2(Coin pt2) {
        this.pt2 = pt2;
    }

    public Revetement getRev1() {
        return rev1;
    }

    public void setRev1(Revetement rev1) {
        this.rev1 = rev1;
    }

    public Revetement getRev2() {
        return rev2;
    }

    public void setRev2(Revetement rev2) {
        this.rev2 = rev2;
    }

    public Revetement getRev3() {
        return rev3;
    }

    public void setRev3(Revetement rev3) {
        this.rev3 = rev3;
    }

    @Override
    public String toString() {
        return "Mur{" + "id=" + id + ", portes=" + portes + ", fenetres=" + fenetres + ", separation=" + separation + ", pt1=" + pt1 + ", pt2=" + pt2 + ", ext=" + ext + ", rev1=" + rev1 + ", rev2=" + rev2 + ", rev3=" + rev3 + '}';
    }
    
    
}
