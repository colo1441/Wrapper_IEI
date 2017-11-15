package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Predicate;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Wrapper");
        primaryStage.setScene(new Scene(root, 800, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {

        Firefox();
      //  launch(args);


    }

    //WebDriver
    public static void Firefox()
    {
        String exePath = "/Users/kevin/Temporales/geckodriver";
        System.setProperty("webdriver.gecko.driver", exePath);
        String URL1 = "http://elcorteingles.es";
        String URL2 = "http://fnac.es";
        String URL3 = "https://www.mediamarkt.es";

        try {

            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
            capabilities.setCapability("browser", true);
            //Crear Driver
            WebDriver driver = new FirefoxDriver(capabilities);
            //Abrir url
            driver.get(URL3);

            WebElement categorias = driver.findElement(By.id("navigation__all--categories"));
            categorias.click();

            WebElement hogar = driver.findElement(By.className("world_hogar-jardin"));
            hogar.click();

            WebDriverWait waiting = new WebDriverWait(driver, 50);
            waiting.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.cssSelector("a[href*='https://tiendas.mediamarkt.es/cafeteras-cafe']")
                    ));

            WebElement cafes = driver.findElement(By.cssSelector("a[href*='https://tiendas.mediamarkt.es/cafeteras-cafe']"));
            cafes.click();

            /*
            WebElement cafeteras = driver.findElement(By.className("categoryTree1"));
            cafeteras.click();
            */

            //driver.quit();
        }catch(Exception exc)
        {
            System.out.print(exc);
        }

        /** PASO 1 - Buscar href cafeteras y pulsarlo**/

/*
        WebElement home =  driver.findElement(By.xpath("//a[@href='/?level=10']"));
        try{

            WebElement burguer = driver.findElement(By.id("burguer"));
            //WebElement burguer = driver.findElement(By.cssSelector("a[id*='burguer'] "));
            //WebElement burguer = driver.findElement(By.cssSelector("span[contains(text(), 'burguer icon')]"));
            //WebElement burguer = driver.findElement(By.cssSelector("span[class='burguer.icon']"));
            burguer.click();
            //Error por los espacios
            //WebElement search = driver.findElement(By.className("burguer icon"));
            WebDriverWait waiting = new WebDriverWait(driver, 50);

        }catch(Exception exc)
        {
            System.out.print(exc);
        }

        /*
        WebElement burguerMenu = driver.findElement(By.className("burguer icon"));
        WebDriverWait waiting = new WebDriverWait(driver, 10);
        burguerMenu.click();

        WebElement cafeteras =  driver.findElement(By.xpath("//a[@href='/electrodomesticos/cafeteras/?level=1']"));
        cafeteras.click();


*/
        /** PASO 2 - Esperar **/

        //waiting = new WebDriverWait(driver, 4);



        /** PASO 3 - Buscar elemento **/
        /** PASO 4 - Cerrar la ventana de cookies **/
        /** PASO 5 - **/
        /** Pulsar Elementos **/


    }

    /** Esperar a que se cargue **/

    /*
    public static void waitForPageLoad(WebDriver wdriver){
        WebDriverWait wait = new WebDriverWait(wdriver, 60);
        Predicate<WebDriver> pageLoaded = new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver input) {
                return (
                        (JavascriptExecutor) input)
                        .executeScript("return document.readyState").equals("complete");
            };

        };
        wait.until(pageLoaded);
    }
    */

}

