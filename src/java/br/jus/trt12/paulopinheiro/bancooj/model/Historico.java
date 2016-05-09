package br.jus.trt12.paulopinheiro.bancooj.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "historico", catalog = "bancooj", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Historico.findByParte", query = "SELECT h FROM Historico h WHERE h.parte = :parte")
})
public class Historico implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="Historico_Gen",sequenceName="historico_codigo_seq",allocationSize=1)
    @GeneratedValue(generator="Historico_Gen")
    private Long codigo;
    private String descricao;
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    private Date datahistorico;
    private String observacao;
    @JoinColumn(name = "cod_parte", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Parte parte;
    @JoinColumn(name = "cod_oficial_justica", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Oficial oficial;

    public Historico() {}

    public Historico(String descricao, Date datahistorico, Oficial oficial, Parte parte) {
        this.descricao = descricao;
        this.datahistorico = datahistorico;
        this.oficial = oficial;
        this.parte = parte;
    }

    public Historico(Parte parte, Oficial oficial) {
        this.parte = parte;
        this.oficial = oficial;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDatahistorico() {
        return datahistorico;
    }

    public void setDatahistorico(Date datahistorico) {
        this.datahistorico = datahistorico;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Parte getParte() {
        return parte;
    }

    public void setParte(Parte parte) {
        this.parte = parte;
    }

    public Oficial getOficial() {
        return oficial;
    }

    public void setOficial(Oficial oficial) {
        this.oficial = oficial;
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
        if (!(object instanceof Historico)) {
            return false;
        }
        Historico other = (Historico) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.descricao;
    }

    @Override
    public int compareTo(Object o) {
        Historico outro = (Historico) o;
        return this.datahistorico.compareTo(outro.datahistorico);
    }
}
