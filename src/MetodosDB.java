import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MetodosDB {

    public static Connection conexion(){
        Connection con = null;
        String url = "jdbc:mysql://dns11036.phdns11.es?user=amulero&password=1234";
        try{
            con = DriverManager.getConnection(url);
            if (con!=null){
                System.out.println("Conexión establecida");
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
        return con;
    }

    public static void CrearTabla(String nombre, String[] campos){
        Statement statement;
        Connection con = null;

        String sql = "CREATE TABLE "+nombre+" (";
        for (int i = 0; i < campos.length; i++) {
            sql+= campos[i];
            if(i < campos.length-1){
                sql += ", ";
            }
        }
        sql = sql + ");";
        try {
            con = conexion();
            statement = con.createStatement();
            statement.execute("USE ad2223_amulero");
            statement.execute(sql);
            con.close();
            statement.close();
        }catch (Exception ex) {
            System.out.println(ex);
        }
    }

    //Método que recibe una variable de tipo cadena que es la consulta SQL y un array de los campos que se quieren mostrar. Devuelve una
    //lista de los datos obtenidos.
    public static List<String> mostrarDatos(String consulta, String[] datos) {
        Statement statement;
        Connection con = null;
        List<String> ListaCosas = new ArrayList<>();
        try {

            con = conexion();
            statement = con.createStatement();

            statement.execute("USE ad2223_amulero");
            ResultSet rs = statement.executeQuery(consulta);

            while (rs.next()) {
                for (int i = 0; i < datos.length; i++) {
                    if (nombraColumna(rs, datos[i])) {
                        ListaCosas.add(rs.getString(datos[i]));
                    }
                }
            }
        }catch (Exception ex){
            System.out.println(ex);
        }
        return ListaCosas;
    }

    public static boolean nombraColumna(ResultSet rs, String columnName) {
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();
            for (int x = 1; x <= columns; x++) {
                if (columnName.equals(rsmd.getColumnName(x))) {
                    return true;
                }
            }
        }catch (Exception ex){
            System.out.println(ex);
        }
        return false;
    }

    public static boolean comprobarFila(String consulta){
        boolean existe = false;
        Statement statement;
        Connection con = null;
        try{
            con = conexion();
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(consulta);
            if (rs.next()) {
                existe = true;
            }else {
                System.out.println("Usuario no encontrado, creando un nuevo usuario.");
            }
        }catch (Exception ex){
            System.out.println(ex);
        }
        return  existe;
    }
}
