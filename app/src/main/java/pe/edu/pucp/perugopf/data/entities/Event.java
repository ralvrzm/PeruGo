package pe.edu.pucp.perugopf.data.entities;

import com.google.gson.annotations.SerializedName;

public class Event {
    private int userId;
    private int id;
    private String title;
    @SerializedName("body")
    private String text;
    private String direccion;
    private String fecha;
    private String latitud;
    private String longitud;
    private String esVisible;

    public String getEsVisible() {
        return esVisible;
    }

    public void setEsVisible(String esVisible) {
        this.esVisible = esVisible;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }


    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
