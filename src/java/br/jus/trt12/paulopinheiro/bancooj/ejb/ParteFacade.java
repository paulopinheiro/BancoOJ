package br.jus.trt12.paulopinheiro.bancooj.ejb;

import br.jus.trt12.paulopinheiro.bancooj.exceptions.DadosInvalidosException;
import br.jus.trt12.paulopinheiro.bancooj.model.Bairro;
import br.jus.trt12.paulopinheiro.bancooj.model.Cidade;
import br.jus.trt12.paulopinheiro.bancooj.model.Historico;
import br.jus.trt12.paulopinheiro.bancooj.model.Jurisdicao;
import br.jus.trt12.paulopinheiro.bancooj.model.Parte;
import br.jus.trt12.paulopinheiro.cpfcnpjutil.Validador;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ParteFacade extends AbstractFacade<Parte> {
    @PersistenceContext(unitName = "BancoOJ-PU")
    private EntityManager em;

    public void salvar(Parte parte) throws DadosInvalidosException, Exception {
        if (parte != null) {
            if ((parte.getNome()==null)||(parte.getNome().isEmpty())) throw new DadosInvalidosException("Informe o nome da parte");
            if (parte.getBairro()==null) throw new DadosInvalidosException("Informe ao menos o bairro do endereço da parte");
            if ((parte.getCpfCnpj()!=null)&& !Validador.isCpfOuCnpjValido(parte.getCpfCnpj())) throw new DadosInvalidosException("CPF/CNPJ inválido");
            parte.setNome(parte.getNome().toUpperCase());
            if (parte.getCodigo()==null) super.create(parte);
            else super.edit(parte);
        }
    }

    public void salvarHistorico(Historico historico) throws DadosInvalidosException {
        if (!(historico==null)) {
            if (historico.getDatahistorico()==null) throw new DadosInvalidosException("Informe a data do historico");
            if ((historico.getDescricao()==null)||(historico.getDescricao().isEmpty())) throw new DadosInvalidosException("Informe a descrição do histórico");
            if (historico.getOficial()==null) throw new DadosInvalidosException("Não foi informado o oficial de justiça responsável pela informação");
            if (historico.getParte()==null) throw new DadosInvalidosException("Não foi informada a parte à qual o histórico se refere");

            if (historico.getCodigo()==null) {
                getEntityManager().persist(historico);
                getEntityManager().getEntityManagerFactory().getCache().evict(Parte.class);
            }
            else getEntityManager().merge(historico);
        }
    }

    public void excluirHistorico(Historico historico) throws DadosInvalidosException {
        if (!(historico==null)) {
            getEntityManager().remove(getEntityManager().merge(historico));
            getEntityManager().getEntityManagerFactory().getCache().evict(Parte.class);
        }
    }

    public List<Historico> refreshListaHistorico (Parte parte)  {
        List<Historico> resposta;
        Query pesquisa = getEntityManager().createNamedQuery("Historico.findByParte");
        pesquisa.setParameter("parte", parte);
        resposta = pesquisa.getResultList();
        if (!(resposta==null)) Collections.sort(resposta);
        return resposta;
    }

    public List<Parte> findFiltro(Jurisdicao jurisdicao, Cidade cidade, Bairro bairro, String nome) {
        List<Parte> resposta;
        String p_nome;
        Query pesquisa;

        if ((nome==null)||(nome.trim().isEmpty())) p_nome="%";
        else p_nome=nome;

        if (bairro!=null) {
            pesquisa = getEntityManager().createNamedQuery("Parte.findByBairroNome");
            pesquisa.setParameter("bairro", bairro);
        } else {
            if (cidade!=null) {
                pesquisa = getEntityManager().createNamedQuery("Parte.findByCidadeNome");
                pesquisa.setParameter("cidade", cidade);
            } else {
                if (jurisdicao!=null) {
                    pesquisa = getEntityManager().createNamedQuery("Parte.findByJurisdicaoNome");
                    pesquisa.setParameter("jurisdicao", jurisdicao);
                } else {
                    pesquisa = getEntityManager().createNamedQuery("Parte.findByNome");
                }
            }
        }
        pesquisa.setParameter("nome_maiusculo", p_nome.toUpperCase());
        resposta = pesquisa.getResultList();

        if (resposta!=null) Collections.sort(resposta);

        return resposta;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParteFacade() {
        super(Parte.class);
    }

    public List<Parte> findFiltroCpfCnpj(String cpfCnpj) {
        List<Parte> resposta;
        Query pesquisa;
        String p_cpfCnpj;

        if ((cpfCnpj==null)||(cpfCnpj.trim().isEmpty())) p_cpfCnpj="%";
        else p_cpfCnpj = cpfCnpj;

        pesquisa = getEntityManager().createNamedQuery("Parte.findByCpfCnpj");
        pesquisa.setParameter("cpfCnpj", p_cpfCnpj);

        resposta = pesquisa.getResultList();
        return resposta;
    }
}
