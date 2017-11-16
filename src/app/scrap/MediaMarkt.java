package app.scrap;

import app.model.Categoria;
import app.model.Marca;
import app.util.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static app.util.Constants.MediaMarkt.*;

public class MediaMarkt {

    private List<Categoria> categorias;
    private DesiredCapabilities capabilities;
    private WebDriver driver;
    private boolean marcasVisited;

    public MediaMarkt(){
        categorias = new ArrayList<>();

        try {
            capabilities = DesiredCapabilities.firefox();
            capabilities.setCapability(Constants.BROWSER, true);
            driver = new FirefoxDriver(capabilities);
            driver.get(Constants.URL_MEDIA_MARKT);



            /*WebElement categories = driver.findElement(By.id(NAVIGATION_ALL_CATEGORIES));
            categories.click();

            WebElement navigationHogar = driver.findElement(By.className(NAVIGATION_CATEGORIES_HOGAR));
            navigationHogar.click();

            WebDriverWait waitingHogarCategories = new WebDriverWait(driver, 10);
            waitingHogarCategories.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.cssSelector(NAVIGATION_CATEGORIES_HOGAR_CAFE)
                    ));

            WebElement categoriesCafes = driver.findElement(By.cssSelector(NAVIGATION_CATEGORIES_HOGAR_CAFE));
            categoriesCafes.click();*/

            WebDriverWait waitingCafesPage = new WebDriverWait(driver, 10);
            waitingCafesPage.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.id(NAVIGATION_CATEGORIES_CAFE_TYPES)
                    ));

            // Rellenar categorias de cafeteras
            List<WebElement> elementsCategoriesCafes = (ArrayList<WebElement>)driver.findElements(By.xpath(NAVIGATION_CATEGORIES_CAFE_TYPES_LIST_ELEMENTS));
            if(elementsCategoriesCafes!=null && !elementsCategoriesCafes.isEmpty()) {
                for (WebElement element : elementsCategoriesCafes) {
                    categorias.add(new Categoria(element.getText(), element));
                }
            } else {
                System.err.println("No hay categorías");
            }

            //driver.quit();
        }catch(Exception exc)
        {
            System.out.print(exc);
        }

    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void closeCookies() {
        //CERRAR COOKIES
        driver.findElement(By.id(Constants.MediaMarkt.CLOSE_COOKIES)).click();
    }

    public List<Marca> getMarcasBySelection(WebElement element) {
        ArrayList<Marca> marcas = new ArrayList<>();
        if(!marcasVisited) {
            marcasVisited = true;
            //  driver = new FirefoxDriver(capabilities);
            //  driver.get(Constants.URL_MEDIA_MARKT);

            System.out.println("WEBELEMENT: " +
                    element);
            element.click();

            WebDriverWait waitingCafesPage = new WebDriverWait(driver, 10);
            waitingCafesPage.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.id("brandsFilterElements")
                    ));

            // Rellenar marcas de cafeteras
            List<WebElement> elementsMarcasCafes = (ArrayList<WebElement>) driver.findElements(By.xpath("//*[contains(@class, 'filterElement brandsFilterElement')]"));
            if (elementsMarcasCafes != null && !elementsMarcasCafes.isEmpty()) {
                for (WebElement marcaElement : elementsMarcasCafes) {
                    if (!marcaElement.getText().equalsIgnoreCase("")) {
                        System.out.println("MARCAELEMENT " + marcaElement.getText());
                        marcas.add(new Marca(marcaElement.getText(), Constants.URL_MEDIA_MARKT, marcaElement));
                    }
                }
            } else {
                System.err.println("No hay marcas de " + element.getText());
            }
        } else {
            System.out.println("YA HAS VISITADO ESTA WEB, tienes que salir");
        }

        // driver.quit();
        return marcas;
    }

}
