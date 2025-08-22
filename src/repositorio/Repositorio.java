package repositorio;
// alunos: francisco viana maia neto e felipe antonio ramalho macedo

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import modelo.Cliente;
import modelo.Conta;
import modelo.ContaEspecial;
import modelo.Lancamento;

public class Repositorio {
    // Mapeia chavePIKS -> Conta
    // Atende Regra 2: cada conta é identificada pela chave PIKS
    private static TreeMap<String, Conta> contasPIKS = new TreeMap<>();
    
    // Mapeia CPF -> Cliente
    // Atende Regra 1: cliente identificado pelo CPF, e cada cliente possui 1 conta
    private static TreeMap<Integer, Cliente> clientesCPF = new TreeMap<>();

    // Bloco static garante que, assim que a classe for carregada,
    // os objetos sejam lidos automaticamente dos arquivos CSV
    static {
        lerObjetos();
    }

    // --- MÉTODOS DE CONTA ---
    public static void adicionarConta(Conta c) {
        contasPIKS.put(c.getChavePiks(), c);
    }

    public static void removerConta(Conta c) {
        contasPIKS.remove(c.getChavePiks());
    }

    public static Conta localizarConta(String chave) {
        return contasPIKS.get(chave);
    }

    // Atualiza a chave PIKS de uma conta (Regra 4: chave PIKS pode ser substituída)
    public static void atualizarChavePiks(String chaveAntiga, String chaveNova) {
        Conta conta = contasPIKS.remove(chaveAntiga);
        if (conta != null) {
            conta.setChavePiks(chaveNova);
            contasPIKS.put(chaveNova, conta);
        }
    }

    // --- MÉTODOS DE CLIENTE ---
    public static void adicionarCliente(Cliente c) {
        clientesCPF.put(c.getCpf(), c);
    }

    public static void removerCliente(Cliente c) {
        clientesCPF.remove(c.getCpf());
    }

    public static Cliente localizarCliente(int cpf) {
        return clientesCPF.get(cpf);
    }

    // --- LISTAGENS ---
    public static ArrayList<Conta> getContas() {
        return new ArrayList<>(contasPIKS.values());
    }

    public static ArrayList<Cliente> getClientes() {
        return new ArrayList<>(clientesCPF.values());
    }

    // --- PERSISTÊNCIA (LER CSV) ---
    public static void lerObjetos() {
        try {
            // Garante que os arquivos existam mesmo na primeira execução
            File fContas = new File(new File(".\\contas.csv").getCanonicalPath());
            File fLancamentos = new File(new File(".\\lancamentos.csv").getCanonicalPath());

            if (!fContas.exists()) fContas.createNewFile();
            if (!fLancamentos.exists()) fLancamentos.createNewFile();

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao criar arquivos vazios: " + ex.getMessage());
        }

        try {
            // --------- LEITURA DE CONTAS ---------
            File fContas = new File(new File(".\\contas.csv").getCanonicalPath());
            Scanner scannerContas = new Scanner(fContas);
            while (scannerContas.hasNextLine()) {
                String linha = scannerContas.nextLine().trim();
                if (linha.isEmpty()) continue;
                
                // Formato exigido pelo PDF: id;chavepiks;saldo;limite;cpf;nome
                String[] partes = linha.split(";");
                int id = Integer.parseInt(partes[0]);
                String chave = partes[1];
                double saldo = Double.parseDouble(partes[2]);
                double limite = Double.parseDouble(partes[3]);
                int cpf = Integer.parseInt(partes[4]);
                String nome = partes[5];

                // Cria cliente e conta correspondente
                Cliente cliente = new Cliente(cpf, nome, null);
                Conta conta;
                if (limite > 0) {
                    conta = new ContaEspecial(id, chave, saldo, cliente, limite);
                } else {
                    conta = new Conta(id, chave, saldo, cliente);
                }
                
                cliente.setConta(conta);
                adicionarConta(conta);
                adicionarCliente(cliente);
            }
            scannerContas.close();

            // --------- LEITURA DE LANÇAMENTOS ---------
            File fLancamentos = new File(new File(".\\lancamentos.csv").getCanonicalPath());
            Scanner scannerLanc = new Scanner(fLancamentos);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            while (scannerLanc.hasNextLine()) {
                String linha = scannerLanc.nextLine().trim();
                 if (linha.isEmpty()) continue;

                // Formato exigido pelo PDF: chavepiks;datahora;valor;tipo
                String[] partes = linha.split(";");
                String chave = partes[0];
                LocalDateTime datahora = LocalDateTime.parse(partes[1], formatter);
                double valor = Double.parseDouble(partes[2]);
                String tipo = partes[3];
                
                Conta conta = localizarConta(chave);
                if (conta != null) {
                    Lancamento lanc = new Lancamento(chave, valor, tipo, datahora);
                    conta.adicionarLanc(lanc);
                }
            }
            scannerLanc.close();

        } catch (Exception ex) {
            throw new RuntimeException("Erro na leitura dos arquivos: " + ex.getMessage());
        }
    }

    // --- PERSISTÊNCIA (GRAVAR CSV) ---
    public static void gravarObjetos() {
        try {
            File fContas = new File(new File(".\\contas.csv").getCanonicalPath());
            FileWriter writerContas = new FileWriter(fContas);
            
            File fLancamentos = new File(new File(".\\lancamentos.csv").getCanonicalPath());
            FileWriter writerLanc = new FileWriter(fLancamentos);

            // Percorre todas as contas cadastradas
            for (Conta conta : getContas()) {
                double limite = 0.0;
                if (conta instanceof ContaEspecial) {
                    limite = ((ContaEspecial) conta).getLimite();
                }
                
                // Formato exigido: id;chavepiks;saldo;limite;cpf;nome
                String linhaConta = conta.getId() + ";" + conta.getChavePiks() + ";" + conta.getSaldo() + ";" + limite + ";" +
                                    conta.getCliente().getCpf() + ";" + conta.getCliente().getNome();
                writerContas.write(linhaConta + "\n");
                
                // Para cada conta, grava também seus lançamentos
                for(Lancamento lanc : conta.getLancamentos()){
                     // Formato: chavepiks;datahora;valor;tipo
                     String linhaLanc = conta.getChavePiks() + ";" + lanc.getDatahoraFormatada() + ";" + lanc.getValor() + ";" + lanc.getTipo();
                     writerLanc.write(linhaLanc + "\n");
                }
            }
            writerContas.close();
            writerLanc.close();
            
        } catch (Exception e) {
            throw new RuntimeException("Erro na gravação dos arquivos: " + e.getMessage());
        }
    }
}
