package br.com.caelum.leilao.dominio;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import org.junit.Before;
import org.junit.Test;

import static br.com.caelum.leilao.util.LeilaoMatcher.temUmLance;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class leilaoTest {

    private Usuario steveJobs;
    private Usuario steveWozniak;
    private Usuario billGates;

    @Before
    public void setup() {
        this.steveJobs = new Usuario("Steve Jobs");
        this.steveWozniak = new Usuario("Steve Wozniak");
        this.billGates = new Usuario("Bill Gates");
    }

    @Test
    public void deveReceberUmLance() {
        Leilao leilao = new Leilao("Macbook Por 15");
        assertEquals(0, leilao.getLances().size());

        leilao.propoe(new Lance(steveJobs, 2000));

//        assertEquals(1, leilao.getLances().size());
//        assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
        assertThat(leilao.getLances().size(), equalTo(1));
        assertThat(leilao, temUmLance(leilao.getLances().get(0)));
    }

    @Test
    public void deveReceberVariosLances() {
        Leilao leilao = new CriadorDeLeilao().para("Macbook Pro 15")
                .lance(steveJobs, 2000)
                .lance(steveWozniak, 3000)
                .constroi();

        assertEquals(2, leilao.getLances().size());
        assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
        assertEquals(3000.0, leilao.getLances().get(1).getValor(), 0.00001);
    }

    @Test
    public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {
        Leilao leilao = new CriadorDeLeilao().para("Macbook Pro 15")
                .lance(steveJobs, 2000.0)
                .lance(steveJobs, 3000.0)
                .constroi();

        assertEquals(1, leilao.getLances().size());
        assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
    }

    @Test
    public void naoDeveAceitarMaisDoQue5LancesDeUmMesmoUsuario() {
        Leilao leilao = new CriadorDeLeilao().para("Macbook Pro 15")
                .lance(steveJobs, 2000.0)
                .lance(billGates, 3000.0)
                .lance(steveJobs, 4000.0)
                .lance(billGates, 5000.0)
                .lance(steveJobs, 6000.0)
                .lance(billGates, 7000.0)
                .lance(steveJobs, 8000.0)
                .lance(billGates, 9000.0)
                .lance(steveJobs, 10000.0)
                .lance(billGates, 11000.0)
                .lance(steveJobs, 12000.0) // deve ser ignorado
                .constroi();


        assertEquals(10, leilao.getLances().size());
        assertEquals(11000.0, leilao.getLances().get(leilao.getLances().size() - 1).getValor(), 0.00001);
    }

    @Test
    public void deveDobrarOUltimoLanceDado() {
        Leilao leilao = new CriadorDeLeilao().para("Macbook Pro 15")
                .lance(steveJobs, 2000)
                .lance(billGates, 3000)
                .constroi();

        leilao.dobraLance(steveJobs);

        assertEquals(4000, leilao.getLances().get(2).getValor(), 0.00001);
    }

    @Test
    public void naoDeveDobrarCasoNaoHajaLanceAnterior() {
        Leilao leilao = new CriadorDeLeilao().para("Macbook Pro 15")
                .constroi();

        leilao.dobraLance(steveJobs);

        assertEquals(0, leilao.getLances().size());
    }
}
