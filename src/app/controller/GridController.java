package app.controller;

import app.object.Cafetera;
import app.object.Marca;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GridController implements Initializable {
    private Stage primaryStage;
    private Main mainApp;
    private List<Marca> listBrands;
    private List<Cafetera> listTotalCafeteras;

    @FXML
    Text txtTitle;
    @FXML
    private TableView<Cafetera> tableViewListItems;
    @FXML
    private TableColumn<Cafetera, String> nombreColumn;
    @FXML
    private TableColumn<Cafetera, String> descripcionColumn;
    @FXML
    private TableColumn<Cafetera, String> precioColumn;
    @FXML
    private TableColumn<Cafetera, String> marcaColumn;
    @FXML
    private TableColumn<Cafetera, String> sourceColumn;
    @FXML
    private TableColumn<Cafetera, String> urlColumn;

    private ObservableList<Cafetera> listItemsObservable;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private String initTitle() {
        String text = "";
        int count = 1;
        for(Marca marca : listBrands){
            if(text.isEmpty()){
                text+=marca.getNombre();
            } else {
                text+=", "+marca.getNombre();
            }
            if (count>3)
                return text+" ...";
            count++;
        }

        return text;
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
            txtTitle.setText(initTitle());
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


            listItemsObservable.addAll(listTotalCafeteras);
            tableViewListItems.setItems(listItemsObservable);


        } catch (Exception e) {

        }

    }

    private void fillTotalItemsOfBrands(List<Marca> listBrands) {
        if(listTotalCafeteras ==null) listTotalCafeteras = new ArrayList<>();
        for(Marca marca : listBrands) {
            if(marca.getListCafeteras()!=null) {
                listTotalCafeteras.addAll(marca.getListCafeteras());
            }
        }

        System.err.println("LISTA DE ITEMS : " + listTotalCafeteras.size());
    }
}
