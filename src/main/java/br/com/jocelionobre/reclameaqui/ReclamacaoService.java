
package br.com.jocelionobre.reclameaqui;

import br.com.jocelionobre.reclameaqui.infrasctructure.client.TitleGeneratorClient;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;


@ApplicationScoped
public class ReclamacaoService {

    @Inject
    @RestClient
    TitleGeneratorClient titleGeneratorClient;

    public List<Reclamacao> listar(String filtro, int pagina, int tamanhoPagina) {
        if (filtro != null && !filtro.isEmpty()) {
            return Reclamacao.find("lower(title) like lower(?1) or lower(description) like lower(?1)", "%" + filtro + "%")
                    .page(Page.of(pagina, tamanhoPagina)).list();
        }
        return Reclamacao.findAll().page(Page.of(pagina, tamanhoPagina)).list();
    }

    public Reclamacao buscar(Long id) {
        return Reclamacao.findById(id);
    }

    @Transactional
    public void criar(Reclamacao reclamacao) {
        if (reclamacao.getTitle() == null || reclamacao.getTitle().isBlank()) {
            List<String> response = titleGeneratorClient.generate("all-meat", 1, 1);
            if (response != null && !response.isEmpty()) {
                reclamacao.setTitle(response.get(0));
            }
        }
        Reclamacao.persist(reclamacao);
    }

    @Transactional
    public Reclamacao atualizar(Long id, Reclamacao reclamacao) {
        Reclamacao existente = buscar(id);
        if (existente != null) {
            existente.setTitle(reclamacao.getTitle());
            existente.setDescription(reclamacao.getDescription());
            existente.setLocale(reclamacao.getLocale());
            existente.setCompany(reclamacao.getCompany());
        }
        return existente;
    }

    @Transactional
    public void deletar(Long id) {
        Reclamacao.deleteById(id);
    }
}

