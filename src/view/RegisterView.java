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

        // Common fields
        TextField idField = new TextField(); idField.setPromptText("Customer ID (e.g. IND001)");
        TextField name1 = new TextField(); name1.setPromptText("First name / Company name");
        TextField name2 = new TextField(); name2.setPromptText("Surname / Registration No");
        TextField address = new TextField(); address.setPromptText("Address");
        TextField phone = new TextField(); phone.setPromptText("Phone");
        TextField email = new TextField(); email.setPromptText("Email");
        PasswordField pass = new PasswordField(); pass.setPromptText("Password");

        // Individual-specific fields
        Label individualLabel = new Label("Individual Customer Details:");
        individualLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2E86C1;");
        TextField dobField = new TextField(); dobField.setPromptText("Date of Birth (YYYY-MM-DD)");
        TextField nationalIdField = new TextField(); nationalIdField.setPromptText("National ID (9 digits)");
        TextField occupationField = new TextField(); occupationField.setPromptText("Occupation");
        TextField employerField = new TextField(); employerField.setPromptText("Employer name");
        TextField employerAddrField = new TextField(); employerAddrField.setPromptText("Employer address");

        Button submit = new Button("Register");
        Button back = new Button("Back to Login");
        Label status = new Label();

        submit.setOnAction(e -> {
            String id = idField.getText().trim();
            if (id.isEmpty()) { status.setText("Enter customer ID"); return; }
            boolean ok;
            if (individualBtn.isSelected()) {
                ok = regController.registerIndividual(
                        id,
                        name1.getText(),
                        name2.getText(),
                        dobField.getText(),
                        nationalIdField.getText(),
                        occupationField.getText(),
                        employerField.getText(),
                        employerAddrField.getText(),
                        address.getText(),
                        phone.getText(),
                        email.getText(),
                        pass.getText()
                );
            } else {
                ok = regController.registerCompany(id, name1.getText(), name2.getText(),
                        "", "", "", address.getText(), phone.getText(),
                        email.getText(), pass.getText());
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

        root.getChildren().addAll(title, individualBtn, companyBtn,
                idField, name1, name2, address, phone, email, pass,
                individualLabel, dobField, nationalIdField, occupationField,
                employerField, employerAddrField,
                submit, back, status);
        return new Scene(root, 450, 750);
    }
}