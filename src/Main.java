import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean revisado = false;
        int continuar;
        Scanner sc=new Scanner(System.in);
      do {
        Usuarios usuario=Utilidades.iniciarSesion();
        if (Utilidades.mostrarMensajesNuevos(usuario) && !revisado){
            revisado=true;
        }
        else if (revisado){
            int opcion= Utilidades.mostrarOpciones();
            switch (opcion){
                case 1:
                    Utilidades.verChat(usuario);
                    break;

                case 2:
                    Utilidades.escribirEnChat(usuario);
                    break;

                case 3:
                    Utilidades.verContactos(usuario);
                    break;

                case 4:
                    Utilidades.ajustarContactos(usuario);
                    break;

                case 5:
                    System.out.println("Introduzca el nombre del contacto");
                    String nombreContacto= sc.nextLine();
                    usuario.setContactos(nombreContacto);

                case 6:
                    Utilidades.crearChat(usuario);

            }
        }
          System.out.println("Desea continuar? Introduzca 0 en caso afirmativo");
         continuar= sc.nextInt();

      } while (continuar==0);
    }
}