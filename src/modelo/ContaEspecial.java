package modelo;

public class ContaEspecial extends Conta {
    private double limite;

    public ContaEspecial(int id, String chavePiks, double saldo, Cliente cliente, double limite) {
        super(id, chavePiks, saldo, cliente);
        this.limite = limite;
    }

    public double getLimite() { return limite; }
    public void setLimite(double limite) { this.limite = limite; }

    @Override
    public void debitar(double valor) {
        if (valor <= 0) throw new IllegalArgumentException("Valor deve ser positivo");
        double novoSaldo = getSaldo() - valor;
        if (novoSaldo < -limite)
            throw new IllegalStateException("Limite da conta especial excedido");
        setSaldo(novoSaldo);
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", limite=%.2f", limite);
    }
}
