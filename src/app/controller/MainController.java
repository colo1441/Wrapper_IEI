package app.controller;

import app.model.Categoria;
import app.model.Marca;
import app.scrap.Fnac;
import app.scrap.MediaMarkt;
import app.util.Constants;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
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
    CheckBox chbMediaMarkt;

    @FXML
    CheckBox chbFNAC;

    private MediaMarkt mediaMarkt;
    private boolean clickedMediaMarkt;

    private Fnac fnac;
    private boolean clickedFNAC;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbCategoria.setPromptText(SELECT_ARTICLE_PROMPT);
        cbMarca.setPromptText(SELECT_BRAND_PROMPT);

        cbCategoria.setDisable(true);
        cbMarca.setDisable(true);

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
                System.out.println("Seleccionado cbCategoria: " + cbCategoria.getSelectionModel().getSelectedItem());
            }

            if(cbCategoria.getSelectionModel().getSelectedItem().getSource().equals(Constants.URL_MEDIA_MARKT)) {
                cbMarca.setItems(FXCollections.observableArrayList(mediaMarkt.getMarcasBySelection(cbCategoria.getSelectionModel().getSelectedItem())));
            }else if(cbCategoria.getSelectionModel().getSelectedItem().getSource().equals(Constants.URL_FNAC)) {
                //cbMarca.setItems(FXCollections.observableArrayList(fnac.getMarcasBySelection(cbCategoria.getSelectionModel().getSelectedItem())));
            }

            cbMarca.setDisable(false);
        });

        cbMarca.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(cbMarca.getItems()!=null && !cbMarca.getItems().isEmpty()) {
                System.out.println("Seleccionado cbMarca: " + cbMarca.getSelectionModel().getSelectedItem().getNombre());
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
