package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import models.ObjectForList;
import org.neodatis.odb.OID;

public class ItemSectionController {
    @FXML private Label l_text1;
    @FXML private Label l_text2;
    @FXML private Label l_text3;
 //   @FXML private Label l_text4;
    @FXML private Label l_textBox;
 //   @FXML private String StrIcon;
 //   @FXML private String StrColorItem;
    @FXML private VBox vColorBox;

    private SectionController mController;

    private OID oid;
    private String model;
    private int id;

    public void setParentController(SectionController controller) {
        this.mController = controller;
    }

    public void setData(ObjectForList item) {
        id = item.getId();
        oid = item.getOid();
        model = item.getModelObject();
        l_text1.setText(item.getText1());
        l_text2.setText(item.getText2());
        l_text3.setText(item.getText3());
  //      l_text4.setText(item.getText4());
        l_textBox.setText(item.getTextBox());
  //      StrIcon=item.getIcon();
   //     StrColorItem=item.getColorItem();
        vColorBox.setStyle("-fx-background-color: "+item.getColorBox());
    }

    public void viewDetail() {
            mController.openDetail(id, oid,model,l_text1.getText());
    }


}
