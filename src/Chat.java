import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class Chat {

    private int idChat;

    private String nombreChat;
    private int idParticipante1;
    private int idParticipante2;

    private ArrayList<Mensajes> mensajes;


    public Chat(){
    }

    public Chat(String nombre, int idParticipante1, int idParticipante2){
        this.nombreChat = nombre;
        this.idParticipante1 = idParticipante1;
        this.idParticipante2 = idParticipante2;
        this.idChat=setIdChat(nombreChat);
    }

    public int getIdChat() {
        return idChat;
    }

        public void setIdChat(String nombreChat) {
        Utilidades.obtenerChatPorNombre(nombreChat);
        this.idChat = idChat;
    }

    public ArrayList<Mensajes> getMensajes() {
        return mensajes;
    }

    public String getNombreChat() {
        return nombreChat;
    }

    //TODO
    public static Chat crearChat(String nombreChat, String nombreParticipante){
        return new Chat();
    }


    //TODO
    public static void mandarMensaje(Mensajes mensaje, Chat chat){

    }

    public static void mostrarContenidoChat(Chat chatSelected){}
}
