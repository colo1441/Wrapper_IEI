package app.object;

import java.util.List;

public class Marca extends Base {
    private List<Cafetera> listCafeteras;

    public Marca(String nombre) {
        super(nombre);
    }

    public List<Cafetera> getListCafeteras() {
        return listCafeteras;
    }

    public void setListCafeteras(List<Cafetera> listCafeteras) {
        this.listCafeteras = listCafeteras;
    }
}
