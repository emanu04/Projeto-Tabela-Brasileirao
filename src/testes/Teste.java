package testes;
//import time.Time;
import teclado.Teclado;
import time.Partidas;

public class Teste {
	public static void main(String[] args) {
	
	
	
		//Time t1 = new Time(null, 0, 0, 0, 0, 0, 0);
		Partidas p1 = new Partidas(0);
		
		boolean loop = true;
		
		while(loop) {
			
			p1.carregarDados();
			p1.imprimeMenu();
			int escolha = Teclado.leInt("Digite a opção desejada: ");
			
			if(escolha == 1) {
				p1.quantidadeTimes();
				p1.insereTime();
				p1.salvarDados();
			}
			
			if(escolha == 2) {
				p1.registraPartida();
				p1.salvarDados();

			}
			
			if(escolha == 3) {
				p1.imprimeTabela();
				p1.salvarDados();

			}
			
			if(escolha == 4) {
				p1.imprimeInfo();
				p1.salvarDados();

			}
			
			if(escolha == 5) {
				System.out.println("Você escolheu sair, até a próxima! ");
				p1.salvarDados();

				loop = false;
			}
		
		
		}
	}
}
