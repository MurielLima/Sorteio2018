/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Muriel
 */
@Document
public class Premio {

    @Id
    private String idPremio;
    private String nome;
    private String descricao;
    private boolean disponivel;

    public Premio() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.idPremio != null ? this.idPremio.hashCode() : 0);
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
        final Premio other = (Premio) obj;
        if ((this.idPremio == null) ? (other.idPremio != null) : !this.idPremio.equals(other.idPremio)) {
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public String getIdPremio() {
        return idPremio;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

}
