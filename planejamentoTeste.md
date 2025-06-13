# **Plano de Teste - StartUp Rush - DELL IT Academy 22**

**Versão:** 1.0  
**Data:** 13/06/2025  
**Autor:** João Fernando Hanzel Demetrio  
**Revisado por:** João Fernando Hanzel Demetrio

---

## **1. Objetivo**
Garantir a qualidade das funcionalidades principais do sistema através de uma análise estática e testes automatizados, cobrindo:
- Code Smells
- Padrões
- Avaliação individual de startups (`avaliaEquipe`)
- Batalhas entre startups (`batalha`)
- Rodadas de competição (`rodada`)

---

## **2. Estratégia de Teste**

### **2.1 Tipos de Teste**
| Tipo             | Framework         | Cobertura                                  |
|------------------|-------------------|--------------------------------------------|
| Análise Estática | SonarQube IDE     | Qualidade do código (Padrões, code smells) |
| Testes Unitários | JUnit 5 + Mockito | Validação da Lógica de negócio             |


### **2.2 Ferramentas**
- **Análise estática**: SonarQube IDE 
- **Testes dinâmicos**: JUnit 5, Mockito


---

## **3. Testes Unitários**

### **3.1 AvaliacaoEquipeTest**
**Objetivo**: Validar o sistema de pontuação individual de startups.

```java
public class AvaliacaoEquipeTest {
    private Startup startup;
    
    @BeforeEach
    void setUp() {
        startup = new Startup("Teste", "Slogan", 2023);
        startup.setPontos(0);
    }
    
    @Test
    void testAdicionaPontosPositivos() {
        // Testa eventos que adicionam pontos
    }
    
    @Test 
    void testSubtraiPontosNegativos() {
        // Testa eventos que removem pontos
    }
}
```

**Casos de Teste**:
1. CT-AE-01: Evento positivo (+6 pontos)
2. CT-AE-02: Evento negativo (-4 pontos)
3. CT-AE-03: Evento duplicado (deve ser bloqueado)

---

### **3.2 BatalhaTest**
**Objetivo**: Garantir o correto funcionamento das batalhas entre 2 startups.

```java
public class BatalhaTest {
    private List<Startup> startups;
    
    @Test
    void testVitoriaStartupA() {
        // Configura startup A com mais pontos
        // Verifica se A é retornada como vencedora
    }
}
```

**Casos de Teste**:
1. CT-BT-01: Vitória por pontos maiores
2. CT-BT-02: Bloqueio de reavaliação
3. CT-BT-03: Mensagens de saída corretas

---

### **3.3 RodadaTest**
**Objetivo**: Verificar o processamento de rodadas com múltiplas startups.

```java
public class RodadaTest {
    @Test
    void testRodadaNormal() {
        // 4 startups → 2 vencedores esperados
        List<Startup> startups = Arrays.asList(...);
        
        try (MockedStatic<Batalha> mock = mockStatic(Batalha.class)) {
            mock.when(() -> Batalha.batalha(any()))
                .thenReturn(startups.get(0));
                
            List<Startup> vencedores = Rodada.rodada(startups, new ArrayList<>());
            assertEquals(2, vencedores.size());
        }
    }
}
```

**Casos de Teste**:
1. CT-RD-01: Rodada com 4 startups (2 vencedores)
---

## **4. Requisitos Técnicos**

### **4.1 Dependências**
```xml
<dependencies>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>RELEASE</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-junit-jupiter</artifactId>
        <version>5.17.0</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```


## **5. Aprovação**

| Nome          | Papel | Assinatura | Data  |
|---------------|-------|------------|-------|
| João Demetrio | Garantia de Qualidade | João FHD   | 13/06 |
| João Demetrio   | Desenvolvimento | João FHD   | 13/06 |




