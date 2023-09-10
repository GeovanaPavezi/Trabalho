package AplicacaoBancaria;

import java.util.Hashtable;

public class Cliente {
    private String nome;
    private String cpf;

    public Cliente(String cpf, String nome) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return "Cliente: " + nome + " CPF: " + cpf;
    }
}
