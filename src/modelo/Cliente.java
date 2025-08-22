package modelo;
// alunos: francisco viana maia neto e felipe antonio ramalho macedo

public class Cliente {
    private int cpf; // Alterado de String para int
    private String nome;
    private Conta conta;

    // Construtor para appconsole/Cadastrar.java
    public Cliente(int cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }

    // Construtor para repositorio/Repositorio.java (leitura de arquivo)
    public Cliente(int cpf, String nome, Conta conta) { // Alterado o tipo do cpf
        this.cpf = cpf;
        this.nome = nome;
        this.conta = conta;
    }

    // Getters
    public int getCpf() { return cpf; } // Alterado o tipo de retorno
    public String getNome() { return nome; }
    public Conta getConta() { return conta; }

    // Setters
    public void setConta(Conta conta) { this.conta = conta; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCpf(int cpf) { this.cpf = cpf; } // Mantido como int

    @Override
    public String toString() {
        int idConta = (conta != null) ? conta.getId() : -1;
        // O formatador %d é usado para o cpf (inteiro)
        return String.format("Cliente[cpf=%d, nome=%s, idConta=%d]",
                cpf, nome, idConta);
    }
}