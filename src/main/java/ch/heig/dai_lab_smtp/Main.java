package ch.heig.dai_lab_smtp;

import java.util.List;

public class Main {
    final static String FILE_VICTIMES = "src/main/resources/victims.txt";
    final static String FILE_MESSAGES = "src/main/resources/messagesJ.json";

    public static void main(String[] args) {

        // TODO : Traitement des arguments et remplacer les variables ci-dessous + constantes de fichiers
        int nbGroups = 2;
        int nbPersonPerGroups = 3;

        SMTPClient client = new SMTPClient();
        Prank prank = new Prank(FILE_VICTIMES, FILE_MESSAGES, nbPersonPerGroups);

        for (int i = 0; i < nbGroups; i++) {
            prank.generateNewPrank();
            client.run(prank.getMessageSubject(), prank.getMessageBody(), prank.getSender(), prank.getReceivers());
        }
    }
}
