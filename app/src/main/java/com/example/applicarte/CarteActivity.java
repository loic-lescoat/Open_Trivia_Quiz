// Auteurs: Loïc LESCOAT et Antoine DESCAMPS
// nous n'avons pas utilise les corrections
// a cause de l'utilisation de Html.fromHtml dans AccueilActivity, notre appli ne marche que sur API >= 24


package com.example.applicarte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

public class CarteActivity extends AppCompatActivity implements View.OnClickListener {

    private Carte carte;
    Quiz q; // a remplacer --> quiz qui vient du intent
    Iterator<Carte> it;

    LinearLayout monLayout;
    int nbQPosees = 0; // dans le quiz qui s'affiche a l'utilisateur
    int nbBonnesReponses = 0; // idem

    // ajouter de la musique
    // MediaPlayer musique;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carte);

        Intent intent = getIntent();
        q = (Quiz) intent.getSerializableExtra("quiz");
        it = q.getCartesIterator(); // cartes qui sont dans le quiz

        carte = it.next(); // l'iterateur it contient au moins 1 carte

        peupler(); // afficher la prochaine carte du quiz

        // musique = MediaPlayer.create(this, R.raw.tournerenbaspourquoi); // preparer musique



    }

    @Override
    protected void onResume() {
        super.onResume();

//        musique.start(); // demarrer musique
    }

    @Override
    protected void onPause() {

//        musique.pause(); // musique en pause
        super.onPause();
    }

    public void toutMasquer(){



        //trouver les views
        monLayout = findViewById(R.id.monLayout);
        int nbView = monLayout.getChildCount();



        Vector<View> viewVector = new Vector<View>(); // liste des views
        for (int i=0; i<nbView;i++){
            viewVector.add(monLayout.getChildAt(i));
        }

        Iterator<View> viewIterator = viewVector.iterator();
        while (viewIterator.hasNext()){ // rendre invisible chaque view
            View v = viewIterator.next();
            v.setVisibility(View.GONE); // on fait comme si on retirait chaque view
        }
    }



    public void peupler(){ // afficher a l'ecran la prochaine carte du quiz
        nbQPosees++;
        monLayout = findViewById(R.id.monLayout);
        // trouver cb de boutons a aj
        int nbRep = carte.getPropositionsIncorrectes().size() + 1; // nombre total de reponses


        // ajouter q
        TextView textView = new TextView(this);
        textView.setText(carte.getQuestion());
        monLayout.addView(textView);

        // ajouter boutons
        Vector<Button> vectButton = new Vector<Button>(); // vecteur contenant boutons
        for (int i=0; i < nbRep; i++){
            Button b = new Button(this);
            b.setOnClickListener(this);
            monLayout.addView(b);
            vectButton.add(b); // aj les boutons au vect

        }





        Collections.shuffle(vectButton); // on melange les boutons, pas les questions; c'est suffisant

        Vector<String> vectReponses = carte.getPropositionsIncorrectes();
        vectReponses.add(carte.getReponseCorrecte()); // contient toutes les rep

        Iterator<Button> itB = vectButton.iterator();
        Iterator<String> itR = vectReponses.iterator();

        while (itB.hasNext()){ // meme nb de reponses que de boutons
            Button b = itB.next();
            b.setText(itR.next());
        }
    }

    public void boutonClique(View view){
        popUpResultat(view); // cette fonction incremente nbBonneReponses aussi
        passerALaProchaineCarte(view);
    }

    public void passerALaProchaineCarte(View view){
        if (it.hasNext()) { // il reste des questions apres
            carte = it.next();
            toutMasquer(); // on masque tout, puis on repeuple
            peupler(); // afficher la prochaine carte
        } else{ // on  a fini la derniere question
//            musique.stop(); // arreter la musique
            ouvrirActiFin();
        }
    }

    public void ouvrirActiFin(){
        Intent intent = new Intent();
        intent.setClass(this, ScoreActivity.class);
        intent.putExtra("nbQPosees", nbQPosees);
        intent.putExtra("nbBonnesReponses", nbBonnesReponses);
        startActivity(intent);
        finish();
    }

    public void popUpResultat(View view) {
        Button boutonClique = (Button) view;
        String message = boutonClique.getText().toString();
        if (message == carte.getReponseCorrecte()){
            nbBonnesReponses++; // on incremente nbBonnesReponses aussi
            Toast.makeText(this, "bravo", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "incorrect", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) { // on n'a pas choisit le nom de la fonction du onClickListener, vu qu'on a ajoute le bouton en Java, pas en XML
        boutonClique(v);
    }
}