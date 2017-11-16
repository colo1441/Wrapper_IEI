package app.scrap;

import app.model.Categoria;
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
        ExeDriver();
    }

    public static final String URL_FNAC = "http://fnac.es";

    //WebDriver
    public static void ExeDriver() {
        String exePath = "C:\\Program Files (x86)\\geckodriver-v0.19.1-win64\\geckodriver.exe";
        System.setProperty("webdriver.gecko.driver", exePath);
    }

    public List<Categoria> TiposCafetera(){

        try {

            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
            capabilities.setCapability("browser", true);
            //Crear Driver
            WebDriver driver = new FirefoxDriver(capabilities);
            //Abrir url
            driver.get(URL_FNAC);

            driver.findElement(By.cssSelector("i[class*='close']")).click();

            WebDriverWait waiting = new WebDriverWait(driver, 5);

            WebElement menu_hogar = driver.findElement(By.cssSelector("a[href*='https://www.fnac.es/hogar#bl=MMHogar']"));

            menu_hogar.click();
            menu_hogar.click();

            waiting = new WebDriverWait(driver, 5);
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
            /*List<Categoria> categorias =

            for (WebElement tipo : tipos_cafetera) {
                if (!tipo.getText().equals("")) {
                   categorias.add(tipo);
                }
            }*/
            
            //driver.quit();
        } catch (Exception exc) {
            System.out.print(exc);
        }
    }


    }
}
