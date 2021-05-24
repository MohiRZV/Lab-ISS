package Controllers;

import Domain.Account;
import Domain.Cart;
import Domain.Customer;
import Domain.Product;
import Services.Service;
import Services.ServiceException;
import UI.utils.AlertDisplayer;
import UI.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ProductViewController implements Observer {
    private Service service;
    private Customer currentCustomer;
    private Account account;
    ObservableList<Product> productsModel = FXCollections.observableArrayList();
    ObservableList<Product> cartModel = FXCollections.observableArrayList();
    ObservableList<Customer> customerModel = FXCollections.observableArrayList();
    private Cart cart = new Cart();
    public void setService(Service service, Account account){
        this.service=service;
        this.account=account;
        service.addObserver(this);
        init();
    }

    @FXML
    TableView<Product> tableViewProduse;

    @FXML
    TableColumn<String, Product> tableColumnDenumire;
    @FXML
    TableColumn<String, Product> tableColumnProducator;
    @FXML
    TableColumn<Double, Product> tableColumnPret;
    @FXML
    TableColumn<Double, Product> tableColumnCantitate;


    @FXML
    ListView<Product> listViewCart;

    @FXML
    TextField textFieldCantitate;

    @FXML
    ComboBox<Customer> comboBoxCustomer;

    @FXML
    public void initialize(){
        tableColumnDenumire.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnProducator.setCellValueFactory(new PropertyValueFactory<>("producer"));
        tableColumnCantitate.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tableColumnPret.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableViewProduse.setItems(productsModel);
        comboBoxCustomer.setItems(customerModel);

    }
    private void init(){
        initModelProducts();
        listViewCart.setCellFactory(new Callback<ListView<Product>,
                                            ListCell<Product>>() {
                                        @Override
                                        public ListCell<Product> call(ListView<Product> list) {
                                            return new ListViewModel();
                                        }
                                    }
        );
        initModelCart();
        initModelCustomers();
    }
    private void initModelProducts(){
        Iterable<Product> products= null;
        products = service.getAllProducts();
        List<Product> productList= StreamSupport.stream(products.spliterator(),false).collect(Collectors.toList());
        productsModel.setAll(productList);
    }

    private void initModelCustomers(){
        Iterable<Customer> customers= null;
        customers = service.getAllCustomers();
        List<Customer> customerList= StreamSupport.stream(customers.spliterator(),false).collect(Collectors.toList());
        customerModel.setAll(customerList);
    }

    @Override
    public void update() {
        initModelProducts();
    }

    static class ListViewModel extends ListCell<Product> {
        public ListViewModel() {
        }

        @Override
        public void updateItem(Product item, boolean empty) {
            super.updateItem(item, empty);
            String message=null;
            if(item==null||empty){

            }else{
                message = item.getName()+" de la "+item.getProducer()+"\ncantitate: "+item.getQuantity()+" pret/unit: "+
                        item.getPrice();
            }
            this.setText(message);
            setGraphic(null);
        }

    }
    private void initModelCart(){
        listViewCart.setItems(cartModel);
    }

    @FXML
    public void onComboBoxCustomerAction(){
        currentCustomer=comboBoxCustomer.getSelectionModel().getSelectedItem();
    }
    private void addToCart(Product product, Product oldProduct){
        if(product.getQuantity()>oldProduct.getQuantity()){
            AlertDisplayer.showErrorMessage(null,"Produsul nu este disponibil in cantitatea dorita!");
            return;
        }
        cartModel.add(product);
        cart.addProduct(product);
    }
    @FXML
    public void onBtnAddToCart(){
        Product product = tableViewProduse.getSelectionModel().getSelectedItem();

        if(product==null){
            AlertDisplayer.showErrorMessage(null, "Trebuie sa selectati un produs!");
        }else{
            if(cart.contains(product.getId())){
                AlertDisplayer.showErrorMessage(null, "Nu puteti adauga de mai multe ori acelasi produs!");
                return;
            }
            try {
                Double quantity = Double.parseDouble(textFieldCantitate.getText());
                Product orderedProduct = new Product(product);
                orderedProduct.setQuantity(quantity);
                addToCart(orderedProduct, product);
            }catch (Exception ex){
                AlertDisplayer.showErrorMessage(null, ex.getMessage()+"\nTrebuie sa intoduceti o valoare reala");
            }
        }
    }

    @FXML
    private void onBtnViewOrders(){
        showOrdersWindow(account);
    }
    private void showOrdersWindow(Account account){

        FXMLLoader mloader = new FXMLLoader(
                getClass().getClassLoader().getResource("views/ordersView.fxml"));

        try {
            Parent croot=mloader.load();
            OrderViewController orderViewController = mloader.getController();
            orderViewController.setService(service, account);
            Stage stage=new Stage();
//            stage.setTitle("Window for " +account.getName());
            stage.setScene(new Scene(croot));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void placeOrder(){
        if(currentCustomer == null){
            AlertDisplayer.showErrorMessage(null, "Trebuie sa selectati un client!");
            return;
        }
        try{
            service.placeOrder(cart, currentCustomer, account.getId());
            cart=new Cart();
            cartModel.remove(0, cartModel.size());
            initModelCart();
            AlertDisplayer.showMessage(null, Alert.AlertType.CONFIRMATION, "Success!", "Order placed successfully!");
        }catch (ServiceException ex){
            AlertDisplayer.showErrorMessage(null, ex.getMessage());
        }
    }
    @FXML
    public void onBtnPlaceOrder(){
        System.out.println(cart);
        placeOrder();
    }



}
