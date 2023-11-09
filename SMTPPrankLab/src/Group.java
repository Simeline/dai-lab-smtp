import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Group {
    private final List<Mail> listeVictimes = new ArrayList<>();

    public Group(int taille) {
        String pathVictimes = "config/victims.txt";
        List<String> victimes = LectureVictimes.lireListeVictimes(pathVictimes);
        Collections.shuffle(victimes);
        for (int i = 0; i < taille; i++) {
            listeVictimes.add(new Mail(victimes.get(i)));
        }
    }

    public List<Mail> getListeVictimes() {
        return this.listeVictimes;
    }
}
