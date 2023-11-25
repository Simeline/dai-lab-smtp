package ch.heig.dai_lab_smtp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Prank {
    private final String fileVictims;
    private final String fileMessages;

    private final int numberPersonPerGroup;
    private String sender;
    private List<String> receivers = new ArrayList<>();
    private ReadFiles.Message message;
    private String subject;

    public Prank(String fileVictims, String fileMessages) {
        this(fileVictims, fileMessages, 5);
    }

    public Prank(String fileVictims, String fileMessages, int groupSize) {
        this.fileVictims = fileVictims;
        this.fileMessages = fileMessages;
        this.numberPersonPerGroup = groupSize;
    }

    public String getSender(){
        return this.sender;
    }

    public List<String> getReceivers(){
        return this.receivers;
    }

    public String getMessageSubject(){
        return this.message.getSubject();
    }
    public String getMessageBody(){
        return this.message.getBody();
    }
    public ReadFiles.Message getMessage(){
        return this.message;
    }

    public void generateNewPrank(){ // TODO : Improve by storing full list so we don't need to read file multiple times ?
        // Clear the receivers list
        this.receivers.clear();

        // Get the sender and receivers
        List<String> victimes = ReadFiles.lireListeVictimes(fileVictims, true);
        int i;
        for (i = 0; i < this.numberPersonPerGroup -1; i++) {
            this.receivers.add(victimes.get(i));
        }
        this.sender = victimes.get(i);

        // Get the message
        List<ReadFiles.Message> messages = ReadFiles.lireListeMessagesJSON(fileMessages, true);
        this.message = messages.getFirst();
    }
}
