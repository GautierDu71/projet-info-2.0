/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.gautier.projet_info_2;

import java.util.ArrayList;

public class Batiment {
    
    int idBatiment;
    private ArrayList<Etage> etages ;
    //constructeur
    public Batiment(int idBatiment) {
        this.idBatiment = idBatiment;
        this.etages = new ArrayList<Etage>();
    }
    //getters et setters
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
    
    public Etage getEtage(int i) {
        return etages.get(i);
    }
    //pour ajouter un etage a la liste
    public void ajoutEtage() {
        this.etages.add(new Etage(this.etages.size(),4));
    }
    
    public void clear() {
        this.etages.clear();
    }
    
    @Override
    public String toString() {
        return "Batiment{" + "idBatiment=" + idBatiment + ", etages=" + etages + '}';
    }
    
}
