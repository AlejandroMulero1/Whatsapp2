import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) {
        boolean mensajesNuevos;
        Scanner sc = new Scanner(System.in);
        //Timer timer = new Timer();
        //timer.scheduleAtFixedRate(new TimerTask() {
        // @Override public void run() {
        Usuarios usuario = Utilidades.iniciarSesion();
        Utilidades.mostrarMensajesNuevos(usuario);
              int opcion = Utilidades.mostrarOpciones();
              switch (opcion) {
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
                      String nombreContacto = sc.nextLine();
                      usuario.setContactos(nombreContacto);
                      break;

                  case 6:
                      Utilidades.crearChat(usuario);
                      break;
              }
            }

        //}, 1000, 5000);
    }
    //}


