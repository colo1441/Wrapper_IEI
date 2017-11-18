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
import static app.util.Constants.Fnac.CAFE_MARCAS_LIST_ELEMENTS;

public class Fnac {

    private List<Categoria> categorias;
    private DesiredCapabilities capabilities;
    private static WebDriver driver;

    public static void main(String[] args) {

        ArrayList<Marca> marcas = new ArrayList<>();
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability(BROWSER, true);
        //Crear Driver
        WebDriver driver = new FirefoxDriver(capabilities);
        driver = new FirefoxDriver(capabilities);
        driver.get("https://www.fnac.es/n97714/Desayuno-y-cafe/Cafeteras-expreso-y-automaticas");

        WebElement menos = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/div[2]/div[3]/span[1]/i"));
        menos.click();

        JavascriptExecutor jse = (JavascriptExecutor)driver;
        /* Scroll Down*/
        jse.executeScript("scroll(0, 250);");

        WebElement menos2 = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/div[2]/div[3]/span[3]/i"));
        menos2.click();

        driver.findElement(By.id("htmlPopinCookies")).findElement(By.xpath("/html/body/div[2]/div[1]/div/div[2]/div[3]/div[2]/button")).click();

        /* Scroll up */
        //jse.executeScript("scroll(0, -50);");

        WebDriverWait waiting = new WebDriverWait(driver, 20);
        waiting.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.className("js-FiltersContainer")
                ));

        driver.findElement(By.className("js-FiltersContainer")).findElement(By.tagName("i")).click();;

        waiting = new WebDriverWait(driver, 10);

        //if(categoria.getListMarcas()==null){
        // Rellenar marcas de cafeteras
        List<WebElement> elementsMarcasCafes = driver.findElements(By.className("Filters-choiceTxt"));
        if(elementsMarcasCafes!=null && !elementsMarcasCafes.isEmpty()) {
            for (WebElement marcaElement : elementsMarcasCafes) {
                if (!marcaElement.getText().equalsIgnoreCase("")) {
                    Marca marca = new Marca(marcaElement.getText(), marcaElement);
                    marca.setSource(Constants.URL_FNAC);
                    marcas.add(marca);
                    System.out.println(marca);
                }
            }
            //categoria.setListMarcas(marcas);

        } else {
            //System.err.println("No hay marcas de " + categoria.getNombre());
        }
            /*} else {
                //marcas.addAll(categoria.getListMarcas());
                System.out.println("YA HAS VISITADO ESTA WEB, tienes que salir");
            }*/

        //driver.quit();
        //return marcas;

    }


    public Fnac() {
        try {
            categorias = new ArrayList<>();

            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
            capabilities.setCapability(BROWSER, true);
            //Crear Driver
            WebDriver driver = new FirefoxDriver(capabilities);
            //Abrir url
            driver.get(Constants.URL_FNAC);


            WebElement menu_lat = driver.findElement(By.className("category-menu"));
            WebDriverWait waiting = new WebDriverWait(driver, 10);
            waiting.until( ExpectedConditions.presenceOfElementLocated(By.cssSelector("dl.categoryMenu-list:nth-child(1) > dt:nth-child(1) > i:nth-child(2)")));
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

    public List<Marca> getMarcasBySelection(Categoria categoria) {
        ArrayList<Marca> marcas = new ArrayList<>();
        if(categoria.getListMarcas()==null){
            driver = new FirefoxDriver(capabilities);
            driver.get(categoria.getUrl());



            /* Ocultar menus que no nos interesan */
            WebElement menos = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/div[2]/div[3]/span[1]/i"));
            menos.click();

            JavascriptExecutor jse = (JavascriptExecutor)driver;
            /* Scroll Up */
            jse.executeScript("scroll(0, 250);");

            WebElement menos2 = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/div[2]/div[3]/span[3]/i"));
            menos2.click();

            WebElement menos3 = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/div[2]/div[3]/span[4]/i"));
            menos3.click();

            /* Scroll Down */
            jse.executeScript("scroll(0, -250);");

            // Rellenar marcas de cafeteras
            List<WebElement> elementsMarcasCafes = driver.findElements(By.className("Filters-choiceTxt"));
            if(elementsMarcasCafes!=null && !elementsMarcasCafes.isEmpty()) {
                for (WebElement marcaElement : elementsMarcasCafes) {
                    if (!marcaElement.getText().equalsIgnoreCase("")) {
                        Marca marca = new Marca(marcaElement.getText(), marcaElement);
                        marca.setSource(Constants.URL_FNAC);
                        marcas.add(marca);
                    }
                }
                categoria.setListMarcas(marcas);
                System.out.println(elementsMarcasCafes.toString());
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

    public static void closeCookies() {
        //CERRAR COOKIES
        driver.findElement(By.cssSelector(Constants.Fnac.CLOSE_COOKIES)).click();
    }
}

