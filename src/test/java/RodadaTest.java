import ifrs.vvs.entities.Startup;
import ifrs.vvs.service.Batalha;
import ifrs.vvs.service.Rodada;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class RodadaTest {

    @Test
    void testRodadaNormal() {

        List<Startup> startups = new ArrayList<>();
        startups.add(new Startup("A", "Slogan", 2000));
        startups.add(new Startup("B", "Slogan", 2001));
        startups.add(new Startup("C", "Slogan", 2002));
        startups.add(new Startup("D", "Slogan", 2003));

        // Simula entrada: escolhe batalha 1, escolhe batalha 2, depois sai
        String input = "1\n1\nS\n2\n3\nS\nS";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Mock do Batalha.batalha()
        try (MockedStatic<Batalha> mockedBatalha = mockStatic(Batalha.class)) {
            // Configura para retornar a primeira startup de cada partida
            mockedBatalha.when(() -> Batalha.batalha(any()))
                    .thenAnswer(inv -> {
                        List<Startup> partida = inv.getArgument(0);
                        return partida.get(0);
                    });

            // Executa
            List<Startup> vencedores = Rodada.rodada(startups, new ArrayList<>());

            // Verifica
            assertEquals(2, vencedores.size(), "Deveria ter 2 vencedores");
        }
    }
}
