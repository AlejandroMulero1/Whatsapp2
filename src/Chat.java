import java.lang.reflect.Constructor;
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
        //TODO SELECT * FROM MENSAJES WHERE ID CHAT= idChat (OTRO METODO)
        //TEST
        this.mensajes.add(new Mensajes("Hola javi",1,1,new Timestamp(System.currentTimeMillis()),0));
        this.mensajes.add(new Mensajes("Hola Ale",1,2,new Timestamp(System.currentTimeMillis()),0));
        this.mensajes.add(new Mensajes("Adios Javi",1,1,new Timestamp(System.currentTimeMillis()),0));
        this.mensajes.add(new Mensajes("Adios Ale",1,2,new Timestamp(System.currentTimeMillis()),0));
        return mensajes;
    }

    /**
     * Metodo que insertara en la base de datos un nuevo mensaje en el chat que le corresponda
     * @param mensaje : parametro que contiene los datos del mensaje a insertar en la base de datos
     */
    public void setMensajes(Mensajes mensaje) {
        //TODO INSERT IN MENSAJES MENSAJE (OTRO METODO)
        this.mensajes.add(mensaje);
    }

    public String getNombreChat() {
        return nombreChat;
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

    //MedioBien
    public static void mostrarContenidoChat(int idChat){
        String consulta = "SELECT texto FROM mensajes WHERE idChat "+idChat+"ORDER BY horaLLegada";
        int m = 0;
        List<String> ListaMensajes = MetodosDB.mostrarDatos(consulta, new String[]{"texto, horaLlegada, idEmisor"}, new String[]{"string","timestamp", "int"});
        for (int i = 0; i < ListaMensajes.size()/3; i++) {
            for (int j = 0; j < 3; j++) {
                switch (j){
                    case 0:
                    case 1:
                        System.out.print(ListaMensajes.get(m) +"  ");
                        break;
                    case 2:System.out.print(MetodosDB.mostrar1Dato("SELECT nombre FROM Usuarios WHERE id = "+ListaMensajes.get(m), "nombre", "string")+"\n");
                        break;
                }
            }
        }
    }
}
