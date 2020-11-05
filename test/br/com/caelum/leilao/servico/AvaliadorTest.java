package br.com.caelum.leilao.servico;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import org.junit.*;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;

public class AvaliadorTest {

    private Avaliador leiloeiro;
    private Usuario joao;
    private Usuario jose;
    private Usuario maria;

    @Before
    public void setup() {
        this.leiloeiro = new Avaliador();
        this.joao = new Usuario("João");
        this.jose = new Usuario("José");
        this.maria = new Usuario("Maria");
        System.out.println("inicializando teste!");
    }

    @After
    public void finaliza() {
        System.out.println("fim");
    }

    @BeforeClass
    public static void testandoBeforeClass() {
        System.out.println("before class");
    }

    @AfterClass
    public static void testandoAfterClass() {
        System.out.println("after class");
    }

    @Test
    public void deveEntenderLancesEmOrdemCrescente() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 novo")
                .lance(joao, 250.0)
                .lance(jose, 300.0)
                .lance(maria, 400.0)
                .constroi();

        // parte 2: ação
        leiloeiro.avalia(leilao);

        // parte 3: validação
//        assertEquals(400, leiloeiro.getMaiorLance(), 0.00001);
//        assertEquals(250, leiloeiro.getMenorLance(), 0.00001);
        assertThat(leiloeiro.getMenorLance(), equalTo(250.0));
        assertThat(leiloeiro.getMaiorLance(), equalTo(400.0));
    }

    @Test
    public void deveEntenderLancesEmOrdemCrescenteOutrosValores() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 novo")
                .lance(joao, 1000.0)
                .lance(jose, 2000.0)
                .lance(maria, 3000.0)
                .constroi();

        // parte 2: ação
        leiloeiro.avalia(leilao);

        // parte 3: validação
        assertEquals(3000.0, leiloeiro.getMaiorLance(), 0.00001);
        assertEquals(1000.0, leiloeiro.getMenorLance(), 0.00001);
    }

    @Test
    public void deveEntenderLeilaoComApenasUmLance() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation 2 novo")
                .lance(joao, 1000.0)
                .constroi();

        leiloeiro.avalia(leilao);

//        assertEquals(1000.0, leiloeiro.getMaiorLance(), 0.0001);
//        assertEquals(1000.0, leiloeiro.getMenorLance(), 0.0001);
        assertThat(leiloeiro.getMenorLance(), equalTo(leiloeiro.getMaiorLance()));
    }

    @Test
    public void deveEntenderLeilaoComLancesEmOrdemRandomica() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
                .lance(joao,200.0)
                .lance(maria,450.0)
                .lance(joao,120.0)
                .lance(maria,700.0)
                .lance(joao,630.0)
                .lance(maria,230.0)
                .constroi();

        leiloeiro.avalia(leilao);

//        assertEquals(700.0, leiloeiro.getMaiorLance(), 0.0001);
//        assertEquals(120.0, leiloeiro.getMenorLance(), 0.0001);
        assertThat(leiloeiro.getMaiorLance(), equalTo(700.0));
        assertThat(leiloeiro.getMenorLance(), equalTo(120.0));
    }

    @Test
    public void deveEntenderLeilaoComLancesEmOrdemDecrescente() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
                .lance(joao, 400.0)
                .lance(maria, 300.0)
                .lance(joao, 200.0)
                .lance(maria, 100.0)
                .constroi();

        leiloeiro.avalia(leilao);

//        assertEquals(400.0, leiloeiro.getMaiorLance(), 0.0001);
//        assertEquals(100.0, leiloeiro.getMenorLance(), 0.0001);
        assertThat(leiloeiro.getMaiorLance(), equalTo(400.0));
        assertThat(leiloeiro.getMenorLance(), equalTo(100.0));
    }

    @Test
    public void deveEncontrarOsTresMaioresLances() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
                .lance(joao, 100.0)
                .lance(maria, 200.0)
                .lance(joao, 300.0)
                .lance(maria, 400.0)
                .constroi();

        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();
        assertEquals(3, maiores.size());

//        assertEquals(400.0, maiores.get(0).getValor(), 0.00001);
//        assertEquals(300.0, maiores.get(1).getValor(), 0.00001);
//        assertEquals(200.0, maiores.get(2).getValor(), 0.00001);
        assertThat(maiores, hasItems(
                new Lance(maria, 400),
                new Lance(joao, 300),
                new Lance(maria, 200)
        ));
    }

    @Test
    public void deveDevolverTodosLancesCasoNaoHajaNoMinimo3() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
                .lance(joao, 500.0)
                .lance(maria, 400.0)
                .constroi();

        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();
        assertEquals(2, maiores.size());

//        assertEquals(500.0, maiores.get(0).getValor(), 0.00001);
//        assertEquals(400.0, maiores.get(1).getValor(), 0.00001);
        assertThat(maiores.get(0).getValor(), equalTo(500.0));
        assertThat(maiores.get(1).getValor(), equalTo(400.0));
    }

    @Test(expected = RuntimeException.class)
    public void deveDevolverListaVaziaCasoNaoHajaLances() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
                .constroi();

        leiloeiro.avalia(leilao);
    }
}
