import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
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

    public Usuarios(){

    }


    //Getters y Setters
    public int getId() {return id;}



    /**
     * Metodo que accede a la base de datos para obtener todos los contactos del usuario
     * @return una lista con los contactos del usuario
     */
    public List<String> getContactos() {
        String consulta = "SELECT idContacto FROM Contactos WHERE idUsuario = "+this.id;
        String nombre, consulta2;
        List<String> listaIds = MetodosDB.mostrarDatos(consulta, new String[]{"idContacto"}, new String[]{"int"});
        List<String> listaNombreContactos = new ArrayList<>();
        for (int i = 0; i < listaIds.size(); i++) {
            consulta2 = "SELECT nombre FROM Usuarios WHERE id = "+listaIds.get(i);
            nombre = MetodosDB.mostrar1Dato(consulta2, "nombre", "string");
            listaNombreContactos.add(nombre);
        }
        return listaNombreContactos;
    }

    /**
     * Metodo que accede a la base de datos para obtener todos los contactos bloqueados del usuario
     * @return una lista con los contactos bloqueados del usuario
     */
    public List<String> getUsuariosBloqueados() {
        String consulta = "SELECT idBloqueado FROM Bloqueados WHERE idUsuario = "+this.id;
        String nombre, consulta2;
        List<String> listaIds = MetodosDB.mostrarDatos(consulta, new String[]{"idBloqueado"}, new String[]{"int"});
        List<String> listaNombreBloqueados = new ArrayList<>();
        for (int i = 0; i < listaIds.size(); i++) {
            consulta2 = "SELECT nombre FROM Usuarios WHERE id = "+listaIds.get(i);
            nombre = MetodosDB.mostrar1Dato(consulta2, "nombre", "string");
            listaNombreBloqueados.add(nombre);
        }
        return listaNombreBloqueados;
    }


    /**
     * Metodo que accede a la base de datos para insertar un nuevo contacto
     * @param contacto
     */
    public void setContactos(String contacto) {
        try {
            Connection connection=MetodosDB.conexion();
            PreparedStatement pstmt=connection.prepareStatement("INSERT INTO ad2223_amulero.Contactos VALUES(?,?)");
            pstmt.setInt(1, this.id);
            pstmt.setInt(2, Utilidades.obtenerIdUsuarioPorNombre(contacto));
            pstmt.execute();
            connection.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setUsuariosBloqueados(String contacto, boolean bloquear) {
       if (bloquear){
           try {
                Connection connection= MetodosDB.conexion();
                PreparedStatement pstm=connection.prepareStatement("INSERT INTO Bloqueados VALUES (?,?)");
                pstm.setInt(1, this.id);
                pstm.setInt(2, Utilidades.obtenerIdUsuarioPorNombre(contacto));
                pstm.execute();
                connection.close();
           }catch (Exception e){
               e.printStackTrace();
           }
       }
       else {
           try {
               Connection connection=MetodosDB.conexion();
               PreparedStatement pstm=connection.prepareStatement("DELETE FROM Bloqueados WHERE idUsuario=? AND idBloqueado=?");
               pstm.setInt(1, this.id);
               pstm.setInt(2, Utilidades.obtenerIdUsuarioPorNombre(contacto));
               pstm.execute();
               connection.close();
           }catch (Exception e){
               e.printStackTrace();
           }
       }
    }

    public String getNombre() {
        return nombre;
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
        String consulta = "SELECT * FROM Usuarios WHERE nombre = '"+nombre+"'" +" AND contraseña = '"+contraseña+"'";
        Connection con;
        if (!MetodosDB.comprobarFila(consulta)){
            try {
                con = MetodosDB.conexion();
                PreparedStatement pstmt= con.prepareStatement(" INSERT INTO ad2223_amulero.Usuarios (nombre, contraseña) VALUES (?,?)");
                pstmt.setString(1, nombre);
                pstmt.setString(2, contraseña);
                pstmt.executeUpdate();
                System.out.println("Usuario creado, iniciando sesión");
                con.close();
            }catch (Exception ex){
                System.out.println(ex);
            }
        }else{
            System.out.println("Sesión iniciada");
        }


        List<String> DatosUsuario= MetodosDB.mostrarDatos(consulta, new String[]{"id", "nombre", "contraseña"}, new String[]{"int", "string", "string"});
        Usuarios usuario = new Usuarios(Integer.parseInt(DatosUsuario.get(0)), DatosUsuario.get(1), DatosUsuario.get(2));
        return usuario;
    }
}
