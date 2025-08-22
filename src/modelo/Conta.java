package modelo;

public class Conta {
    private int id;
    private String chavePiks;
    private double saldo;
    private Cliente cliente;

    public Conta(int id, String chavePiks, double saldo, Cliente cliente) {
        this.id = id;
        this.chavePiks = chavePiks;
        this.saldo = saldo;
        this.cliente = cliente;
    }

    public int getId() { return id; }
    public String getChavePiks() { return chavePiks; }
    public void setChavePiks(String chavePiks) { this.chavePiks = chavePiks; }
    public double getSaldo() { return saldo; }
    protected void setSaldo(double saldo) { this.saldo = saldo; }
    public Cliente getCliente() { return cliente; }

    // Operações de negócio
    public void creditar(double valor) {
        if (valor <= 0) throw new IllegalArgumentException("Valor deve ser positivo");
        this.saldo += valor;
    }

    public void debitar(double valor) {
        if (valor <= 0) throw new IllegalArgumentException("Valor deve ser positivo");
        if (this.saldo - valor < 0)
            throw new IllegalStateException("Conta comum não pode ter saldo negativo");
        this.saldo -= valor;
    }

    @Override
    public String toString() {
        return String.format("Conta[id=%d, chave=%s, saldo=%.2f, cpf=%s, nome=%s]",
                id, chavePiks, saldo, cliente.getCpf(), cliente.getNome());
    }
}
