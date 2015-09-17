package br.com.gsn.sysbusapp.util;

/**
 * Created by p001234 on 05/05/15.
 */
public final class UrlServico {

    private UrlServico() {}

    public static final String URL_LOGIN = "http://sysbusweb-gsanton.rhcloud.com/services/usuario/{usuario}/{senha}";
    public static final String URL_NOVO_USUARIO = "http://sysbusweb-gsanton.rhcloud.com/services/usuario";
    public static final String URL_NOVA_RECLAMACAO = "http://sysbusweb-gsanton.rhcloud.com/services/reclamacao";
    public static final String URL_NOVA_LINHA = "http://sysbusweb-gsanton.rhcloud.com/services/veiculo";
    public static final String URL_LISTAGEM_LINHA = "http://sysbusweb-gsanton.rhcloud.com/services/linha";
    public static final String URL_LISTAGEM_LINHA_FAVORITA = "http://sysbusweb-gsanton.rhcloud.com/services/linhafavorita/{idUsuario}";
    public static final String URL_LISTAGEM_LINHA_POR_NUMERO = "http://sysbusweb-gsanton.rhcloud.com/services/linha/{numeroLinha}";
    public static final String URL_LISTAGEM_ORIGEM_RECLAMACAO = "http://sysbusweb-gsanton.rhcloud.com/services/origemreclamacao/{objetoReclamado}";
    public static final String URL_LINHAS_MAIS_RECLAMADAS = "http://sysbusweb-gsanton.rhcloud.com/services/reclamacao/linhasmaisreclamadas/{quantidade}";
    public static final String URL_VEICULOS_EM_DESLOCAMENTO = "http://sysbusweb-gsanton.rhcloud.com/services/localizacaolinha/veiculosemdeslocamento/{idUsuario}/{intervalo}";
    public static final String URL_VEICULOS_EM_DESLOCAMENTO_PROXIMOS = "http://sysbusweb-gsanton.rhcloud.com/services/localizacaolinha/veiculosemdeslocamentoproximos/{idUsuario}/{intervalo}/{distancia}/{latitude}/{longitude}";

}
