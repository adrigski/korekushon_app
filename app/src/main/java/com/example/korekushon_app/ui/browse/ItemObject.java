package com.example.korekushon_app.ui.browse;

public class ItemObject {

    String console_name, product_id, product_name;

    public ItemObject(String product_name) {
        this.product_name = product_name;
    }


    public String getConsole_name() {
        return console_name;
    }

    public void setConsole_name(String console_name) {
        this.console_name = console_name;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

}
