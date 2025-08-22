package modelo;
// alunos: francisco viana maia neto e felipe antonio ramalho macedo

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Lancamento {
    private String chavePiks;   // Identifica a conta relacionada ao lançamento
    private LocalDateTime datahora; // Regra 3: lançamento identificado pela data/hora do computador
    private double valor;       // Sempre positivo (o tipo diferencia crédito/débito)
    private String tipo;        // "+" para crédito ou "-" para débito

    // Construtor usado quando o lançamento é criado no sistema (transferência, crédito, débito)
    public Lancamento(String chavePiks, double valor, String tipo) {
        this.chavePiks = chavePiks;
        this.datahora = LocalDateTime.now(); // Data/hora atual do sistema
        this.valor = valor;
        this.tipo = tipo;
    }

    // Construtor usado ao reconstruir lançamentos a partir do arquivo CSV
    public Lancamento(String chavePiks, double valor, String tipo, LocalDateTime datahora) {
        this.chavePiks = chavePiks;
        this.datahora = datahora;
        this.valor = valor;
        this.tipo = tipo;
    }

    // Getters
    public String getChavePiks() { return chavePiks; }

    // Retorna a data/hora formatada conforme exigido no PDF: "dd/MM/yyyy hh:mm:ss"
    public String getDatahoraFormatada() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return datahora.format(fmt);
    }

    public double getValor() { return valor; }
    public String getTipo() { return tipo; }

    @Override
    public String toString() {
        // Exibição: datahora, tipo, valor e chave da conta (conforme regras do projeto)
        return String.format("[%s] %s %.2f (chave=%s)",
                getDatahoraFormatada(), tipo, valor, chavePiks);
    }
}
