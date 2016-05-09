package br.jus.trt12.paulopinheiro.bancooj.geral.jsf;

import br.jus.trt12.paulopinheiro.bancooj.ejb.JurisdicaoFacade;
import br.jus.trt12.paulopinheiro.bancooj.ejb.OficialFacade;
import br.jus.trt12.paulopinheiro.bancooj.exceptions.DadosInvalidosException;
import br.jus.trt12.paulopinheiro.bancooj.model.Jurisdicao;
import br.jus.trt12.paulopinheiro.bancooj.model.Oficial;
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

@ManagedBean
@ViewScoped
public class OficialMB implements Serializable {
    @EJB private OficialFacade oficialFacade;
    @EJB private JurisdicaoFacade jurisdicaoFacade;

    private List<Oficial> listaOficiais;
    private Oficial oficial;

    private List<Jurisdicao> listaJurisdicoes;

    public OficialMB() {}

    public boolean isNovoOficial() {
        return (this.getOficial().getCodigo()==null)||(this.getOficial().getCodigo()==0);
    }

    public void salvar(ActionEvent evt) {
        try {
            oficialFacade.salvar(this.getOficial());
            mensagemSucesso("Usuário salvo com sucesso");
            setOficial(null);
            setListaOficiais(null);
        } catch (DadosInvalidosException ex) {
            this.mensagemErro(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger("OficialMB.class").log(Level.SEVERE, ex.getMessage(), ex);
            mensagemErro(ex.getMessage());
        }
    }

    public void novo(ActionEvent evt) {
        setOficial(null);
    }

    public void excluir(ActionEvent evt) {
        try {
            oficialFacade.remove(this.getOficial());
            mensagemSucesso("Usuário excluído com sucesso");
            setOficial(null);
            setListaOficiais(null);
        } catch (Exception ex) {
            Logger.getLogger("OficialMB.class").log(Level.SEVERE, ex.getMessage(), ex);
            mensagemErro(ex.getMessage());
        }
    }

    public int getQuantOficiais() {
        if (this.getListaOficiais()==null) return 0;
        return this.getListaOficiais().size();
    }

    private void mensagemErro(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,mensagem,null));
        System.out.println(mensagem);
    }

    private void mensagemSucesso (String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,mensagem,null));
    }

    public List<Oficial> getListaOficiais() {
        if (this.listaOficiais==null) {
            this.listaOficiais=oficialFacade.findAll();
            Collections.sort(this.listaOficiais);
        }
        return listaOficiais;
    }

    public void setListaOficiais(List<Oficial> listaOficiais) {
        if (this.listaOficiais==null) this.listaOficiais = oficialFacade.findAll();
        this.listaOficiais = listaOficiais;
    }

    public Oficial getOficial() {
        if (this.oficial==null) this.oficial = new Oficial();
        return oficial;
    }

    public void setOficial(Oficial oficial) {
        this.oficial = oficial;
    }

    public List<Jurisdicao> getListaJurisdicoes() {
        if (this.listaJurisdicoes==null) this.listaJurisdicoes=jurisdicaoFacade.findAll();
        return listaJurisdicoes;
    }
}
