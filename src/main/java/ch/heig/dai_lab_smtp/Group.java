package ch.heig.dai_lab_smtp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Group {
    private final List<Mail> listeVictimes = new ArrayList<>();
    private final int taille;

    /**
     * Constructeur par int
     * Pour construire une liste de victimes, il lui faut un fichier de victimes. Ensuite, il mélange
     * aléatoirement cette lisste et en récupère qu'un nombre déterminé de victimes.
     * @param taille du groupe formé
     */
    public Group(int taille) {
        this.taille = taille;
        String pathVictimes = "src/main/resources/victims.txt";
        List<String> victimes = LectureVictimes.lireListeVictimes(pathVictimes);
        Collections.shuffle(victimes);
        for (int i = 0; i < taille; i++) {
            listeVictimes.add(new Mail(victimes.get(i)));
        }
    }

    /**
     *     Constructeur par défaut qui met une taille à 4
     */
    public Group() {
        this(4); // Appelle le constructeur avec un argument
    }

    public List<Mail> getListeVictimes() {
        return this.listeVictimes;
    }

    /**
     * Taille du groupe (-1)
     * @return taille du groupe sans l'émetteur
     */
    public int sizeOfVictims() {
        return this.taille - 1; // -1 car j'exclu l'émetteur
    }
}
