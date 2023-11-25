package ch.heig.dai_lab_smtp;

import java.io.*;
import java.net.Socket;

// il faut exécuter dans le terminal la commande : docker run -d -p 1080:1080 -p 1025:1025 maildev/maildev
// http://localhost:1080/#/ --> on pourra lire sur ce site les messages
public class SMTPClient {
    Prank groupe;
    public static void main(String[] args) {

        try (     Socket socket = new Socket("localhost", 1025);
                  BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                  PrintWriter out = new PrintWriter(socket.getOutputStream(), true))
        {

            // Lire la réponse
            String response = in.readLine();
            System.out.println("Réponse du serveur : " + response + "\n");

            // Étape 1 : Se connecter au serveur SMTP
            out.println("EHLO localhost");

            // Lire la réponse
            response = in.readLine();
            System.out.println("Réponse du serveur : " + response + "\n");

            //************************************************************************* EXEMPLE D'ECRITURE
            // Étape 2 : Envoyer l'e-mail
            out.println("MAIL FROM: <valentin.bugna@heig-vd.ch>");
            //out.println("MAIL FROM: <" + groupe.getSender() + ">");
            response = in.readLine();
            System.out.println("Réponse du serveur : " + response + "\n");

            out.println("RCPT TO: <valentin.bugna@heig-vd.ch>");
            response = in.readLine();
            System.out.println("Réponse du serveur : " + response + "\n");

            out.println("DATA");
            response = in.readLine();
            System.out.println("Réponse du serveur : " + response + "\n");

            // Envoyer l'en-tête et le corps du message
            out.println("Subject: Votre sujet ");
            out.println("From: <valentin.bugna@heig-vd.ch>");
            out.println("To: <valentin.bugna@heig-vd.ch>");
            out.println("Content-Type: text/plain; charset=UTF-8");
            out.println();
            out.println("Corps de votre e-mail ici.");
            out.println("\r\n.\r\n");
            response = in.readLine();
            System.out.println("Réponse du serveur : " + response + "\n");

            //*************************************************************************

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
