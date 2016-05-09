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
@Table(name = "cidade", catalog = "bancooj", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Cidade.findAll", query = "SELECT c FROM Cidade c"),
    @NamedQuery(name = "Cidade.findByJurisdicao", query = "SELECT c FROM Cidade c where c.jurisdicao = :jurisdicao")
})
public class Cidade implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="Cidade_Gen",sequenceName="cidade_codigo_seq",allocationSize=1)
    @GeneratedValue(generator="Cidade_Gen")
    private Long codigo;
    private String nome;
    private String uf;
    @JoinColumn(name = "cod_jurisdicao", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Jurisdicao jurisdicao;

    public Cidade() {}

    public Cidade(String nome, String uf, Jurisdicao jurisdicao) {
        this.nome = nome;
        this.uf = uf;
        this.jurisdicao = jurisdicao;
    }

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

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Jurisdicao getJurisdicao() {
        return jurisdicao;
    }

    public void setJurisdicao(Jurisdicao jurisdicao) {
        this.jurisdicao = jurisdicao;
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
        if (!(object instanceof Cidade)) {
            return false;
        }
        Cidade other = (Cidade) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nome + "-" + this.uf;
    }

    @Override
    public int compareTo(Object o) {
        Cidade outro = (Cidade) o;
        Collator col = Collator.getInstance(Locale.getDefault());
        if ((this.getNome()==null)||(outro.getNome()==null)) return 0;
        return col.compare(this.getNome().toUpperCase(), outro.getNome().toUpperCase());
    }
}
