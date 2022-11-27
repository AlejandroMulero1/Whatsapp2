import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utilidades {
    private static Scanner s = new Scanner(System.in);

    /**
     * Metodo que inicia la sesion del usuario preguntandole sus datos y devuelve el usuario "logeado" a través del metodo
     * obtenerUsuario, el cual lo obtendra si ya existe en la base de datos, o lo creara
     * @return objeto de la clase usuario que contendra los datos del usuario logueado
     */
    public static Usuarios iniciarSesion(){
        System.out.println("Introduzca el nombre de usuario");
        String usuario = s.next();
        System.out.println("Introduzca la contraseña");
        String contraseña = s.next();
        Usuarios usuario1 = Usuarios.obtenerUsuario(usuario, contraseña); //TODO
        return usuario1;
    }

    /**
     * Metodo que le mostrara al usuario previamente logeado si tiene mensajes nuevos, a traves de obtener con el metodo
     * obtenerChatsUsuario una lista con todos los chats en lso que participa el usuario, para posteriormente, recorrer las listas
     * de mensajes que poseen esos chats, por cada mensaje no leido que se encuentre durante el recorrido, se aumentara en un contador la cantidad
     * de mensajes no leidos para ser mostrado posteriormente al usuario
     * @param usuario objeto de la clase usuario que representa el usuario que ha sido logeado
     * @return
     */
    public static boolean mostrarMensajesNuevos(Usuarios usuario){
        boolean mensajesNuevos=false;
        int numMensajesNuevos=0;
        List<Chat> chatsUsuario=obtenerChatsUsuario(usuario.getId());
        for(Chat chat:chatsUsuario){
            for (Mensajes mensaje: chat.getMensajes(chat.getIdChat())){
                if(mensaje.isLeido()==0){
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

    /**
     * Metodo que servira unicamente para mostrar por pantalla al usuario las diversas opciones que posee para interactuar con el programa
     * la eleccion del usuario sera recogida en el parametro opcion para ser posteriormente devuelta al main para actuar en funcion
     * a su valor
     * @return parametro que contiene la elección del usuario
     */
    public static int mostrarOpciones(){
        int opcion=0;
        System.out.println("1. Leer el chat");
        System.out.println("2. Enviar un mensaje");
        System.out.println("3. Mostrar listado de contactos");
        System.out.println("4. Bloquear o desbloquear un contacto");
        System.out.println("5. Añadir contacto");
        System.out.println("6. Crear Chat");
        opcion=s.nextInt();
        while (opcion<0 || opcion>6) {
            System.out.println("Opcion incorrecta vuelva a intentarlo");
            opcion = s.nextInt();
        }
        return opcion;
    }

    /**
     * Metodo que mostrara al usuario todos los chats en los que participa, para preguntarle a que chat quiere acceder
     * a traves del metodo mostrarOpcionesChat, despues, se obtendra el chat deseado con el metodo getChat y para posteriormente llamar al
     * metodo mostrarCont
     * @param usuario : parametro que contiene el usuario del cual se mostrarán los chats
     */
    public static void verChat(Usuarios usuario){
        String chat= mostrarOpcionesChats(usuario);
        Chat chatSelected=getChat(chat);

        List<Mensajes> mensajesChat=chatSelected.getMensajes(chatSelected.getIdChat());
        for (Mensajes mensaje:mensajesChat){
            if (!usuario.getUsuariosBloqueados().contains(obtenerNombreUsuarioPorId(mensaje.getIdEmisor())))
                System.out.println(obtenerNombreUsuarioPorId(mensaje.getIdEmisor())+ ": " + mensaje.getTexto() + " " + mensaje.getHoraLlegada() + "( " + mensaje.isLeido() + ")");
            mensaje.setLeido(1);
        }
    }

    /**
     * Metodo que mostrara por pantalla todos los chats en los que participa el usuario pasado por parametro a través de mostrar
     * por pantalla el listado obtenido por el metodo ObtenerChatsUsuario
     * @param usuario : parámetro que contiene el usuario del cual se van a mostrar los chats en los que participa
     * @return
     */
    public static String mostrarOpcionesChats(Usuarios usuario){
        for (Chat chat: obtenerChatsUsuario(usuario.getId())){
            System.out.println(chat.getNombreChat());
        }
        System.out.println("Elija el chat deseado");
        String nombreChat=s.next();
        return nombreChat;
    }

    /**
     * Metodo que le permitira al usuario mandar un mensaje en el chat que el desee, enseñando sus chats a traves del ya mencionado
     * mostrarOpcionesChats, para posteriormente obtener el objeto de la clase chat correspondiente al chat deseado a traves del metodo getChat
     * y al finalizar, se le pedira al usuario el texto que quiera insertar en su mensaje
     * @param escritor : parametro que contiene al usuario que va a escribir ese mensaje
     */
    public static void escribirEnChat(Usuarios escritor){
        String chat= mostrarOpcionesChats(escritor);
        Chat chatSelected=getChat(chat);
        System.out.println("Escriba el mensaje:");
        String texto=s.next();
        Mensajes mensajeEnviado=new Mensajes(texto, chatSelected.getIdChat(),escritor.getId(), new Timestamp(System.currentTimeMillis()) ,0);
        chatSelected.setMensajes(mensajeEnviado);
    }

    /**
     * Metodo que muestra por pantalla el listado de contactos del usuario, además de devolver todos los contactos en un listado
     * para la posterior manipulacion del listado
     * @param usuario : parametro el cual contiene el usuario del que se van a mostrar sus contactos
     * @return listado que contiene todos los contactos del usuario
     */
    public static List<String> verContactos(Usuarios usuario){
        List<String> contactos=usuario.getContactos();
        for (String contacto:contactos){
            System.out.println(contacto);
        }
        return contactos;
    }

    /**
     * Metodo que muestra por pantalla el listado de contactos bloqueados del usuario, además de devolver todos los contactos bloqueados en un listado
     * para la posterior manipulacion del listado
     * @param usuario parametro el cual contiene el usuario del que se van a mostrar sus contactos bloqueados
     * @return listado que contiene todos los contactos bloqueados del usuario
     */
    public static List<String> verBloqueados(Usuarios usuario){
        List<String> bloqueados=usuario.getUsuariosBloqueados();
        for (String bloqueado:bloqueados){
            System.out.println(bloqueado);
        }
        return bloqueados;
    }

    /**
     * Metodo que bloquea o desbloquea un contacto, a traves de preguntar al usuario que desea hacer,
     * para pedirle al usuario el contacto, el metodo utilizara los metodos verContactos y verBloqueados
     * @param usuario : parametro el cual contiene al usuario del cual se accederan a sus listas de bloqueados y desbloqueados
     */
    public static void ajustarContactos(Usuarios usuario){
        System.out.println("1. Bloquear");
        System.out.println("2. Desbloquear");
        System.out.println("Que desea hacer");
        int opcion=s.nextInt();
        if (opcion==1){
            List<String> contactos=verContactos(usuario);
            System.out.println("Inserte el contacto al que quiere bloquear");
            String bloqueado= s.next();
            for (String contacto:contactos){
                if(contacto.equals(bloqueado)){
                    usuario.setUsuariosBloqueados(contacto, true);
                }
            }
        } else if (opcion==2){
            List<String> bloqueados=verBloqueados(usuario);
            System.out.println("Escriba al contacto que desea desbloquear");
            int desbloqueado=s.nextInt();
            for (String bloqueado:bloqueados){
                if(bloqueado.equals(desbloqueado)){
                    usuario.setUsuariosBloqueados(bloqueado, false);
                }
            }
        }
    }


    //METODOS PARA LA DAL

    /**
     * Metodo que accede a la base de datos para obtener el chat al que pertenezca el parametro nombre, el cual es el
     * nombre de ese chat
     * @param nombre : parametro que almacena el nombre del chat deseado
     * @return un objeto de la clase chat que representa el chat deseado
     */
   public static Chat getChat(String nombre){
        //TODO SELECT * FROM CHATS WHERE NOMBRE=nombre (AQUI)
       //TODO RETUN CHAT
        return new Chat(1, "chatTest", 1, 2); //TEST
   }

    /**
     * Metodo que a través de la id de un usuario accedera a la base de datos y obtendra todos los chats en los que participa
     * el usuario, devolviendo todos los chats encontrados en una lista
     * @param idUsuario: parametro que almacena la id del usuario del cual queremos obtener sus chats
     * @return listado con todos los chats en los que participa el usuario
     */
   public static List<Chat> obtenerChatsUsuario(int idUsuario){
        //TODO SELECT * FROM CHAT WHERE (idParticipante1=idUsuario) OR (idParticipante2=idUsuario) (AQUI)
       //TODO RETURN CHATS
       //TEST
       List<Chat> listaChats=new ArrayList<>();
       listaChats.add(new Chat(1, "chatTest", 1, 2));
        return listaChats;
   }

    /**
     * Metodo que a traves de la id de un usuario, accedera a la base de datos para encontrar el nombre del usuario
     * al que pertenece esa id
     * @return el nombre de usuario al cual pertenece esa id
     */
    public static String obtenerNombreUsuarioPorId(int id){
       String nombreUsuario;
       //TODO SELECT nombre FROM Usuarios WHERE idUsuario=id (AQUI)
        if (id==1){nombreUsuario="Ale";}else {nombreUsuario="Javi";} //TEST
       return nombreUsuario;
    }

    /**
     * Metodo que crea un chat, pidiendole al usuario el contacto el cual desea añadir y el nombre que desea que tenga el chat
     * @param usuario : parametro el cual almacena el ususario que desea crear un nuevo chat
     */
    public static void crearChat(Usuarios usuario){
        verContactos(usuario);
        System.out.println("Introduzca al contacto que desea agregar a este chat: ");
        String contacto=s.nextLine();
        System.out.println();
        System.out.println("Introduzca el nombre del chat");
        String nombre=s.nextLine();
        //TODO INSERT CHATS CHAT (AQUI)
    }
}
