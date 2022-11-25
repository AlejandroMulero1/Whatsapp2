import java.util.List;

public class Usuarios {
    private int id;
    private String nombre;
    private String contraseña;
    private List<Usuarios> usuariosBloqueados;
    private List<Usuarios> contactos;

    public Usuarios(int id, String nombre, String contraseña){
        this.id = id;
        this.nombre = nombre;
        this.contraseña = contraseña;

        this.usuariosBloqueados = setUsuariosBloqueados(this.id);
        this.contactos = setContactos(this.id);
    }

    public Usuarios (){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //TODO
    public List<Usuarios> setUsuariosBloqueados(int id) {
        return this.usuariosBloqueados;
    }

    //TODO
    public List<Usuarios> setContactos(int id) {

        return this.contactos;
    }

    //TODO
    public static Usuarios getUsuario(String nombre, String contraseña){


        return new Usuarios();
    }

    //TODO
    public static boolean estaBloqueado(Usuarios usuario1, Usuarios Usuario2){
        
        return true;
    }

    //TODO
    public static void mostrarContactos(){

    }
}
