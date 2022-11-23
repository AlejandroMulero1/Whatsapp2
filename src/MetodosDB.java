import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

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
}
