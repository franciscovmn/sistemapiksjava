package modelo;
// alunos: francisco viana maia neto e felipe antonio ramalho macedo

public class Cliente {
    private int cpf; // Identificador único do cliente (Regra 1: cliente é identificado pelo CPF)
    private String nome; // Nome do cliente
    private Conta conta; // Cada cliente possui exatamente 1 conta (Regra 1)

    // Construtor usado ao cadastrar um cliente no sistema (sem conta definida ainda)
    public Cliente(int cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }

    // Construtor usado ao carregar dados do repositório (já com conta associada)
    public Cliente(int cpf, String nome, Conta conta) {
        this.cpf = cpf;
        this.nome = nome;
        this.conta = conta;
    }

    // Getters
    public int getCpf() { return cpf; }
    public String getNome() { return nome; }
    public Conta getConta() { return conta; }

    // Setters
    public void setConta(Conta conta) { this.conta = conta; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCpf(int cpf) { this.cpf = cpf; }

    @Override
    public String toString() {
        int idConta = (conta != null) ? conta.getId() : -1;
        // Exibição de acordo com o enunciado: cpf, nome e id da conta
        return String.format("Cliente[cpf=%d, nome=%s, idConta=%d]",
                cpf, nome, idConta);
    }
}
