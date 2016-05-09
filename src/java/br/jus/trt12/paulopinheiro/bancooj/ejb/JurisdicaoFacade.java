package br.jus.trt12.paulopinheiro.bancooj.ejb;

import br.jus.trt12.paulopinheiro.bancooj.exceptions.DadosInvalidosException;
import br.jus.trt12.paulopinheiro.bancooj.model.Jurisdicao;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class JurisdicaoFacade extends AbstractFacade<Jurisdicao> {
    @PersistenceContext(unitName = "BancoOJ-PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public JurisdicaoFacade() {
        super(Jurisdicao.class);
    }

    public void salvar(Jurisdicao jurisdicao) throws DadosInvalidosException {
        if (jurisdicao != null) {
            if ((jurisdicao.getNome()==null)||(jurisdicao.getNome().isEmpty())) throw new DadosInvalidosException("Informe o nome da Jurisprudência");

            if (jurisdicao.getCodigo()==null) super.create(jurisdicao);
            else super.edit(jurisdicao);
        }
    }
    
}
