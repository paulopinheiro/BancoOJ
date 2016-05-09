package br.jus.trt12.paulopinheiro.bancooj.ejb;

import br.jus.trt12.paulopinheiro.bancooj.exceptions.DadosInvalidosException;
import br.jus.trt12.paulopinheiro.bancooj.model.Cidade;
import br.jus.trt12.paulopinheiro.bancooj.model.Jurisdicao;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class CidadeFacade extends AbstractFacade<Cidade> {
    @PersistenceContext(unitName = "BancoOJ-PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CidadeFacade() {
        super(Cidade.class);
    }

    public List<Cidade> findByJurisdicao(Jurisdicao jurisdicao) {
        List<Cidade> resposta = null;
        if (jurisdicao==null) return null;

        Query query = getEntityManager().createNamedQuery("Cidade.findByJurisdicao");
        query.setParameter("jurisdicao", jurisdicao);

        resposta = query.getResultList();
        if (resposta!=null) Collections.sort(resposta);
        return resposta;
    }

    public void salvar(Cidade cidade) throws DadosInvalidosException {
        if (cidade != null) {
            if ((cidade.getNome()==null)||(cidade.getNome().isEmpty())) throw new DadosInvalidosException("Informe o nome da cidade");
            if ((cidade.getUf()==null)||(cidade.getUf().isEmpty())) throw new DadosInvalidosException("Informe a UF da cidade");
            if (cidade.getJurisdicao()==null) throw new DadosInvalidosException("Informe a jurisdição à qual pertence a cidade");

            if (cidade.getCodigo()==null) super.create(cidade);
            else super.edit(cidade);
        }
    }
}
