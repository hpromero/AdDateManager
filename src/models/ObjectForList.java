package models;

import org.neodatis.odb.OID;

public class ObjectForList {
    private String text1;
    private String text2;
    private String text3;
    private String text4;
    private String textBox;
    private String icon;
    private String colorItem;
    private String colorBox;
    private String modelObject;
    private OID oid;
    private int id;

    public ObjectForList(int id, OID oid, String text1, String text2, String text3, String text4, String textbox, String icon, String colorItem, String colorBox, String modelObject) {
        this.id = id;
        this.oid = oid;
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = text3;
        this.text4 = text4;
        this.textBox = textbox;
        this.icon = icon;
        this.colorItem = colorItem;
        this.colorBox = colorBox;
        this.modelObject = modelObject;
    }

    public String getText1() {
        return text1;
    }

    public String getText2() {
        return text2;
    }

    public String getText3() {
        return text3;
    }

    public String getText4() {
        return text4;
    }

    public String getTextBox() {
        return textBox;
    }

    public String getIcon() {
        return icon;
    }

    public String getColorItem() {
        return colorItem;
    }

    public String getColorBox() {
        return colorBox;
    }

    public String getModelObject() {
        return modelObject;
    }

    public OID getOid() {
        return oid;
    }
    public int getId() {
        return id;
    }
}
