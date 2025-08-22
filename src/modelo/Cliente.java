package modelo;

public class Cliente {
    private String cpf;
    private String nome;
    private Conta conta; // cada cliente possui apenas uma conta

    public Cliente(String cpf, String nome, Conta conta) {
        this.cpf = cpf;
        this.nome = nome;
        this.conta = conta;
    }

    // Getters e Setters
    public String getCpf() { return cpf; }
    public String getNome() { return nome; }
    public Conta getConta() { return conta; }
    public void setConta(Conta conta) { this.conta = conta; }

    @Override
    public String toString() {
        return String.format("Cliente[cpf=%s, nome=%s, idConta=%d]",
                cpf, nome, conta != null ? conta.getId() : -1);
    }
}
