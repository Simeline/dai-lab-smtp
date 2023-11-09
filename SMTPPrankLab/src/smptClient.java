import java.io.*;
import java.net.Socket;

public class smptClient {
    public static void main(String[] args) {

        try {
            Socket socket = new Socket("localhost", 25);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Étape 1 : Se connecter au serveur SMTP
            out.println("HELO localhost");

            // Lire la réponse
            String response = in.readLine();
            System.out.println("Réponse du serveur : " + response);

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
