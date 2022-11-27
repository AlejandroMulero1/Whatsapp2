import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MetodosDB {

    public static Connection conexion() {
        Connection con = null;
        String url = "jdbc:mysql://dns11036.phdns11.es?user=amulero&password=1234";
        try {
            con = DriverManager.getConnection(url);
            if (con != null) {
                System.out.println("Conexión establecida");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return con;
    }

    public static void CrearTabla(String nombre, String[] campos) {
        Statement statement;
        Connection con = null;

        String sql = "CREATE TABLE " + nombre + " (";
        for (int i = 0; i < campos.length; i++) {
            sql += campos[i];
            if (i < campos.length - 1) {
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
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    //Método que recibe una variable de tipo cadena que es la consulta SQL y un array de los campos que se quieren recoger. Devuelve una
    //lista de los datos obtenidos.
    public static List<String> mostrarDatos(String consulta, String[] datos, String[]tipoDatos) {
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
                        switch (tipoDatos[i]) {
                            case "int":
                            case "boolean":
                                ListaCosas.add(String.valueOf(rs.getInt(datos[i])));
                                break;
                            case "string":
                                ListaCosas.add(rs.getString(datos[i]));
                                break;
                            case "timestamp":
                                ListaCosas.add(String.valueOf(rs.getTimestamp(datos[i])));
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return ListaCosas;
    }


    //Método que recibe la consulta y el dato a recoger
    public static String mostrar1Dato(String consulta, String dato, String tipoDato) {
        Statement statement;
        String item = "";
        Connection con = null;
        try {

            con = conexion();
            statement = con.createStatement();

            statement.execute("USE ad2223_amulero");
            ResultSet rs = statement.executeQuery(consulta);
        //Comprueba que se ha encontrado la fila y la columna y la recoge en la variable item
            while (rs.next()) {
                if (nombraColumna(rs, dato)) {
                    switch (tipoDato) {
                        case "int":
                        case "boolean":
                            item = String.valueOf(rs.getInt(dato));
                            break;
                        case "string":
                            item = rs.getString(dato);
                            break;
                        case "timestamp":
                            item = String.valueOf(rs.getTimestamp(dato));
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        //Devuelve el dato
        return item;
    }

    //Método que comprueba si existe la columna
    public static boolean nombraColumna(ResultSet rs, String columnName) {
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();
            for (int x = 1; x <= columns; x++) {
                if (columnName.equals(rsmd.getColumnName(x))) {
                    return true;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return false;
    }

    //Método que comprueba si existe la fila
    public static boolean comprobarFila(String consulta) {
        boolean existe = false;
        Statement statement;
        Connection con = null;
        try {
            con = conexion();
            statement = con.createStatement();
            statement.execute("USE ad2223_amulero");
            ResultSet rs = statement.executeQuery(consulta);
            if (rs.next()) {
                existe = true;
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return existe;
    }
}
