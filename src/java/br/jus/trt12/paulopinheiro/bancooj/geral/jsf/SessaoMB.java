package br.jus.trt12.paulopinheiro.bancooj.geral.jsf;

import br.jus.trt12.paulopinheiro.bancooj.model.Oficial;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.inject.Named;

@Named
@SessionScoped
public class SessaoMB implements Serializable {
    private Oficial oficialSessao;

    public SessaoMB() {}

    public String desconectar() {
        setOficialSessao(null);
        return "index.xhtml?faces-redirect=true";
    }

    public boolean isOficialSessaoEmpty() {
        return getOficialSessao().getCodigo()==null;
    }

    public boolean isOficialSessaoAdmin() {
        if (isOficialSessaoEmpty()) return false;
        return getOficialSessao().isAdministrador();
    }

    public Oficial getOficialSessao() {
        if (this.oficialSessao==null) this.oficialSessao=new Oficial();
        return this.oficialSessao;
    }

    public void setOficialSessao(Oficial oficialSessao) {
        this.oficialSessao = oficialSessao;
    }
}
