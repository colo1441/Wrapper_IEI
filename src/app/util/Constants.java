package app.util;

public class Constants {

    public static final String DRIVER_GECKO_PATH = "/Users/kevin/Temporales/geckodriver";
    public static final String WEB_DRIVER_GECKO = "webdriver.gecko.driver";
    public static final String URL_FNAC = "https://www.fnac.es/Desayuno-y-cafe/s38477";
    public static final String URL_MEDIA_MARKT = "https://tiendas.mediamarkt.es/cafeteras-cafe";
    public static final String MEDIA_MARKT = "MEDIAMARKT";
    public static final String FNAC = "FNAC";
    public static final String BROWSER = "browser";
    public static final String ATTRIBUTE_HREF = "href";
    public static final String SELECT_ARTICLE_PROMPT = "Seleccionar artículo";
    public static final String SELECT_BRAND_PROMPT = "Seleccionar marca";


    public static class MediaMarkt{
        public static final String DESCENDANT_A= "./descendant::a";
        public static final String CLOSE_COOKIES= "close-cookies-law";
        public static final String WAITING_CATEGORIES_CAFE_TYPES ="categoryTree_10002237";
        public static final String CATEGORIES_CAFE_TYPES_LIST_ELEMENTS = "//*[contains(@class, 'categoriesTreeContainer categoriesTreeContainer1 ')]";
        public static final String WAITING_CAFE_MARCAS_LIST_ELEMENTS = "brandsFilterElements";
        public static final String CAFE_MARCAS_LIST_ELEMENTS = "//*[contains(@class, 'filterElement brandsFilterElement')]";
    }

    public static class Fnac{
        public static final String CLOSE_COOKIES= "i.icon:nth-child(3)";
        public static final String WAITING_CAFE_MARCAS_LIST_ELEMENTS = "Filters.js-SearchFilters";
        public static final String CAFE_MARCAS_LIST_ELEMENTS = "//*[contains(@class, 'Filters-content')]";
    }
}
