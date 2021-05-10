package UI;

import Controllers.LoginController;
import Controllers.ProductViewController;
import Domain.UserType;
import Repo.AccountRepo;
import Repo.AccountRepoInterface;
import Repo.ProductRepo;
import Repo.ProductRepoInterface;
import Services.AccountService;
import Services.ProductService;
import Services.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
//        accountService.signUp("admin","admin", UserType.Admin);
//        accountService.signUp("a","a", UserType.Salesman);
//        accountService.signUp("b","b", UserType.Salesman);
//        System.out.println(accountService.findAccount("admin"));

//        productService.addProduct(3.0,"water",2.5,"Aque");
//        System.out.println(productService.getOne(1L));

        Service service = new Service(productService);

        LoginController ctrl = loader.getController();
        ctrl.setService(accountService, service);


        primaryStage.setTitle("Login Sales Company");
        primaryStage.setScene(new Scene(root));
        primaryStage.setWidth(800);
        primaryStage.show();
    }
}
