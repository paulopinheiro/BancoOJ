package br.jus.trt12.paulopinheiro.bancooj.geral.jsf;

import br.jus.trt12.paulopinheiro.bancooj.ejb.OficialFacade;
import br.jus.trt12.paulopinheiro.bancooj.exceptions.DadosInvalidosException;
import br.jus.trt12.paulopinheiro.bancooj.model.Oficial;
import br.jus.trt12.paulopinheiro.bancooj.util.ContextoJSF;
import br.jus.trt12.paulopinheiro.ldaputil.usuarios.Usuario;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;

@Model
public class GeralMB implements Serializable {
    @EJB OficialFacade oficialFacade;
    @Inject SessaoMB sessaoMB;

    private List<Oficial> listaOficiais;
    private Oficial oficial;
    private String senha;

    public GeralMB() {}

    public String conectar() {
        try {
            if (getOficial().getCodigo() == null) throw new DadosInvalidosException("Escolha o usuário");
            if (getSenha().trim().isEmpty()) throw new DadosInvalidosException("Digite a senha");

            Usuario usuario = new Usuario();
            usuario.autenticar(getOficial().getMatricula(), getSenha());
            if (usuario.isAutenticado()) {
                setOficialSessao(getOficial());
                return "/home.xhtml";
            }
            else throw new DadosInvalidosException("Credenciais inválidas!");
        } catch (DadosInvalidosException ex) {
            mensagemErro(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger("GeralMB.class").log(Level.SEVERE, ex.getMessage(), ex);
            mensagemErro(ex.getMessage());
        }
        return "index.xhtml";
    }

    public List<Oficial> getListaOficiais() {
        if (this.listaOficiais==null) this.listaOficiais = oficialFacade.findAtivos();
        return listaOficiais;
    }

    public void setListaOficiais(List<Oficial> listaOficiais) {
        this.listaOficiais = listaOficiais;
    }

    public Oficial getOficial() {
        if (this.oficial==null) this.oficial= new Oficial();
        return oficial;
    }

    public void setOficial(Oficial oficial) {
        this.oficial = oficial;
    }

    public String getSenha() {
        if (this.senha==null) this.senha="";
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    private void setOficialSessao(Oficial oficialSessao) {
        sessaoMB.setOficialSessao(oficialSessao);
    }

    private void mensagemErro(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,mensagem,null));
    }
}
