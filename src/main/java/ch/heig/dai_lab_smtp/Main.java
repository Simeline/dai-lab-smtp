package ch.heig.dai_lab_smtp;

import static ch.heig.dai_lab_smtp.Configuration.*;


public class Main {

    public static void main(String[] args) {

        // Check the number of groups
        if(nbGroups < 1){
            System.err.println("ERREUR avec le nombre de groupes.");
            return;
        }

        SMTPClient client = new SMTPClient();
        Prank prank = new Prank(FILE_VICTIMES, FILE_MESSAGES, nbPersonPerGroups);

        for (int i = 0; i < nbGroups; i++) {
            prank.generateNewPrank();
            client.sendMessage(prank.getMessageSubject(), prank.getMessageBody(), prank.getSender(), prank.getReceivers());
        }
    }
}
