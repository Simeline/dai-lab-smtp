package ch.heig.dai_lab_smtp;

import static ch.heig.dai_lab_smtp.Config.*;


public class Main {

    public static void main(String[] args) {

        SMTPClient client = new SMTPClient();
        Prank prank = new Prank(FILE_VICTIMES, FILE_MESSAGES, nbPersonPerGroups);

        for (int i = 0; i < nbGroups; i++) {
            prank.generateNewPrank();
            client.run(prank.getMessageSubject(), prank.getMessageBody(), prank.getSender(), prank.getReceivers());
        }
    }
}
