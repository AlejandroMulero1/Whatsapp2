import java.util.ArrayList;
import java.util.List;

public class Usuarios {
    private int id;

    private static int usuariosCreados=1;
    private String nombre;
    private String contraseña;
    private List<String> usuariosBloqueados=new ArrayList<>();
    private List<String> contactos=new ArrayList<>();

    public Usuarios(int id, String nombre, String contraseña){
        this.id = id;
        this.nombre = nombre;
        this.contraseña = contraseña;
    }

    //Getters y Setters
    public int getId() {return id;}

    /**
     * Metodo que accede a la base de datos para obtener todos los contactos del usuario
     * @return una lista con los contactos del usuario
     */
    public List<String> getContactos() {
        //TODO SELECT * FROM CONTACTOS (METODO NUEVO)
        contactos.add("Javi"); //TEST
        return contactos;
    }

    /**
     * Metodo que accede a la base de datos para obtener todos los contactos bloqueados del usuario
     * @return una lista con los contactos bloqueados del usuario
     */
    public List<String> getUsuariosBloqueados() {
        //TODO SELECT * FROM BLOQUEADOS (METODO NUEVO)
        return usuariosBloqueados;
    }


    /**
     * Metodo que accede a la base de datos para insertar un nuevo contacto
     * @param contaco
     */
    public void setContactos(String contaco) {
        this.contactos.add(contaco); //TEST
        //TODO INSERT IN CONTACTOS (METODO NUEVO)
    }

    public void setUsuariosBloqueados(String contacto, boolean bloquear) {
       if (bloquear){
           this.usuariosBloqueados.add(contacto); //TEST
           //TODO INSERT IN BLOQUEADOS (METODO NUEVO)
       }
       else {
           this.usuariosBloqueados.remove(contacto); //TEST
           //TODO DELETE IN BLOQUEADOS (METODO NUEVO)
       }
    }

    //Metodos

    /**
     * Metodo que a traves del nombre y la contraseña, accede a la base de datos para buscar un usuario que posea los datos ingresados, y si no existe
     * ninguno que los posea, insertara un nuevo usuario
     * @param nombre
     * @param contraseña
     * @return
     */
    public static Usuarios obtenerUsuario(String nombre, String contraseña){
        //TODO SELECT USUARIO -> IF(FALSE){INSERT INTO USUARIOS (USUARIO NUEVO)} ELSE{RETUN USUARIO} (HACER AQUI)
        //TEST
        Usuarios usuario=new Usuarios(usuariosCreados, nombre, contraseña);
        usuariosCreados++;
        return usuario;
    }


    public static boolean estaBloqueado(Usuarios usuario1, Usuarios usuario2){
        String consulta = "SELECT * FROM bloqueados WHERE idUsuario = "+usuario1.getId() +" AND idBloqueado = "+usuario2.getId();
        Boolean establoqueado = MetodosDB.comprobarFila(consulta);
        return establoqueado;
    }

    //TODO
    public static void mostrarContactos(){

    }
}
