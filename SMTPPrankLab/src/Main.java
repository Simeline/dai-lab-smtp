import java.util.List;

public class Main {

    public static void main(String[] args) {

        String pathVictimes = "config/victims.txt";
        List<String> victimes = LectureVictimes.lireListeVictimes(pathVictimes);

//        // Afficher les victimes
//        for (String victime : victimes) {
//            System.out.println(victime);
//        }

        String pathMessagesJ = "config/messagesJ.json"; // Assurez-vous que le chemin soit correct
        List<Message> messagesJ = LectureVictimes.lireListeMessagesJSON(pathMessagesJ);

//        // Afficher les messages du file json
//        for (int i = 0; i < messagesJ.size(); i++) {
//            System.out.println((i + 1) + ")\n" + messagesJ.get(i) + "\n");
//        }

        String pathMessages = "config/messages.txt";
        List<String> messages = LectureVictimes.lireListeMessagesTXT(pathMessages);

//        // Afficher les messages du file txt
//        for (int i = 0; i < messages.size(); i++) {
//            System.out.println((i + 1) + ")" + messages.get(i));
//        }

        Group newGrp = new Group();

//        // Afficher par défaut 3 adresses mail tirées aléatoirement du fichier victims.txt.
//        for (Mail m : newGrp.getListeVictimes())
//            System.out.println(m.getMail());

        Prank p = new Prank();

        // Afficher la répartition du groupe en vue d'envoyer les mails et de l'implémentation de fonction
        p.display();
    }
}
