
import ifrs.vvs.entities.Startup;
import ifrs.vvs.service.Batalha;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

class AvaliacaoEquipeTest {

    private Startup startup;
    private Scanner scanner;

    @BeforeEach
    void setUp() {
        startup = new Startup("Teste Startup", "Teste Slogan", 2000);
        startup.setPontos(0);
    }

    @Test
    void testOpcao1_PitchConvincente() {
        // Simula entrada do usuário: "1" + "S" (para sair)
        String input = "1\nS";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        scanner = new Scanner(in);

        // Executa o método
        Batalha.avaliaEquipe(startup, scanner);

        // Verifica pontos e contador
        assertEquals(6, startup.getPontos());
        assertEquals(1, startup.getTotalPitchConvincente());
    }

    @Test
    void testOpcao2_ProdutoComBugs() {
        String input = "2\nS";
        scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        Batalha.avaliaEquipe(startup, scanner);

        assertEquals(-4, startup.getPontos());
        assertEquals(1, startup.getTotalProdutoComBugs());
    }

    @Test
    void testOpcaoRepetida_NaoDeveAplicarNovamente() {
        String input = "1\n1\nS"; // Tenta aplicar a opção 1 duas vezes
        scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        Batalha.avaliaEquipe(startup, scanner);

        // Só deve aplicar uma vez
        assertEquals(6, startup.getPontos());
        assertEquals(1, startup.getTotalPitchConvincente());
    }

    @Test
    void testOpcaoInvalida_NaoAlteraPontos() {
        String input = "X\nS"; // Opção inválida
        scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        Batalha.avaliaEquipe(startup, scanner);

        assertEquals(0, startup.getPontos()); // Pontos iniciais mantidos
    }
}

