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
    private static TreeMap<String, Conta> contasPIKS = new TreeMap<>();
    private static TreeMap<Integer, Cliente> clientesCPF = new TreeMap<>();

    // O bloco static garante que a leitura dos objetos seja feita assim que a classe for carregada.
    static {
        lerObjetos();
    }

    public static void adicionarConta(Conta c) {
        contasPIKS.put(c.getChavePiks(), c);
    }

    public static void removerConta(Conta c) {
        contasPIKS.remove(c.getChavePiks());
    }

    public static Conta localizarConta(String chave) {
        return contasPIKS.get(chave);
    }

    public static void adicionarCliente(Cliente c) {
        clientesCPF.put(Integer.parseInt(c.getCpf()), c);
    }

    public static void removerCliente(Cliente c) {
        clientesCPF.remove(Integer.parseInt(c.getCpf()));
    }

    public static Cliente localizarCliente(int cpf) {
        return clientesCPF.get(cpf);
    }

    public static ArrayList<Conta> getContas() {
        return new ArrayList<>(contasPIKS.values());
    }

    public static ArrayList<Cliente> getClientes() {
        return new ArrayList<>(clientesCPF.values());
    }

    public static void lerObjetos() {
        try {
            // Garante que os arquivos existam
            File fContas = new File(new File(".\\contas.csv").getCanonicalPath());
            File fLancamentos = new File(new File(".\\lancamentos.csv").getCanonicalPath());

            if (!fContas.exists()) fContas.createNewFile();
            if (!fLancamentos.exists()) fLancamentos.createNewFile();

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao criar arquivos vazios: " + ex.getMessage());
        }

        try {
            // Leitura de contas.csv
            File fContas = new File(new File(".\\contas.csv").getCanonicalPath());
            Scanner scannerContas = new Scanner(fContas);
            while (scannerContas.hasNextLine()) {
                String linha = scannerContas.nextLine().trim();
                if (linha.isEmpty()) continue;
                
                String[] partes = linha.split(";");
                int id = Integer.parseInt(partes[0]);
                String chave = partes[1];
                double saldo = Double.parseDouble(partes[2]);
                double limite = Double.parseDouble(partes[3]);
                int cpf = Integer.parseInt(partes[4]);
                String nome = partes[5];

                Cliente cliente = new Cliente(String.valueOf(cpf), nome, null);
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

            // Leitura de lancamentos.csv
            File fLancamentos = new File(new File(".\\lancamentos.csv").getCanonicalPath());
            Scanner scannerLanc = new Scanner(fLancamentos);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            while (scannerLanc.hasNextLine()) {
                String linha = scannerLanc.nextLine().trim();
                 if (linha.isEmpty()) continue;

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

    public static void gravarObjetos() {
        try {
            File fContas = new File(new File(".\\contas.csv").getCanonicalPath());
            FileWriter writerContas = new FileWriter(fContas);
            
            File fLancamentos = new File(new File(".\\lancamentos.csv").getCanonicalPath());
            FileWriter writerLanc = new FileWriter(fLancamentos);

            for (Conta conta : getContas()) {
                double limite = 0.0;
                if (conta instanceof ContaEspecial) {
                    limite = ((ContaEspecial) conta).getLimite();
                }
                
                String linhaConta = conta.getId() + ";" + conta.getChavePiks() + ";" + conta.getSaldo() + ";" + limite + ";" +
                                    conta.getCliente().getCpf() + ";" + conta.getCliente().getNome();
                writerContas.write(linhaConta + "\n");
                
                for(Lancamento lanc : conta.getLancamentos()){
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