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
    public void setContactos(String contacto) {
        //TODO TEST
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


    public static boolean estaBloqueado(Usuarios usuario1, Usuarios usuario2){
        String consulta = "SELECT * FROM bloqueados WHERE idUsuario = "+usuario1.getId() +" AND idBloqueado = "+usuario2.getId();
        Boolean establoqueado = MetodosDB.comprobarFila(consulta);
        return establoqueado;
    }

    public static void mostrarContactos(int id){
        String consulta = "SELECT idContacto FROM Contactos WHERE idUsuario = "+id;
        String consulta2;
        List<String>ListaIDs = MetodosDB.mostrarDatos(consulta, new String[]{"idContacto"}, new String[]{"int"});
        for (int i = 0; i < ListaIDs.size(); i++) {
            consulta2 = "SELECT nombre FROM Usuarios WHERE idUsuario = "+ListaIDs.get(i);
            System.out.println(MetodosDB.mostrar1Dato(consulta, "nombre", "string"));
        }
    }
}
