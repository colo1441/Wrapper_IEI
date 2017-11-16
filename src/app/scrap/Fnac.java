package app.scrap;

import app.model.Web;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Fnac {

    public static void main(String[] args) {
        Driver();
    }

    //WebDriver
    public static void Driver()
    {
        String exePath = "C:\\Program Files (x86)\\geckodriver-v0.19.1-win64\\geckodriver.exe";
        System.setProperty("webdriver.gecko.driver", exePath);
        String URL = "http://fnac.es";

        try {

            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
            capabilities.setCapability("browser", true);
            //Crear Driver
            WebDriver driver = new FirefoxDriver(capabilities);
            //Abrir url
            driver.get(URL);

            driver.findElement(By.cssSelector("i[class*='close']")).click();

            WebDriverWait waiting = new WebDriverWait(driver,5);

            WebElement menu_hogar = driver.findElement(By.cssSelector("a[href*='https://www.fnac.es/hogar#bl=MMHogar']"));

            menu_hogar.click();
            menu_hogar.click();

            waiting = new WebDriverWait(driver,5);
            /*waiting.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.cssSelector("a[href*='https://www.fnac.es/Desayuno-y-cafe/s38477#bl=MMHogar']")
                    ));*/

            WebElement menu_cafe = driver.findElement(By.cssSelector("a[href*='https://www.fnac.es/Desayuno-y-cafe/s38477']"));
            menu_cafe.click();

            WebElement menu_lat = driver.findElement(By.className("category-menu"));
            WebElement menos = driver.findElement(By.cssSelector("dl.categoryMenu-list:nth-child(1) > dt:nth-child(1) > i:nth-child(2)"));
            menos.click();
            WebElement menos2 = driver.findElement(By.cssSelector("dl.categoryMenu-list:nth-child(3) > dt:nth-child(1) > i:nth-child(2) "));
            menos2.click();

            List<WebElement> tipos_cafetera = menu_lat.findElements(By.className("categoryMenu-link"));

            for(WebElement cafetera : tipos_cafetera) {
                if(!cafetera.getText().equals(""))
                {
                    /* Crear tipo cafetera en "lista de tipos"*/
                    System.out.println("TIPO : "+cafetera.getText());

                }
            }

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
