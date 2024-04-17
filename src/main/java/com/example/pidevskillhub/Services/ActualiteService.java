package com.example.pidevskillhub.Services;

import com.example.pidevskillhub.MaConnexion;
import com.example.pidevskillhub.entities.Actualite;


import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.pidevskillhub.entities.Reclamation;
import javafx.scene.image.Image;

public class ActualiteService {

    private Connection cnx;

    public ActualiteService() {
        cnx = MaConnexion.getInstance().getCnx();
    }

    public void ajouterActualite(Actualite actualite) {
        String query = "INSERT INTO actualite(titre, date_publication, description, categorie, image) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ste = cnx.prepareStatement(query);
            ste.setString(1, actualite.getTitre());
            ste.setDate(2, Date.valueOf(actualite.getDate_publication()));
            ste.setString(3, actualite.getDescription());
            ste.setString(4, actualite.getCategorie());
            // Convert the Image to byte array before inserting into the database
            ste.setString(5, actualite.getImageUrl());
            ste.executeUpdate();
            System.out.println("Actualité ajoutée !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'ajout de l'actualité : " + ex.getMessage());
        }
    }

    public List<Actualite> afficherActualites() {
        List<Actualite> actualites = new ArrayList<>();
        String sql = "SELECT * FROM actualite";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Actualite actualite = new Actualite(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getDate("date_publication").toLocalDate(),
                        rs.getString("description"),
                        rs.getString("categorie"),

                        rs.getString("image")
                );
                actualites.add(actualite);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des actualités : " + ex.getMessage());
        }
        return actualites;
    }

    public void supprimerActualite(int id) {
        try {
            String req = "DELETE FROM actualite WHERE id = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            System.out.println("Suppression...");
            ps.setInt(1, id);

            ps.executeUpdate();
            System.out.println("Une ligne supprimée dans la table...");
        } catch (SQLException e) {

        }

    }

    public void modifierActu(Actualite a ) {
        try {
            String req = "UPDATE actualite SET titre=?, description = ?,categorie=?, image=?  WHERE id= ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, a.getTitre());
            ps.setString(2, a.getDescription());
            ps.setString(3, a.getCategorie());
            ps.setString(4, a.getImage());

            ps.setInt(5, (int) a.getId());
            System.out.println("Modification...");
            ps.executeUpdate();

            System.out.println("Une ligne modifiée dans la table...");
        } catch (SQLException e) {

        }

    }   // Add methods for update and delete if needed
}
