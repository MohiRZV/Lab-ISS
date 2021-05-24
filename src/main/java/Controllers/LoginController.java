package Controllers;

import Domain.Account;
import Domain.UserType;
import Services.AccountService;
import Services.BadCredentialsException;
import Services.Service;
import UI.utils.AlertDisplayer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class LoginController {
    private AccountService accountService;
    private Service service;
    public void setService(AccountService accountService, Service service){
        this.accountService=accountService;
        this.service=service;
    }
    @FXML
    TextField textFieldUsername;
    @FXML
    PasswordField passwordField;

    @FXML
    public void onBtnLogin(){
        String username = textFieldUsername.getText();
        String password = passwordField.getText();

        try {
            Account account=accountService.logIn(username,password);
            if(account.getUserType().equals(UserType.Salesman))
                showMainWindow(account);
            else
                showAdminWindow(account);
        } catch (BadCredentialsException e) {
            AlertDisplayer.showErrorMessage(null, e.getMessage());
        }
    }

    private void showMainWindow(Account account){

        FXMLLoader mloader = new FXMLLoader(
                getClass().getClassLoader().getResource("views/mainView.fxml"));

        try {
            Parent croot=mloader.load();
            ProductViewController productViewController = mloader.getController();
            productViewController.setService(service, account);
            Stage stage=new Stage();
//            stage.setTitle("Window for " +account.getName());
            stage.setScene(new Scene(croot));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showAdminWindow(Account account){
        FXMLLoader mloader = new FXMLLoader(
                getClass().getClassLoader().getResource("views/adminView.fxml"));

        try {
            Parent croot=mloader.load();
            AdminController adminController = mloader.getController();
            adminController.setService(service,accountService);

            Stage stage=new Stage();
//            stage.setTitle("Window for " +account.getName());
            stage.setScene(new Scene(croot));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
