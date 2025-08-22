package modelo;

public class Cliente {
    private String cpf;
    private String nome;
    private Conta conta;

    public Cliente(int cpf, String nome) {
        this.cpf = String.valueOf(cpf); // Converte o CPF para String
        this.nome = nome;
    }

    public String getCpf() { return cpf; }
    public String getNome() { return nome; }
    public Conta getConta() { return conta; }
    public void setConta(Conta conta) { this.conta = conta; }

    @Override
    public String toString() {
        // Verifica se a conta não é nula para evitar erro.
        int idConta = (conta != null) ? conta.getId() : -1;
        return String.format("Cliente[cpf=%s, nome=%s, idConta=%d]",
                cpf, nome, idConta);
    }
}