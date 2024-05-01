package com.example.pidevskillhub.Services;

import com.example.pidevskillhub.entities.Reclamation;
import com.example.pidevskillhub.MaConnexion;
import javafx.scene.image.Image;

import com.itextpdf.text.Document;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;




public class ReclamationService {

    Connection cnx;

    public ReclamationService() {
        cnx = MaConnexion.getInstance().getCnx();
    }


    public void ajouterReclamation(Reclamation r) {


            String query = "INSERT INTO reclamation(objet, contenu,statut,date_reclamation) VALUES (?, ?,?,?)";
            try {
                PreparedStatement ste = cnx.prepareStatement(query);
                ste.setString(1, r.getObjet());
                ste.setString(2, r.getContenu());
                ste.setString(3,"En attente");
                LocalDate dateRec= LocalDate.now();
                ste.setDate(4, Date.valueOf(dateRec));
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
        LocalDate date_Rec= LocalDate.now();
        try {
            ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Reclamation r = new Reclamation();
                r.setId(rs.getInt("id"));

                r.setObjet(rs.getString("objet"));
                r.setContenu(rs.getString("contenu"));
                r.setStatut("En attente");
                r.setDate_reclamation(date_Rec);


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

    public List<Reclamation> trier() {
        List<Reclamation> list = new ArrayList<>();
        try {
            String req = "SELECT * FROM reclamation order by objet asc";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {

                Reclamation r = new Reclamation();
                r.setId(rs.getInt(1));
                r.setObjet(rs.getString(2));
                r.setContenu(rs.getString(3));
                r.setStatut(rs.getString(4));
               // r.setDate_reclamation(rs.getDate(5).toLocalDate());

                list.add(r);
            }

        } catch (SQLException e) {

        }
        return list;

    }




        public void generatePDF() {
            Document doc = new Document();
            String sql = "select* from reclamation";

            try {
                Statement prepared = cnx.prepareStatement(sql);
                ResultSet rs = prepared.executeQuery(sql);

                String desktopPath = System.getProperty("user.home") + File.separator + "Desktop";
                String pdfFilePath = desktopPath + File.separator + "Reclamation.pdf";
                PdfWriter.getInstance(doc, new FileOutputStream(pdfFilePath));
                doc.open();


                doc.add(new Paragraph(" "));
                doc.add(new Paragraph(" "));
                doc.add(new Paragraph("                                                     Liste des Reclamations "));
                doc.add(new Paragraph(" "));

                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                PdfPCell cell;


                cell = new PdfPCell(new Phrase("Id de reclamation", FontFactory.getFont("Comic Sans MS", 12)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.GRAY);
                table.addCell(cell);
                ///
                cell = new PdfPCell(new Phrase("Objet", FontFactory.getFont("Comic Sans MS", 12)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.GRAY);
                table.addCell(cell);
                ///
                cell = new PdfPCell(new Phrase("Contenu", FontFactory.getFont("Comic Sans MS", 12)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.GRAY);
                table.addCell(cell);
                ///
                cell = new PdfPCell(new Phrase("Statut", FontFactory.getFont("Comic Sans MS", 12)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.GRAY);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("Date ", FontFactory.getFont("Comic Sans MS", 12)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.GRAY);
                table.addCell(cell);
                ///

                while (rs.next()) {

                    cell = new PdfPCell(new Phrase(rs.getString("id"), FontFactory.getFont("Comic Sans MS", 12)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    table.addCell(cell);
                    ///////
                    cell = new PdfPCell(new Phrase(rs.getString("objet"), FontFactory.getFont("Comic Sans MS", 12)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    table.addCell(cell);
                    ///////
                    cell = new PdfPCell(new Phrase(rs.getString("contenu"), FontFactory.getFont("Comic Sans MS", 12)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    table.addCell(cell);
                    ///////
                    cell = new PdfPCell(new Phrase(rs.getString("statut"), FontFactory.getFont("Comic Sans MS", 12)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    table.addCell(cell);
                    ////////////
                    cell = new PdfPCell(new Phrase(rs.getString("date_reclamation"), FontFactory.getFont("Comic Sans MS", 12)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    table.addCell(cell);
                    ///////


                }

                doc.add(table);
                doc.close();
                File pdfFile = new File(pdfFilePath);
                if (pdfFile.exists()) {
                    Desktop.getDesktop().open(pdfFile);
                } else {
                    System.out.println("Le fichier PDF n'existe pas.");
                }

            } catch (FileNotFoundException ex) {
                Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | DocumentException ex) {
                Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }




