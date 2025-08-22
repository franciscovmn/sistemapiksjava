package modelo;

public class Cliente {
    private String cpf;
    private String nome;
    private Conta conta;

    // Construtor para appconsole/Cadastrar.java
    public Cliente(int cpf, String nome) {
        this.cpf = String.valueOf(cpf);
        this.nome = nome;
    }

    // Construtor para repositorio/Repositorio.java (leitura de arquivo)
    public Cliente(String cpf, String nome, Conta conta) {
        this.cpf = cpf;
        this.nome = nome;
        this.conta = conta;
    }

    // Getters
    public String getCpf() { return cpf; }
    public String getNome() { return nome; }
    public Conta getConta() { return conta; }

    // Setters (NECESSÁRIOS PARA A TELA CLIENTE)
    public void setConta(Conta conta) { this.conta = conta; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCpf(int cpf) { this.cpf = String.valueOf(cpf); }


    @Override
    public String toString() {
        int idConta = (conta != null) ? conta.getId() : -1;
        return String.format("Cliente[cpf=%s, nome=%s, idConta=%d]",
                cpf, nome, idConta);
    }
}