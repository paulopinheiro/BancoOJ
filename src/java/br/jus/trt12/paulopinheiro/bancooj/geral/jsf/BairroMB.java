package br.jus.trt12.paulopinheiro.bancooj.geral.jsf;

import br.jus.trt12.paulopinheiro.bancooj.ejb.BairroFacade;
import br.jus.trt12.paulopinheiro.bancooj.exceptions.DadosInvalidosException;
import br.jus.trt12.paulopinheiro.bancooj.model.Bairro;
import br.jus.trt12.paulopinheiro.bancooj.model.Cidade;
import br.jus.trt12.paulopinheiro.bancooj.util.ContextoJSF;
import java.io.Serializable;
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
public class BairroMB implements Serializable {
    private Cidade cidade;

    @EJB private BairroFacade bairroFacade;

    private Bairro bairro;
    private List<Bairro> listaBairros;
    
    public BairroMB() {}

    public boolean isNovoBairro() {
        return (this.getBairro().getCodigo()==null)||(this.getBairro().getCodigo()==0);
    }

    public void salvar(ActionEvent evt) {
        try {
            bairroFacade.salvar(this.getBairro());
            mensagemSucesso("Bairro salvo com sucesso");
            setBairro(null);
            setListaBairros(null);
        } catch (DadosInvalidosException ex) {
            mensagemErro(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger("BairroMB.class").log(Level.SEVERE, ex.getMessage(), ex);
            mensagemErro(ex.getMessage());
        }
    }

    public void novo(ActionEvent evt) {
        setBairro(null);
    }

    public void excluir(ActionEvent evt) {
        try {
            bairroFacade.remove(this.getBairro());
            mensagemSucesso("Bairro salvo com sucesso");
            setBairro(null);
            setListaBairros(null);
        } catch (Exception ex) {
            Logger.getLogger("BairroMB.class").log(Level.SEVERE, ex.getMessage(), ex);
            mensagemErro(ex.getMessage());
        }
    }

    public Bairro getBairro() {
        if (this.bairro==null) this.bairro = new Bairro(getCidade());
        return bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public int getQuantBairros() {
        if (this.getListaBairros()==null) return 0;
        return this.getListaBairros().size();
    }

    public List<Bairro> getListaBairros() {
        if (this.listaBairros==null) this.listaBairros=this.bairroFacade.findByCidade(getCidade());

        return listaBairros;
    }

    public void setListaBairros(List<Bairro> listaBairros) {
        this.listaBairros = listaBairros;
    }

    private void mensagemErro(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,mensagem,null));
        System.out.println(mensagem);
    }

    private void mensagemSucesso (String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,mensagem,null));
    }
}
