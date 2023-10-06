package com.example.myapplication;

public class Estado {

    private int id;
    private String estadoo;

    private String estadocustom;

    public Estado() {
    }

    public Estado(int id, String estadoo) {
        this.id = id;
        this.estadoo = estadoo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEstadoo() {
        return estadoo;
    }

    public void setEstadoo(String estadoo) {
        this.estadoo = estadoo;
    }

    @Override
    public String toString() {

        this.estadocustom  = this.estadocustom = " "+estadoo;
        return estadocustom;
    }
}
