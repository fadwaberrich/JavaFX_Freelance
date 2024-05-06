package com.example.pidevskillhub;

import com.example.pidevskillhub.Services.ActualiteService;
import com.example.pidevskillhub.Services.ReclamationService;
import com.example.pidevskillhub.entities.Actualite;
import com.example.pidevskillhub.entities.Reclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

public class FrontRec_Actu {
    ReclamationService rs =new ReclamationService();
    ActualiteService as= new ActualiteService();
    Alert alert = new Alert(Alert.AlertType.NONE);

    @FXML
    private Button ActualiserREC;

    @FXML
    private AnchorPane an1;

    @FXML
    private TextField FreelancerTextField;
    @FXML
    private TextField txtId;

    @FXML
    private ListView<Reclamation> ListViewRec;
    @FXML
    private Button TrierRec;

    @FXML
    private TextField ObjetTextField;

    @FXML
    private Button btnModifier;
    @FXML
    private Button admin;

    @FXML
    private Button btnSoumettre;

    @FXML
    private Button btnSupprimer;

    @FXML
    private TextArea contenuTextField;
    @FXML
    private GridPane GridPane;
    @FXML
    private VBox vbox;
    @FXML
    private void initialize() throws URISyntaxException {
        List<Reclamation> reclamations = rs.afficherReclamtion();



        // Clear existing items in the ListView
        ListViewRec.getItems().clear();

        // Add reclamations to the ListView
        ListViewRec.getItems().addAll(reclamations);
       afficherActualites();


    }


     @FXML
     void Actualiser(ActionEvent event) {
        // Retrieve reclamations from the service
         List<Reclamation> reclamations = rs.afficherReclamtion();

         // Clear existing items in the ListView
         ListViewRec.getItems().clear();

         // Add reclamations to the ListView
         ListViewRec.getItems().addAll(reclamations);

     }
    @FXML
    void GoToAdmin(ActionEvent event) {
        try {
            AnchorPane xx = FXMLLoader.load(getClass().getResource("/FXML/BackRec_Rec.fxml"));
            an1.getChildren().setAll(xx);
        } catch (IOException e) {
            e.printStackTrace(); // Print the exception details to console for debugging
        }
    }




    @FXML
    void SupprimerReclamation(ActionEvent event) {
        if (txtId.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une réclamation à supprimer.");
            alert.showAndWait();
            return; // Exit the method if the text field is empty
        }
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous vraiment supprimer cet reclamation ?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {

            int id = Integer.parseInt(txtId.getText());
            rs.supprimerReclamation(id);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("recalamation  supprimée avec succés!");
            alert.show();

        }
    }

        @FXML
        void modifierReclamation (ActionEvent event){
            Reclamation r = new Reclamation();
            if (txtId.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner une réclamation à modifier.");
                alert.showAndWait();
                return; // Exit the method if the text field is empty
            }

            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Voulez-vous vraiment modifier cette reclamation ?");
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {

                    String Contenu= contenuTextField.getText();
                    String objet= ObjetTextField.getText();
                int id = Integer.parseInt(txtId.getText());
                r.setId(id);
               r.setContenu(Contenu);
               r.setObjet(objet);



                rs.modifierReclamtion(r);
                }

        }
        @FXML
        private void AjouterReclamation (ActionEvent event){
            // Récupérer les données entrées par l'utilisateur
            String objet = ObjetTextField.getText();
            String contenu = contenuTextField.getText();
            LocalDate date_reclamation= LocalDate.now();
            if (objet.isEmpty() || contenu.isEmpty()) {
                // Afficher un message d'erreur si un champ est vide
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs.");
                alert.showAndWait();
                return; // Sortir de la méthode si un champ est vide
            }
            if (contenuTextField.getText().length() < 10) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("le contenu doit au moin contenir 10 caractéres");
                alert.showAndWait();
                return;

            }

            // Créer une instance de Reclamation avec les données récupérées et une date actuelle
            Reclamation reclamation = new Reclamation();
            reclamation.setObjet(objet);
            reclamation.setContenu(contenu);
            reclamation.setDate_reclamation(date_reclamation);


            // Ajouter la nouvelle réclamation à la base de données en utilisant le service
            rs.ajouterReclamation(reclamation);

            // Rafraîchir la liste des réclamations affichée dans l'interface utilisateur
            ListViewRec.getItems().add(reclamation);

            // Effacer les champs après soumission
            ObjetTextField.setText("");
            contenuTextField.clear();
        }
    @FXML
    public void Trier(ActionEvent event){
        ListViewRec.getItems().clear();
        List< Reclamation> r = rs.trier();
        ListViewRec.getItems().addAll(r);

    }


    private void afficherActualites() throws URISyntaxException {
        List<Actualite> actualites = as.afficherActualites();

        // Clear existing content in the GridPane
        GridPane.getChildren().clear();
        GridPane.setHgap(30);
        GridPane.setVgap(30);


        // Maximum number of columns based on the number of items
        int maxColumns = Math.min(actualites.size(), 2); // Maximum 8 columns or less if fewer items

        // Set the number of columns and rows in the GridPane
        GridPane.getColumnConstraints().clear();
        for (int i = 0; i < maxColumns; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(100.0 / maxColumns); // Equal width columns
            GridPane.getColumnConstraints().add(columnConstraints);
        }
        int row = 0;
        for (Actualite actualite : actualites) {
            VBox vbox = new VBox();
            vbox.setSpacing(10); // Adding spacing between the nodes
            vbox.setStyle(
                    "-fx-background-color: white;" + // Background color
                            "-fx-border-radius: 13;" +       // Border radius
                            "-fx-border-width: 1;" +          // Border width
                            "-fx-border-color: black;"        // Border color
            );
            // Set the preferred width and height of the VBox
            vbox.setPrefWidth(100);
            vbox.setPrefHeight(100);
            // Centering content within the VBox
            vbox.setAlignment(Pos.CENTER);
            // Adding padding to the VBox
            vbox.setPadding(new Insets(10));

            // Assuming actualite.getImage() returns the Base64 encoded image string
            String base64Image = actualite.getImage();
            Image image = null;

            if (base64Image != null && !base64Image.isEmpty()) {
                try {
                    // Decode Base64 string to byte array
                    byte[] imageBytes = Base64.getDecoder().decode(base64Image);

                    // Create Image from byte array
                    image = new Image(new ByteArrayInputStream(imageBytes));
                } catch (IllegalArgumentException e) {
                    // Handle invalid Base64 string
                    e.printStackTrace();
                }
            }

// Create ImageView from Image
            ImageView imageView = new ImageView(image);
            if (image != null) {
                imageView.setFitWidth(180); // Set the width of the image
                imageView.setFitHeight(100); // Set the height of the image
            }


            Label titleLabel = new Label(actualite.getTitre());

            titleLabel.setWrapText(true); // Wrap text within label
            titleLabel.setAlignment(Pos.CENTER); // Center the text horizontally


            Label descriptionLabel = new Label(actualite.getDescription());
            descriptionLabel.setWrapText(true); // Wrap text within label
            descriptionLabel.setAlignment(Pos.CENTER); // Center the text horizontally

            vbox.getChildren().addAll(imageView, titleLabel, descriptionLabel);
            GridPane.add(vbox, row % maxColumns, row / maxColumns);

            row++;
        }



    }



}
