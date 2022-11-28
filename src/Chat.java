import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Chat {

    private int idChat;

    private static int  contadorChats=1;
    private String nombreChat;
    private int idParticipante1;
    private int idParticipante2;

    private ArrayList<Mensajes> mensajes = new ArrayList<>();

    public Chat(int idChat, String nombre, int idParticipante1, int idParticipante2){
        this.idChat=idChat;
        this.nombreChat = nombre;
        this.idParticipante1 = idParticipante1;
        this.idParticipante2 = idParticipante2;
    }

    public Chat() {

    }

    //Geters y Setters
    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(String nombreChat) {
        this.idChat = idChat;
    }


    /**
     * Metodo que accederá a la base de datos y obtiene todos los mensajes pertenecientes al chat al que pertenece la id que
     * le pasamos como parametro al método
     * @param idChat : parametro que contiene la id del chat del cual deseamos obtener sus mensajes
     * @return una lista de mensajes la cual posee todos los mensajes de esa conversacion
     */
    public ArrayList<Mensajes> getMensajes(int idChat) {
        //TODO SELECT * FROM MENSAJES WHERE IdChat= :idChat
        //TODO TEST
        ArrayList<Mensajes> ListaMensajesCompleta = new ArrayList<>();
        //Consulta
        String consulta = "SELECT * FROM Mensajes WHERE idChat = " + idChat + " ORDER BY horaLLegada";
        int m = 0;
        //Extracción de la base de datos de todos los datos de los mensajes
        List<String> ListaMensajes = MetodosDB.mostrarDatos(consulta, new String[]{"idMensaje", "texto", "idChat", "horaLlegada", "idEmisor", "leido"}, new String[]{"int", "string", "int", "timestamp", "int", "boolean"});
        //Bucle que introduce los datos de la BBDD obtenidos en una lista de objetos Mensaje
        for (int i = 0; i < ListaMensajes.size() / 6; i++) {
            Mensajes mensaje = new Mensajes();
            for (int j = 0; j < 6; j++) {
                switch (j) {
                    case 0: mensaje.setIdMensaje(Integer.parseInt(ListaMensajes.get(m)));
                        break;
                    case 1:mensaje.setTexto(ListaMensajes.get(m));
                        break;
                    case 2:mensaje.setIdChat(Integer.parseInt(ListaMensajes.get(m)));
                        break;
                    case 3:mensaje.setHoraLlegada(Timestamp.valueOf(ListaMensajes.get(m)));
                        break;
                    case 4:mensaje.setIdEmisor(Integer.parseInt(ListaMensajes.get(m)));
                        break;
                    case 5:mensaje.setLeido(Integer.parseInt(ListaMensajes.get(m)));
                        break;
                }
                m++;
            }
            ListaMensajesCompleta.add(mensaje);
        }
        return ListaMensajesCompleta;
    }


    /**
     * Metodo que insertara en la base de datos un nuevo mensaje en el chat que le corresponda
     * @param mensaje : parametro que contiene los datos del mensaje a insertar en la base de datos
     */
    public void setMensajes(Mensajes mensaje) {
        UtilidadesDB.insertarMensaje(mensaje);
    }

    public String getNombreChat() {
        return nombreChat;
    }

    public int getIdParticipante1() {
        return idParticipante1;
    }

    public int getIdParticipante2() {
        return idParticipante2;
    }


//Metodos


    //Hecho
    public static Chat crearChat(String nombreChat, String nombreParticipante1, String nombreParticipante2){
        Chat chat;
        Connection con;
        String consulta1 = "SELECT id FROM Usuarios WHERE nombre = "+nombreParticipante1;
        String consulta2 = "SELECT id FROM Usuarios WHERE nombre = "+nombreParticipante2;
        int id1, id2;
        //Busca en la BBDD los id correspondientes a los nombres recibidos
        id1 = Integer.parseInt(MetodosDB.mostrar1Dato(consulta1, "id", "int"));
        id2 = Integer.parseInt(MetodosDB.mostrar1Dato(consulta2, "id", "int"));

        //Inserta el chat en la BBDD
        try {
            con = MetodosDB.conexion();
            PreparedStatement pstmt= con.prepareStatement("USE ad2223_amulero INSERT INTO Chat VALUES(?, ?, ?)");
            pstmt.setString(1, nombreChat);
            pstmt.setInt(2, id1);
            pstmt.setInt(3, id2);
            pstmt.execute();
        }catch (Exception ex){
            System.out.println(ex);
        }
        Chat chatInsertado=new Chat(contadorChats, nombreChat, id1, id2);
        contadorChats++;
        return chatInsertado;
    }
}
