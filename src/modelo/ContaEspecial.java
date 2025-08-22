package modelo;
// alunos: francisco viana maia neto e felipe antonio ramalho macedo

public class ContaEspecial extends Conta {
    private double limite;

    // Construtor para appconsole/Cadastrar.java
    public ContaEspecial(int id, String chavePiks, double limite) {
        super(id, chavePiks);
        this.limite = limite;
    }
    
    // Construtor para repositorio/Repositorio.java (leitura de arquivo)
    public ContaEspecial(int id, String chavePiks, double saldo, Cliente cliente, double limite) {
        super(id, chavePiks, saldo, cliente);
        this.limite = limite;
    }

    public double getLimite() { return limite; }
    public void setLimite(double limite) { this.limite = limite; }

    @Override
    public void debitar(double valor) {
        if (valor <= 0) throw new IllegalArgumentException("Valor do débito deve ser positivo");
        if (getSaldo() - valor < -limite)
            throw new IllegalStateException("Limite da conta especial excedido");
        
        setSaldo(getSaldo() - valor);
        adicionarLanc(new Lancamento(getChavePiks(), valor, "-"));
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", limite=%.2f", limite);
    }
}