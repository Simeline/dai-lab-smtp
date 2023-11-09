import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LectureVictimes {

    public static void main(String[] args) {
        String pathVictimes = "config/victims.txt";
        List<String> victimes = lireListeVictimes(pathVictimes);

//        // Afficher les victimes
//        for (String victime : victimes) {
//            System.out.println(victime);
//        }

        String pathMessagesJ = "config/messagesJ.json"; // Assurez-vous que le chemin soit correct
        List<Message> messagesJ = lireListeMessagesJSON(pathMessagesJ);

//        // Afficher les messages du file json
//        for (int i = 0; i < messagesJ.size(); i++) {
//            System.out.println((i + 1) + ")\n" + messagesJ.get(i) + "\n");
//        }

        String pathMessages = "config/messages.txt";
        List<String> messages = lireListeMessagesTXT(pathMessages);

//        // Afficher les messages du file txt
//        for (int i = 0; i < messages.size(); i++) {
//            System.out.println((i + 1) + ")" + messages.get(i));
//        }

        Group newGrp = new Group();

//        // Afficher par défaut 3 adresses mail tirées aléatoirement du fichier victims.txt.
//        for (Mail m : newGrp.getListeVictimes())
//            System.out.println(m.getMail());

        // Afficher la répartition du groupe en vue d'envoyer les mails
        Prank p = new Prank();
        p.display();
    }

    public static final Pattern ADDRESSE_MAIL_VALIDE =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean isEmailValid(String keyword) {
        Matcher match = ADDRESSE_MAIL_VALIDE.matcher(keyword);
        return match.find();
    }

    // static parce que c'est indépendant de la classe, on peut utiliser la fonction sans créer / instancier la classe
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

    public static List<Message> lireListeMessagesJSON(String path) {
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

        return messages;
    }

    public static List<String> lireListeVictimes(String path) {

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

        return victimes;
    }
}