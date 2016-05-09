package br.jus.trt12.paulopinheiro.bancooj.ejb;

import br.jus.trt12.paulopinheiro.bancooj.exceptions.DadosInvalidosException;
import br.jus.trt12.paulopinheiro.bancooj.model.Oficial;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class OficialFacade extends AbstractFacade<Oficial> {
    @PersistenceContext(unitName = "BancoOJ-PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public void salvar(Oficial oficial) throws DadosInvalidosException {
        if (oficial != null) {
            if ((oficial.getNome()==null)||(oficial.getNome().isEmpty())) throw new DadosInvalidosException("Informe o nome do OJ usuário");
            if ((oficial.getMatricula()==null)||(oficial.getMatricula().isEmpty())) throw new DadosInvalidosException("Informe a matrícula do OJ usuário");
            if (oficial.getJurisdicao()==null) throw new DadosInvalidosException("Informe a jurisdição onde o OJ usuário atuará");

            if (oficial.getCodigo()==null) super.create(oficial);
            else super.edit(oficial);
        }
    }

    public List<Oficial> findAtivos() {
        List<Oficial> resposta;
        Query pesquisa = em.createNamedQuery("Oficial.findAtivos");
        resposta = pesquisa.getResultList();
        Collections.sort(resposta);
        return resposta;
    }

    public OficialFacade() {
        super(Oficial.class);
    }
}
