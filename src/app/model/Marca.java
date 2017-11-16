package app.model;

import org.openqa.selenium.WebElement;

import java.util.List;

public class Marca extends CommonElement {
    private String idCheckbox;
    private WebElement webElement;
    private List<Item> listItems;

    public Marca(String nombre, WebElement webElement) {
        super(nombre);
        this.webElement = webElement;
    }

    public String getIdCheckbox() {
        return idCheckbox;
    }

    public void setIdCheckbox(String idCheckbox) {
        this.idCheckbox = idCheckbox;
    }

    public WebElement getWebElement() {
        return webElement;
    }

    public void setWebElement(WebElement webElement) {
        this.webElement = webElement;
    }
}
