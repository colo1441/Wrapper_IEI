package app.web;

import app.object.Categoria;
import app.object.Cafetera;
import app.object.Marca;
import app.util.Constants;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static app.util.Constants.ATTRIBUTE_HREF;
import static app.util.Constants.BROWSER;
import static app.util.Constants.FNAC;

public class Fnac {

    private List<Categoria> categorias;
    private DesiredCapabilities capabilities;
    private static WebDriver driver;
    boolean more;

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

    public List<Marca> getMarcasBySelection(Categoria categoria) {
        ArrayList<Marca> marcas = new ArrayList<>();
        if(categoria.getListMarcas()==null){
            driver = new FirefoxDriver(capabilities);
            System.out.println("URL DE FNAC CATETORIA : " + categoria.getUrl());
            driver.get(categoria.getUrl());

            try {
                driver.findElement(By.xpath("./descendant::button[@class='toggleFilters js-toggleFilters']")).click();
            }catch (Exception e){

                System.out.println("No hay botton ver mas, motivo: " + e.getLocalizedMessage());
            }


            WebElement elementsMarcasCafes = driver.findElement(By.cssSelector("div.js-FiltersContainer:nth-child(4) > div:nth-child(1)"));
            List<WebElement> elementsCafe = elementsMarcasCafes.findElements(By.xpath("./descendant::a[@class = 'Filters-choice  isActive']"));
            if(elementsCafe!=null && !elementsCafe.isEmpty()) {
                int i = 1;
                for (WebElement marcaElement : elementsCafe) {
                    if (!marcaElement.getText().equalsIgnoreCase("")) {
                        Marca marca = new Marca(marcaElement.getText());
                        String url2 = marcaElement.findElement(By.xpath("/html/body/div[2]/div[1]/div/div[2]/div[3]/div[2]/div/a["+i+"]")).getAttribute("data-filter");
                        marca.setUrl(categoria.getUrl()+ "?SFilt=" +url2);
                        marca.setSource(Constants.FNAC);
                        marcas.add(marca);
                        i++;
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


    public List<Marca> getCafeterasFrom(List<Marca> list) {
        System.out.println("LISTA RCIBIDA: " + list.size());
        try {
            Iterator<Marca> it = list.iterator();
            Marca marcaIterate = null;
            while (it.hasNext()) {
                more = true;
                marcaIterate = it.next();
                marcaIterate.setListCafeteras(new ArrayList<>());
                capabilities = DesiredCapabilities.firefox();
                capabilities.setCapability(Constants.BROWSER, true);
                driver = new FirefoxDriver(capabilities);
                System.out.println("URL_MARCA: ---> " + marcaIterate.getUrl());
                driver.get(marcaIterate.getUrl());

                while (more) {

                    List<WebElement> objetos = driver.findElements(By.xpath("./descendant::li[@class = 'clearfix Article-item']"));
                    for (WebElement objeto : objetos) {

                        String nombre = objeto.findElement(By.xpath("./descendant::a[@class='js-minifa-title']")).getText();
                        String precio = objeto.findElement(By.className("userPrice")).getText();
                        String description = "Descripción no disponible";
                        String url = objeto.findElement(By.xpath("./descendant::a[@class = 'js-minifa-title']")).getAttribute(Constants.ATTRIBUTE_HREF);
                        Cafetera cafetera = new Cafetera(nombre, description, marcaIterate.getNombre(), precio, FNAC, url);
                        System.out.println("cafetera ->" + cafetera);

                        marcaIterate.getListCafeteras().add(cafetera);

                    }
                    cambiarPagina(driver);
                }

            }
        }catch (Exception e){
            System.err.println(e.getLocalizedMessage());
        }

        return list;
    }

    private void cambiarPagina(WebDriver driver) {


        try {
            String BTSiguientePagina = driver.findElement(By.cssSelector(".nextLevel > a:nth-child(1)")).getAttribute(ATTRIBUTE_HREF);

            System.out.println("URL de la siguiente pagina ->"+BTSiguientePagina);
            if(BTSiguientePagina==null){
                more = false;
            }else {
                driver.get(BTSiguientePagina);
            }
            //driver.quit();
        } catch (NoSuchElementException e) {
            System.out.println("Fallo al recolectar la url de la pagina siguiente");
            more = false;
            driver.quit();

        }


    }
}