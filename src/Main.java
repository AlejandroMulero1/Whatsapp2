public class Main {
    public static void main(String[] args) {
        boolean seguir;
        boolean revisado = false;
      do {
        Usuarios usuario=Utilidades.iniciarSesion();
        if (Utilidades.mostrarMensajesNuevos(usuario) && !revisado){
            Utilidades.mostrarMensajesNuevos(usuario);
            revisado=true;
        }
        else if (revisado){
            int opcion= Utilidades.mostrarOpciones();
            switch (opcion){
                case 1:
                    Utilidades.verChat(usuario);
                    break;

                case 2:
                    escribirEnChat();
                    break;

                case 3:
                    verContactos();
                    break;

                case 4:
                    ajustarContactos();
                    break;
            }
        }
          seguir=true;
      } while (seguir);
    }
}