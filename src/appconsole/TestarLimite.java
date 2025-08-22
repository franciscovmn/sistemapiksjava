package appconsole;

import modelo.Conta;
import repositorio.Repositorio;

public class TestarLimite {

	public TestarLimite() {
		try {
			Conta conta1 = Repositorio.localizarConta("chave1");
			Conta conta5 = Repositorio.localizarConta("chave5");
			
			try {conta1.debitar(1.0);} 
			catch (Exception e) {System.out.println("--->" + e.getMessage());}
		
			try {conta5.debitar(5001.0);} 
			catch (Exception e) {System.out.println("--->" + e.getMessage());}

		} catch (Exception e) {
			System.out.println("--->" + e.getMessage());
		}
		
	}
	
	public static void main(String[] args) {
		new TestarLimite();
	}
}
