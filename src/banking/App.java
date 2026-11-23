package banking;

import view.LoginView;
import dao.DatabaseConnection;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        DatabaseConnection.getConnection();
        LoginView login = new LoginView();
        stage.setScene(login.getScene(stage));
        stage.setTitle("Pelonomi Banking System");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
