/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.gautier.projet_info_2;

import java.util.ArrayList;

/**
 *
 * @author emarx02
 */
public class Batiment {
    int idBatiment;
    private ArrayList<Etage> etages ;

    public Batiment(int idBatiment, ArrayList<Etage> etages) {
        this.idBatiment = idBatiment;
        this.etages = etages;
    }

    public int getIdBatiment() {
        return idBatiment;
    }

    public void setIdBatiment(int idBatiment) {
        this.idBatiment = idBatiment;
    }

    public ArrayList<Etage> getEtages() {
        return etages;
    }

    public void setEtages(ArrayList<Etage> etages) {
        this.etages = etages;
    }

    @Override
    public String toString() {
        return "Batiment{" + "idBatiment=" + idBatiment + ", etages=" + etages + '}';
    }
    
}
