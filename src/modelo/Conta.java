package modelo;

import java.util.ArrayList;

public class Conta {
    private int id;
    private String chavePiks;
    private double saldo;
    private Cliente cliente;
    private ArrayList<Lancamento> lancamentos = new ArrayList<>();

    // Construtores...
    public Conta(int id, String chavePiks) {
        this.id = id;
        this.chavePiks = chavePiks;
        this.saldo = 0.0;
        this.lancamentos = new ArrayList<>();
    }
    
    public Conta(int id, String chavePiks, double saldo, Cliente cliente) {
        this.id = id;
        this.chavePiks = chavePiks;
        this.saldo = saldo;
        this.cliente = cliente;
    }

    // Getters e Setters...
    public int getId() { return id; }
    public String getChavePiks() { return chavePiks; }
    public void setChavePiks(String chavePiks) { this.chavePiks = chavePiks; }
    public double getSaldo() { return saldo; }
    protected void setSaldo(double saldo) { this.saldo = saldo; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public ArrayList<Lancamento> getLancamentos() { return lancamentos; }
    public void adicionarLanc(Lancamento lanc) { this.lancamentos.add(lanc); }

    // Métodos de operação...
    public void creditar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor do crédito deve ser positivo.");
        }
        this.saldo += valor;
        this.adicionarLanc(new Lancamento(this.chavePiks, valor, "+"));
    }

    public void debitar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor do débito deve ser positivo.");
        }
        if (this.saldo - valor < 0) {
            throw new IllegalStateException("Saldo insuficiente. Conta comum não pode ter saldo negativo.");
        }
        this.saldo -= valor;
        this.adicionarLanc(new Lancamento(this.chavePiks, valor, "-"));
    }
    

    /**
     * Método principal de transferência.
     * Realiza verificações de segurança antes de efetuar a transação.
     * @param valor O montante a ser transferido.
     * @param destino A conta que receberá o valor.
     */
    public void transferir(double valor, Conta destino) {
        if (destino == null) {
            throw new IllegalArgumentException("A conta de destino não pode ser nula.");
        }
        
        // garantindo que a regra de negócio seja checada antes de qualquer operação.
        if (this.equals(destino)) {
            throw new IllegalArgumentException("Conta de origem e destino devem ser diferentes.");
        }

        // A lógica de débito e crédito permanece a mesma, pois já é segura.
        // Se this.debitar() falhar, a exceção interromperá o método antes do crédito.
        this.debitar(valor);
        destino.creditar(valor);
    }
    
    // Método sobrecarregado (mantido como estava, pois é uma boa solução)
    public void transferir(Conta destino, double valor) {
        this.transferir(valor, destino);
    }
    // ------------------------------------

    @Override
    public String toString() {
        String cpfCliente = (cliente != null) ? cliente.getCpf() : "N/A";
        String nomeCliente = (cliente != null) ? cliente.getNome() : "N/A";
        return String.format("Conta[id=%d, chave=%s, saldo=%.2f, cpf=%s, nome=%s]",
                id, chavePiks, saldo, cpfCliente, nomeCliente);
    }
}