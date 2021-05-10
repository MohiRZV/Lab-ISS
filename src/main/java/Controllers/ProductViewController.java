package Controllers;

import Domain.Cart;
import Domain.Product;
import Services.Service;
import Services.ServiceException;
import UI.utils.AlertDisplayer;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ProductViewController {
    private Service service;
    ObservableList<Product> productsModel = FXCollections.observableArrayList();
    ObservableList<Product> cartModel = FXCollections.observableArrayList();
    private Cart cart = new Cart();
    public void setService(Service service){
        this.service=service;
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
    public void initialize(){
        tableColumnDenumire.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnProducator.setCellValueFactory(new PropertyValueFactory<>("producer"));
        tableColumnCantitate.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tableColumnPret.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableViewProduse.setItems(productsModel);

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
    }
    private void initModelProducts(){
        Iterable<Product> products= null;
        products = service.getAllProducts();
        List<Product> productList= StreamSupport.stream(products.spliterator(),false).collect(Collectors.toList());
        productsModel.setAll(productList);
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

    private void addToCart(Product product){
        cartModel.add(product);
        cart.addProduct(product);
    }
    @FXML
    public void onBtnAddToCart(){
        Product product = tableViewProduse.getSelectionModel().getSelectedItem();

        if(product==null){
            AlertDisplayer.showErrorMessage(null, "Trebuie sa selectati un produs!");
        }else{
            try {
                Double quantity = Double.parseDouble(textFieldCantitate.getText());
                Product orderedProduct = new Product(product);
                orderedProduct.setQuantity(quantity);
                addToCart(orderedProduct);
            }catch (Exception ex){
                AlertDisplayer.showErrorMessage(null, ex.getMessage()+"\nTrebuie sa intoduceti o valoare reala");
            }
        }
    }

    private void placeOrder(){
        try{
            service.placeOrder(cart);
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
