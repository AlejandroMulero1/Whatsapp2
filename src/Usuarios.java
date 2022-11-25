import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
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
        Connection con;
        Usuarios usuario;
        String consulta1 = "SELECT * FROM Usuarios WHERE nombre = "+nombre;

        if (!MetodosDB.comprobarFila(consulta1)){
            try {
                con = MetodosDB.conexion();
                PreparedStatement pstmt= con.prepareStatement("INSERT INTO Usuarios VALUES(?, ?)");
                pstmt.setString(1, nombre);
                pstmt.setString(2, contraseña);
            }catch (Exception ex){
                System.out.println(ex);
            }
        }

            List<String> ListaDatos = MetodosDB.mostrarDatos(consulta1, new String[]{"id", "nombre", "contraseña"});

        usuario = new Usuarios(Integer.parseInt(ListaDatos.get(0)), ListaDatos.get(1), ListaDatos.get(2));

        return usuario;
    }

    //TODO
    public static boolean estaBloqueado(Usuarios usuario1, Usuarios Usuario2){
        
        return true;
    }

    //TODO
    public static void mostrarContactos(){

    }
}
