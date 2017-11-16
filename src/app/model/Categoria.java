package app.model;

import org.openqa.selenium.WebElement;

public class Categoria {
    private String nombre;
    private WebElement webElement;

    public Categoria() {
    }

    public Categoria(String nombre, String url) {
        this.nombre = nombre;
    }

    public Categoria(String nombre, WebElement webElement) {
        this.nombre = nombre;
        this.webElement = webElement;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public WebElement getWebElement() {
        return webElement;
    }

    public void setWebElement(WebElement webElement) {
        this.webElement = webElement;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
