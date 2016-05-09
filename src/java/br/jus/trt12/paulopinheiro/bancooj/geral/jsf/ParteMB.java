package br.jus.trt12.paulopinheiro.bancooj.geral.jsf;

import br.jus.trt12.paulopinheiro.bancooj.ejb.BairroFacade;
import br.jus.trt12.paulopinheiro.bancooj.ejb.CidadeFacade;
import br.jus.trt12.paulopinheiro.bancooj.ejb.ParteFacade;
import br.jus.trt12.paulopinheiro.bancooj.exceptions.DadosInvalidosException;
import br.jus.trt12.paulopinheiro.bancooj.model.Bairro;
import br.jus.trt12.paulopinheiro.bancooj.model.Cidade;
import br.jus.trt12.paulopinheiro.bancooj.model.Historico;
import br.jus.trt12.paulopinheiro.bancooj.model.Oficial;
import br.jus.trt12.paulopinheiro.bancooj.model.Parte;
import br.jus.trt12.paulopinheiro.bancooj.util.ContextoJSF;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

@ManagedBean
@ViewScoped
public class ParteMB implements Serializable {
    private Parte parte;

    @EJB private ParteFacade parteFacade;
    @EJB private BairroFacade bairroFacade;
    @EJB private CidadeFacade cidadeFacade;
    @Inject SessaoMB sessaoMB;

    private List<Cidade> cidades;
    private List<Bairro> bairros;
    private Cidade cidade;

    private String cpf_cnpj_exemplo;


    private Oficial oficial;

    private Historico historico;

    public ParteMB() {}

    public boolean isNovaParte() {
        return (this.getParte().getCodigo()==null)||(this.getParte().getCodigo()==0);
    }

    public void salvar(ActionEvent evt) {
        try {
            parteFacade.salvar(this.getParte());
            mensagemSucesso("Parte salva com sucesso");
        } catch (DadosInvalidosException ex) {
            this.mensagemErro(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger("ParteMB.class").log(Level.SEVERE, ex.getMessage(), ex);
            this.mensagemErro(ex.getMessage());
        }
    }

    public void nova(ActionEvent evt) {
        try {
            setParte(null);
            setHistorico(null);
        } catch (Exception ex) {
            Logger.getLogger("ParteMB.class").log(Level.SEVERE, ex.getMessage(), ex);
            this.mensagemErro(ex.getMessage());
        }
    }

    public void excluir(ActionEvent evt) {
        try {
            parteFacade.remove(this.getParte());
            setParte(null);
            mensagemSucesso("Parte excluída com sucesso");
        } catch (Exception ex) {
            Logger.getLogger("ParteMB.class").log(Level.SEVERE, ex.getMessage(), ex);
            this.mensagemErro(ex.getMessage());
        }
    }

    public void salvarHistorico(ActionEvent evt) {
        try {
            parteFacade.salvarHistorico(this.getHistorico());
            mensagemSucesso("Histórico salvo com sucesso");
            setHistorico(null);
            refreshListaHistoricos();
        } catch (DadosInvalidosException ex) {
            this.mensagemErro(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger("ParteMB.class").log(Level.SEVERE, ex.getMessage(), ex);
            mensagemErro(ex.getMessage());
        }
    }

    public void novoHistorico(ActionEvent evt) {
        setHistorico(null);
    }

    public void excluirHistorico(ActionEvent evt) {
        try {
            parteFacade.excluirHistorico(this.getHistorico());
            mensagemSucesso("Histórico excluído com sucesso");
            setHistorico(null);
            refreshListaHistoricos();
        } catch (DadosInvalidosException ex) {
            this.mensagemErro(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger("ParteMB.class").log(Level.SEVERE, ex.getMessage(), ex);
            mensagemErro(ex.getMessage());
        }
    }

    private void refreshListaHistoricos() {
        if (!(this.parte==null)) {
            this.parte.setListaHistorico(parteFacade.refreshListaHistorico(this.parte));
        }
    }

    public boolean isNovoHistoricoB() {
        return (this.getHistorico().getCodigo()==null)||(this.getHistorico().getCodigo()==0);
    }
    public Parte getParte() {
        if (this.parte==null) this.parte=new Parte();
        return parte;
    }

    public void setParte(Parte parte) {
        this.parte = parte;
        if ((this.parte!=null) && (this.parte.getBairro()!=null)) setCidade(this.parte.getBairro().getCidade());
    }

    private void mensagemErro(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,mensagem,null));
        System.out.println(mensagem);
    }

    private void mensagemSucesso (String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,mensagem,null));
    }

    public List<Bairro> getBairros() {
        if (this.bairros== null) this.bairros=bairroFacade.findByCidade(getCidade());
        return this.bairros;
    }

    private Oficial getOficial() {
        if (this.oficial==null) this.oficial = sessaoMB.getOficialSessao();
        return this.oficial;
    }

    public Cidade getCidade() {
        if (this.cidade==null) this.cidade = new Cidade();
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public List<Cidade> getCidades() {
        if (this.cidades==null) this.cidades = cidadeFacade.findAll();
        return cidades;
    }

    public Historico getHistorico() {
        if (this.historico==null) {
            this.historico = new Historico(getParte(),getOficial());
            this.historico.setDatahistorico(new Date());
        }
        return historico;
    }

    public void setHistorico(Historico historico) {
        this.historico = historico;
    }

    public void alteracaoCidade(AjaxBehaviorEvent evt) {
        this.bairros=null;
    }

    public String getCpf_cnpj_exemplo() {
        return cpf_cnpj_exemplo;
    }

    public void setCpf_cnpj_exemplo(String cpf_cnpj_exemplo) {
        this.cpf_cnpj_exemplo = cpf_cnpj_exemplo;
    }

}
