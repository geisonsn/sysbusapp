package br.com.gsn.sysbusapp.business;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.abstraction.BusinessDialogTaskOperation;
import br.com.gsn.sysbusapp.activity.MainActivity;
import br.com.gsn.sysbusapp.model.AbstractSpringRestResponse;
import br.com.gsn.sysbusapp.model.LinhaFavoritaDTO;
import br.com.gsn.sysbusapp.model.SpringRestResponse;
import br.com.gsn.sysbusapp.model.UsuarioDTO;
import br.com.gsn.sysbusapp.model.UsuarioWrapperDTO;
import br.com.gsn.sysbusapp.persistence.LinhaFavoritaDao;
import br.com.gsn.sysbusapp.task.TemplateAsyncTask;
import br.com.gsn.sysbusapp.util.ConnectionUtil;
import br.com.gsn.sysbusapp.util.PreferencesUtil;
import br.com.gsn.sysbusapp.util.RegexValidatorUtil;
import br.com.gsn.sysbusapp.util.SpringRestClient;
import br.com.gsn.sysbusapp.util.UrlServico;

/**
 * Created by Geison on 06/09/2015.
 */
public class LoginBusiness extends BusinessDialogTaskOperation<String, Integer, SpringRestResponse> {

    private DialogInterface dialog;
    private TemplateAsyncTask<String, Integer, SpringRestResponse> task;
    private LinhaFavoritaDao linhaFavoritaDao;

    public LoginBusiness(Activity context) {
        this.context = context;
        linhaFavoritaDao = new LinhaFavoritaDao(context);
    }

    @Override
    public void onProgressUpdate(Integer... values) {

    }

    @Override
    public SpringRestResponse doInBackground(String... params) {
        String urlServico = UrlServico.URL_LOGIN;

        String paramUsuario = params[0];
        String paramSenha = params[1];

        urlServico = urlServico.replace("{usuario}", paramUsuario);
        urlServico = urlServico.replace("{senha}", paramSenha);

        return new SpringRestClient()
            .showMessage(true)
            .getForObject(context, urlServico, UsuarioWrapperDTO.class);
    }

    @Override
    public void onPostExecute(final SpringRestResponse response) {

        response.setOnHttpOk(new AbstractSpringRestResponse.OnHttpOk() {
            @Override
            public void doThis() {
                UsuarioWrapperDTO wrapper = (UsuarioWrapperDTO) response.getObjectReturn();
                UsuarioDTO usuario = wrapper.getUsuario();

                PreferencesUtil.getInstance(context).set(PreferencesUtil.ID_USUARIO, usuario.getId());
                PreferencesUtil.getInstance(context).set(PreferencesUtil.NOME_USUARIO, usuario.getNome());
                PreferencesUtil.getInstance(context).set(PreferencesUtil.EMAIL_USUARIO, usuario.getEmail());

                salvarFavoritosOffline(wrapper.getLinhasFavoritas());

                onCloseDialog();

                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }

        });
        response.setOnHttpNotFound(new AbstractSpringRestResponse.OnHttpNotFound() {
            @Override
            public void doThis() {
                Toast.makeText(context, context.getResources().getString(R.string.msg_senha_usuario_incorreto), Toast.LENGTH_SHORT).show();
            }
        });

        response.executeCallbacks();
        super.handleProgressRequest();
    }

    private void salvarFavoritosOffline(List<LinhaFavoritaDTO> linhasFavoritas) {
        for (LinhaFavoritaDTO l : linhasFavoritas) {
            Long idUsuario = l.getIdUsuario();
            boolean isFavorita = linhaFavoritaDao.isLinhaFavorita(idUsuario, l.getIdLinha());
            if (!isFavorita) {
                linhaFavoritaDao.insert(idUsuario, l);
            }
        }
    }

    @Override
    public void cancelTaskOperation() {
        if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
            task.cancel(true);
        }
    }

    @Override
    public void onConfirmButton(DialogInterface dialog) {
        this.dialog = dialog;
        Dialog dialogFragment = ((Dialog) dialog);
        EditText email = (EditText) dialogFragment.findViewById(R.id.email);
        EditText senha = (EditText) dialogFragment.findViewById(R.id.senha);

        if (ConnectionUtil.isNetworkConnected(context)) {
            if (loginValido(dialogFragment)) {
                if (ConnectionUtil.isNetworkConnected(context)) {
                    super.handleProgressRequest();
                    task = new TemplateAsyncTask(this);
                    task.execute(email.getText().toString(), senha.getText().toString());
                } else {
                    Toast.makeText(context, R.string.sem_conexao_com_internet, Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(context, R.string.voce_nao_esta_conectado, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean loginValido(Dialog dialog) {
        EditText email = (EditText) dialog.findViewById(R.id.email);
        EditText senha = (EditText) dialog.findViewById(R.id.senha);

        EditText campoComFoco = null;

        boolean isValid = true;

        if (TextUtils.isEmpty(email.getText())) {
            campoComFoco = email;
            email.setError(email.getHint() + " obrigatório");
            isValid = false;
        } else {
            if (!RegexValidatorUtil.isValidEmail(email.getText().toString())) {
                campoComFoco = email;
                email.setError("Email inválido");
                isValid = false;
            }
        }

        if (TextUtils.isEmpty(senha.getText())) {
            if (campoComFoco == null) {
                campoComFoco = senha;
            }
            senha.setError(senha.getHint() + " obrigatória");
            isValid = false;
        }

        if (campoComFoco != null) {
            campoComFoco.requestFocus();
        }

        return isValid;
    }
}
