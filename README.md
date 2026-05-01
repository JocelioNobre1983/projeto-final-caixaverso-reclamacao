# Relatório de Atividade – Projeto Final Caixaverso Reclamação

## 1. Métodos escolhidos e justificativa

Os métodos selecionados foram:

### `registrarReclamacao(Reclamacao reclamacao)`
- Possui validação de campos obrigatórios.
- Permite cenários positivos e negativos.
- Contém regra de negócio relevante: não aceitar reclamações sem descrição.

### `atualizarStatus(Long id, Status novoStatus)`
- Implementa regras de transição de status (**ABERTA → EM_ANALISE → RESOLVIDA**).
- Possui múltiplos fluxos de execução: transição válida e transição inválida.
- É mais complexo, pois envolve lógica condicional e validação de estados.

👉 **Justificativa**: ambos os métodos possuem regras de negócio reais, não são simples delegações, e permitem múltiplos cenários de teste.

---

## 2. Cenários de teste implementados

### `registrarReclamacao`
- **Cenário positivo**: salvar reclamação com descrição válida.
- **Cenário negativo**: lançar `IllegalArgumentException` ao tentar salvar reclamação com descrição vazia.

### `atualizarStatus`
- **Cenário positivo**: atualizar status de **ABERTA → EM_ANALISE**.
- **Cenário negativo**: tentativa inválida de atualizar status **ABERTA → RESOLVIDA** lança `IllegalStateException`.

👉 Os testes foram escritos com **JUnit 5 + Quarkus Test**, utilizando **Mockito** para isolamento das dependências.

---