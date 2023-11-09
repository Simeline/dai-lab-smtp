import java.io.*;
import java.net.Socket;

public class SMTPClient {
    public static void main(String[] args) {

        try {
            // il faut exécuter dans le terminal la commande : docker run -d -p 1080:1080 -p 1025:1025 maildev/maildev
            Socket socket = new Socket("localhost", 1025);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Lire la réponse
            String response = in.readLine();
            System.out.println("Réponse du serveur : " + response + "\n");

            // Étape 1 : Se connecter au serveur SMTP
            out.println("HELO localhost");

            // Lire la réponse
            response = in.readLine();
            System.out.println("Réponse du serveur : " + response + "\n");

            // Étape 2 : Envoyer l'e-mail
            // ...

            // Fermer les flux et le socket
            in.close();
            out.close();
            socket.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
