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

import static app.util.Constants.ATTRIBUTE_HREF;
import static app.util.Constants.MediaMarkt.*;

public class MediaMarkt {

    private List<Categoria> categorias;
    private DesiredCapabilities capabilities;
    private WebDriver driver;

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
                            By.id(WAITING_CATEGORIES_CAFE_TYPES)
                    ));

            // Rellenar categorias de cafeteras
            List<WebElement> elementsCategoriesCafes = (ArrayList<WebElement>)driver.findElements(By.xpath(CATEGORIES_CAFE_TYPES_LIST_ELEMENTS));
            if(elementsCategoriesCafes!=null && !elementsCategoriesCafes.isEmpty()) {

                //define elements for navegation between tags
                WebElement idDivLevel,firstLevel;

                for (WebElement element : elementsCategoriesCafes) {
                    firstLevel = element.findElement(By.xpath("./descendant::a"));
                    Categoria categoria = new Categoria(element.getText(), element);
                    categoria.setSource(Constants.URL_MEDIA_MARKT);
                    categoria.setUrl(firstLevel.getAttribute(ATTRIBUTE_HREF));

                    System.out.println("Categoria: " +categoria);

                    categorias.add(categoria);
                }
            } else {
                System.err.println("No hay categorías");
            }

            driver.quit();
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

    public List<Marca> getMarcasBySelection(Categoria categoria) {
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
                        marca.setSource(Constants.URL_MEDIA_MARKT);
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
    }

}
