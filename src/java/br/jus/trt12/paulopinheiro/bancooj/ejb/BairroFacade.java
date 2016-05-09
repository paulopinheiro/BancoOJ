/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt12.paulopinheiro.bancooj.ejb;

import br.jus.trt12.paulopinheiro.bancooj.exceptions.DadosInvalidosException;
import br.jus.trt12.paulopinheiro.bancooj.model.Bairro;
import br.jus.trt12.paulopinheiro.bancooj.model.Cidade;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class BairroFacade extends AbstractFacade<Bairro> {
    @PersistenceContext(unitName = "BancoOJ-PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BairroFacade() {
        super(Bairro.class);
    }

    public List<Bairro> findByCidade(Cidade cidade) {
        List<Bairro> resposta = null;
        if (cidade==null) return null;

        Query query = getEntityManager().createNamedQuery("Bairro.findByCidade");
        query.setParameter("cidade", cidade);

        resposta = query.getResultList();
        if (resposta!=null) Collections.sort(resposta);
        return resposta;
    }

    public void salvar(Bairro bairro) throws DadosInvalidosException {
        if (bairro != null) {
            if ((bairro.getNome()==null)||(bairro.getNome().isEmpty())) throw new DadosInvalidosException("Informe o nome do bairro");
            if (bairro.getCidade()==null) throw new DadosInvalidosException("Informe a cidade à qual pertence o bairro");

            if (bairro.getCodigo()==null) super.create(bairro);
            else super.edit(bairro);
        }
    }
}
