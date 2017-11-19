package app.object;

public class Cafetera extends Base {

    private String description;
    private String price;
    private String brand;

    public Cafetera(String name, String description, String brand, String price, String sourceURL, String url) {
        super(name);
        this.description = description;
        this.brand=brand;
        this.price = price;
        setSource(sourceURL);
        setUrl(url);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
