package JogoDaVelha;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.Scanner;
import javax.swing.*;

public class JogoDaVelha extends JFrame {
	JButton [] bt = new JButton[9];
	JButton novo = new JButton("Novo Jogo");
	JButton zerar = new JButton("Zerar Placar");
	
	JLabel placar = new JLabel("Placar");
	JLabel px = new JLabel ("X	0");
	JLabel po = new JLabel ("O	0");
	
	int PX = 0;
	int PO = 0;
	boolean xo = false;
	boolean[] click =  new boolean[9];
	
	public JogoDaVelha() {
		// Definir a tela
		setVisible(true);
		setTitle("Jogo da Velha");
		setDefaultCloseOperation(3);
		setLayout(null);
		setBounds(500,300,700,500); // Para definir o tamanho da janela
		
		add(placar);
		add(px);
		add(po);
		placar.setBounds(420, 50,100,30);
		px.setBounds(400, 75,140,30);
		po.setBounds(425, 75,140,30);
		
		add(novo);
		add(zerar);
		novo.setBounds(410,130,140,30);
		zerar.setBounds(410,180,140,30);
		
		
		novo.addActionListener(new java.awt.event.ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		
		zerar.addActionListener(new java.awt.event.ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				PO = 0;
				PX = 0;
				atualizar();
			}
		});
		
		// Criar os botões que para simbolizar cada campo
		criarBotao(bt);
		// Inicializar o click como falso
		inicializarClick(click);
		// Criar acao nos 9 botoes
		for (int i = 0; i < 9; i++) {
			int index = i;
			bt[i].addActionListener(new java.awt.event.ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent e) {
					if(click[index]== false) {
						click[index] = true;
						mudar(bt[index]);
					}
				}
			});
		}
	}
	
	// Criar 9 botoes sinalizando as posicoes do jogo da velha
	public void criarBotao(JButton[] bt) {
		int cont = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				bt[cont] = new JButton();
				add(bt[cont]);
				bt[cont].setBounds((100*i) + 50, (100*j) + 50, 95 , 95); // Para definir o tamanho dos botões
				bt[cont].setFont(new Font("Arial", Font.BOLD,40)); // Fonte do que vai ser escrito no botao
				cont++;
			}
		}
	}
	
	// Inicializa todos os clicks dos botoes como falso
	public void inicializarClick(boolean []click) {
		for (int i = 0; i < 9; i++) {
			click[i] = false;
		}
	}
	
	// Muda a vez do jogador
	public void mudar(JButton btn) {
		if(xo) {
			btn.setText("O");
			xo = false;
		}
		else {
			btn.setText("X");
			xo = true;
		}
		ganhou();
	}
	
	public void atualizar() {
		px.setText("X 	"+ PX);
		po.setText("O 	"+ PO);
	}
	
	// Verifica quem ganhou ou se teve empate
	public void ganhou () {
		int cont = 0;
		
		for(int i = 0; i < 9; i++) {
			if(click[i]== true) {
				cont++;
			}
		}
		
		if(bt[0].getText() == "X" && bt[1].getText() == "X" && bt[2].getText() == "X" ||
		   bt[3].getText() == "X" && bt[4].getText() == "X" && bt[5].getText() == "X" ||
		   bt[6].getText() == "X" && bt[7].getText() == "X" && bt[8].getText() == "X" ||
		   bt[0].getText() == "X" && bt[3].getText() == "X" && bt[6].getText() == "X" ||
		   bt[1].getText() == "X" && bt[4].getText() == "X" && bt[7].getText() == "X" ||
		   bt[2].getText() == "X" && bt[5].getText() == "X" && bt[8].getText() == "X" ||
		   bt[0].getText() == "X" && bt[4].getText() == "X" && bt[8].getText() == "X" ||
		   bt[2].getText() == "X" && bt[4].getText() == "X" && bt[6].getText() == "X" ) {
			
			JOptionPane.showMessageDialog(null, "X ganhou");	
			PX++;
			atualizar();
			limpar();
		}
		else if(bt[0].getText() == "O" && bt[1].getText() == "O" && bt[2].getText() == "O" ||
				bt[3].getText() == "O" && bt[4].getText() == "O" && bt[5].getText() == "O" ||
				bt[6].getText() == "O" && bt[7].getText() == "O" && bt[8].getText() == "O" ||
				bt[0].getText() == "O" && bt[3].getText() == "O" && bt[6].getText() == "O" ||
				bt[1].getText() == "O" && bt[4].getText() == "O" && bt[7].getText() == "O" ||
				bt[2].getText() == "O" && bt[5].getText() == "O" && bt[8].getText() == "O" ||
				bt[0].getText() == "O" && bt[4].getText() == "O" && bt[8].getText() == "O" ||
				bt[2].getText() == "O" && bt[4].getText() == "O" && bt[6].getText() == "O" ) {
					
					JOptionPane.showMessageDialog(null, "O ganhou");	
					PO++;
					atualizar();
					limpar();
		}
		else if(cont == 9){
			JOptionPane.showMessageDialog(null, "Empate");	
			limpar();
		}
	}
	
	// Limpar as posicoes do jogo para um novo jogo
	public void limpar() {
		for (int i = 0; i < 9; i++) {
			bt[i].setText("");
			click[i] = false;
			xo = false;
		}
	}

	// Funcao principal
	public static void main(String [] args) {
		new JogoDaVelha();
	}
}
