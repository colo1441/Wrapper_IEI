package app.controller;

import app.Main;
import app.model.Categoria;
import app.model.Marca;
import app.scrap.Fnac;
import app.scrap.MediaMarkt;
import app.util.Constants;
import com.sun.tools.internal.jxc.ap.Const;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static app.util.Constants.SELECT_ARTICLE_PROMPT;
import static app.util.Constants.SELECT_BRAND_PROMPT;

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
    private List<Marca> marcasSelected;

    private Main mainApp;

    public void initStage(Stage primaryStage, Main main) {
        this.mainApp = main;
        this.stage = primaryStage;
        this.marcasSelected = new ArrayList<Marca>();
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
            }

            if(newValue){
                clickedMediaMarkt = newValue;
                selectMediaMarkt();
                cbCategoria.setDisable(false);

            } else {
                clickedMediaMarkt = !newValue;
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
            }

            if(newValue){
                clickedFNAC = newValue;
                selectFNAC();
                cbCategoria.setDisable(false);

            } else {
                clickedFNAC = !newValue;
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
            marcasSelected.clear();
            marcasSelected.addAll(chbComboMarca.getCheckModel().getCheckedItems());
        });


        searchBTN.setOnAction(event -> {
            try {

                if(marcasSelected!=null && !marcasSelected.isEmpty()) {
                    //open new window with data
                    System.out.println("MARCA SELECCIONADA, SE BUSCARA POR MARCA: " + marcaSelected);
                    List<Marca> listMarcasMediamarkt = new ArrayList<>();
                    List<Marca> listMarcasFNAC = new ArrayList<>();
                    for(Marca marca : marcasSelected){
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

                    marcasSelected.clear();
                    marcasSelected.addAll(listTotalSearch);
                    mainApp.initItemsController(marcasSelected);

                } else {
                    //open the categories page with the cafes and tes brand
                    System.out.println("NO HAY MARCAS SELECCIONADAS, SE BUSCARA POR CATEGORIA: " + categoriaSelected);
                    //marcasSelected = mediaMarkt.recogerObjetos(categoriaSelected.getListMarcas());


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
