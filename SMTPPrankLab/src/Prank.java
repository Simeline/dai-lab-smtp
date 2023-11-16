public class Prank {
    private final Mail sender;
    private final Group receivers;

    /**
     * Constructeur qui permet de séparer l'émetteur des récepteurs
     */
    public Prank() {
        this.receivers = new Group();
        this.sender = receivers.getListeVictimes().get(0);
        receivers.getListeVictimes().remove(0);
    }

    /**
     * Affichage des deux catégories d'un groupe
     */
    public void display() {
        StringBuilder messageCourant = new StringBuilder();
        messageCourant.append("****************** I'm the sender : ************************\n").append(sender.getMail()).append("\n");
        messageCourant.append("****************** here is the list of my victims : ********\n");
        for (int i = 0; i < receivers.sizeOfVictims(); i++) {
            messageCourant.append(receivers.getListeVictimes().get(i).getMail()).append("\n");
        }
        System.out.println(messageCourant);
    }
}
