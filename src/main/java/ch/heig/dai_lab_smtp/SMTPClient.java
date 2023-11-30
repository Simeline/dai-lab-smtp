package ch.heig.dai_lab_smtp;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import static ch.heig.dai_lab_smtp.Configuration.*;

public class SMTPClient {

    private PrintWriter out;
    private BufferedReader in;

    private void readHeader() throws IOException
    {
        String line = in.readLine();
        if (!line.startsWith("250")) {
            throw new IOException("SMTP error: " + line);
        }
        while (line.startsWith("250-")) {
            line = in.readLine();
            System.out.println("(S): " + line);
        }
    }

    private void readFromServer() throws IOException {
        System.out.println("(S): " + in.readLine());
    }

    private void writeToServer(String message) throws IOException {
        out.println(message);
    }

    public void sendMessage(String subject, String message, String sender, List<String> receivers) {

        try {
            Socket socket = new Socket(localhost, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);

            // Wait server first message
            readFromServer();

            // Connect to the SMTP Server
            writeToServer("EHLO localhost");
            readHeader();

            // Send the email
            writeToServer("MAIL FROM: <" + sender + ">");

            readFromServer();

            for (String to : receivers) {
                writeToServer("RCPT TO: <" + to + ">");
                readFromServer();
            }

            writeToServer("DATA");
            readFromServer();

            writeToServer("Content-Type: text/plain; charset=UTF-8");
            writeToServer("Content-Transfer-Encoding: base64"); // obligatoire pour pouvoir lire le "é" de réponse
            writeToServer("Subject: " + subject);
            writeToServer("From: <" + sender + ">");

            for (String to : receivers) {
                writeToServer("To: <" + to + ">");
            }

            writeToServer(""); // obligatoire pour lire le body
            writeToServer(Base64.getEncoder().encodeToString(message.getBytes(StandardCharsets.UTF_8))); // body

            writeToServer("\r\n.\r\n"); // signifie la fin du body
            writeToServer("QUIT");
            out.close();
            in.close();
            socket.close();
            System.out.println();

        } catch (IOException e) {
            System.err.println("ERREUR lors de l'envoie du message : " + e.getMessage());
            System.exit(1);
        }
    }
}
