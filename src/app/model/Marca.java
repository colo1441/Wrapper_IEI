package app.model;

import org.openqa.selenium.WebElement;

public class Marca {
    private String nombre;
    private String idCheckbox;
    private String source;
    private WebElement webElement;


    public Marca(String nombre, String source, WebElement webElement) {
        this.nombre = nombre;
        this.source = source;
        this.webElement = webElement;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
