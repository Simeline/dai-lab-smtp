import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LectureVictimes {

    public static final Pattern ADDRESSE_MAIL_VALIDE =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean isEmailValid(String keyword) {
        Matcher match = ADDRESSE_MAIL_VALIDE.matcher(keyword);
        return match.find();
    }

    // static parce que c'est indépendant de la classe, on peut utiliser la fonction sans créer / instancier la classe
    public static List<String> lireListeMessages(String path) {

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

    public static List<String> lireListeVictimes(String path) {

        List<String> victimes = new ArrayList<>();

        try (var reader = new BufferedReader(new FileReader(path))) {

            String ligne;
            while ((ligne = reader.readLine()) != null) {
                if (isEmailValid(ligne))
                    victimes.add(ligne);
                else
                    System.out.println("ERREUR dans l'adresse mail : " + ligne);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return victimes;
    }

    public static void main(String[] args) {
        String pathVictimes = "config/victims.txt";
        List<String> victimes = lireListeVictimes(pathVictimes);

        // Afficher les victimes
        for (String victime : victimes) {
            System.out.println(victime);
        }

        String pathMessages = "config/messages.txt";
        List<String> messages = lireListeMessages(pathMessages);

//        // Afficher les messages
//        for (int i = 0; i < messages.size(); i++) {
//            System.out.println((i + 1) + ")" + messages.get(i));
//        }
    }
}