package Controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.VBox;
import models.ObjectForList;
import org.neodatis.odb.OID;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

//public class SectionController implements Initializable {
public class SectionController {

    @FXML private VBox vbContent = null;
    @FXML private Label lbSubTitle;
    @FXML private Label lbTitle;
    @FXML private Button btnAdd;
    @FXML private Button btnSearch;
    @FXML private TextField tfSearch;
    private String model;
    private String title;
    private ArrayList<ObjectForList> objectList;
    private ArrayList<ObjectForList> objectListFilter;

//    @Override
//    public void initialize(URL location, ResourceBundle resources) { }

    public void setInitialData(String title,ArrayList<ObjectForList> objectList,String model){
        this.title = title;
        this.objectList=objectList;
        this.objectListFilter=objectList;
        this.model =model;
        openList();
    }

    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnAdd) {
            openDetail(0,null,this.model,"Nuevo");
        }
        if (actionEvent.getSource() == btnSearch) {
            filter();
        }
    }

    public void openDetail(int id, OID oid, String model, String subTitle){
        this.model=model;
        switch (model){
            case "User":
                lbSubTitle.setText(subTitle);
                openUserDetail(oid);
                break;
            case "Customer":
                lbSubTitle.setText(subTitle);
                openCustomerDetail(oid);
                break;
            case "Department":
                lbSubTitle.setText(subTitle);
                openDepartmentDetail(oid);
                break;
            case "Date":
                lbSubTitle.setText(subTitle);
                openDateDetail(id);
                break;
            default:
                break;
        }
    }


    public void openList(){
        Collections.sort(objectListFilter);
        vbContent.getChildren().clear();
        VBox vb = new VBox();
        vb.setPadding(new Insets(30, 0, 0, 0));
        vbContent.getChildren().add(vb);
        this.model=model;
        lbSubTitle.setText("Listado");
        lbTitle.setText(title);
        Node[] nodes = new Node[objectListFilter.size()];
        for (int i = 0; i < nodes.length; i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/ItemSection.fxml"));
                nodes[i] = loader.load();
                ItemSectionController controller = loader.getController();
                ObjectForList object = (ObjectForList) objectListFilter.get(i);
                controller.setData(object);
                controller.setParentController(this);
                vbContent.getChildren().add(nodes[i]);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public void openUserDetail(OID oid){
        vbContent.getChildren().clear();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/DetailUser.fxml"));
            Node node = loader.load();
            DetailUserController controller = loader.getController();
            controller.open(oid);
            controller.setParentController(this);
            vbContent.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void openCustomerDetail(OID oid){
        vbContent.getChildren().clear();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/DetailCustomer.fxml"));
            Node node = loader.load();
            DetailCustomerController controller = loader.getController();
            controller.open(oid);
            controller.setParentController(this);
            vbContent.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openDepartmentDetail(OID oid){
        vbContent.getChildren().clear();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/DetailDepartment.fxml"));
            Node node = loader.load();
            DetailDepartmentController controller = loader.getController();
            controller.open(oid);
            controller.setParentController(this);
            vbContent.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openDateDetail(int id){
        vbContent.getChildren().clear();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/DetailDate.fxml"));
            Node node = loader.load();
            DetailDateController controller = loader.getController();
            controller.open(id,false);
            controller.setSectionController(this);
            vbContent.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void filter() {
        objectListFilter = new ArrayList<>();
        if (!tfSearch.getText().isEmpty() && tfSearch.getText()!=null ) {
            objectListFilter = new ArrayList<>();
            for (ObjectForList item : objectList) {
                if (item.getText1().toLowerCase().contains(tfSearch.getText().toLowerCase())
                        || item.getText2().toLowerCase().contains(tfSearch.getText().toLowerCase())
                        || item.getText3().toLowerCase().contains(tfSearch.getText().toLowerCase())
                        || item.getTextBox().toLowerCase().contains(tfSearch.getText().toLowerCase())
                        || item.getText4().toLowerCase().contains(tfSearch.getText().toLowerCase())) {
                    objectListFilter.add(item);
                }
            }
        }else{
            objectListFilter = objectList;
        }
        openList();
    }
}