import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class Chat {
    String nombreChat;
    int idParticipante1;
    int idParticipante2;
    List<Mensajes> mensajes;


    public Chat(){
    }

    public Chat(String nombre, int idParticipante1, int idParticipante2){
        this.nombreChat = nombre;
        this.idParticipante1 = idParticipante1;
        this.idParticipante2 = idParticipante2;
    }

    public static Chat crearChat(String nombreChat, String nombreParticipante){
        return new Chat();
    }

    public static void mostrarChat(Chat chat){

    }

    public static void mandarMensaje(Mensajes mensaje, Chat chat){

    }
}
