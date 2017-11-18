package app.controller;

import app.Main;
import app.model.Item;
import app.model.Marca;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ItemsController implements Initializable {
    private Stage primaryStage;
    private Main mainApp;
    private List<Marca> listBrands;
    private List<Item> listTotalItems;

    @FXML
    private TableView<Item> tableViewListItems;
    @FXML
    private TableColumn<Item, String> nombreColumn;
    @FXML
    private TableColumn<Item, String> descripcionColumn;
    @FXML
    private TableColumn<Item, String> precioColumn;
    @FXML
    private TableColumn<Item, String> marcaColumn;
    @FXML
    private TableColumn<Item, String> sourceColumn;
    @FXML
    private TableColumn<Item, String> urlColumn;

    @FXML
    Button boton_aceptar;

    private ObservableList<Item> listItemsObservable;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        boton_aceptar.setOnMouseClicked((EventHandler<Event>) event -> {
            Stage stage = (Stage)boton_aceptar.getScene().getWindow();
            stage.close();
        });

    }

    public void initStage(Stage primaryStage, Main main, List<Marca> brands) {
        System.out.println("LISTA DE MARCAS A PINTAR EN GRID: " + brands.size());
        this.listBrands = brands;
        this.primaryStage = primaryStage;
        this.mainApp = main;
        listItemsObservable = FXCollections.observableArrayList();
        fillTotalItemsOfBrands(brands);
        if(mainApp==null){
            mainApp = new Main();
        }

        try {
            nombreColumn.setCellValueFactory(param -> new
                    ReadOnlyObjectWrapper<>(param.getValue().getNombre()));

            descripcionColumn.setCellValueFactory(param -> new
                    ReadOnlyObjectWrapper<>(param.getValue().getDescription()));

            precioColumn.setCellValueFactory(param -> new
                    ReadOnlyObjectWrapper<>(param.getValue().getPrice()));

            marcaColumn.setCellValueFactory(param -> new
                    ReadOnlyObjectWrapper<>(param.getValue().getBrand()));

            sourceColumn.setCellValueFactory(param -> new
                    ReadOnlyObjectWrapper<>(param.getValue().getSource()));

            urlColumn.setCellValueFactory(param -> new
                    ReadOnlyObjectWrapper<>(param.getValue().getUrl()));


            listItemsObservable.addAll(listTotalItems);
            tableViewListItems.setItems(listItemsObservable);


        } catch (Exception e) {

        }

    }

    private void fillTotalItemsOfBrands(List<Marca> listBrands) {
        if(listTotalItems==null) listTotalItems = new ArrayList<>();
        for(Marca marca : listBrands) {
            if(marca.getListItems()!=null) {
                listTotalItems.addAll(marca.getListItems());
            }
        }

        System.err.println("LISTA DE ITEMS : " + listTotalItems.size());
    }
}
