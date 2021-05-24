package Controllers;

import Domain.Account;
import Domain.Order;
import Domain.Product;
import Services.Service;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class OrderViewController {
    private Service service;
    private Account account;
    ObservableList<Order> ordersModel = FXCollections.observableArrayList();
    public void setService(Service service, Account account) {
        this.service=service;
        this.account=account;
        init();
    }

    @FXML
    TableView<Order> tableViewOrder;
    @FXML
    TableColumn<Order, String> tableColumnClient;
    @FXML
    TableColumn<Order, String> tableColumnProduse;

    public String getStringFromProducts(Set<Product> list){
        AtomicReference<String> s= new AtomicReference<>("");
        list.forEach(p-> s.set(s + p.getName() + ", "));
        return s.get();
    }
    @FXML
    public void initialize(){
        tableColumnClient.setCellValueFactory(cell->new SimpleStringProperty(service.getClient(cell.getValue().getCustomerID()).getName()));
        tableColumnProduse.setCellValueFactory(cell->new SimpleStringProperty(
                getStringFromProducts(cell.getValue().getProducts())
                )
        );

        tableViewOrder.setItems(ordersModel);

    }
    private void init(){
        initModelOrders();
    }
    private void initModelOrders(){
        Iterable<Order> orders= null;
        orders = service.getAllOrdersByID(account.getId());
        List<Order> orderList= StreamSupport.stream(orders.spliterator(),false).collect(Collectors.toList());
        ordersModel.setAll(orderList);
    }

    @FXML
    public void onBtnCancelOrder(){
        Order order = tableViewOrder.getSelectionModel().getSelectedItem();
        service.cancelOrder(order);
    }

    private void makePDF(Order order) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("order.pdf"));

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);

        Phrase chunk = new Phrase(getStringFromOrder(order), font);
        document.add(chunk);
        document.close();
    }

    private String getStringFromOrder(Order order) {
        StringBuilder rez = new StringBuilder();
        rez.append("Salesman ID: ").append(order.getSalesmanID());
        rez.append("\nCustomer ID: ").append(order.getCustomerID());
        rez.append("\nDate: ").append(order.getDate());
        rez.append("\nStatus: ").append(order.getStatus());
        rez.append("\n\nProducts: ");
        for(Product p : (Set<Product>)order.getProducts()){
            rez.append("\n").append(p.toString());
        }
        return rez.toString();
    }

    @FXML
    public void onBtnPrintOrder(){
        Order order = tableViewOrder.getSelectionModel().getSelectedItem();
        try {
            makePDF(order);
        } catch (FileNotFoundException | DocumentException e){
            e.printStackTrace();
        }
    }
}
