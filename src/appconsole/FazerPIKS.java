package appconsole;

import modelo.Conta;
import repositorio.Repositorio;

public class FazerPIKS {

	public FazerPIKS() {
		Conta conta1 = Repositorio.localizarConta("chave1");
		Conta conta5 = Repositorio.localizarConta("chave5");
		
		try {
			// Correção: (valor, destino)
			conta5.transferir(5000.0, conta1);	
			System.out.println("\ntransferiu da 5 para 1");
			System.out.println(conta1);
			System.out.println(conta5);

		} catch (Exception e) {
			System.out.println("--->" + e.getMessage());
		}
		try {
            // Correção: (valor, destino)
			conta1.transferir(5000.0, conta5);		
			System.out.println("\ntransferiu da 1 para 5");
			System.out.println(conta1);
			System.out.println(conta5);
		} catch (Exception e) {
			System.out.println("--->" + e.getMessage());
		}
		
		Repositorio.gravarObjetos();
	}
	
	public static void main(String[] args) {
		new FazerPIKS();
	}
}