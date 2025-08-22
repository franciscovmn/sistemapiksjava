package modelo;
// alunos: francisco viana maia neto e felipe antonio ramalho macedo

public class ContaEspecial extends Conta {
    private double limite; // Regra 6: conta especial pode ter saldo negativo até o limite fornecido

    // Construtor para criar uma conta especial manualmente (ex: no cadastro)
    public ContaEspecial(int id, String chavePiks, double limite) {
        super(id, chavePiks);
        this.limite = limite;
    }
    
    // Construtor usado na leitura do repositório (já traz saldo, cliente e limite definidos)
    public ContaEspecial(int id, String chavePiks, double saldo, Cliente cliente, double limite) {
        super(id, chavePiks, saldo, cliente);
        this.limite = limite;
    }

    public double getLimite() { return limite; }
    public void setLimite(double limite) { this.limite = limite; }

    @Override
    public void debitar(double valor) {
        if (valor <= 0) 
            throw new IllegalArgumentException("Valor do débito deve ser positivo");
        
        // Diferença em relação à Conta comum:
        // aqui o saldo pode ficar negativo, mas nunca menor que -limite
        if (getSaldo() - valor < -limite)
            throw new IllegalStateException("Limite da conta especial excedido");
        
        // Atualiza o saldo e registra lançamento
        setSaldo(getSaldo() - valor);
        adicionarLanc(new Lancamento(getChavePiks(), valor, "-"));
    }

    @Override
    public String toString() {
        // Exibe todos os dados da conta comum + o limite da conta especial
        return super.toString() + String.format(", limite=%.2f", limite);
    }
}
