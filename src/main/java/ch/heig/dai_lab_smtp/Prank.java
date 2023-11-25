package ch.heig.dai_lab_smtp;

public class Prank {
    private final String sender;
    private final Group receivers;

    /**
     * Constructeur qui permet de séparer l'émetteur des récepteurs
     */
    public Prank() {
        this(5);
    }

    public Prank(int groupSize) {
        this.receivers = new Group(groupSize);
        this.sender = receivers.getListeVictimes().get(0);
        receivers.getListeVictimes().remove(0);
    }

    String getSender() {
        return sender;
    }

    /**
     * Affichage des deux catégories d'un groupe
     */
    public void display() {
        StringBuilder messageCourant = new StringBuilder();
        messageCourant.append("****************** I'm the sender : ************************\n").append(sender).append("\n");
        messageCourant.append("****************** here is the list of my victims : ********\n");
        for (int i = 0; i < receivers.sizeOfVictims(); i++) {
            messageCourant.append(receivers.getListeVictimes().get(i)).append("\n");
        }
        System.out.println(messageCourant);
    }



}
