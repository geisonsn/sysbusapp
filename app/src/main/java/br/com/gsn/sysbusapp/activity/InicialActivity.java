package br.com.gsn.sysbusapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import br.com.gsn.sysbusapp.R;


public class InicialActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicial);
    }

    public void realizarLogin(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void realizarCadastro(View view) {
        startActivity((new Intent(this, MainActivity.class)));
    }
}
