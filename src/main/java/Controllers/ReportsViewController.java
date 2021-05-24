package Controllers;

import Domain.*;
import Services.Service;
import UI.utils.AlertDisplayer;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ReportsViewController {
    private Service service;
    ObservableList<Customer> customerModel = FXCollections.observableArrayList();
    ObservableList<SalesmanDTO> salesmanDTOModel = FXCollections.observableArrayList();
    ObservableList<ClientDTO> clientDTOModel = FXCollections.observableArrayList();
    final String option1 = "1. View most fideli clients";
    final String option2 = "2. View best employees";
    ObservableList<String> reportTypes = FXCollections.observableArrayList(option1, option2);
    public void setService(Service service){
        this.service=service;
        init();
    }

    @FXML
    ComboBox<String> comboBoxReportTypes;

    @FXML
    TableView<SalesmanDTO> tableViewSalesman;
    @FXML
    TableColumn<SalesmanDTO, String> tableColumnSalesmanID;
    @FXML
    TableColumn<SalesmanDTO, Double> tableColumnSalesmanValue;

    @FXML
    TableView<ClientDTO> tableViewClients;
    @FXML
    TableColumn<ClientDTO, String> tableColumnClientID;
    @FXML
    TableColumn<ClientDTO, String> tableColumnClientProducts;
    @FXML
    TableColumn<ClientDTO, Double> tableColumnClientValue;

    @FXML
    public void initialize(){
        tableColumnSalesmanID.setCellValueFactory(new PropertyValueFactory<>("salesmanID"));
        tableColumnSalesmanValue.setCellValueFactory(new PropertyValueFactory<>("value"));

        tableViewSalesman.setItems(salesmanDTOModel);

        tableColumnClientID.setCellValueFactory(new PropertyValueFactory<>("clientID"));
        tableColumnClientValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        tableColumnClientProducts.setCellValueFactory(cell->new SimpleStringProperty(
                getProductsAsString(service.getMostFrqOrderedProduct(cell.getValue().getClientID()))
        ));
        tableViewClients.setItems(clientDTOModel);

        comboBoxReportTypes.setItems(reportTypes);
    }

    private String getProductsAsString(Iterable<Product> list) {
        AtomicReference<String> s= new AtomicReference<>("");
        list.forEach(p-> s.set(s + p.getName() + ", "));
        return s.get();
    }

    private void init(){

        initModelCustomers();
    }

    private void initModelClients() {
        Iterable<ClientDTO> customers= null;
        customers = service.getTopClients();
        List<ClientDTO> customerList= StreamSupport.stream(customers.spliterator(),false).collect(Collectors.toList());
        clientDTOModel.setAll(customerList);
    }

    private void initModelSalesman() {
        Iterable<SalesmanDTO> salesman= null;
        salesman = service.getTopSalesman();
        List<SalesmanDTO> salesmanDTOList= StreamSupport.stream(salesman.spliterator(),false).collect(Collectors.toList());
        salesmanDTOModel.setAll(salesmanDTOList);
    }

    private void initModelCustomers(){
        Iterable<Customer> customers= null;
        customers = service.getAllCustomers();
        List<Customer> customerList= StreamSupport.stream(customers.spliterator(),false).collect(Collectors.toList());
        customerModel.setAll(customerList);
    }
    @FXML
    public void onComboBoxChanged(){
        String option = comboBoxReportTypes.getSelectionModel().getSelectedItem();
        if(option==null || option.isEmpty())
            return;
        switch (option){
            case option1:
                displayTopClients();
                break;
            case option2:
                displayTopSalesman();
                break;
        }
    }

    private void displayTopSalesman() {
        System.out.println("top salesman");
        initModelSalesman();
    }

    private void displayTopClients() {
        System.out.println("top clients");

        initModelClients();
    }

    private String getStringFromClientDTO(ClientDTO client) {
        StringBuilder rez = new StringBuilder();
        rez.append("\n\n\n\nClient overview\n\n").append("Client ID: ").append(client.getClientID());
        rez.append("Total order value: ").append(client.getValue()).append("\n\n\n\n");
        rez.append("Most frequently ordered products:\n\n");
        Iterable<Product> products = service.getMostFrqOrderedProduct(client.getClientID());
        products.forEach(x->{
            rez.append(x.getName()).append("\t");
            rez.append("comandat in cantitate de: ").append(x.getQuantity()).append("\n\n");
        });
        rez.append("\n\n");
        return rez.toString();
    }
    private String getStringFromSalesmanDTO(SalesmanDTO salesman) {
        return "Username: " + salesman.getSalesmanID() + "\n" +
                "Total sold value: " + salesman.getValue() + "\n\n";
    }

    private void makePDF1(Iterable<ClientDTO> clients) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("clientsReport.pdf"));

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        for(ClientDTO c : clients) {
            Phrase chunk = new Phrase(getStringFromClientDTO(c), font);
            document.add(chunk);
        }
        document.close();
    }


    private void makePDF(Iterable<SalesmanDTO> salesmans) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("salesmanReport.pdf"));

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        for(SalesmanDTO salesman : salesmans) {
            Phrase chunk = new Phrase(getStringFromSalesmanDTO(salesman), font);
            document.add(chunk);
        }
        document.close();
    }

    @FXML
    public void onBtnPrint() throws FileNotFoundException, DocumentException {
        String option = comboBoxReportTypes.getSelectionModel().getSelectedItem();
        if(option==null || option.isEmpty()) {
            AlertDisplayer.showErrorMessage(null,"Trebuie sa selectati un raport!");
            return;
        }
        switch (option){
            case option1:
                makePDF1(tableViewClients.getItems());
                break;
            case option2:
                makePDF(tableViewSalesman.getItems());
                break;
        }
    }



}
