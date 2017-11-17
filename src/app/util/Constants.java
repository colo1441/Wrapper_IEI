package app.util;

public class Constants {

    public static final String DRIVER_GECKO_PATH = "/Users/COLO/Downloads/geckodriver-v0.19.1-win64/geckodriver.exe";
    public static final String WEB_DRIVER_GECKO = "webdriver.gecko.driver";
    public static final String URL_EL_CORTE_INGLES = "http://elcorteingles.es";
    public static final String URL_FNAC = "https://www.fnac.es/Desayuno-y-cafe/s38477";
//    public static final String URL_MEDIA_MARKT = "https://www.mediamarkt.es";
    public static final String URL_MEDIA_MARKT = "https://tiendas.mediamarkt.es/cafeteras-cafe";
    public static final String BROWSER = "browser";
    public static final String ATTRIBUTE_HREF = "href";
    public static final String SELECT_ARTICLE_PROMPT = "Seleccionar artículo";


    public static class MediaMarkt{
        public static final String CLOSE_COOKIES= "close-cookies-law";
        public static final String NAVIGATION_ALL_CATEGORIES= "navigation__all--categories";
        public static final String NAVIGATION_CATEGORIES_HOGAR="world_hogar-jardin";
        //public static final String NAVIGATION_CATEGORIES_HOGAR_CAFE="category_hogar-jardin--ctg-cafe";
        public static final String NAVIGATION_CATEGORIES_HOGAR_CAFE="a[href*='https://tiendas.mediamarkt.es/cafeteras-cafe']";
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
