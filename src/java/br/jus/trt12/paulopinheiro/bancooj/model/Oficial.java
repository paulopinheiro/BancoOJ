package br.jus.trt12.paulopinheiro.bancooj.model;

import java.io.Serializable;
import java.text.Collator;
import java.util.Locale;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "oficial", catalog = "bancooj", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Oficial.findAll", query = "SELECT o FROM Oficial o"),
    @NamedQuery(name = "Oficial.findByJurisdicao", query = "SELECT o FROM Oficial o WHERE o.jurisdicao = :jurisdicao"),
    @NamedQuery(name = "Oficial.findAtivos", query = "SELECT o FROM Oficial o WHERE o.ativo = true"),
})
public class Oficial implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="Oficial_Gen",sequenceName="oficial_codigo_seq",allocationSize=1)
    @GeneratedValue(generator="Oficial_Gen")
    private Long codigo;
    private String nome;
    private boolean ativo;
    private String matricula;
    @JoinColumn(name = "cod_jurisdicao", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Jurisdicao jurisdicao;
    private boolean administrador;

    public Oficial() {}

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Jurisdicao getJurisdicao() {
        return jurisdicao;
    }

    public void setJurisdicao(Jurisdicao jurisdicao) {
        this.jurisdicao = jurisdicao;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Oficial)) {
            return false;
        }
        Oficial other = (Oficial) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nome + " - " + this.matricula;
    }

    @Override
    public int compareTo(Object o) {
        Oficial outro = (Oficial) o;
        Collator col = Collator.getInstance(Locale.getDefault());
        if ((this.getNome()==null)||(outro.getNome()==null)) return 0;
        return col.compare(this.getNome().toUpperCase(), outro.getNome().toUpperCase());
    }
}
