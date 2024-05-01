package com.example.pidevskillhub;

import com.example.pidevskillhub.Services.ActualiteService;
import com.example.pidevskillhub.Services.ReclamationService;
import com.example.pidevskillhub.entities.Actualite;
import com.example.pidevskillhub.entities.Reclamation;
import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.*;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;



import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class BackRecRec {
    ReclamationService rs =new ReclamationService();
    ActualiteService as= new ActualiteService();
    Alert alert = new Alert(Alert.AlertType.NONE);

    @FXML
    private Button AjouterActu;

    @FXML
    private Button  GoToFront;


    @FXML
    private Button RecBackAcualiser;
    @FXML
    private Button pdf;
    @FXML
    private Button excel;
    @FXML
    private Button SupprimerRec;
    @FXML
    private ListView<Reclamation> RecListBack;

    @FXML
    private TextField CategorieField;
    @FXML
    private TextField RecID;

    @FXML
    private TextArea DescriptionField;

    @FXML
    private TextField b;

    @FXML
    private Button btnBrowse;

    @FXML
    private TextField titreField;
    @FXML
    private ListView<Actualite> ListViewActu;

   @FXML
   private TextField ActuID;
    @FXML
    private ImageView imageViewActu;
    @FXML
    private AnchorPane an2;

    @FXML
    void GoToFront(ActionEvent event){
        try {
            AnchorPane xx = FXMLLoader.load(getClass().getResource("/FXML/FrontRec_Actu.fxml"));
            an2.getChildren().setAll(xx);
        } catch (IOException e) {
            e.printStackTrace(); // Print the exception details to console for debugging
        }
    }


    @FXML
    void AjouterActualite(ActionEvent event) {
        String titre = titreField.getText();
        LocalDate date_publication =LocalDate.now();
        String description = DescriptionField.getText();
        String categorie = CategorieField.getText();
        String image = null;
        Image javafxImage = imageViewActu.getImage();
        if (javafxImage != null) {
            // Convert the JavaFX Image to BufferedImage
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(javafxImage, null);
            if (bufferedImage != null) {
                // Write the BufferedImage to a byte array
                try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    ImageIO.write(bufferedImage, "png", baos);
                    // Encode the byte array to Base64
                    byte[] imageBytes = baos.toByteArray();
                    image = Base64.getEncoder().encodeToString(imageBytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }





        if (titre.isEmpty() || description.isEmpty() || categorie.isEmpty()) {
            // Display an error message or perform appropriate action
            // For example, show an alert dialog
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Fields");
            alert.setContentText("Please fill in all the fields.");
            alert.showAndWait();
            return; // Exit the method if any field is empty
        }
        else if (DescriptionField.getText().length() < 10) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("la description doit au moin contenir 10 caractéres");
            alert.showAndWait();
            return;

        }


        Actualite actualites=new Actualite();
        actualites.setTitre(titre);
        actualites.setDate_publication(date_publication);
        actualites.setDescription(description);
        actualites.setCategorie(categorie);
        if (image != null) {
            actualites.setImage(image);
        }
      //  actualites.setImage(image);

        // Create an Actualite object with the data entered by the user
        as.ajouterActualite(actualites);


    }

    @FXML
    void Browse(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                // Read the image file and convert it to a byte array
                FileInputStream fis = new FileInputStream(selectedFile);
                byte[] buffer = new byte[(int) selectedFile.length()];
                fis.read(buffer);
                fis.close();

                // Encode the byte array to Base64
                String base64Image = Base64.getEncoder().encodeToString(buffer);

                // Display the image in the ImageView
                Image image = new Image(selectedFile.toURI().toString());
                System.out.println(image);
                imageViewActu.setImage(image);



            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void SupprimerActu(ActionEvent event) {
        if (ActuID.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une actualité à supprimer.");
            alert.showAndWait();
            return; // Exit the method if the text field is empty
        }
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous vraiment supprimer cette actualité ?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {

            int id = Integer.parseInt(ActuID.getText());
            as.supprimerActualite(id);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Actualité  supprimée avec succés!");
            alert.show();

        }
    }

@FXML
    public void ModifierActu(ActionEvent actionEvent) {

        if (ActuID.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une actualité à modifier.");
            alert.showAndWait();
            return; // Exit the method if the text field is empty
        }
    Actualite a=new Actualite();

    alert.setAlertType(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation");
    alert.setHeaderText(null);
    alert.setContentText("Voulez-vous vraiment modifier cette actualité ?");
    Optional<ButtonType> action = alert.showAndWait();
    if (action.get() == ButtonType.OK) {

        String titre= titreField.getText();
        String description= DescriptionField.getText();
        String categorie= CategorieField.getText();
        String image= imageViewActu.toString();
        int id = Integer.parseInt(ActuID.getText());
        a.setId(id);
       a.setTitre(titre);
        a.setDescription(description);
        LocalDate datePublication = LocalDate.now();
        a.setDate_publication(datePublication);
            a.setImage(image);
            a.setCategorie(categorie);



       as.modifierActu(a);
    }

    }
@FXML
    public void ActualiserACt(ActionEvent actionEvent) {
    List<Actualite> actualites = as.afficherActualites();

    // Clear existing items in the ListView
    ListViewActu.getItems().clear();

    // Add reclamations to the ListView
    ListViewActu.getItems().addAll(actualites);
    }
    @FXML
    public void SupprimerRec(ActionEvent event ) {

        if (RecID.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une reclamation à supprimer.");
            alert.showAndWait();
            return; // Exit the method if the text field is empty

        }
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous vraiment supprimer cet reclamation ?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {

            int id = Integer.parseInt(RecID.getText());
            rs.supprimerReclamation(id);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("recalamation  supprimée avec succés!");
            alert.show();

        }
    }


@FXML
    public void ActualiserRec(ActionEvent event ){
    // Retrieve reclamations from the service
    List<Reclamation> reclamations = rs.afficherReclamtion();

    // Clear existing items in the ListView
    RecListBack.getItems().clear();

    // Add reclamations to the ListView
    RecListBack.getItems().addAll(reclamations);
}
 @FXML
    public void  GenererPDF(ActionEvent event){
    rs.generatePDF();
     Alert alert = new Alert(Alert.AlertType.ERROR);
     alert.setTitle("Information Dialog");
     alert.setHeaderText(null);
     alert.setContentText("Votre Fichier  est importé en PDF avec succeés  !");
     alert.showAndWait();


 }
@FXML
    public void GenererExcel(ActionEvent event)
{
    as.generateExcel();
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Information Dialog");
    alert.setHeaderText(null);
    alert.setContentText("Votre Fichier  est importé en Excel avec succeés   !");
    alert.showAndWait();
}
}