package com.example.pidevskillhub.Services;

import com.example.pidevskillhub.MaConnexion;
import com.example.pidevskillhub.entities.Actualite;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


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

    public void modifierActu(Actualite a) {
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

    }

    public void generateExcel() {
        String sql = "select * from actualite";
        Statement ste;
        try {

            ste = cnx.prepareStatement(sql);
            ResultSet rs = ste.executeQuery(sql);
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("Actualités ");
            HSSFRow header = sheet.createRow(0);


            //cell.setCellStyle(style);

            header.createCell(0).setCellValue("Titre");
            header.createCell(1).setCellValue("Date Publication");
            header.createCell(2).setCellValue("Description");
            header.createCell(3).setCellValue("Categorie");
            header.createCell(4).setCellValue("Image");
            int index = 1;
            while (rs.next()) {
                HSSFRow row = sheet.createRow(index);


                row.createCell(0).setCellValue(rs.getString("titre"));
                row.createCell(1).setCellValue(rs.getString("date_publication"));
                row.createCell(2).setCellValue(rs.getString("description"));
                row.createCell(3).setCellValue(rs.getString("categorie"));
                String image = rs.getString("image");
                if (image.length() > 32767) {
                    image = image.substring(0, 32767);
                }
                row.createCell(4).setCellValue(image);
                index++;
            }

            String desktopPath = System.getProperty("user.home") + File.separator + "Desktop";
            String filePath = desktopPath + File.separator + "Actualites.csv";
            FileOutputStream fileOut = new FileOutputStream(filePath);
            wb.write(fileOut);
            fileOut.close();


            File excelFile = new File(filePath);
            if (excelFile.exists()) {
                Desktop.getDesktop().open(excelFile);
            } else {
                System.out.println("Le fichier Excel n'existe pas.");
            }


            ste.close();
            rs.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }
}
