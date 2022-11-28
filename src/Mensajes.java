import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

    //Geters y seters
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

    public void setIdMensaje(int idMensaje) {
        this.idMensaje = idMensaje;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    public void setIdEmisor(int idEmisor) {
        this.idEmisor = idEmisor;
    }

    public void setHoraLlegada(Timestamp horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public int getLeido() {
        return leido;
    }

    /**
     * Metodo que accede a la base de datos para actualizar los mensajes a leidos
     * @param leido : parametro que almacena si el mensaje ha sido leido o no
     */
    public void setLeido(int leido) {
        try {
            Connection connection=MetodosDB.conexion();
            PreparedStatement pstmt= connection.prepareStatement("UPDATE ad2223_amulero.Mensajes SET leido=1 WHERE idMensaje=?");
            pstmt.setInt(1, this.idMensaje);
            pstmt.execute();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        this.leido = leido;
    }
}
