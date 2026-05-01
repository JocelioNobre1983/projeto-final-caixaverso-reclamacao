package br.com.jocelionobre.reclameaqui;

import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;

@ApplicationScoped
public class ReclamacaoService {

    public List<Reclamacao> listar(String filtro, int pagina, int tamanhoPagina) {
        if (filtro != null && !filtro.isEmpty()) {
            return Reclamacao.find("lower(descricao) like lower(?1) or lower(cliente) like lower(?1)", "%" + filtro + "%")
                    .page(Page.of(pagina, tamanhoPagina)).list();
        }
        return Reclamacao.findAll().page(Page.of(pagina, tamanhoPagina)).list();
    }

    public Reclamacao buscar(Long id) {
        return Reclamacao.findById(id);
    }

    @Transactional
    public Reclamacao registrarReclamacao(Reclamacao reclamacao) {
        if (reclamacao.getDescricao() == null || reclamacao.getDescricao().isBlank()) {
            throw new IllegalArgumentException("Descrição não pode ser vazia");
        }
        Reclamacao.persist(reclamacao);
        return reclamacao;
    }

    @Transactional
    public Reclamacao atualizarStatus(Long id, Status novoStatus) {
        Reclamacao existente = buscar(id);
        if (existente != null) {
            if (novoStatus == Status.EM_ANALISE && existente.getStatus() == Status.ABERTA) {
                existente.setStatus(Status.EM_ANALISE);
            } else if (novoStatus == Status.RESOLVIDA && existente.getStatus() == Status.EM_ANALISE) {
                existente.setStatus(Status.RESOLVIDA);
            } else {
                throw new IllegalStateException("Transição de status inválida");
            }
        }
        return existente;
    }

    public void criar(@Valid Reclamacao reclamacao) {
    }

    public Reclamacao atualizar(Long id, Reclamacao reclamacao) {
        return reclamacao;
    }

    public void deletar(Long id) {
    }
}
