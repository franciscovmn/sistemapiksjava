package appconsole;
/**
 * SI - POO - Prof. Fausto Ayres
 * Teste da Fachada
 * 
 */

import modelo.Cliente;
import modelo.Conta;
import modelo.ContaEspecial;
import repositorio.Repositorio;

public class Cadastrar {

	public Cadastrar() {
		try {
			Conta conta;
			Cliente cli;
			cli = new Cliente(1111, "joao");
			conta = new Conta(1, "chave1"); 
			conta.setCliente(cli);
			cli.setConta(conta);
			Repositorio.adicionarConta(conta);
			Repositorio.adicionarCliente(cli);
		
			cli = new Cliente(2222, "maria");
			conta = new Conta(2, "chave2"); 
			conta.setCliente(cli);
			cli.setConta(conta);
			Repositorio.adicionarConta(conta);
			Repositorio.adicionarCliente(cli);

			cli = new Cliente(3333, "jose");
			conta = new Conta(3, "chave3"); 
			conta.setCliente(cli);
			cli.setConta(conta);
			Repositorio.adicionarConta(conta);
			Repositorio.adicionarCliente(cli);
			
			cli = new Cliente(4444, "paulo");
			conta = new ContaEspecial(4, "chave4", 4000.0); 
			conta.setCliente(cli);
			cli.setConta(conta);
			Repositorio.adicionarConta(conta);
			Repositorio.adicionarCliente(cli);
			
			cli = new Cliente(5555, "ana");
			conta = new ContaEspecial(5, "chave5", 5000.0); 
			conta.setCliente(cli);
			cli.setConta(conta);
			Repositorio.adicionarConta(conta);
			Repositorio.adicionarCliente(cli);
			
			Repositorio.gravarObjetos();
			
			System.out.println("Cadastrou contas ");
		} catch (Exception e) {
			System.out.println("--->"+e.getMessage());
		}		
		
	}

	public static void main (String[] args) 
	{
		new Cadastrar();
	}
}


