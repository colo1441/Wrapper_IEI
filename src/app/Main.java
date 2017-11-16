package app;

import app.controller.MainController;
import app.scrap.MediaMarkt;
import app.util.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.function.Predicate;

public class Main extends Application {

    private Stage primaryStage;
    private AnchorPane rootPane;

    public static void main(String[] args) {

        String exePath = Constants.DRIVER_GECKO_PATH;
        System.setProperty(Constants.WEB_DRIVER_GECKO, exePath);
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Wrapper");
        initLayout();
    }

    private void initLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainController.class.getResource("main.fxml"));
        rootPane = loader.load();
        Scene scene = new Scene(rootPane);
        primaryStage.setScene(scene);
        primaryStage.show();
        MainController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);
    }
}


