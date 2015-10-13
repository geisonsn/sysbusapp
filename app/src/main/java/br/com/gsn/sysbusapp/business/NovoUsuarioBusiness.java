package br.com.gsn.sysbusapp.business;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.abstraction.BusinessDialogTaskOperation;
import br.com.gsn.sysbusapp.task.TemplateAsyncTask;
import br.com.gsn.sysbusapp.activity.MainActivity;
import br.com.gsn.sysbusapp.model.AbstractSpringRestResponse;
import br.com.gsn.sysbusapp.model.SpringRestResponse;
import br.com.gsn.sysbusapp.model.UsuarioDTO;
import br.com.gsn.sysbusapp.util.ConnectionUtil;
import br.com.gsn.sysbusapp.util.PreferencesUtil;
import br.com.gsn.sysbusapp.util.RegexValidatorUtil;
import br.com.gsn.sysbusapp.util.SpringRestClient;
import br.com.gsn.sysbusapp.util.UrlServico;

/**
 * Created by Geison on 07/09/2015.
 */
public class NovoUsuarioBusiness extends BusinessDialogTaskOperation<UsuarioDTO, Integer, SpringRestResponse> {

    private TemplateAsyncTask<UsuarioDTO, Integer, SpringRestResponse> task;

    public NovoUsuarioBusiness(Activity context) {
        this.context = context;
    }

    @Override
    public void onConfirmButton(DialogInterface dialog) {

        if (ConnectionUtil.isNetworkConnected(context)) {
            Dialog form = ((Dialog) dialog);
            boolean validForm = isValidForm(form);
            if (validForm) {
                if (ConnectionUtil.isNetworkConnected(context)) {
                    super.handleProgressRequest();
                    UsuarioDTO usuarioDTO = getUsuarioDTO(form);
                    task = new TemplateAsyncTask(this);
                    task.execute(usuarioDTO);
                } else {
                    Toast.makeText(context, R.string.sem_conexao_com_internet, Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(context, R.string.voce_nao_esta_conectado, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onProgressUpdate(Integer... values) {

    }

    @Override
    public SpringRestResponse doInBackground(UsuarioDTO... params) {
        return new SpringRestClient()
            .showMessage(true)
            .post(context, UrlServico.URL_NOVO_USUARIO, params[0], UsuarioDTO.class);
    }

    @Override
    public void onPostExecute(final SpringRestResponse response) {
        response.setOnHttpCreated(new AbstractSpringRestResponse.OnHttpCreated() {
            @Override
            public void doThis() {
                UsuarioDTO u = (UsuarioDTO) response.getObjectReturn();

                PreferencesUtil.getInstance(context).set(PreferencesUtil.ID_USUARIO, u.getId());
                PreferencesUtil.getInstance(context).set(PreferencesUtil.NOME_USUARIO, u.getNome());
                PreferencesUtil.getInstance(context).set(PreferencesUtil.EMAIL_USUARIO, u.getEmail());

                Toast.makeText(context, "Usuário cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context, MainActivity.class));
            }
        });
        response.executeCallbacks();
        super.handleProgressRequest();
    }

    @Override
    public void cancelTaskOperation() {
        if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
            task.cancel(true);
        }
    }

    public boolean isValidForm(Dialog dialog) {

        boolean isValid = true;

        EditText nome, email, senha;

        nome = (EditText) dialog.findViewById(R.id.nome);
        email = (EditText) dialog.findViewById(R.id.email);
        senha = (EditText) dialog.findViewById(R.id.senha);

        List<View> invalidFields = new ArrayList<>();

        if (TextUtils.isEmpty(nome.getText().toString())) {
            nome.setError("Nome obrigatório");
            invalidFields.add(nome);
            isValid = false;
        }
        if (TextUtils.isEmpty(email.getText().toString())) {
            email.setError("Email obrigatório");
            invalidFields.add(email);
            isValid = false;
        } else {
            if (!RegexValidatorUtil.isValidEmail(email.getText().toString())) {
                email.setError("Email inválido");
                invalidFields.add(email);
                isValid = false;
            }
        }
        if (TextUtils.isEmpty(senha.getText().toString())) {
            senha.setError("Senha obrigatória");
            invalidFields.add(senha);
            isValid = false;
        }

        if (!isValid) {
            invalidFields.get(0).requestFocus();
        }

        return isValid;
    }

    private UsuarioDTO getUsuarioDTO(Dialog dialog) {
        EditText nome, email, senha;

        nome = (EditText) dialog.findViewById(R.id.nome);
        email = (EditText) dialog.findViewById(R.id.email);
        senha = (EditText) dialog.findViewById(R.id.senha);

        UsuarioDTO u = new UsuarioDTO();
        u.setNome(nome.getText().toString());
        u.setEmail(email.getText().toString());
        u.setPassword(senha.getText().toString());
        return u;
    }
}
