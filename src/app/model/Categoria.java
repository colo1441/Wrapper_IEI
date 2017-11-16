package app.model;

import org.openqa.selenium.WebElement;

import java.util.List;

public class Categoria extends CommonElement {
    private WebElement webElement;
    private List<Marca> listMarcas;

    public Categoria(String nombre) {
        super(nombre);
    }

    public Categoria(String nombre, WebElement webElement) {
        super(nombre);
        this.webElement = webElement;
    }

    public WebElement getWebElement() {
        return webElement;
    }

    public void setWebElement(WebElement webElement) {
        this.webElement = webElement;
    }

    public List<Marca> getListMarcas() {
        return listMarcas;
    }

    public void setListMarcas(List<Marca> listMarcas) {
        this.listMarcas = listMarcas;
    }
}
