import java.sql.Timestamp;

public class Mensajes {
    private int idMensaje;
    private String texto;
    private int idChat;
    private int idEmisor;
    private Timestamp horaLlegada;
    private boolean leido;

    public Mensajes(){

    }

    public Mensajes(String texto, int idChat, int idEmisor, Timestamp horaLlegada, int leido){

        this.texto=texto;
        this.idChat=idChat;
        this.idEmisor=idEmisor;
        this.horaLlegada=horaLlegada;
        this.leido=leido;
    }
}
