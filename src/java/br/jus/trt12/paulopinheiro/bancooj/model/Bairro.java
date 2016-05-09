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
@Table(name = "bairro", catalog = "bancooj", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Bairro.findAll", query = "SELECT b FROM Bairro b"),
    @NamedQuery(name = "Bairro.findByCidade", query = "SELECT b FROM Bairro b WHERE b.cidade = :cidade")
})
public class Bairro implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="Bairro_Gen",sequenceName="bairro_codigo_seq",allocationSize=1)
    @GeneratedValue(generator="Bairro_Gen")
    private Long codigo;
    private String nome;
    private String observacao;
    @JoinColumn(name = "cod_cidade", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Cidade cidade;

    public Bairro() {}

    public Bairro(String nome, Cidade cidade) {
        this.nome = nome;
        this.cidade = cidade;
    }

    public Bairro(Cidade cidade) {
        this.cidade = cidade;
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

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
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
        if (!(object instanceof Bairro)) {
            return false;
        }
        Bairro other = (Bairro) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nome + " - " + this.cidade;
    }

    @Override
    public int compareTo(Object o) {
        Bairro outro = (Bairro) o;
        Collator col = Collator.getInstance(Locale.getDefault());
        if ((this.getNome()==null)||(outro.getNome()==null)) return 0;
        return col.compare(this.getNome().toUpperCase(), outro.getNome().toUpperCase());
    }
    
}
