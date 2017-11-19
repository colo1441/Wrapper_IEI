package app.object;

import org.openqa.selenium.WebElement;

import java.util.List;

public class Categoria extends Base {
    private List<Marca> listMarcas;

    public Categoria(String nombre) {
        super(nombre);
    }

    public List<Marca> getListMarcas() {
        return listMarcas;
    }

    public void setListMarcas(List<Marca> listMarcas) {
        this.listMarcas = listMarcas;
    }
}
