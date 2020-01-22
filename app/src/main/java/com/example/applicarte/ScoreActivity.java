// Auteurs: Loïc LESCOAT et Antoine DESCAMPS
// nous n'avons pas utilise les corrections
// a cause de l'utilisation de Html.fromHtml dans AccueilActivity, notre appli ne marche que sur API >= 24

package com.example.applicarte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreActivity extends AppCompatActivity {

    TextView msgFin; // TV pour annoncer le message de fin de quiz
    int nbQPosees;
    int nbBonnesReponses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Intent intent = getIntent();
        nbQPosees = intent.getIntExtra("nbQPosees", -1);
        nbBonnesReponses = intent.getIntExtra("nbBonnesReponses", -1);

        msgFin = findViewById(R.id.msgFin);
        msgFin.setText(String.format(getString(R.string.msgFin), nbBonnesReponses, nbQPosees));

        TextView tv1 = findViewById(R.id.scorePrecedent);
        SharedPreferences infoScore = getSharedPreferences("infoScore", Context.MODE_PRIVATE); // on ne sait pas si des donnees sont enregistrees; mais
        int nbBonnesReponsesPrecedent = infoScore.getInt("nbBonnesReponses", -1); // si ce n'est pas le cas, les valeurs par defaut ne seront pas affichees!
        int nbQPoseesPrecedent = infoScore.getInt("nbQPosees", 1000); // voir (1) plus bas
        String pseudoPrecedent = infoScore.getString("pseudo", "aucun pseudo");

        if (infoScore.contains("nbBonnesReponses")) { // un record a deja ete enregistre
            float score = ((float) nbBonnesReponses) /nbQPosees;
            float scorePrecedent = ((float) nbBonnesReponsesPrecedent) / nbQPoseesPrecedent;
            if (score > scorePrecedent){ // record battu


                tv1.setText(String.format(getString(R.string.recordBattu), pseudoPrecedent, nbBonnesReponsesPrecedent, nbQPoseesPrecedent));
            } else if (scorePrecedent == score){ // record egale
                tv1.setText(String.format(getString(R.string.recordEgale),pseudoPrecedent,nbBonnesReponsesPrecedent,nbQPoseesPrecedent));
            } else { // record pas battu
                //EditText editText = findViewById(R.id.editText);
                //Button button = findViewById(R.id.button);

                //editText.setVisibility(View.INVISIBLE); // cacher les views pour ajouter un pseudo
                //button.setVisibility(View.INVISIBLE);

                tv1.setText(String.format(getString(R.string.dessousRecord), pseudoPrecedent, nbBonnesReponsesPrecedent, nbQPoseesPrecedent));

            }

        } else{ // on n'a jamais enregistre de record (1)
            tv1.setText(getString(R.string.premierRecord));
        }
    }

    public void boutonClique(View v){ // on verifie si on doit enregistrer le score, puis on le fait le cas echeant
        SharedPreferences infoScore = getSharedPreferences("infoScore", Context.MODE_PRIVATE);
        float score = ((float) nbBonnesReponses) /nbQPosees;
        if (infoScore.contains("nbBonnesReponses")){ // il y a deja un score enregistre
            int nbBonnesReponsesPrecedent = infoScore.getInt("nbBonnesReponses", -1);
            int nbQPoseesPrecedent = infoScore.getInt("nbQPosees", 1000);

            float scorePrecedent = ((float) nbBonnesReponsesPrecedent) / nbQPoseesPrecedent;
            if (score >= scorePrecedent){ // record egale ou battu
                enregistrerLeScore(v);
            }
        } else{ // il n y a pas de score enregistre
            enregistrerLeScore(v);
        }

        passerALEcranDeFin(score);
    }

    private void passerALEcranDeFin(float score) {
        Intent intent = new Intent(this, FinActivity.class);
        intent.putExtra("score", score);
        startActivity(intent);
        finish(); // on ne peut pas revenir en arriere
    }

    public void enregistrerLeScore(View v){
        SharedPreferences infoScore = getSharedPreferences("infoScore", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = infoScore.edit();
        editor.putInt("nbBonnesReponses", nbBonnesReponses);
        editor.putInt("nbQPosees", nbQPosees);

        EditText et = findViewById(R.id.editText);
        String pseudo = et.getText().toString();
        editor.putString("pseudo", pseudo);
        editor.apply();











    }
}
