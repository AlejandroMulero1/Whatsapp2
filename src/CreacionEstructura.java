import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
public class CreacionEstructura {
    public static void main(String[] args) {
        Connection con = MetodosDB.conexion();
        Statement statement;
        try{
/*

            //Tabla Usuarios

            MetodosDB.CrearTabla("Usuarios", new String[]{"id int PRIMARY KEY AUTO_INCREMENT",
                    "nombre varchar(45)", "contraseña varchar(45)"});


            //Tabla Chat
            MetodosDB.CrearTabla("Chat", new String[]{"idChat int PRIMARY KEY AUTO_INCREMENT",
                    "nombreChat varchar(45)", "idPrimerUsuario int", "idSegundoUsuario int",
                    "FOREIGN KEY (idPrimerUsuario) REFERENCES Usuarios (id) ON DELETE CASCADE ON UPDATE CASCADE",
                    "FOREIGN KEY (idSegundoUsuario) REFERENCES Usuarios (id) ON DELETE CASCADE ON UPDATE CASCADE"});

            //Tabla Mensajes
            MetodosDB.CrearTabla("Mensajes", new String[]{
                    "idMensaje int PRIMARY KEY AUTO_INCREMENT",
                    "texto varchar(255)",
                    "idChat int",
                    "idEmisor int",
                    "horaLlegada timestamp",
                    "leido bit",
                    "FOREIGN KEY (idChat) REFERENCES Chat (idChat) ON DELETE CASCADE ON UPDATE CASCADE",
                    "FOREIGN KEY (idEmisor) REFERENCES Usuarios (id) ON DELETE CASCADE ON UPDATE CASCADE"});


            MetodosDB.CrearTabla("Bloqueados", new String[]{
                    "idUsuario int",
                    "idBloqueado int",
                    "PRIMARY KEY (idUsuario, idBloqueado)",
                    "FOREIGN KEY (idUsuario) REFERENCES Usuarios (id) ON DELETE CASCADE ON UPDATE CASCADE",
                    "FOREIGN KEY (idBloqueado) REFERENCES Usuarios (id) ON DELETE CASCADE ON UPDATE CASCADE"});

            MetodosDB.CrearTabla("Contactos", new String[]{
                    "idUsuario int",
                    "idContacto int",
                    "PRIMARY KEY (idUsuario, idContacto)",
                    "FOREIGN KEY (idUsuario) REFERENCES Usuarios (id) ON DELETE CASCADE ON UPDATE CASCADE",
                    "FOREIGN KEY (idContacto) REFERENCES Usuarios (id) ON DELETE CASCADE ON UPDATE CASCADE"});



 */
        }catch (Exception ex){
            System.out.println(ex);
        }
    }
}
