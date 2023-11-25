package ch.heig.dai_lab_smtp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Prank {
    private final List<String> victimes;
    private final List<ReadFiles.Message> messages;
    private final int numberPersonPerGroup;

    private String sender;
    private final List<String> receivers;
    private ReadFiles.Message message;
    private String subject;
    private String body;
    private static final Random random = new Random();


    /**
     * Constructeur qui par défaut crée un groupe de 5
     * @param fileVictims fichiers des victimes
     * @param fileMessages fichiers des messages
     */
    public Prank(String fileVictims, String fileMessages) {
        this(fileVictims, fileMessages, 5);
    }

    /**
     * Constructeur qui annonce la taille du groupe
     * @param fileVictims fichiers des victimes
     * @param fileMessages fichiers des messages
     * @param groupSize taille du groupe
     */
    public Prank(String fileVictims, String fileMessages, int groupSize) {

        // Get the sender and receivers
        this.victimes = ReadFiles.lireListeVictimes(fileVictims, true);;
        // Get the message
        messages = ReadFiles.lireListeMessagesJSON(fileMessages, true);
        // Get the size of the group
        this.numberPersonPerGroup = groupSize;

        this.sender = null;
        this.receivers = new ArrayList<>();
        this.message = null;
        this.body = null;
        this.subject = null;
    }

    /**
     * Getter
     * @return l'expéditeur
     */
    public String getSender(){
        return this.sender;
    }

    /**
     * Getter
     * @return liste des destinataires
     */
    public List<String> getReceivers(){
        return this.receivers;
    }

    /**
     * Getter
     * @return un type Message qui comprend le corps et le sujet du message
     */
    public ReadFiles.Message getMessage(){
        return this.message;
    }

    public String getSubject() {
        return this.subject;
    }

    public String getBody() {
        return this.body;
    }

    /**
     * Pour l'instant, on crée un seul groupe dans cette fonction...
     */
    public void generateNewPrank(){ // TODO : Improve by storing full list so we don't need to read file multiple times ?

        // Clear the receivers list
        this.receivers.clear();

        // Random mixing of list elements.
        Collections.shuffle(victimes);

        int i = 0;
        this.sender = victimes.get(i);
        for (i = 1; i < this.numberPersonPerGroup; i++) {
            this.receivers.add(victimes.get(i));
        }

        this.message = messages.get(random.nextInt(messages.size()));
        this.body = message.getBody();
        this.subject = message.getSubject();
    }
}
