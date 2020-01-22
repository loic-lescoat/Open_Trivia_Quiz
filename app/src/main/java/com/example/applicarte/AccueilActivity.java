// Auteurs: Loïc LESCOAT et Antoine DESCAMPS
// nous n'avons pas utilise les corrections
// a cause de l'utilisation de Html.fromHtml dans AccueilActivity, notre appli ne marche que sur API >= 24

package com.example.applicarte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html; // Html.fromHtml ne marche que sur API >= 24
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Vector;

public class AccueilActivity extends AppCompatActivity {


    Quiz q;
    Intent lancerQuestionnaire;
    int nbQ = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        String url = "https://opentdb.com/api.php?amount=" + Integer.toString(nbQ);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Response.Listener<String> responseListener = new CarteResponseListener();
        Response.ErrorListener responseErrorListener = new CarteResponseErrorListener();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener, responseErrorListener);
        requestQueue.add(stringRequest);



        // lancer le questionnaire
        lancerQuestionnaire = new Intent();
        lancerQuestionnaire.setClass(this, CarteActivity.class);


    }

    private class CarteResponseListener implements Response.Listener<String> {
        @Override
        public void onResponse(String response) {
            // traiter le json et ajouter les q
            try{
                q = new Quiz();
                // traiter json
                JSONObject obj = new JSONObject(response);
                JSONArray res = obj.getJSONArray("results");
                for (int i = 0; i < res.length();i++){
                    JSONObject cetteQ = res.getJSONObject(i);
                    String question = String.valueOf(Html.fromHtml(cetteQ.getString("question"), Html.FROM_HTML_MODE_COMPACT)); // ne marche que sur API >= 24
                    String bonneReponse = String.valueOf(Html.fromHtml(cetteQ.getString("correct_answer"), Html.FROM_HTML_MODE_COMPACT));
                    JSONArray mauvRep = cetteQ.getJSONArray("incorrect_answers");
                    Vector<String> mauvRepVect = new Vector<String>();
                    for (int j=0; j<mauvRep.length();j++){
                        mauvRepVect.add(String.valueOf(Html.fromHtml(mauvRep.getString(j),Html.FROM_HTML_MODE_COMPACT)));
                    }
                    // normalement tout est bon
                    q.ajouter(new Carte(question, bonneReponse, mauvRepVect));
                }

                etapesFinales();

            } catch (Exception e){
                remplirQuizAvecQParDefaut();
                etapesFinales();
            }

        }
    }

    private void etapesFinales(){ // met q dans extra, et lance le questionnaire
        lancerQuestionnaire.putExtra("quiz", q);
        startActivity(lancerQuestionnaire);
        finish(); // ne pas retourner en arriere
    }

    private class CarteResponseErrorListener implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {
            remplirQuizAvecQParDefaut();
            etapesFinales();
        }
    }

       public void remplirQuizAvecQParDefaut(){
        q = new Quiz();

       Vector<String> propositionsIncorrectes = new Vector<String>();
       propositionsIncorrectes.add("Drachouny");
       propositionsIncorrectes.add("la Bête de la Seille");
       propositionsIncorrectes.add("la Tarasque");
       q.ajouter(new Carte(
               "Comment s'appelle l'animal mythique à l'apparence d'un dragon qui vivait à Metz ?",
               "Le Graoully",
               propositionsIncorrectes));

       propositionsIncorrectes = new Vector<String>();
       propositionsIncorrectes.add("faux");
       q.ajouter(new Carte(
               "Paul Verlaine est né à Metz.",
               "vrai",
               propositionsIncorrectes));

       propositionsIncorrectes = new Vector<String>();
       propositionsIncorrectes.add("la minette lorraine");
       propositionsIncorrectes.add("la pierre de Norroy");
       propositionsIncorrectes.add("la pierre d'Euville");
       q.ajouter(new Carte(
               "Quelle pierre emblématique de la région est utilisée par de nombreux bâtiments de Metz ?",
               "la pierre de Jaumont",
               propositionsIncorrectes));

       propositionsIncorrectes = new Vector<String>();
       propositionsIncorrectes.add("rue de la tour aux rats");
       propositionsIncorrectes.add("rue du trou aux serpents");
       propositionsIncorrectes.add("rue du pas du loup");
       propositionsIncorrectes.add("rue aux ours");
       propositionsIncorrectes.add("rue du faisan");
       propositionsIncorrectes.add("rue de l'écrevisse");
       propositionsIncorrectes.add("rue de la chèvre");
       propositionsIncorrectes.add("rue des castors");
       q.ajouter(new Carte(
               "Laquelle de ces rues n'existe pas à Metz ?",
               "rue des ânes",
               propositionsIncorrectes));
   }





}
