package app.scrap;

import app.model.Categoria;
import app.model.Item;
import app.model.Marca;
import app.util.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static app.util.Constants.ATTRIBUTE_HREF;
import static app.util.Constants.MediaMarkt.*;

public class MediaMarkt {

    private List<Categoria> categorias;
    private DesiredCapabilities capabilities;
    private WebDriver driver;
    private boolean haveMore = true;

    public MediaMarkt(){
        categorias = new ArrayList<>();

        try {
            capabilities = DesiredCapabilities.firefox();
            capabilities.setCapability(Constants.BROWSER, true);
            driver = new FirefoxDriver(capabilities);
            driver.get(Constants.URL_MEDIA_MARKT);



            WebDriverWait waitingCafesPage = new WebDriverWait(driver, 10);
            waitingCafesPage.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.id(WAITING_CATEGORIES_CAFE_TYPES)
                    ));

            List<WebElement> elementsCategoriesCafes = (ArrayList<WebElement>)driver.findElements(By.xpath(CATEGORIES_CAFE_TYPES_LIST_ELEMENTS));
            if(elementsCategoriesCafes!=null && !elementsCategoriesCafes.isEmpty()) {

                //define elements for navegation between tags
                WebElement firstLevel;

                for (WebElement element : elementsCategoriesCafes) {
                    firstLevel = element.findElement(By.xpath(DESCENDANT_A));
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
                WebElement firstLevel;
                for (WebElement marcaElement : elementsMarcasCafes) {
                    try {
                        firstLevel = marcaElement.findElement(By.xpath(DESCENDANT_A));
                        if (!marcaElement.getText().equalsIgnoreCase("")) {
                            Marca marca = new Marca(marcaElement.getText(), marcaElement);
                            marca.setSource(Constants.URL_MEDIA_MARKT);
                            marca.setUrl(firstLevel.getAttribute(ATTRIBUTE_HREF));
                            marcas.add(marca);

                            System.out.println("MARCA : " + marca.getNombre() + " url-> " + marca.getUrl());
                        }
                    }catch (Exception e) {
                        System.err.println("Problemas con el firstelement: " + e.getLocalizedMessage());
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
    public List<Marca> recogerObjetos(List<Marca> list) {

        Iterator<Marca> it = list.iterator();
        Marca marcaIterate = null;
        while(it.hasNext()){
            marcaIterate = it.next();
            marcaIterate.setListItems(new ArrayList<>());
            capabilities = DesiredCapabilities.firefox();
            capabilities.setCapability(Constants.BROWSER, true);
            driver = new FirefoxDriver(capabilities);
            System.out.println("URL_MARCA: ---> " + marcaIterate.getUrl());
            driver.get(marcaIterate.getUrl());


            while(haveMore) {

                WebDriverWait waitingCafesPage = new WebDriverWait(driver, 10);
                waitingCafesPage.until(
                        ExpectedConditions.presenceOfElementLocated(
                                By.id(WAITING_CAFE_MARCAS_LIST_ELEMENTS)
                        ));

                List<WebElement> objetos = driver.findElements(By.id("categoryProductContainer"));
                for (WebElement objeto : objetos) {

                    String nombre = objeto.findElement(By.xpath("./descendant::a[@class='productName product10Name']")).getText();
                    String precio = objeto.findElement(By.xpath("./descendant::span[@class= 'bigpricesrc']")).getText();
                    String descripcion = objeto.findElement(By.className("product10ShortDescription")).getText();
                    String url = objeto.findElement(By.xpath("./descendant::a[@class = 'productName product10Name']")).getAttribute(Constants.ATTRIBUTE_HREF);

                    System.out.println("Objeto ->" + nombre);
                    System.out.println("Precio ->" + precio);
                    System.out.println("Descripcion ->"+ descripcion);
                    System.out.println("URL ->"+ url);
                    marcaIterate.getListItems().add(new Item(nombre,descripcion, marcaIterate.getNombre(), precio,Constants.MEDIA_MARKT ,url));

                }
                cambiarPagina(driver);
            }
            System.out.println("Longitud de la lista ->"+marcaIterate.getListItems().size());
        }
        return list;
    }

    public void cambiarPagina (WebDriver driver) {
        WebElement pagina = driver.findElement(By.xpath("./descendant::div[@class = 'resumePaginator']"));

        try {
            WebElement BTSiguientePagina = driver.findElement(By.xpath("./descendant::a[@class= 'button bPager gray left arrow']"));

            driver.get(BTSiguientePagina.getAttribute(Constants.ATTRIBUTE_HREF));
        }catch (NoSuchElementException e){
            haveMore = false;
            driver.quit();
        }
    }



}
