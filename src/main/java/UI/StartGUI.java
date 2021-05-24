package UI;

import Controllers.LoginController;
import Controllers.ProductViewController;
import Domain.Order;
import Domain.OrderStatus;
import Domain.Product;
import Domain.UserType;
import Repo.*;
import Services.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StartGUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("views/loginView.fxml"));
        Parent root=loader.load();

        AccountRepoInterface accountRepo = new AccountRepo();
        AccountService accountService = new AccountService(accountRepo);

        ProductRepoInterface productRepo = new ProductRepo();
        ProductService productService = new ProductService(productRepo);

        CustomerRepoInterface customerRepo = new CustomerRepo();
        CustomerService customerService = new CustomerService(customerRepo);

        OrderRepoInterface orderRepo = new OrderRepo();
        OrderService orderService = new OrderService(orderRepo, productRepo);
//        accountService.signUp("admin","admin", UserType.Admin);
//        accountService.signUp("a","a", UserType.Salesman);
//        accountService.signUp("b","b", UserType.Salesman);
//        System.out.println(accountService.findAccount("admin"));

//        productService.addProduct(3.0,"water",2.5,"Aque");
//        System.out.println(productService.getOne(1L));
//        Product product = new Product(3L,3.0, "test", 13.2, "Tester");
//        Set<Product> list = new HashSet<>();
//        list.add(product);
//        Order order = new Order(list, LocalDateTime.now(), OrderStatus.Processing, 1L);
//        orderRepo.add(order);
//        for(Order order1 : orderRepo.getAll()){
//            System.out.println(order1);
//        }

        Service service = new Service(productService, customerService, orderService);

        LoginController ctrl = loader.getController();
        ctrl.setService(accountService, service);


        primaryStage.setTitle("Login Sales Company");
        primaryStage.setScene(new Scene(root));
        primaryStage.setWidth(800);
        primaryStage.show();
    }
}
