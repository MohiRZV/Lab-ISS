package Controllers;

import Domain.Product;
import Domain.UserType;
import Services.AccountService;
import Services.Service;
import UI.utils.AlertDisplayer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AdminController {
    ObservableList<Product> productsModel = FXCollections.observableArrayList();
    private Service service;
    private AccountService accountService;

    public void setService(Service service, AccountService accountService){
        this.service=service;
        this.accountService=accountService;
        init();
    }

    @FXML
    TextField textFieldCantitate;
    @FXML
    TextField textFieldDenumire;
    @FXML
    TextField textFieldProducator;
    @FXML
    TextField textFieldPret;
    @FXML
    ComboBox<Product> comboBox;
    @FXML
    public void initialize(){
        comboBox.setItems(productsModel);
    }
    private void init(){
        initModelProducts();
    }
    private void initModelProducts(){
        Iterable<Product> products= null;
        products = service.getAllProducts();
        List<Product> productList= StreamSupport.stream(products.spliterator(),false).collect(Collectors.toList());
        productsModel.setAll(productList);
    }
    @FXML
    public void onBtnAdaugaProdus(){
        String denumire=textFieldDenumire.getText();
        String producator=textFieldProducator.getText();
        Double cantitate;
        Double pret;
        try{
            cantitate=Double.parseDouble(textFieldCantitate.getText());
            pret=Double.parseDouble(textFieldPret.getText());
        }catch (Exception ex){
            AlertDisplayer.showErrorMessage(null, "Pretul si cantitatea trebuie sa fie numere reale!");
            return;
        }
        try {
            service.addProduct(cantitate, denumire, pret, producator);
            AlertDisplayer.showMessage(null, Alert.AlertType.CONFIRMATION,"Add successfull","Produsul a fost adaugat cu succes!");
        }catch (Exception ex){
            AlertDisplayer.showErrorMessage(null, ex.getMessage()+"\nSomething went wrong!");
        }
    }

    @FXML
    public void onBtnModificaProdus(){
        Double cantitate=null;
        Double pret=null;
        String denumire=textFieldDenumire.getText();
        String producator=textFieldProducator.getText();
        try{
            cantitate=Double.parseDouble(textFieldCantitate.getText());
            pret=Double.parseDouble(textFieldPret.getText());
        }catch (Exception ex){
            AlertDisplayer.showErrorMessage(null, "Pretul si cantitatea trebuie sa fie numere reale!");
            return;
        }
        try {
            Product product = new Product(comboBox.getSelectionModel().getSelectedItem().getId(),cantitate, denumire, pret, producator);
            service.updateProduct(product);
            AlertDisplayer.showMessage(null, Alert.AlertType.CONFIRMATION,"Update successfull","Produsul a fost actualizat cu succes!");
        }catch (Exception ex){
            AlertDisplayer.showErrorMessage(null, ex.getMessage()+"\nSomething went wrong!");
        }
    }

    @FXML
    public void onComboBoxAction(){
        Product product=comboBox.getSelectionModel().getSelectedItem();
        textFieldCantitate.setText(product.getQuantity().toString());
        textFieldDenumire.setText(product.getName());
        textFieldPret.setText(product.getPrice().toString());
        textFieldProducator.setText(product.getProducer());
    }
    @FXML
    TextField textFieldUsername;
    @FXML
    TextField textFieldParola;

    @FXML
    public void onBtnAdaugaAgent(){
        String username=textFieldUsername.getText();
        String password=textFieldParola.getText();
        accountService.signUp(username,password, UserType.Salesman);
    }
}
