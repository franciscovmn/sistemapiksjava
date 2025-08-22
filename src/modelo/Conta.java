package modelo;
// alunos: francisco viana maia neto e felipe antonio ramalho macedo

import java.util.ArrayList;

public class Conta {
    private int id;                // Identificador único da conta (Regra 2)
    private String chavePiks;      // Chave PIKS única que pode ser substituída (Regra 4)
    private double saldo;          // Saldo da conta (Regra 6: comum não pode ser negativo)
    private Cliente cliente;       // Dono da conta
    private ArrayList<Lancamento> lancamentos = new ArrayList<>(); 
    // Histórico de lançamentos (Regra: cada débito/crédito gera registro com data/hora e tipo)

    // Construtor para criar conta nova (saldo inicial = 0)
    public Conta(int id, String chavePiks) {
        this.id = id;
        this.chavePiks = chavePiks;
        this.saldo = 0.0;
        this.lancamentos = new ArrayList<>();
    }
    
    // Construtor usado ao carregar conta de repositório (já com saldo e cliente definidos)
    public Conta(int id, String chavePiks, double saldo, Cliente cliente) {
        this.id = id;
        this.chavePiks = chavePiks;
        this.saldo = saldo;
        this.cliente = cliente;
    }

    // Getters e Setters
    public int getId() { return id; }
    public String getChavePiks() { return chavePiks; }
    public void setChavePiks(String chavePiks) { this.chavePiks = chavePiks; }
    public double getSaldo() { return saldo; }
    protected void setSaldo(double saldo) { this.saldo = saldo; } // protegido para evitar uso indevido
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public ArrayList<Lancamento> getLancamentos() { return lancamentos; }
    public void adicionarLanc(Lancamento lanc) { this.lancamentos.add(lanc); }

    // Método para creditar valor na conta
    public void creditar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor do crédito deve ser positivo.");
        }
        this.saldo += valor;
        // Registra o lançamento de crédito no histórico
        this.adicionarLanc(new Lancamento(this.chavePiks, valor, "+"));
    }

    // Método para debitar valor da conta
    public void debitar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor do débito deve ser positivo.");
        }
        // Regra 6: conta comum não pode ficar com saldo negativo
        if (this.saldo - valor < 0) {
            throw new IllegalStateException("Saldo insuficiente. Conta comum não pode ter saldo negativo.");
        }
        this.saldo -= valor;
        // Registra o lançamento de débito no histórico
        this.adicionarLanc(new Lancamento(this.chavePiks, valor, "-"));
    }

    /**
     * Método de transferência entre contas.
     * Aplica as regras de negócio (Regra 5: contas diferentes, Regra 6: verificação de saldo).
     */
    public void transferir(double valor, Conta destino) {
        if (destino == null) {
            throw new IllegalArgumentException("A conta de destino não pode ser nula.");
        }
        if (this.equals(destino)) { // Regra 5
            throw new IllegalArgumentException("Conta de origem e destino devem ser diferentes.");
        }
        // Se o débito falhar (saldo insuficiente), a transferência é cancelada automaticamente
        this.debitar(valor);
        destino.creditar(valor);
    }
    
    // Sobrecarga apenas para inversão de parâmetros (boa prática de usabilidade)
    public void transferir(Conta destino, double valor) {
        this.transferir(valor, destino);
    }

    @Override
    public String toString() {
        String cpfCliente = (cliente != null) ? String.valueOf(cliente.getCpf()) : "N/A";
        String nomeCliente = (cliente != null) ? cliente.getNome() : "N/A";
        // Exibição de acordo com o enunciado: id, chavePiks, saldo, cpf e nome
        return String.format("Conta[id=%d, chave=%s, saldo=%.2f, cpf=%s, nome=%s]",
                id, chavePiks, saldo, cpfCliente, nomeCliente);
    }
}
