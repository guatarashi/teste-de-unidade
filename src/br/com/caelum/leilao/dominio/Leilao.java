package br.com.caelum.leilao.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Leilao {

	private String descricao;
	private List<Lance> lances;
	
	public Leilao(String descricao) {
		this.descricao = descricao;
		this.lances = new ArrayList<Lance>();
	}

	public void propoe(Lance lance) {
		if (lances.isEmpty() || podeDarLance(lance.getUsuario())) {
			lances.add(lance);
		}
	}

	private boolean podeDarLance(Usuario usuario) {
		return !ultimoLanceDado().getUsuario().equals(usuario) && qtdeDeLancesDo(usuario) < 5;
	}

	public String getDescricao() {
		return descricao;
	}

	public List<Lance> getLances() {
		return Collections.unmodifiableList(lances);
	}

	public void dobraLance(Usuario usuario) {
		Lance ultimoLance = ultimoLanceDo(usuario);

		if (ultimoLance != null) {
			propoe(new Lance(usuario, ultimoLance.getValor() * 2));
		}
	}

	private Lance ultimoLanceDo(Usuario usuario) {
		return lances.stream()
				.filter(lance -> lance.getUsuario().equals(usuario))
				.findFirst()
				.orElse(null);
	}

	private long qtdeDeLancesDo(Usuario usuario) {
		return lances.stream().filter(lance1 -> lance1.getUsuario().equals(usuario)).count();
	}

	private Lance ultimoLanceDado() {
		return lances.get(lances.size() - 1);
	}
}
