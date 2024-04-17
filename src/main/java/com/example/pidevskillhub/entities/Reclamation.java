package com.example.pidevskillhub.entities;

import java.time.LocalDate;

public class Reclamation {
    private int  id;
    private String objet;

    private String contenu;
    private String statut;
    private LocalDate date_reclamation;


    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", objet='" + objet + '\'' +
                ", contenu='" + contenu + '\'' +
                ", statut='" + statut + '\'' +
                ", date_reclamation=" + date_reclamation +
                '}';
    }

    public Reclamation() {
    }

    public Reclamation(int id, String objet, String contenu, String statut, LocalDate date_reclamation) {
        this.id = id;
        this.objet = objet;
        this.contenu = contenu;
        this.statut = statut;
        this.date_reclamation = date_reclamation;
    }

    public int getId() {
        return id;
    }

    public String getObjet() {
        return objet;
    }

    public String getContenu() {
        return contenu;
    }

    public LocalDate getDate_reclamation() {
        return date_reclamation;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setDate_reclamation(LocalDate date_reclamation) {
        this.date_reclamation = date_reclamation;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
