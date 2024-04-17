package com.example.pidevskillhub.Services;

import com.example.pidevskillhub.entities.Reclamation;
import com.example.pidevskillhub.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReclamationService {

    Connection cnx;

    public ReclamationService() {
        cnx = MaConnexion.getInstance().getCnx();
    }


    public void ajouterReclamation(Reclamation r) {


            String query = "INSERT INTO reclamation(objet, contenu) VALUES (?, ?)";
            try {
                PreparedStatement ste = cnx.prepareStatement(query);
                ste.setString(1, r.getObjet());
                ste.setString(2, r.getContenu());
                ste.executeUpdate();
                System.out.println("Reclamation Ajoutée !!");
            } catch (SQLException ex) {
                System.out.println("Error while adding reclamation: " + ex.getMessage());
                ex.printStackTrace(); // Print stack trace for detailed error information
            }
        }



    public List<Reclamation> afficherReclamtion() {

        List<Reclamation> reclamations = new ArrayList<>();
        String sql = "select * from reclamation";
        Statement ste;
        try {
            ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Reclamation r = new Reclamation();
                r.setId(rs.getInt("id"));

                r.setObjet(rs.getString("objet"));
                r.setContenu(rs.getString("contenu"));
                r.setStatut(rs.getString("statut"));


                reclamations.add(r);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return reclamations;

    }
    public void supprimerReclamation(int id) {
        try {
            String req = "DELETE FROM reclamation WHERE id = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            System.out.println("Suppression...");
            ps.setInt(1, id);

            ps.executeUpdate();
            System.out.println("Une ligne supprimée dans la table...");
        } catch (SQLException e) {

        }

    }
    public void modifierReclamtion(Reclamation r) {
        try {
            String req = "UPDATE reclamation SET objet= ?, contenu = ? WHERE id= ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, r.getObjet());
            ps.setString(2, r.getContenu());

            ps.setInt(3, (int) r.getId());
            System.out.println("Modification...");
            ps.executeUpdate();

            System.out.println("Une ligne modifiée dans la table...");
        } catch (SQLException e) {

        }

    }




}
