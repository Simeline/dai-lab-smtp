package ch.heig.dai_lab_smtp;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class SMTPClient {
    public void run(String subject, String message, String sender, List<String> receivers) {

        // il faut exécuter dans le terminal la commande : docker run -d -p 1080:1080 -p 1025:1025 maildev/maildev
        // http://localhost:1080/#/ --> on pourra lire sur ce site les messages
        try(Socket socket = new Socket("localhost", 1025);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);) {

            // Wait server first message
            String response = in.readLine();
            System.out.println("Réponse du serveur : " + response + "\n");  // TODO : check code and Remove

            // Connect to the SMTP Server
            out.println("EHLO localhost");
            response = in.readLine();
            System.out.println("Réponse du serveur : " + response + "\n"); // TODO : check code and Remove

            // Send the email
            out.println("MAIL FROM: <" + sender + ">");
            response = in.readLine();
            System.out.println("Réponse du serveur : " + response + "\n"); // TODO : check code and Remove

            for (String mail : receivers) {
                out.println("RCPT TO: <"+ mail +">");
                response = in.readLine();
                System.out.println("Réponse du serveur : " + response + "\n"); // TODO : check code and Remove
            }

            out.println("DATA");
            response = in.readLine();
            System.out.println("Réponse du serveur : " + response + "\n"); // TODO : check code and Remove
            out.println("Subject: " + subject);
            out.println("From: <" + sender + ">");
            out.println("Content-Type: text/plain; charset=UTF-8");
            out.println();
            out.println(message);
            out.println("\r\n.\r\n");
            response = in.readLine();
            System.out.println("Réponse du serveur : " + response + "\n"); // TODO : check code and Remove

            // TODO : Fermer avec un QUIT
            out.println("QUIT");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
