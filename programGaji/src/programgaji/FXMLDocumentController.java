/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package programgaji;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Haikal Febriano
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TableColumn<Data2, String> gaji_col;

    @FXML
    private ComboBox<?> jabatan_box;

    @FXML
    private TableColumn<Data2, String> lembur_col;

    @FXML
    private TextField lembur_txtfield;

    @FXML
    private TableColumn<Data2, String> nama_col;

    @FXML
    private TextField nama_txtfield;

    @FXML
    private Button reset_btn;

    @FXML
    private Button simpan_btn;

    @FXML
    private TableView<Data2> tableview;

    @FXML
    private TableColumn<Data2, String> total_col;

    private Alert alert;
    private PreparedStatement prepare;
    private ResultSet result;
    private Connection connect;

    private String[] typeList = {"HRD", "Senior", "Junior"};

    public void jabatan_ComboBox() {

        List<String> typeL = new ArrayList<>();

        for (String data : typeList) {
            typeL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(typeL);
        jabatan_box.setItems(listData);
    }

    public void inventoryAddBtn() {

        if (nama_txtfield.getText().isEmpty()
                || lembur_txtfield.getText().isEmpty()
                || jabatan_box.getSelectionModel().getSelectedItem() == null) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Mohon isi semua kolom yang kosong!");
            alert.showAndWait();

        } else {

            connect = Data.connectDB();

            try {
                int gaji_lembur = Integer.parseInt(lembur_txtfield.getText()) * 100000;
                if (jabatan_box.getSelectionModel().getSelectedItem() == "HRD") {
                    prepare = connect.prepareStatement("INSERT INTO gaji2 " + "(nama, gaji, lembur, total)"
                            + "VALUES(?,?,?,?)");
                    prepare.setString(1, nama_txtfield.getText());
                    prepare.setString(2, Integer.toString(15000000));
                    prepare.setString(3, lembur_txtfield.getText());
                    prepare.setString(4, Integer.toString(gaji_lembur + 15000000));

                    prepare.executeUpdate();

                } else if (jabatan_box.getSelectionModel().getSelectedItem() == "Senior") {
                    prepare = connect.prepareStatement("INSERT INTO gaji2 " + "(nama, gaji, lembur, total)"
                            + "VALUES(?,?,?,?)");
                    prepare.setString(1, nama_txtfield.getText());
                    prepare.setString(2, Integer.toString(10000000));
                    prepare.setString(3, lembur_txtfield.getText());
                    prepare.setString(4, Integer.toString(gaji_lembur + 10000000));

                    prepare.executeUpdate();

                } else if (jabatan_box.getSelectionModel().getSelectedItem() == "Junior") {
                    prepare = connect.prepareStatement("INSERT INTO gaji2 " + "(nama, gaji, lembur, total)"
                            + "VALUES(?,?,?,?)");
                    prepare.setString(1, nama_txtfield.getText());
                    prepare.setString(2, Integer.toString(5000000));
                    prepare.setString(3, lembur_txtfield.getText());
                    prepare.setString(4, Integer.toString(gaji_lembur + 5000000));

                    prepare.executeUpdate();

                }

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Berhasil Ditambahkan!");
                alert.showAndWait();
                inventoryShowData();

                nama_txtfield.setText("");
                lembur_txtfield.setText("");
                jabatan_box.getSelectionModel().clearSelection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void reset_Btn() {
        try {

            String delete_all = "DELETE FROM gaji2";
            prepare = connect.prepareStatement(delete_all);
            prepare.executeUpdate();

            inventoryShowData();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ObservableList<Data2> inventoryDataList() {

        ObservableList<Data2> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM gaji2";

        connect = Data.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            Data2 prodata;

            while (result.next()) {

                prodata = new Data2(result.getInt("id"),
                        result.getString("nama"),
                        result.getString("gaji"),
                        result.getString("lembur"),
                        result.getString("total"));

                listData.add(prodata);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    // SHOW DATA ON TABLE
    private ObservableList<Data2> inventoryListData;

    public void inventoryShowData() {
        inventoryListData = inventoryDataList();

        nama_col.setCellValueFactory(new PropertyValueFactory<>("nama"));
        gaji_col.setCellValueFactory(new PropertyValueFactory<>("gaji"));
        lembur_col.setCellValueFactory(new PropertyValueFactory<>("lembur"));
        total_col.setCellValueFactory(new PropertyValueFactory<>("total"));

        tableview.setItems(inventoryListData);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        jabatan_ComboBox();
        inventoryShowData();
        // TODO
    }

}