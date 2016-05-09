package br.jus.trt12.paulopinheiro.bancooj.geral.jsf;

import br.jus.trt12.paulopinheiro.bancooj.ejb.CidadeFacade;
import br.jus.trt12.paulopinheiro.bancooj.ejb.JurisdicaoFacade;
import br.jus.trt12.paulopinheiro.bancooj.exceptions.DadosInvalidosException;
import br.jus.trt12.paulopinheiro.bancooj.model.Cidade;
import br.jus.trt12.paulopinheiro.bancooj.model.Jurisdicao;
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
public class CidadeMB implements Serializable {
    @EJB private CidadeFacade cidadeFacade;
    @EJB private JurisdicaoFacade jurisdicaoFacade;

    private List<Cidade> listaCidades;
    private Cidade cidade;

    private List<Jurisdicao> listaJurisdicoes;

    public CidadeMB() {}

    public boolean isNovaCidade() {
        return (this.getCidade().getCodigo()==null)||(this.getCidade().getCodigo()==0);
    }

    public void salvar(ActionEvent evt) {
        try {
            cidadeFacade.salvar(this.getCidade());
            mensagemSucesso("Cidade salva com sucesso");
            setCidade(null);
            setListaCidades(null);
        } catch (DadosInvalidosException ex) {
            mensagemErro(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger("CidadeMB.class").log(Level.SEVERE, ex.getMessage(), ex);
            mensagemErro(ex.getMessage());
        }
    }

    public void nova(ActionEvent evt) {
        setCidade(null);
    }

    public void excluir(ActionEvent evt) {
        try {
            cidadeFacade.remove(this.getCidade());
            mensagemSucesso("Cidade excluída com sucesso");
            setCidade(null);
            setListaCidades(null);
        } catch (Exception ex) {
            Logger.getLogger("CidadeMB.class").log(Level.SEVERE, ex.getMessage(), ex);
            mensagemErro(ex.getMessage());
        }
    }

    public int getQuantCidades() {
        if (this.getListaCidades()==null) return 0;
        return this.getListaCidades().size();
    }

    public List<Cidade> getListaCidades() {
        if (this.listaCidades==null) {
            this.listaCidades = cidadeFacade.findAll();
            Collections.sort(this.listaCidades);
        }
        return listaCidades;
    }

    public void setListaCidades(List<Cidade> listaCidades) {
        this.listaCidades = listaCidades;
    }

    public Cidade getCidade() {
        if (this.cidade==null) this.cidade = new Cidade();
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    private void mensagemErro(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,mensagem,null));
        System.out.println(mensagem);
    }

    private void mensagemSucesso (String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,mensagem,null));
    }

    public List<Jurisdicao> getListaJurisdicoes() {
        if (this.listaJurisdicoes==null) this.listaJurisdicoes=jurisdicaoFacade.findAll();
        return listaJurisdicoes;
    }
}
