package app.controller;

import app.model.Categoria;
import app.model.Marca;
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
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private Stage stage;
    @FXML
    ComboBox<Categoria> cbCategoria;

    @FXML
    ComboBox<Marca> cbMarca;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MediaMarkt mediaMarkt = new MediaMarkt();

        //Rellenar categorias
        cbCategoria.setPromptText("Seleccionar artículo");
        cbCategoria.setItems(FXCollections.observableArrayList(mediaMarkt.getCategorias()));
        cbCategoria.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                cbMarca.getItems().clear();
                System.out.println("Seleccionado: " + cbCategoria.getSelectionModel().getSelectedItem());
                cbMarca.setItems(FXCollections.observableArrayList(
                        mediaMarkt.getMarcasBySelection(cbCategoria.getSelectionModel().getSelectedItem().getWebElement())
                ));

            }
        });

    }

    public void setPrimaryStage(Stage primaryStage) {
        this.stage = primaryStage;
    }
}
