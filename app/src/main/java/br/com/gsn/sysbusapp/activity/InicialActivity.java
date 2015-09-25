package br.com.gsn.sysbusapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.dialog.LoginDialog;
import br.com.gsn.sysbusapp.dialog.NovoUsuarioDialog;
import br.com.gsn.sysbusapp.fragment.DialogContentFragment;
import br.com.gsn.sysbusapp.util.PreferencesUtil;


public class InicialActivity extends FragmentActivity {

    private DialogFragment dialogLogin;
    private DialogContentFragment dialogNovoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicial);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (PreferencesUtil.getInstance(this).isUsuarioLogado()) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    public void realizarLogin(View view) {
        dialogLogin = new LoginDialog();
        dialogLogin.show(getSupportFragmentManager(), "login");
    }

    public void realizarCadastro(View view) {
        dialogNovoUsuario = new NovoUsuarioDialog();
        dialogNovoUsuario.show(getSupportFragmentManager(), "cadastroUsuario");
    }

    public void acessarSemUsuario(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
