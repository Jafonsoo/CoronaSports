package pt.ipg.coronasports;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void bpaises(View view) {
        Intent intent = new Intent(this, activity_pais.class);
        startActivity(intent);
    }

    public void bequipas(View view) {
        Intent intent = new Intent(this, activity_equipa.class);
        startActivity(intent);
    }

    public void bjogador(View view) {
        Intent intent = new Intent(this, activity_atleta.class);
        startActivity(intent);
    }

    public void bestatistica(View view) {
    }
}
