package br.com.gsn.sysbusapp.util;

/**
 * Created by p001234 on 05/05/15.
 */
public final class UrlServico {

    private UrlServico() {}

    //Login do usuário
    public static final String URL_LOGIN = "http://sysbusweb-gsanton.rhcloud.com/services/usuario/{usuario}/{senha}";
    //Inclusão de usário
    public static final String URL_NOVO_USUARIO = "http://sysbusweb-gsanton.rhcloud.com/services/usuario";

    //Cadastro de linha/veículo
    public static final String URL_NOVA_LINHA = "http://sysbusweb-gsanton.rhcloud.com/services/veiculo";
    //Listagem dos veículos de uma linha
    public static final String URL_LISTAGEM_VEICULOS_POR_LINHA = "http://sysbusweb-gsanton.rhcloud.com/services/veiculo/{numeroLinha}";

    //Listagem de linhas
    public static final String URL_LISTAGEM_LINHA = "http://sysbusweb-gsanton.rhcloud.com/services/linha";

    //Listagem de linhas favoritas
    public static final String URL_LISTAGEM_LINHA_FAVORITA = "http://sysbusweb-gsanton.rhcloud.com/services/linhafavorita/{idUsuario}";

    //Sincronização de favoritos
    public static final String URL_SINCRONIZAR_FAVORITOS = "http://sysbusweb-gsanton.rhcloud.com/services/linhafavorita/sincronizarFavoritos";

    //Listagem de origem reclamação
    public static final String URL_LISTAGEM_ORIGEM_RECLAMACAO = "http://sysbusweb-gsanton.rhcloud.com/services/origemreclamacao/{objetoReclamado}";

    //Inclusão de reclamação
    public static final String URL_NOVA_RECLAMACAO = "http://sysbusweb-gsanton.rhcloud.com/services/reclamacao";
    //Listagem de linhas mais reclamadas
    public static final String URL_LINHAS_MAIS_RECLAMADAS = "http://sysbusweb-gsanton.rhcloud.com/services/reclamacao/linhasmaisreclamadas/{quantidade}";
    //Listagem das principais reclamações de uma linha
    public static final String URL_RECLAMACOES_POR_LINHA = "http://sysbusweb-gsanton.rhcloud.com/services/reclamacao/principaisreclamacoesporlinha/{idLinha}/{quantidade}";

    //Inclusão de localização da linha
    public static final String URL_CHECKIN = "http://sysbusweb-gsanton.rhcloud.com/services/localizacaolinha";
    //Listagem de linhas em deslocamenot
    public static final String URL_VEICULOS_EM_DESLOCAMENTO = "http://sysbusweb-gsanton.rhcloud.com/services/localizacaolinha/veiculosemdeslocamento/{intervalo}";
    //Listagem de linhas em deslocamento próximas
    public static final String URL_VEICULOS_EM_DESLOCAMENTO_PROXIMOS = "http://sysbusweb-gsanton.rhcloud.com/services/localizacaolinha/veiculosemdeslocamentoproximos/{intervalo}/{distancia}/{latitude}/{longitude}";
}
