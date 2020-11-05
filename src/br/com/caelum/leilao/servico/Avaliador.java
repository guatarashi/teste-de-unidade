package br.com.caelum.leilao.servico;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;

import java.util.List;
import java.util.stream.Collectors;

public class Avaliador {

    private double maiorDeTodos = Double.NEGATIVE_INFINITY;
    private double menorDeTodos = Double.POSITIVE_INFINITY;
    private List<Lance> maiores;

    public void avalia(Leilao leilao) {
        // lança exceção
        if (leilao.getLances().size() == 0) {
            throw new RuntimeException("Não é possível avaliar um leilão sem lances!");
        }

        leilao.getLances().stream().forEach(lance -> {
            if (lance.getValor() > maiorDeTodos) {
                maiorDeTodos = lance.getValor();
            }

            if (lance.getValor() < menorDeTodos) {
                menorDeTodos = lance.getValor();
            }
        });

        pegaOsMaioresNo(leilao);
    }

    private void pegaOsMaioresNo(Leilao leilao) {
        maiores = leilao.getLances().stream()
                .sorted((o1, o2) -> Double.compare(o2.getValor(), o1.getValor()))
                .collect(Collectors.toList())
                .subList(0, leilao.getLances().size() > 3 ? 3 : leilao.getLances().size());
    }

    public List<Lance> getTresMaiores() {
        return this.maiores;
    }

    public double getMaiorLance() {
        return maiorDeTodos;
    }

    public double getMenorLance() {
        return menorDeTodos;
    }
}
