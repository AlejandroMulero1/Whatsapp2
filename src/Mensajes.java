import java.sql.Timestamp;

public class Mensajes {
    private int idMensaje;
    private String texto;
    private int idChat;
    private int idEmisor;
    private Timestamp horaLlegada;
    private int leido;

    public Mensajes(){

    }

    public Mensajes(String texto, int idChat, int idEmisor, Timestamp horaLlegada, int leido){

        this.texto=texto;
        this.idChat=idChat;
        this.idEmisor=idEmisor;
        this.horaLlegada=horaLlegada;
        this.leido=leido;
    }

    public int getIdMensaje() {
        return idMensaje;
    }

    public String getTexto() {
        return texto;
    }

    public int getIdChat() {
        return idChat;
    }

    public int getIdEmisor() {
        return idEmisor;
    }

    public Timestamp getHoraLlegada() {
        return horaLlegada;
    }

    public int isLeido() {
        return leido;
    }
}
