package ch.heig.dai_lab_smtp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadFiles {
    /**
     * Ce pattern permet de vérifier le format d'une adresse mail
     */
    public static final Pattern ADDRESSE_MAIL_VALIDE =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    /**
     * Cette classe est utilisée pour la lecture des fichiers JSON
     */
    public static class Message {
        private final String subject;
        private final String body;

        public Message(String subject, String body) {
            this.subject = subject;
            this.body = body;
        }

        public String getSubject() {
            return subject;
        }

        public String getBody() {
            return body;
        }

        @Override
        public String toString() {
            return "Subject: " + subject + "\n" + "Body: " + body;
        }
    }


    /**
     * Fonction de vérification d'adresse mail
     * @param keyword email à vérifier
     * @return si l'adresse est valide ou non
     */
    public static boolean isEmailValid(String keyword) {
        Matcher match = ADDRESSE_MAIL_VALIDE.matcher(keyword);
        return match.find();
    }


    // static parce que c'est indépendant de la classe, on peut utiliser la fonction sans créer / instancier la classe

    /**
     * Fonction de lecture d'un fichier de messages txt
     * @param path chemin jusqu'au fichier.txt
     * @return une liste de toutes les messages d'un fichier séparés par "---"
     */
    public static List<String> lireListeMessagesTXT(String path) {

        List<String> messages = new ArrayList<>();
        StringBuilder messageCourant = new StringBuilder();

        try (var reader = new BufferedReader(new FileReader(path))) {

            String ligne;

            while ((ligne = reader.readLine()) != null) {

                if (ligne.equals("---")) {
                    messages.add(messageCourant.toString());
                    messageCourant.setLength(0); // Réinitialiser le StringBuilder
                }
                else {
                    messageCourant.append(ligne).append("\n");
                }
            }
            messages.add(messageCourant.toString()); // Ajouter le dernier message
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return messages;
    }

    /**
     * Fonction de lecture d'un fichier de messages json
     * @param path chemin jusqu'au fichier.json
     * @return une liste de toutes les lignes du fichier
     */
    public static List<Message> lireListeMessagesJSON(String path, boolean shuffle) {
        List<Message> messages = new ArrayList<>();

        try (var reader = new BufferedReader(new FileReader(path))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            JSONArray jsonArray = new JSONArray(jsonContent.toString());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String subject = jsonObject.getString("Subject");
                String body = jsonObject.getString("Body");

                Message message = new Message(subject, body);
                messages.add(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(shuffle)
            Collections.shuffle(messages);

        return messages;
    }

    /**
     * Fonction de lecture du fichier des adresses mails
     * @param path chemin jusqu'au fichier.txt
     * @return une liste de toutes les victimes
     */
    public static List<String> lireListeVictimes(String path, boolean shuffle) {

        List<String> victimes = new ArrayList<>();

        try (var reader = new BufferedReader(new FileReader(path))) {

            String ligne;
            while ((ligne = reader.readLine()) != null) {
                if (isEmailValid(ligne))
                    victimes.add(ligne);
                else {
                    System.err.println("ERREUR dans l'adresse mail : " + ligne);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        if(shuffle)
            Collections.shuffle(victimes);

        return  victimes;
    }
}