package ch.heig.dai_lab_smtp;

import java.util.ArrayList;
import java.util.List;

import static ch.heig.dai_lab_smtp.Config.*;


public class Group {
    private final List<String> listeVictimes = new ArrayList<>();
    private final int taille;

    /**
     * Constructeur par int
     * Pour construire une liste de victimes, il lui faut un fichier de victimes. Ensuite, il mélange
     * aléatoirement cette lisste et en récupère qu'un nombre déterminé de victimes.
     * @param taille du groupe formé
     */
    public Group(int taille) {
        this.taille = taille;
        List<String> victimes = ReadFiles.lireListeVictimes(FILE_VICTIMES, true);

        for (int i = 0; i < taille; i++) {
            listeVictimes.add(victimes.get(i));
        }
    }

    /**
     *     Constructeur par défaut qui met une taille à 5
     */
    public Group() {
        this(5); // Appelle le constructeur avec un argument
    }

    public List<String> getListeVictimes() {
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
