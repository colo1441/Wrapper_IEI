package app.controller;

import app.object.Categoria;
import app.object.Marca;
import app.web.Fnac;
import app.web.MediaMarkt;
import app.util.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static app.util.Constants.SELECT_ARTICLE_PROMPT;

public class MainController implements Initializable {
    private Stage stage;
    @FXML
    ComboBox<Categoria> cbCategoria;

    @FXML
    CheckBox chbMediaMarkt;

    @FXML
    CheckComboBox<Marca> chbComboMarca;

    @FXML
    CheckBox chbFNAC;

    @FXML
    Button searchBTN;


    private MediaMarkt mediaMarkt;
    private boolean clickedMediaMarkt;

    private Fnac fnac;
    private boolean clickedFNAC;

    private Marca marcaSelected;
    private Categoria categoriaSelected;
    private List<Marca> listMarcasSelected;

    private Main mainApp;

    public void initStage(Stage primaryStage, Main main) {
        this.mainApp = main;
        this.stage = primaryStage;
        this.listMarcasSelected = new ArrayList<Marca>();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbCategoria.setPromptText(SELECT_ARTICLE_PROMPT);

        cbCategoria.setDisable(true);
        chbComboMarca.setDisable(true);
         searchBTN.setDisable(true);


        chbMediaMarkt.selectedProperty().addListener((observable, oldValue, newValue) -> {

            if(cbCategoria.getItems()!=null)
                cbCategoria.getItems().clear();

            if(chbComboMarca.getItems()!=null) {
                chbComboMarca.getCheckModel().clearChecks();
                chbComboMarca.getItems().clear();
                chbComboMarca.setDisable(true);
                searchBTN.setDisable(true);
            }

            if(newValue){
                clickedMediaMarkt = true;
                selectMediaMarkt();
                cbCategoria.setDisable(false);

            } else {
                clickedMediaMarkt = false;
                if(!clickedFNAC){
                    cbCategoria.getItems().clear();
                    cbCategoria.setDisable(true);
                }
            }

            if(clickedFNAC){
                List<Categoria> categoriasTotales = new ArrayList<>();
                categoriasTotales.addAll(cbCategoria.getItems());
                categoriasTotales.addAll(fnac.getCategorias());
                cbCategoria.setItems(FXCollections.observableArrayList(categoriasTotales));
                cbCategoria.setDisable(false);
            }

        });
        chbFNAC.selectedProperty().addListener((observable, oldValue, newValue) -> {

            if(cbCategoria.getItems()!=null)
                cbCategoria.getItems().clear();

            if(chbComboMarca.getItems()!=null) {
                chbComboMarca.getCheckModel().clearChecks();
                chbComboMarca.getItems().clear();
                chbComboMarca.setDisable(true);
                searchBTN.setDisable(true);
            }

            if(newValue){
                clickedFNAC = true;
                selectFNAC();
                cbCategoria.setDisable(false);

            } else {
                clickedFNAC = false;
                //limpiar todo lo que tenga que ver con fnac
                if(!clickedMediaMarkt){
                    cbCategoria.getItems().clear();
                    cbCategoria.setDisable(true);
                }

            }

            if(clickedMediaMarkt){
                List<Categoria> categoriasTotales = new ArrayList<>();
                categoriasTotales.addAll(cbCategoria.getItems());
                categoriasTotales.addAll(mediaMarkt.getCategorias());
                cbCategoria.setItems(FXCollections.observableArrayList(categoriasTotales));
                cbCategoria.setDisable(false);
            }

        });


        cbCategoria.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(cbCategoria.getItems()!=null && !cbCategoria.getItems().isEmpty()){
                categoriaSelected = cbCategoria.getSelectionModel().getSelectedItem();
                System.out.println("Seleccionado cbCategoria: " + categoriaSelected  + " url: " + categoriaSelected.getUrl());

                if(categoriaSelected!=null && categoriaSelected.getSource().equals(Constants.URL_MEDIA_MARKT)) {
                    ObservableList<Marca> marcaObservableList = FXCollections.observableArrayList(mediaMarkt.getMarcasBySelection(cbCategoria.getSelectionModel().getSelectedItem()));
                    chbComboMarca.getCheckModel().clearChecks();
                    chbComboMarca.getItems().clear();
                    chbComboMarca.getItems().addAll(marcaObservableList);
                  }else if(categoriaSelected!=null && categoriaSelected.getSource().equals(Constants.URL_FNAC)) {
                    ObservableList<Marca> marcaObservableList = FXCollections.observableArrayList(fnac.getMarcasBySelection(cbCategoria.getSelectionModel().getSelectedItem()));
                    chbComboMarca.getCheckModel().clearChecks();
                    chbComboMarca.getItems().clear();
                    chbComboMarca.getItems().addAll(marcaObservableList);
                }

                chbComboMarca.setDisable(false);
                searchBTN.setDisable(false);
            }


        });

        chbComboMarca.getCheckModel().getCheckedItems().addListener((ListChangeListener<Marca>) c -> {
            listMarcasSelected.clear();
            listMarcasSelected.addAll(chbComboMarca.getCheckModel().getCheckedItems());
        });


        searchBTN.setOnAction(event -> {
            try {

                if(listMarcasSelected !=null && !listMarcasSelected.isEmpty()) {
                    //open new window with data
                    System.out.println("MARCA SELECCIONADA, SE BUSCARA POR MARCA: " + marcaSelected);
                    List<Marca> listMarcasMediamarkt = new ArrayList<>();
                    List<Marca> listMarcasFNAC = new ArrayList<>();
                    for(Marca marca : listMarcasSelected){
                        if(marca.getSource().equalsIgnoreCase(Constants.MEDIA_MARKT)){
                            listMarcasMediamarkt.add(marca);
                        } else if(marca.getSource().equalsIgnoreCase(Constants.FNAC)){
                            listMarcasFNAC.add(marca);
                        }
                    }
                    List<Marca> listTotalSearch = new ArrayList<>();

                    if(!listMarcasMediamarkt.isEmpty())
                        listTotalSearch.addAll(mediaMarkt.recogerObjetos(listMarcasMediamarkt));
                    if(!listMarcasFNAC.isEmpty())
                        listTotalSearch.addAll(fnac.recogerObjetos(listMarcasFNAC));

                    listMarcasSelected.clear();
                    listMarcasSelected.addAll(listTotalSearch);
                    mainApp.initItemsController(listMarcasSelected);

                } else {
                    //open the categories page with the cafes and tes brand
                    System.out.println("NO HAY MARCAS SELECCIONADAS, SE BUSCARA POR CATEGORIA: " + categoriaSelected);
                    //listMarcasSelected = mediaMarkt.recogerObjetos(categoriaSelected.getListMarcas());


                    List<Marca> listMarcasMediamarkt = new ArrayList<>();
                    List<Marca> listMarcasFNAC = new ArrayList<>();
                    for(Marca marca : categoriaSelected.getListMarcas()){
                        if(marca.getSource().equalsIgnoreCase(Constants.MEDIA_MARKT)){
                            listMarcasMediamarkt.add(marca);
                        } else if(marca.getSource().equalsIgnoreCase(Constants.FNAC)){
                            listMarcasFNAC.add(marca);
                        }
                    }
                    List<Marca> listTotalSearch = new ArrayList<>();

                    if(!listMarcasMediamarkt.isEmpty())
                        listTotalSearch.addAll(mediaMarkt.recogerObjetos(listMarcasMediamarkt));
                    if(!listMarcasFNAC.isEmpty())
                        listTotalSearch.addAll(fnac.recogerObjetos(listMarcasFNAC));


                    categoriaSelected.getListMarcas().clear();
                    categoriaSelected.setListMarcas(listTotalSearch);
                    if(!categoriaSelected.getListMarcas().isEmpty())
                        mainApp.initItemsController(categoriaSelected.getListMarcas());
                }

            } catch (Exception e) {
                System.err.println("Ha ocurrido un error lanzando la pantalla del grid");
                e.printStackTrace();
            }
        });
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.stage = primaryStage;
    }

    public void selectMediaMarkt() {
        if(mediaMarkt==null)
            mediaMarkt = new MediaMarkt();
        //Rellenar categorias
        cbCategoria.setItems(FXCollections.observableArrayList(mediaMarkt.getCategorias()));
    }

    public void selectFNAC() {
        if(fnac==null)
            fnac = new Fnac();
        //Rellenar categorias
        cbCategoria.setItems(FXCollections.observableArrayList(fnac.getCategorias()));

    }
}
