package com.ibm.androidapp;

public class apiStorage {
    int _id;
    String _name;
public apiStorage(int i, String string, String cursorString){}
    public apiStorage(int _id, String _name) {
        this._id = _id;
        this._name = _name;
    }



    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }
}
