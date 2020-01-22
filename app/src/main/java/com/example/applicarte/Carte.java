package com.example.applicarte;

import java.io.Serializable;
import java.util.Vector;

/**
 * Cette classe représente une "fiche - question" d'un quiz, de type QCM :
 * un énoncé et quelques propositions dont 1 et 1 seule est correcte.
 *
 * @author Virginie Galtier
 * @see Quiz
 */
public class Carte implements Serializable {
    // question posée par la fiche
    private String question;

    // réponse attendue
    private String reponseCorrecte;

    // liste des propositions incorrectes
    private Vector<String> propositionsIncorrectes;

    /**
     * Crée une nouvelle carte.
     *
     * @param question            énoncé de la carte (question posée)
     * @param reponseCorrecte     réponse attendue
     * @param propositionsIncorrectes liste des propositions incorrectes
     */
    public Carte(String question, String reponseCorrecte, Vector<String> propositionsIncorrectes) {
        this.question = question;
        this.reponseCorrecte = reponseCorrecte;
        this.propositionsIncorrectes = propositionsIncorrectes;
    }

    /**
     * Teste si la réponse fournie est celle attendue. Les 2 textes doivent être strictement
     * identiques (longueur, case...).
     *
     * @param reponseFournie texte de la réponse fournie par le joueur
     * @return vrai si la réponse fournie est strictement identique à celle attendue, faux sinon
     */
    public boolean isCorrecte(String reponseFournie) {
        return reponseFournie.equals(reponseCorrecte);
    }

    /**
     * Fournit l'énoncé de la question.
     *
     * @return le texte de la question posée par la carte
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Fournit le texte de la réponse correcte.
     *
     * @return réponse correcte
     */
    public String getReponseCorrecte() {
        return reponseCorrecte;
    }

    /**
     * Fournit la liste des propositions incorrectes.
     *
     * @return liste des propositions incorrectes
     */
    public Vector<String> getPropositionsIncorrectes() {
        return propositionsIncorrectes;
    }
}
