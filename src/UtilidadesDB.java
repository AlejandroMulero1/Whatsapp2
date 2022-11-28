import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class UtilidadesDB {

    /**
     * Metodo que a través de la id de un usuario accedera a la base de datos y obtendra todos los chats en los que participa
     * el usuario, devolviendo todos los chats encontrados en una lista
     * @return listado con todos los chats en los que participa el usuario
     */
    public static List<Chat> obtenerChat(int id){
        int m = 0;
        String nombreChat = null;
        int idParticipante1 = 0, idParticipante2 = 0;

        List<Chat> ListaChats = new ArrayList<>();
        String consulta = "SELECT * FROM Chat WHERE idPrimerUsuario = "+id+" OR idSegundoUsuario = "+id;
        String[] datos = new String[]{"nombreChat","idPrimerUsuario","idSegundoUsuario"};
        List<String> ListaDatos = MetodosDB.mostrarDatos(consulta, datos, new String[]{"string", "int", "int"});

        //Con este bucle vamos a recoger todos los datos obtenidos de la ListaDatos y vamos a incorporarlos a una lista de objetos Chats
        for (int i = 0; i < ListaDatos.size()/datos.length; i++) {
            for (int j = 0; j < 3; j++) {
                switch (j){
                    case 0: nombreChat = ListaDatos.get(m);
                        break;
                    case 1: idParticipante1 = Integer.parseInt(ListaDatos.get(m));
                        break;
                    case 2: idParticipante2 = Integer.parseInt(ListaDatos.get(m));
                }
                m++;
            }
            ListaChats.add(new Chat(1 , nombreChat, idParticipante1, idParticipante2));
        }
        return ListaChats;
    }


    /**
     * Metodo que inserta en la BD un mensaje pasado por parametro a través de una conexion con la BD y un PreparedStatement
     * @param mensajeEnviado
     */
    public static void insertarMensaje(Mensajes mensajeEnviado){
        Connection con = null;

        try {
            con = MetodosDB.conexion();
            PreparedStatement pstmt= con.prepareStatement("INSERT INTO ad2223_amulero.Mensajes (texto, idChat, idEmisor, horaLlegada, leido) VALUES(?, ?, ?, ?, ?)");
            pstmt.setString(1, mensajeEnviado.getTexto());
            pstmt.setInt(2, mensajeEnviado.getIdChat());
            pstmt.setInt(3, mensajeEnviado.getIdEmisor());
            pstmt.setTimestamp(4, mensajeEnviado.getHoraLlegada());
            pstmt.setInt(5, mensajeEnviado.isLeido());
            pstmt.execute();

        }catch (Exception ex){
           ex.printStackTrace();
        }
    }

    /**
     * Metodo que accede a la base de datos para obtener el chat al que pertenezca el parametro nombre, el cual es el
     * nombre de ese chat
     * @param nombre : parametro que almacena el nombre del chat deseado
     * @return un objeto de la clase chat que representa el chat deseado
     */
    public static Chat obtenerChat(String nombre, List<Chat>ListaChats){
        Chat chat = new Chat();
        for (int i = 0; i < ListaChats.size(); i++) {
            if(nombre.equals(ListaChats.get(i).getNombreChat())){
                chat = ListaChats.get(i);
            }
        }
        return chat;
    }

}
