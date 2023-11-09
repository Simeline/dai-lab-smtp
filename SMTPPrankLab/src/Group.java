import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Group {
    private final List<Mail> listeVictimes = new ArrayList<>();
    private final int taille;

    public Group(int taille) {
        this.taille = taille;
        String pathVictimes = "config/victims.txt";
        List<String> victimes = LectureVictimes.lireListeVictimes(pathVictimes);
        Collections.shuffle(victimes);
        for (int i = 0; i < taille; i++) {
            listeVictimes.add(new Mail(victimes.get(i)));
        }
    }

    // Constructeur par défaut qui met une taille à 3
    public Group() {
        this(4); // Appelle le constructeur avec un argument
    }

    public List<Mail> getListeVictimes() {
        return this.listeVictimes;
    }

    public int sizeOfVictims() {
        return this.taille - 1; // -1 car j'exclu l'émetteur
    }
}
