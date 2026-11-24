package view;

import controller.RegistrationController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterView {
    private RegistrationController regController = new RegistrationController();

    public Scene getScene(Stage stage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Label title = new Label("Register New Customer");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        ToggleGroup typeGroup = new ToggleGroup();
        RadioButton individualBtn = new RadioButton("Individual Customer");
        RadioButton companyBtn = new RadioButton("Company Customer");
        individualBtn.setToggleGroup(typeGroup);
        companyBtn.setToggleGroup(typeGroup);
        individualBtn.setSelected(true);

        // Common Customer Fields
        TextField idField = new TextField();
        idField.setPromptText("Customer ID (e.g. IND001)");
        TextField name1 = new TextField();
        name1.setPromptText("First name / Company name");
        TextField name2 = new TextField();
        name2.setPromptText("Surname / Registration No");
        TextField address = new TextField();
        address.setPromptText("Address");
        TextField phone = new TextField();
        phone.setPromptText("Phone");
        TextField email = new TextField();
        email.setPromptText("Email");
        PasswordField pass = new PasswordField();
        pass.setPromptText("Password");

        // --- Individual Details ---
        Label individualLabel = new Label("--- Individual Details ---");
        individualLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2E86C1;");
        TextField dobField = new TextField();
        dobField.setPromptText("Date of Birth (YYYY-MM-DD)");
        TextField nationalIdField = new TextField();
        nationalIdField.setPromptText("National ID (9 digits)");
        TextField occupationField = new TextField();
        occupationField.setPromptText("Occupation");
        TextField employerField = new TextField();
        employerField.setPromptText("Employer name");
        TextField employerAddrField = new TextField();
        employerAddrField.setPromptText("Employer address");

        // --- Company Details (Placed Below Individual Details) ---
        Label companyLabel = new Label("--- Company Details ---");
        companyLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #27AE60;");
        TextField industryField = new TextField();
        industryField.setPromptText("Industry (e.g. Logistics)");
        TextField contactPersonField = new TextField();
        contactPersonField.setPromptText("Contact person name");
        TextField taxNumberField = new TextField();
        taxNumberField.setPromptText("Tax number (e.g. TIN123456)");

        // Update ID hint when switching radio buttons
        typeGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle == individualBtn) {
                idField.setPromptText("Customer ID (e.g. IND001)");
            } else {
                idField.setPromptText("Customer ID (e.g. CO007)");
            }
        });

        Button submit = new Button("Register");
        Button back = new Button("Back to Login");
        Label status = new Label();

        submit.setOnAction(e -> {
            String id = idField.getText().trim();
            if (id.isEmpty() || name1.getText().trim().isEmpty() || name2.getText().trim().isEmpty()) {
                status.setText("Please enter Customer ID, Name, and Surname/Registration No.");
                status.setStyle("-fx-text-fill: red;");
                return;
            }

            boolean ok;
            if (individualBtn.isSelected()) {
                if (dobField.getText().trim().isEmpty() || nationalIdField.getText().trim().isEmpty()) {
                    status.setText("Please fill in Date of Birth and National ID.");
                    status.setStyle("-fx-text-fill: red;");
                    return;
                }
                ok = regController.registerIndividual(
                        id, name1.getText().trim(), name2.getText().trim(),
                        dobField.getText().trim(), nationalIdField.getText().trim(), occupationField.getText().trim(),
                        employerField.getText().trim(), employerAddrField.getText().trim(),
                        address.getText().trim(), phone.getText().trim(), email.getText().trim(), pass.getText());
            } else {
                if (industryField.getText().trim().isEmpty()
                        || contactPersonField.getText().trim().isEmpty()
                        || taxNumberField.getText().trim().isEmpty()) {
                    status.setText("Please fill in Industry, Contact Person, and Tax Number.");
                    status.setStyle("-fx-text-fill: red;");
                    return;
                }
                ok = regController.registerCompany(
                        id, name1.getText().trim(), name2.getText().trim(),
                        industryField.getText().trim(), contactPersonField.getText().trim(), taxNumberField.getText().trim(),
                        address.getText().trim(), phone.getText().trim(), email.getText().trim(), pass.getText());
            }

            if (ok) {
                status.setText("Registered! Go back and login.");
                status.setStyle("-fx-text-fill: green;");
            } else {
                status.setText("Registration failed.");
                status.setStyle("-fx-text-fill: red;");
            }
        });

        back.setOnAction(e -> {
            LoginView login = new LoginView();
            stage.setScene(login.getScene(stage));
        });

        root.getChildren().addAll(
                title, individualBtn, companyBtn,
                idField, name1, name2, address, phone, email, pass,
                individualLabel, dobField, nationalIdField, occupationField, employerField, employerAddrField,
                companyLabel, industryField, contactPersonField, taxNumberField,
                submit, back, status);

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);

        return new Scene(scrollPane, 470, 720);
    }
}