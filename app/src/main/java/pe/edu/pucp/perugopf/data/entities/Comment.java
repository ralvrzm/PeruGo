package pe.edu.pucp.perugopf.data.entities;

import com.google.gson.annotations.SerializedName;

public class Comment {
    private int eventId;
    private int id;
    @SerializedName("name")
    private String nombre;
    @SerializedName("email")
    private String correo;
    @SerializedName("body")
    private String comentario;

    public int getEventId() {
        return eventId;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getComentario() {
        return comentario;
    }
}
