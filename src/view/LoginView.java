package view;

import controller.LoginController;
import banking.model.Customer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView {
    private LoginController loginController = new LoginController();

    public Scene getScene(Stage stage) {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Label title = new Label("Pelonomi Bank - Login");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField idField = new TextField();
        idField.setPromptText("Customer ID");
        idField.setMaxWidth(250);

        PasswordField passField = new PasswordField();
        passField.setPromptText("Password");
        passField.setMaxWidth(250);

        Button loginBtn = new Button("Login");
        Button registerBtn = new Button("Register New Customer");
        Label status = new Label();

        loginBtn.setOnAction(e -> {
            String id = idField.getText().trim();
            String pass = passField.getText();
            if (id.isEmpty() || pass.isEmpty()) {
                status.setText("Please fill in all fields.");
                return;
            }
            Customer c = loginController.authenticate(id, pass);
            if (c != null) {
                DashboardView dashboard = new DashboardView();
                stage.setScene(dashboard.getScene(stage, c));
            } else {
                status.setText("Invalid credentials. Try again.");
                status.setStyle("-fx-text-fill: red;");
            }
        });

        registerBtn.setOnAction(e -> {
            RegisterView register = new RegisterView();
            stage.setScene(register.getScene(stage));
        });

        root.getChildren().addAll(title, new Label("Customer ID:"), idField,
                new Label("Password:"), passField, loginBtn, registerBtn, status);
        return new Scene(root, 400, 450);
    }
}