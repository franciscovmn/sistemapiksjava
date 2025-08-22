package modelo;

import java.util.ArrayList;

public class Conta {
    private int id;
    private String chavePiks;
    private double saldo;
    private Cliente cliente;
    private ArrayList<Lancamento> lancamentos = new ArrayList<>();

    public Conta(int id, String chavePiks) {
        this.id = id;
        this.chavePiks = chavePiks;
        this.saldo = 0.0; // Saldo inicial padrão
        this.lancamentos = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getChavePiks() { return chavePiks; }
    public void setChavePiks(String chavePiks) { this.chavePiks = chavePiks; }
    public double getSaldo() { return saldo; }
    protected void setSaldo(double saldo) { this.saldo = saldo; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public ArrayList<Lancamento> getLancamentos() { return lancamentos; }
    public void adicionarLanc(Lancamento lanc) { this.lancamentos.add(lanc); }

    public void creditar(double valor) {
        if (valor <= 0) throw new IllegalArgumentException("Valor do crédito deve ser positivo");
        this.saldo += valor;
        this.adicionarLanc(new Lancamento(this.chavePiks, valor, "+"));
    }

    public void debitar(double valor) {
        if (valor <= 0) throw new IllegalArgumentException("Valor do débito deve ser positivo");
        if (this.saldo - valor < 0)
            throw new IllegalStateException("Conta comum não pode ter saldo negativo");
        this.saldo -= valor;
        this.adicionarLanc(new Lancamento(this.chavePiks, valor, "-"));
    }
    
    public void transferir(double valor, Conta destino) {
        if (this.equals(destino))
            throw new IllegalArgumentException("Conta de origem e destino devem ser diferentes");
            
        this.debitar(valor);
        destino.creditar(valor);
    }

    @Override
    public String toString() {
        return String.format("Conta[id=%d, chave=%s, saldo=%.2f, cpf=%s, nome=%s]",
                id, chavePiks, saldo, cliente.getCpf(), cliente.getNome());
    }
}