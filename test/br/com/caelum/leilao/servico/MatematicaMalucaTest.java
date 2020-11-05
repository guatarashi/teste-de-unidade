package br.com.caelum.leilao.servico;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MatematicaMalucaTest {

    @Test
    public void deveMultiplicarNumerosMaioresQue30() {
        int numero = 50;
        MatematicaMaluca matematicaMaluca = new MatematicaMaluca();
        int contaMaluca = matematicaMaluca.contaMaluca(numero);

        assertEquals(numero * 4, contaMaluca);
    }

    @Test
    public void deveMultiplicarNumerosMaioresQue10EMenoresQue30() {
        int numero = 20;
        MatematicaMaluca matematicaMaluca = new MatematicaMaluca();
        int contaMaluca = matematicaMaluca.contaMaluca(numero);

        assertEquals(numero * 3, contaMaluca);
    }

    @Test
    public void deveMultiplicarNumerosMenoresQue10() {
        int numero = 5;
        MatematicaMaluca matematicaMaluca = new MatematicaMaluca();
        int contaMaluca = matematicaMaluca.contaMaluca(numero);

        assertEquals(numero * 2, contaMaluca);
    }
}
