package com.example.pidevskillhub.entities;

import java.time.LocalDate;

public class Actualite {
    private int  id;
    private String titre;

    private String description;
    private String categorie;
    private String image;
    private LocalDate date_publication;

    public Actualite() {
    }

    public int getId() {
        return id;
    }

    public Actualite(int id, String titre, LocalDate date_publication,String description, String categorie, String image) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.categorie = categorie;
        this.image = image;
        this.date_publication = date_publication;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDate getDate_publication() {
        return date_publication;
    }

    public void setDate_publication(LocalDate date_publication) {
        this.date_publication = date_publication;
    }

    @Override
    public String toString() {
        return "Actualite{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", date_publication=" + date_publication+ '\'' +
                ", description='" + description + '\'' +
                ", categorie='" + categorie + '\'' +
                ", image='" + image + '\'' +

                '}';
    }
    public String getImageUrl() {
        return image;
    }
}
