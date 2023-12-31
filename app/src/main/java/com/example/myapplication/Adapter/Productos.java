package com.example.myapplication.Adapter;

public class Productos {

    private int id;
    private String nombre;
    private long codigo;
    private String cantidad;
    private String precio;

    private String foto;

    private boolean isSelected;
    private boolean isFound;


    public Productos(int id, String nombre, long codigo, String cantidad, String precio, String foto) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.cantidad = cantidad;
        this.precio = precio;
        this.foto = foto;
        this.isSelected = false;
        this.isFound = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isFound() {
        return isFound;
    }

    public void setFound(boolean found) {
        isFound = found;
    }


    @Override
    public String toString() {
        return "Productos{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", codigo=" + codigo +
                ", cantidad='" + cantidad + '\'' +
                ", precio='" + precio + '\'' +
                ", foto='" + foto + '\'' +
                ", isSelected=" + isSelected +
                ", isFound=" + isFound +
                '}';
    }
}