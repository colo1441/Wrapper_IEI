package app.scrap;

import app.model.Categoria;
import app.model.Marca;
import app.model.Web;
import app.util.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static app.util.Constants.ATTRIBUTE_HREF;
import static app.util.Constants.BROWSER;
import static app.util.Constants.MediaMarkt.CAFE_MARCAS_LIST_ELEMENTS;
import static app.util.Constants.MediaMarkt.WAITING_CAFE_MARCAS_LIST_ELEMENTS;

public class Fnac {

    private List<Categoria> categorias;
    private DesiredCapabilities capabilities;
    private WebDriver driver;

    public Fnac() {
        try {
            categorias = new ArrayList<>();


            capabilities = DesiredCapabilities.firefox();
            capabilities.setCapability(BROWSER, true);
            //Crear Driver
            WebDriver driver = new FirefoxDriver(capabilities);
            //Abrir url
            driver.get(Constants.URL_FNAC);


            WebElement menu_lat = driver.findElement(By.className("category-menu"));
            WebElement menos = driver.findElement(By.cssSelector("dl.categoryMenu-list:nth-child(1) > dt:nth-child(1) > i:nth-child(2)"));
            menos.click();
            WebElement menos2 = driver.findElement(By.cssSelector("dl.categoryMenu-list:nth-child(3) > dt:nth-child(1) > i:nth-child(2) "));
            menos2.click();

            List<WebElement> tiposCafeteras = menu_lat.findElements(By.className("categoryMenu-link"));

            if(tiposCafeteras!=null && !tiposCafeteras.isEmpty()) {
                for (WebElement tipo : tiposCafeteras) {
                    if (!tipo.getText().equals("")) {
                        Categoria categoria = new Categoria(tipo.getText());
                        categoria.setUrl(tipo.getAttribute(ATTRIBUTE_HREF));
                        categoria.setWebElement(tipo);
                        categoria.setSource(Constants.URL_FNAC);
                        categorias.add(categoria);
                    }
                }
            } else {

            }

            driver.quit();
        } catch (Exception exc) {
            System.out.print(exc);
        }
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

   /* public List<Marca> getMarcasBySelection(Categoria categoria) {
        ArrayList<Marca> marcas = new ArrayList<>();
        if(categoria.getListMarcas()==null) {
            driver = new FirefoxDriver(capabilities);
            driver.get(categoria.getUrl());

            WebDriverWait waitingCafesPage = new WebDriverWait(driver, 10);
            waitingCafesPage.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.id(WAITING_CAFE_MARCAS_LIST_ELEMENTS)
                    ));

            // Rellenar marcas de cafeteras
            List<WebElement> elementsMarcasCafes = (ArrayList<WebElement>) driver.findElements(By.xpath(CAFE_MARCAS_LIST_ELEMENTS));
            if (elementsMarcasCafes != null && !elementsMarcasCafes.isEmpty()) {
                for (WebElement marcaElement : elementsMarcasCafes) {
                    if (!marcaElement.getText().equalsIgnoreCase("")) {
                        Marca marca = new Marca(marcaElement.getText(), marcaElement);
                        marca.setSource(Constants.URL_FNAC);
                        marcas.add(marca);
                    }
                }
                categoria.setListMarcas(marcas);
            } else {
                System.err.println("No hay marcas de " + categoria.getNombre());
            }
        } else {
            marcas.addAll(categoria.getListMarcas());
            System.out.println("YA HAS VISITADO ESTA WEB, tienes que salir");
        }

        driver.quit();
        return marcas;
    }*/
}
