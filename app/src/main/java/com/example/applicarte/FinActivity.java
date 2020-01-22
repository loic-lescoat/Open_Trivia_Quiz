// Auteurs: Loïc LESCOAT et Antoine DESCAMPS
// nous n'avons pas utilise les corrections
// a cause de l'utilisation de Html.fromHtml dans AccueilActivity, notre appli ne marche que sur API >= 24

package com.example.applicarte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FinActivity extends AppCompatActivity {

    float score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin);

        // pour montrer une page web a la fin
//        WebView wv = findViewById(R.id.webView);
//        wv.setWebViewClient(new WebViewClient());
//        wv.loadUrl("http://www.tourisme-metz.com/fr/accueil.html");

    }

    public void quitterLeJeu(View v){
        finish();
    }

    public void partagerResultat(View view) {
        Intent i = getIntent();
        score = i.getFloatExtra("score", (float) -1.0);
        Intent versSMS = new Intent(Intent.ACTION_SENDTO);
        versSMS.setData(Uri.parse("smsto:"));
        versSMS.putExtra("sms_body", "Hey! Check out this quiz! I got a score of " +
                Float.toString(score) + "/1.0.");
        // Intent choixAppSms = Intent.createChooser(intent, "avec quelle app SMS?"); // decommenter pour activer le choix d'appli SMS
        startActivity(versSMS);
    }
}

