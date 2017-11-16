package app.model;

public class CommonElement {
    private String id;
    private String nombre;
    private String source;
    private String url;

    public CommonElement(String nombre) {
        this.nombre = nombre;
    }

    public CommonElement(String id, String nombre, String source, String url) {
        this.id = id;
        this.nombre = nombre;
        this.source = source;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return nombre;
    }

}

