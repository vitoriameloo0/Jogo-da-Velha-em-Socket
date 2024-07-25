package JogoDaVelha;

import java.util.Scanner;

public class JogoDaVelha {
	
	public static void main(String []args) {
		Campo [][] velha = new Campo[3][3];
		char simboloAtual = 'X';
		Boolean game = true;
		char vitoria = ' ';
		Scanner scan = new Scanner(System.in);
		
		iniciarJogo(velha);
		while (game) {
			desenhaJogo(velha);
			vitoria = verificaVitoria(velha);
			
			if(vitoria != ' ') {
				System.out.printf("Jogador %s venceu%n", vitoria);
				break;
			}

			try {
				if(verificarJogada(velha, jogar(scan, simboloAtual), simboloAtual)) {
					if(simboloAtual == 'X') {
						simboloAtual = 'O';
					}
					else {
						simboloAtual = 'X';
					}
				}
				
				
			}catch(Exception e) {
				System.out.printf("Error");
			}
		}
		
		System.out.println("Fim do Jogo");
	}
	
	// Desenha o Formato do jogo na tela do Usuario
	public static void desenhaJogo(Campo[][] velha) {
		limparTela();
		System.out.println("    0     1     2");
		System.out.printf("0   %c  |  %c  | %c %n", velha[0][0].getSimbolo(), velha[0][1].getSimbolo(), velha[0][2].getSimbolo());
		System.out.println("  -----------------");
		System.out.printf("1   %c  |  %c  | %c %n", velha[1][0].getSimbolo(), velha[1][1].getSimbolo(), velha[1][2].getSimbolo());
		System.out.println("  -----------------");
		System.out.printf("2   %c  |  %c  | %c %n", velha[2][0].getSimbolo(), velha[2][1].getSimbolo(), velha[2][2].getSimbolo());
	}
	
	// Imprime linhas para limpar a tela do usuario
	public static void limparTela() {
		for(int cont = 0; cont < 5; cont++) {
			System.out.println("");
		}
	}
	
	// Pega a posicao na matriz que o jogador quer jogar
	public static int[] jogar(Scanner scan, char sa) {
		int p[] = new int [2];
		System.out.printf("%s %c%n", "Quem Joga: ", sa);
		System.out.printf("Informe a linha: ");
		p[0] = scan.nextInt();
		System.out.printf("Informe a coluna: ");
		p[1] = scan.nextInt();
		
		return p;
	}
	
	// Verifica se pode fazer jogada em um campo
	public static Boolean verificarJogada(Campo[][] velha, int p[], char simboloAtual) {
		if(velha[p[0]][p[1]].getSimbolo() == ' ') {
			velha[p[0]][p[1]].setSimbolo(simboloAtual);
			return true;
		}
		else {
			return false;
		}
	}
	
	
	public static void iniciarJogo(Campo [][] velha) {
		for(int l = 0; l < 3; l++) {
			for(int c = 0; c < 3; c++) {
				velha[l][c] = new Campo();
			}
		}
	}
	
	
	// Verifica se o jogador ganhou
	public static char verificaVitoria(Campo[][] velha) {
		
		 for (int i = 0; i < velha.length; i++) {

	            if (velha[i][0].getSimbolo() == 'X' && velha[i][1].getSimbolo() == 'X' && velha[i][2].getSimbolo() == 'X' || velha[i][0].getSimbolo() == 'O' && velha[i][1].getSimbolo() == 'O' && velha[i][2].getSimbolo() == 'O') {
	                return velha[i][0].getSimbolo();
	            }
	        }
	        for (int i = 0; i < velha.length; i++) {
	            if (velha[0][i].getSimbolo() == 'X' && velha[1][i].getSimbolo() == 'X' && velha[2][i].getSimbolo() == 'X' || velha[0][i].getSimbolo() == 'O' && velha[1][i].getSimbolo() == 'O' && velha[2][i].getSimbolo() == 'O') {
	                return velha[0][i].getSimbolo();
	            }

	        }

	        if ((velha[0][0].getSimbolo() == 'X' && velha[1][1].getSimbolo() == 'X' && velha[2][2].getSimbolo() == 'X') || (velha[0][2].getSimbolo() == 'O' && velha[1][1].getSimbolo() == 'O' && velha[2][0].getSimbolo() == 'O')) {
	            return velha[1][1].getSimbolo();
	        }

	        if((velha[0][0].getSimbolo() =='O' && velha[1][1].getSimbolo() == 'O' && velha[2][2].getSimbolo() == 'O') || (velha[0][2].getSimbolo() == 'X' && velha[1][1].getSimbolo() == 'X' && velha[2][0].getSimbolo() == 'X')){
	            return velha[1][1].getSimbolo();
	        }
	
		return ' ';
	}
	
}
