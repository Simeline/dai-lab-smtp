package ch.heig.dai_lab_smtp;

import static ch.heig.dai_lab_smtp.Configuration.*;


public class Main {

    public static void main(String[] args) {

        // Check the number of groups
        if (NB_GROUPS < 1) {
            System.err.println("ERREUR : Nombre de groupes invalide. Doit être supérieur à 1.");
            return;
        }

        SMTPClient client = new SMTPClient();
        Prank prank = new Prank(FILE_VICTIMES, FILE_MESSAGES, NB_PERSON_PER_GROUPS);

        for (int i = 0; i < NB_GROUPS; i++) {
            prank.generateNewPrank();
            client.sendMessage(prank.getMessageSubject(), prank.getMessageBody(), prank.getSender(), prank.getReceivers());
        }
    }
}
