package br.jus.trt12.paulopinheiro.bancooj.geral.jsf;

import br.jus.trt12.paulopinheiro.bancooj.ejb.BairroFacade;
import br.jus.trt12.paulopinheiro.bancooj.ejb.CidadeFacade;
import br.jus.trt12.paulopinheiro.bancooj.ejb.JurisdicaoFacade;
import br.jus.trt12.paulopinheiro.bancooj.ejb.ParteFacade;
import br.jus.trt12.paulopinheiro.bancooj.model.Bairro;
import br.jus.trt12.paulopinheiro.bancooj.model.Cidade;
import br.jus.trt12.paulopinheiro.bancooj.model.Jurisdicao;
import br.jus.trt12.paulopinheiro.bancooj.model.Parte;
import br.jus.trt12.paulopinheiro.bancooj.util.ContextoJSF;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

@ManagedBean
@ViewScoped
public class ListaPartesMB implements Serializable {
    @EJB private ParteFacade parteFacade;
    @EJB private BairroFacade bairroFacade;
    @EJB private CidadeFacade cidadeFacade;
    @EJB private JurisdicaoFacade jurisdicaoFacade;

    private List<Parte> listaPartes;

    private String filtroNomeParte;
    private Jurisdicao filtroJurisdicao;
    private Cidade filtroCidade;
    private Bairro filtroBairro;
    private String cpfCnpj;

    private List<Jurisdicao> listaJurisdicoes;
    private List<Cidade> listaCidades;
    private List<Bairro> listaBairros;

    public ListaPartesMB() {}

    public void filtrar(ActionEvent evt) {
        try {
            this.listaPartes = this.parteFacade.findFiltro(filtroJurisdicao, filtroCidade, filtroBairro, filtroNomeParte);
        } catch (Exception ex) {
            Logger.getLogger("ListaPartesMB.class").log(Level.SEVERE, ex.getMessage(), ex);
            mensagemErro(ex.getMessage());
        }
    }

    public void filtrarCpfCnpj(ActionEvent evt) {
        try {
            this.listaPartes = this.parteFacade.findFiltroCpfCnpj(this.getCpfCnpj());
        } catch (Exception ex) {
            Logger.getLogger("ListaPartesMB.class").log(Level.SEVERE, ex.getMessage(), ex);
            mensagemErro(ex.getMessage());
        }
    }

    public List<Bairro> getListaBairros() {
        if (this.listaBairros== null) this.listaBairros=bairroFacade.findByCidade(getFiltroCidade());
        return this.listaBairros;
    }

    public List<Cidade> getListaCidades() {
        if (this.listaCidades==null) this.listaCidades=cidadeFacade.findByJurisdicao(getFiltroJurisdicao());
        return listaCidades;
    }

    public List<Jurisdicao> getListaJurisdicoes() {
        if (this.listaJurisdicoes==null) this.listaJurisdicoes=jurisdicaoFacade.findAll();
        return listaJurisdicoes;
    }

    public int getQuantPartes() {
        if (getListaPartes() == null) return 0;
        return getListaPartes().size();
    }

    public List<Parte> getListaPartes() {
        try {
            if (this.listaPartes == null) {
                this.listaPartes = this.parteFacade.findAll();
                Collections.sort(listaPartes);
            }
        } catch (Exception ex) {
            Logger.getLogger("ListaPartesMB.class").log(Level.SEVERE, ex.getMessage(), ex);
            mensagemErro(ex.getMessage());
        }
        return listaPartes;
    }

    public void setListaPartes(List<Parte> listaPartes) {
        this.listaPartes = listaPartes;
    }

    private void mensagemErro(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,mensagem,null));
    }

    public String getFiltroNomeParte() {
        return filtroNomeParte;
    }

    public void setFiltroNomeParte(String filtroNomeParte) {
        this.filtroNomeParte = filtroNomeParte;
    }

    public Jurisdicao getFiltroJurisdicao() {
        return filtroJurisdicao;
    }

    public void setFiltroJurisdicao(Jurisdicao filtroJurisdicao) {
        this.filtroJurisdicao = filtroJurisdicao;
    }

    public Cidade getFiltroCidade() {
        return filtroCidade;
    }

    public void setFiltroCidade(Cidade filtroCidade) {
        this.filtroCidade = filtroCidade;
    }

    public Bairro getFiltroBairro() {
        return filtroBairro;
    }

    public void setFiltroBairro(Bairro filtroBairro) {
        this.filtroBairro = filtroBairro;
    }

    public void setListaJurisdicoes(List<Jurisdicao> listaJurisdicoes) {
        this.listaJurisdicoes = listaJurisdicoes;
    }

    public void setListaCidades(List<Cidade> listaCidades) {
        this.listaCidades = listaCidades;
    }

    public void setListaBairros(List<Bairro> listaBairros) {
        this.listaBairros = listaBairros;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public void alteracaoJurisdicao(AjaxBehaviorEvent evt) {
        this.filtroCidade=null;
        this.filtroBairro=null;
        this.listaCidades=null;
        this.listaBairros=null;
    }

    public void alteracaoCidade(AjaxBehaviorEvent evt) {
        this.filtroBairro=null;
        this.listaBairros=null;
    }
}
