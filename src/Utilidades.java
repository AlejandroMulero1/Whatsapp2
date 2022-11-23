import java.util.Scanner;

public class Utilidades {
    private static Scanner s = new Scanner(System.in);
    public static Usuarios iniciarSesion(){
        System.out.println("Introduzca el nombre de usuario");
        String usuario = s.next();
        System.out.println("Introduzca la contraseña");
        String contraseña = s.next();
        Usuarios usuario1 = Usuarios.getUsuario(usuario, contraseña);
        return usuario1;
    }
}
