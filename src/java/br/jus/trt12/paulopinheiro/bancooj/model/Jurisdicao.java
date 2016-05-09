package br.jus.trt12.paulopinheiro.bancooj.model;

import java.io.Serializable;
import java.text.Collator;
import java.util.Locale;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "jurisdicao", catalog = "bancooj", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Jurisdicao.findAll", query = "SELECT j FROM Jurisdicao j")
})
public class Jurisdicao implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="Jurisdicao_Gen",sequenceName="jurisdicao_codigo_seq",allocationSize=1)
    @GeneratedValue(generator="Jurisdicao_Gen")
    private Integer codigo;
    private String nome;

    public Jurisdicao() {}

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
        if (!(object instanceof Jurisdicao)) {
            return false;
        }
        Jurisdicao other = (Jurisdicao) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nome;
    }

    @Override
    public int compareTo(Object o) {
        Jurisdicao outro = (Jurisdicao) o;
        Collator col = Collator.getInstance(Locale.getDefault());
        if ((this.getNome()==null)||(outro.getNome()==null)) return 0;
        return col.compare(this.getNome().toUpperCase(), outro.getNome().toUpperCase());
    }
}
