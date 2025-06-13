import ifrs.vvs.entities.Startup;
import ifrs.vvs.service.Batalha;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class BatalhaTest {
    private static final int WIN = 30;
    private List<Startup> startups;
    private Startup startupA;
    private Startup startupB;

    @BeforeEach
    void setUp() {
        startupA = new Startup("Startup A", "Teste Slogan A", 2000);
        startupA.setPontos(0);
        startupB = new Startup("Startup B", "Teste Slogan B", 2001);
        startupB.setPontos(0);
        startups = List.of(startupA, startupB);
    }

    @Test
    void testVitoriaStartupA() {

        startupA.setPontos(0);
        startupB.setPontos(0);

        // Simula:
        // 1. Usuário seleciona Startup A (opção 1) e aplica evento "Pitch convincente" (+6 pontos)
        // 2. Usuário seleciona Startup B (opção 2) e aplica evento "Produto com bugs" (-4 pontos)
        String input = "1\n1\nS\n2\n2\nS";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        //Executa
        Startup vencedor = Batalha.batalha(startups);

        // Verifica
        assertEquals(6 + WIN, startupA.getPontos()); // A ganha + bônus
        assertEquals(-4, startupB.getPontos());
        assertEquals(startupA, vencedor);
    }


    @Test
    void testBloqueioReavaliacao() {
        // Simula tentativa de avaliar duas vezes
        String input = "1\nS\n1\n2\nS";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Batalha.batalha(startups);

        // Verifica mensagem de bloqueio
        assertTrue(outputStream.toString().contains("Equipe já avaliada durante essa rodada!"));
    }
}