/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.gautier.projet_info_2;

/**
 *
 * @author gserouart01
 */
public class Revetement {
    private int idRev;
    private String nom;
    private boolean pourPlafond, pourSol, pourMur;
    private double prixUnitaire;

    public Revetement(int idRev, String nom, boolean pourPlafond, boolean pourSol, boolean pourMur, double prixUnitaire) {
        this.idRev = idRev;
        this.nom = nom;
        this.pourPlafond = pourPlafond;
        this.pourSol = pourSol;
        this.pourMur = pourMur;
        this.prixUnitaire = prixUnitaire;
    }
    
    public boolean isPourPlafond() {
        return pourPlafond;
    }

    public void setPourPlafond(boolean pourPlafond) {
        this.pourPlafond = pourPlafond;
    }

    public boolean isPourSol() {
        return pourSol;
    }

    public void setPourSol(boolean pourSol) {
        this.pourSol = pourSol;
    }

    public boolean isPourMur() {
        return pourMur;
    }

    public void setPourMur(boolean pourMur) {
        this.pourMur = pourMur;
    }

    public int getIdRev() {
        return idRev;
    }

    public void setIdRev(int idRev) {
        this.idRev = idRev;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Revetement{" + "idRev=" + idRev + ", nom=" + nom + ", pourPlafond=" + pourPlafond + ", pourSol=" + pourSol + ", pourMur=" + pourMur + ", prixUnitaire=" + prixUnitaire + '}';
    }
    
      
}
