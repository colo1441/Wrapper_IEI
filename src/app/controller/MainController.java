package app.controller;

import app.Main;
import app.model.Categoria;
import app.model.Item;
import app.model.Marca;
import app.scrap.Fnac;
import app.scrap.MediaMarkt;
import app.util.Constants;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import javax.print.DocFlavor;
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
    ComboBox<Marca> cbMarca;

    @FXML
    ChoiceBox<Marca> choiceBoxMarca;

    @FXML
    CheckBox chbMediaMarkt;

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
        cbMarca.setPromptText(SELECT_BRAND_PROMPT);

        cbCategoria.setDisable(true);
        cbMarca.setDisable(true);
        choiceBoxMarca.setDisable(true);
        searchBTN.setDisable(true);


        chbMediaMarkt.selectedProperty().addListener((observable, oldValue, newValue) -> {

            if(cbCategoria.getItems()!=null)
                cbCategoria.getItems().clear();

            if(cbMarca.getItems()!=null)
                cbMarca.getItems().clear();

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

            if(cbMarca.getItems()!=null)
                cbMarca.getItems().clear();

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
                    cbMarca.setItems(FXCollections.observableArrayList(mediaMarkt.getMarcasBySelection(cbCategoria.getSelectionModel().getSelectedItem())));
                    choiceBoxMarca.setItems(FXCollections.observableArrayList(mediaMarkt.getMarcasBySelection(cbCategoria.getSelectionModel().getSelectedItem())));
                    choiceBoxMarca.setDisable(false);
                }else if(categoriaSelected!=null && categoriaSelected.getSource().equals(Constants.URL_FNAC)) {
                    cbMarca.setItems(FXCollections.observableArrayList(
                            fnac.getMarcasBySelection(cbCategoria.getSelectionModel().getSelectedItem())));
                }

                cbMarca.setDisable(false);
                searchBTN.setDisable(false);
            }


        });

        cbMarca.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(cbMarca.getItems()!=null && !cbMarca.getItems().isEmpty()) {
                marcaSelected = cbMarca.getSelectionModel().getSelectedItem();
                marcasSelected.add(marcaSelected);
                System.out.println("Seleccionado cbMarca: " + marcaSelected.getNombre() + " url: " + marcaSelected.getUrl());
            }

        });

        searchBTN.setOnAction(event -> {
            if(cbMarca.getItems()!=null && !cbMarca.getItems().isEmpty() && marcaSelected!=null ) {
                //open new window with data
                System.out.println("MARCA SELECCIONADA, SE BUSCARA POR MARCA: " + marcaSelected);
                marcasSelected = mediaMarkt.recogerObjetos(marcasSelected);
                try {
                    mainApp.initItemsController(marcasSelected);
                } catch (Exception e) {
                    System.err.println("Ha ocurrido un error lanzando la pantalla del grid");
                    e.printStackTrace();
                }
            } else {
                //open the categories page with the cafes and tes brand
                System.out.println("NO HAY MARCAS SELECCIONADAS, SE BUSCARA POR CATEGORIA: " + categoriaSelected);
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
