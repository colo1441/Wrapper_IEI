package app.controller;

import app.model.Categoria;
import app.model.Marca;
import app.scrap.Fnac;
import app.scrap.MediaMarkt;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

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
    ComboBox<Marca> cbMarca;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //selectMediaMarkt();
        selectFNAC();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.stage = primaryStage;
    }

    public void selectMediaMarkt(){
        MediaMarkt mediaMarkt = new MediaMarkt();

        //Rellenar categorias
        cbCategoria.setPromptText(SELECT_ARTICLE_PROMPT);
        cbCategoria.setItems(FXCollections.observableArrayList(mediaMarkt.getCategorias()));

        cbCategoria.getSelectionModel().selectedItemProperty().addListener((ChangeListener) (ov, t, t1) -> {
            if(cbMarca.getItems()!=null)
                cbMarca.getItems().clear();

            System.out.println("Seleccionado: " + cbCategoria.getSelectionModel().getSelectedItem());
            cbMarca.setPromptText(SELECT_ARTICLE_PROMPT);
            cbMarca.setItems(FXCollections.observableArrayList(
                    mediaMarkt.getMarcasBySelection(cbCategoria.getSelectionModel().getSelectedItem())
            ));
        });

        cbMarca.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(cbMarca.getItems()!=null && !cbMarca.getItems().isEmpty())
                System.out.println("Se ha seleccionado la marca: " +cbMarca.getSelectionModel().getSelectedItem().getNombre());
        });

    }

    public void selectFNAC() {
        Fnac fnac = new Fnac();
        //Rellenar categorias
        if(cbCategoria.getItems()!=null) {
            List<Categoria> listaCategorias = cbCategoria.getItems();
            listaCategorias.addAll(fnac.getCategorias());
            cbCategoria.setItems(FXCollections.observableArrayList(listaCategorias));
        } else {
            cbCategoria.setItems(FXCollections.observableArrayList(fnac.getCategorias()));
        }

        cbCategoria.getSelectionModel().selectedItemProperty().addListener((ChangeListener) (ov, t, t1) -> {
            if(cbMarca.getItems()!=null)
                cbMarca.getItems().clear();

            System.out.println("Seleccionado: " + cbCategoria.getSelectionModel().getSelectedItem());
            cbMarca.setPromptText(SELECT_ARTICLE_PROMPT);
            FXCollections.observableArrayList(
                    fnac.getMarcasBySelection(cbCategoria.getSelectionModel().getSelectedItem()));
            cbMarca.setItems(FXCollections.observableArrayList(
                    fnac.getMarcasBySelection(cbCategoria.getSelectionModel().getSelectedItem())
            ));
        });



    }
}
