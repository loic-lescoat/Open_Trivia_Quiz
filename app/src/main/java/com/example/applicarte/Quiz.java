package com.example.applicarte;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

/**
 * Cette classe représente un jeu (un ensemble) de cartes de QCM.
 *
 * @author Virginie Galtier
 * @see Carte
 */
public class Quiz implements Serializable {

    // liste des "fiches - questions" composant le quiz
    private Vector<Carte> lesCartes;

    /**
     * Crée un nouveau quiz comportant 1 question
     */
    public Quiz() {
        lesCartes = new Vector<Carte>();




    }

    public void ajouter(Carte carte){
        lesCartes.add(carte);
    }

    /**
     * Fournit un itérateur sur les questions du quiz
     *
     * @return itérateur sur les questions du quiz
     */
    public Iterator<Carte> getCartesIterator() {
        return lesCartes.iterator();
    }
}
