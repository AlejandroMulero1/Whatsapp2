import java.util.Scanner;

public class Utilidades {
    private static Scanner s = new Scanner(System.in);
    public static Usuarios iniciarSesion(){
        System.out.println("Introduzca el nombre de usuario");
        String usuario = s.next();
        System.out.println("Introduzca la contraseña");
        String contraseña = s.next();
        Usuarios usuario1 = Usuarios.getUsuario(usuario, contraseña);
        return usuario1;
    }

    public static boolean mostrarMensajesNuevos(Usuarios usuario){
        boolean mensajesNuevos=false;
        int idUsuario=usuario.getId();
        int numMensajesNuevos=0;
        ArrayList<Chat> chatsUsuario=obtenerChatsUsuario(idUsuario);
        for(Chat chat:chatsUsuario){
            for (Mensajes mensaje: chat.getMensajes()){
                if(!mensaje.isLeido()){
                    numMensajesNuevos++;
                }
            }
        }
        if (numMensajesNuevos>0){
            mensajesNuevos=true;
            System.out.println("Tienes " + numMensajesNuevos + " mensajes nuevos");
        }
        return mensajesNuevos;
    }

    public static int mostrarOpciones(){
        int opcion=0;
        System.out.println("1. Leer el chat");
        System.out.println("2. Enviar un mensaje");
        System.out.println("3. Mostrar listado de contactos");
        System.out.println("4. Bloquear o desbloquear un contacto");
        opcion=s.nextInt();
        while (opcion<0 || opcion>4) {
            System.out.println("Opcion incorrecta vuelva a intentarlo");
            opcion = s.nextInt();
        }
        return opcion;
    }

    public static void verChat(Usuarios usuario){
        String chat= mostrarOpcionesChats(usuario);
        Chat chatSelected=obtenerChatPorNombre(chat);
        Chat.mostrarContenidoChat(chatSelected);
    }


    public static String mostrarOpcionesChats(Usuarios usuario){
        String nombreChat="";
        for (Chat chat: mostrarChat(usuario.getId())){
            System.out.println(chat.getNombreChat());
        }
        nombreChat=s.next();
        return nombreChat;
    }


    public static List<Chat> obtenerChat(int id){
        int m = 0;
        String nombreChat = null;
        int idParticipante1 = 0, idParticipante2 = 0;

        List<Chat> ListaChats = new ArrayList<>();
        String consulta = "SELECT * FROM Chat WHERE idParticipante1 = "+id+" OR idParticipante2 = "+id;
        String[] datos = new String[]{"nombreChat","idParticipante1","idParticipante2"};
        List<String> ListaDatos = MetodosDB.mostrarDatos(consulta, datos);

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
            ListaChats.add(new Chat(nombreChat, idParticipante1, idParticipante2));
        }
        return ListaChats;
    }

    public static void escribirEnChat(Usuarios escritor){
        String chat= mostrarOpcionesChats(escritor);
        Chat chatSelected=obtenerChatPorNombre(chat);
        String texto=s.next();
        Mensajes mensajeEnviado=new Mensajes(texto, chatSelected)
    }
    public static void insertarMensaje(Mensajes mensajeEnviado){
        Connection con = null;

        try {
            con = MetodosDB.conexion();
            PreparedStatement pstmt= con.prepareStatement("INSERT INTO mensajes VALUES(?, ?, ?, ?, ?)");
            pstmt.setString(1, mensajeEnviado.getTexto());
            pstmt.setInt(2, mensajeEnviado.getIdChat());
            pstmt.setInt(3, mensajeEnviado.getIdEmisor());
            pstmt.setDate(4, Date.valueOf(mensajeEnviado.getHoraLlegada().toLocalDateTime().toString()));
            pstmt.setInt(5, mensajeEnviado.isLeido());

        }catch (Exception ex){
            System.out.println(ex);
        }
    }

    public static Chat obtenerChat(String nombre, List<Chat>ListaChats){
        Chat chat = new Chat();
        for (int i = 0; i < ListaChats.size(); i++) {
            if(nombre.equals(ListaChats.get(i).getNombreChat())){
                chat = ListaChats.get(i);
            }
        }
        return chat;
    }

    public static void mostrarTodosLosChats(List<Chat>ListaChats){
        for (int i = 0; i < ListaChats.size(); i++) {
            System.out.println(ListaChats.get(i).getNombreChat());
        }
    }





}
