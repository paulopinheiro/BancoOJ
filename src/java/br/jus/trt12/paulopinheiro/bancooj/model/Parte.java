package br.jus.trt12.paulopinheiro.bancooj.model;

import java.io.Serializable;
import java.text.Collator;
import java.util.List;
import java.util.Locale;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "parte", catalog = "bancooj", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Parte.findAll", query = "SELECT p FROM Parte p"),
    @NamedQuery(name = "Parte.findByNome", query = "SELECT p FROM Parte p WHERE UPPER(p.nome) LIKE :nome_maiusculo"),
    @NamedQuery(name = "Parte.findByBairroNome", query = "SELECT p FROM Parte p WHERE p.bairro = :bairro AND p.nome LIKE :nome_maiusculo"),
    @NamedQuery(name = "Parte.findByCidadeNome", query = "SELECT p FROM Parte p WHERE p.bairro.cidade = :cidade AND p.nome LIKE :nome_maiusculo"),
    @NamedQuery(name = "Parte.findByJurisdicaoNome", query = "SELECT p FROM Parte p WHERE p.bairro.cidade.jurisdicao = :jurisdicao AND UPPER(p.nome) LIKE :nome_maiusculo"),
    @NamedQuery(name = "Parte.findByCpfCnpj", query = "SELECT p FROM Parte p WHERE p.cpfCnpj LIKE :cpfCnpj")
})
public class Parte implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="Parte_Gen",sequenceName="parte_codigo_seq",allocationSize=1)
    @GeneratedValue(generator="Parte_Gen")
    private Long codigo;
    private String nome;
    private String logradouro;
    private String numero;
    private String complemento;
    private String telefone;
    @Column(name = "contato_responsavel")
    private String contatoResponsavel;
    private String observacao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parte")
    private List<Historico> listaHistorico;
    @JoinColumn(name = "cod_bairro", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Bairro bairro;
    @Column(name="cpf_cnpj")
    private String cpfCnpj;

    public Parte() {}

    public Parte(String nome, Bairro bairro) {
        this.nome = nome;
        this.bairro = bairro;
    }

    public String getEndereco() {
        String resposta= "";
        if (this.getLogradouro()!=null) resposta = resposta + this.getLogradouro() + " - ";
        if (this.getNumero()!=null) resposta = resposta + this.getNumero() + " - ";
        if (this.getComplemento()!=null) resposta = resposta + this.getComplemento() + " - ";
        resposta = resposta + this.getBairro().toString();
        return resposta;
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

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getContatoResponsavel() {
        return contatoResponsavel;
    }

    public void setContatoResponsavel(String contatoResponsavel) {
        this.contatoResponsavel = contatoResponsavel;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public List<Historico> getListaHistorico() {
        return listaHistorico;
    }

    public void setListaHistorico(List<Historico> listaHistorico) {
        this.listaHistorico = listaHistorico;
    }

    public Bairro getBairro() {
        return bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
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
        if (!(object instanceof Parte)) {
            return false;
        }
        Parte other = (Parte) object;
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
        Parte outro = (Parte) o;
        Collator col = Collator.getInstance(Locale.getDefault());
        if ((this.getNome()==null)||(outro.getNome()==null)) return 0;
        return col.compare(this.getNome().toUpperCase(), outro.getNome().toUpperCase());
    }
    
}
