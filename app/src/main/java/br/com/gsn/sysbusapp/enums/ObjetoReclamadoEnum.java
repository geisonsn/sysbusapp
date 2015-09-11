package br.com.gsn.sysbusapp.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum ObjetoReclamadoEnum {
	
	SELECIONE("Selecione"),
	MOTORISTA("Motorista"),
	COBRADOR("Cobrador"),
	VEICULO("Veículo"), 
	OUTROS("Outros");
	
	private String descricao;
	
	private ObjetoReclamadoEnum(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public static ObjetoReclamadoEnum getFromOrdinal(int index) {
		return values()[index];
	}
	
	public static ObjetoReclamadoEnum getFromName(String name) {
		for (ObjetoReclamadoEnum objetoReclamado : values()) {
			if (objetoReclamado.name().equalsIgnoreCase(name)) {
				return objetoReclamado;
			}
		}
		return null;
	}

	public static ObjetoReclamadoEnum getFromDescricao(String descricao) {
		for (ObjetoReclamadoEnum objetoReclamado : values()) {
			if (objetoReclamado.getDescricao().equalsIgnoreCase(descricao)) {
				return objetoReclamado;
			}
		}
		return null;
	}

	public static List<ObjetoReclamadoEnum> list(boolean semOutros) {
		return construirListaObjetoReclamado(semOutros);
	}
	
	public static List<ObjetoReclamadoEnum> list() {
		return construirListaObjetoReclamado(false);
	}

	private static List<ObjetoReclamadoEnum> construirListaObjetoReclamado(boolean semOutros) {
		
		List<ObjetoReclamadoEnum> list = Arrays.asList(values());
		
		ordenarPorDescricao(list);
		
		ajustarLista(list);
		
		if (semOutros) {
			list = list.subList(0, OUTROS.ordinal());
		}
		
		return list;
	}

	private static void ordenarPorDescricao(List<ObjetoReclamadoEnum> list) {
		Collections.sort(list, new Comparator<ObjetoReclamadoEnum>() {
			@Override
			public int compare(ObjetoReclamadoEnum o1, ObjetoReclamadoEnum o2) {
				return o1.descricao.compareTo(o2.descricao);
			}
		});
	}
	
	private static void ajustarLista(List<ObjetoReclamadoEnum> lista) {
		int indexOutrosOrdenado = lista.indexOf(OUTROS); //Posicao ocupada por OUTROS apos sort
		//Põe a opção 'Outros' no final e reajusta os outros elementos na lista
		for (int index = indexOutrosOrdenado; index < lista.size() - 1; index++) {
			ObjetoReclamadoEnum elementoEsq = lista.get(index);
			ObjetoReclamadoEnum elementoDir = lista.get(index+1);
			lista.set(index, elementoDir);
			lista.set(index + 1, elementoEsq);
		}
		
		//Põe a opção 'Selecione' no início e ajusta outros elementos na lista
		int indexSelecioneOrdenado = lista.indexOf(SELECIONE);
		for (int index = indexSelecioneOrdenado; index > 0; index--) {
			ObjetoReclamadoEnum elementoEsq = lista.get(index-1);
			ObjetoReclamadoEnum elementoDir = lista.get(index);
			lista.set(index -1 , elementoDir);
			lista.set(index, elementoEsq);
		}
		
	}
	
}
