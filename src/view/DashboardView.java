package view;

import controller.AccountController;
import banking.model.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class DashboardView {
    private AccountController accountController = new AccountController();
    private Customer currentCustomer;
    private ListView<String> accountList = new ListView<>();
    private TextArea output = new TextArea();

    public Scene getScene(Stage stage, Customer customer) {
        this.currentCustomer = customer;

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label welcome = new Label("Welcome, " + customer.getFullName() + " (ID: " + customer.getCustomerId() + ")");
        welcome.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label openTitle = new Label("Open New Account:");
        HBox openRow = new HBox(10);
        TextField accNoField = new TextField(); accNoField.setPromptText("Account #");
        ComboBox<String> accTypeBox = new ComboBox<>();
        accTypeBox.getItems().addAll("Savings", "Investment", "Cheque");
        accTypeBox.setValue("Savings");
        TextField openingAmt = new TextField(); openingAmt.setPromptText("Opening amount");
        Button openBtn = new Button("Open Account");
        openRow.getChildren().addAll(accNoField, accTypeBox, openingAmt, openBtn);

        openBtn.setOnAction(e -> {
            String accNo = accNoField.getText().trim();
            if (accNo.isEmpty()) { output.setText("Enter account number"); return; }
            String type = accTypeBox.getValue();
            boolean ok = false;
            try {
                double amt = openingAmt.getText().isEmpty() ? 0 : Double.parseDouble(openingAmt.getText());
                if (type.equals("Savings")) {
                    ok = accountController.openSavingsAccount(currentCustomer, accNo);
                } else if (type.equals("Investment")) {
                    ok = accountController.openInvestmentAccount(currentCustomer, accNo, amt);
                } else {
                    ok = accountController.openChequeAccount(currentCustomer, accNo, "", "");
                }
            } catch (NumberFormatException ex) {
                output.setText("Invalid amount");
                return;
            }
            output.setText(ok ? "Account opened!" : "Failed to open account");
            refreshAccounts();
        });

        Label transTitle = new Label("Deposit / Withdraw:");
        HBox transRow = new HBox(10);
        TextField amountField = new TextField(); amountField.setPromptText("Amount");
        Button depositBtn = new Button("Deposit");
        Button withdrawBtn = new Button("Withdraw");
        Button historyBtn = new Button("View History");
        Button interestBtn = new Button("Apply Interest");
        transRow.getChildren().addAll(amountField, depositBtn, withdrawBtn, historyBtn, interestBtn);

        depositBtn.setOnAction(e -> {
            Account selected = getSelectedAccount();
            if (selected == null) { output.setText("Select an account first"); return; }
            try {
                double amt = Double.parseDouble(amountField.getText());
                boolean ok = accountController.deposit(selected, amt);
                output.setText(ok ? "Deposited BWP " + amt + "\nNew balance: BWP " + selected.getBalance() : "Deposit failed");
            } catch (NumberFormatException ex) {
                output.setText("Invalid amount");
            }
            refreshAccounts();
        });

        withdrawBtn.setOnAction(e -> {
            Account selected = getSelectedAccount();
            if (selected == null) { output.setText("Select an account first"); return; }
            try {
                double amt = Double.parseDouble(amountField.getText());
                boolean ok = accountController.withdraw(selected, amt);
                output.setText(ok ? "Withdrew BWP " + amt + "\nNew balance: BWP " + selected.getBalance() : "Withdrawal failed");
            } catch (NumberFormatException ex) {
                output.setText("Invalid amount");
            }
            refreshAccounts();
        });

        historyBtn.setOnAction(e -> {
            Account selected = getSelectedAccount();
            if (selected == null) { output.setText("Select an account first"); return; }
            StringBuilder sb = new StringBuilder("Transaction History:\n");
            for (Transaction t : selected.getTransactions()) {
                sb.append(t.toString()).append("\n");
            }
            output.setText(sb.toString());
        });

        interestBtn.setOnAction(e -> {
            Account selected = getSelectedAccount();
            if (selected == null) { output.setText("Select an account first"); return; }
            if (selected instanceof SavingsAccount) {
                accountController.applyMonthlyInterest((SavingsAccount) selected);
                output.setText("Interest applied. New balance: BWP " + selected.getBalance());
            } else if (selected instanceof InvestmentAccount) {
                accountController.applyMonthlyInterest((InvestmentAccount) selected);
                output.setText("Interest applied. New balance: BWP " + selected.getBalance());
            } else {
                output.setText("Cheque accounts do not earn interest.");
            }
            refreshAccounts();
        });

        Button logout = new Button("Logout");
        logout.setOnAction(e -> {
            LoginView login = new LoginView();
            stage.setScene(login.getScene(stage));
        });

        output.setPrefHeight(150);

        root.getChildren().addAll(welcome, openTitle, openRow, transTitle, transRow,
                new Label("Your Accounts:"), accountList, output, logout);
        Scene scene = new Scene(root, 800, 600);
        refreshAccounts();
        return scene;
    }

    private void refreshAccounts() {
        List<String> items = new ArrayList<>();
        for (Account a : currentCustomer.getAccounts()) {
            items.add(a.toString());
        }
        accountList.setItems(FXCollections.observableArrayList(items));
    }

    private Account getSelectedAccount() {
        int idx = accountList.getSelectionModel().getSelectedIndex();
        if (idx < 0 || idx >= currentCustomer.getAccounts().size()) return null;
        return currentCustomer.getAccounts().get(idx);
    }
}
