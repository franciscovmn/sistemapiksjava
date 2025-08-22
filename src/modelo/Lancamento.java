package modelo;
// alunos: francisco viana maia neto e felipe antonio ramalho macedo

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Lancamento {
    private String chavePiks;
    private LocalDateTime datahora;
    private double valor;   // sempre positivo
    private String tipo;    // "+" ou "-"

    // Construtor para novos lançamentos
    public Lancamento(String chavePiks, double valor, String tipo) {
        this.chavePiks = chavePiks;
        this.datahora = LocalDateTime.now();
        this.valor = valor;
        this.tipo = tipo;
    }

    // Construtor para leitura de CSV
    public Lancamento(String chavePiks, double valor, String tipo, LocalDateTime datahora) {
        this.chavePiks = chavePiks;
        this.datahora = datahora;
        this.valor = valor;
        this.tipo = tipo;
    }

    public String getChavePiks() { return chavePiks; }
    public String getDatahoraFormatada() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return datahora.format(fmt);
    }
    public double getValor() { return valor; }
    public String getTipo() { return tipo; }

    @Override
    public String toString() {
        return String.format("[%s] %s %.2f (chave=%s)",
                getDatahoraFormatada(), tipo, valor, chavePiks);
    }
}
