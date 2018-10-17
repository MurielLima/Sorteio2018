/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import static config.DAO.premioRepository;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Muriel
 */
@Document
public class Pessoa {

    @Id
    private String idPessoa;
    @Indexed(unique = true)
    private String url;
    private String nome;
    @DBRef
    private Premio idPremio;

    public Premio getIdPremio() {
        return idPremio;
    }

    public void setIdPremio(Premio idPremio) {
        this.idPremio = idPremio;
    }


    public String getPremio(){
        return idPremio.getNome();
    }
    public boolean isSorteado() {
        return (idPremio != null);
    }

    public Pessoa() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.idPessoa != null ? this.idPessoa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pessoa other = (Pessoa) obj;
        if ((this.idPessoa == null) ? (other.idPessoa != null) : !this.idPessoa.equals(other.idPessoa)) {
            return false;
        }
        return true;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
