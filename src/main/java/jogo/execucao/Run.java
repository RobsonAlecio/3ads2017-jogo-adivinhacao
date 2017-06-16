package jogo.execucao;

import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.showInputDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JOptionPane;

import jogo.inteligencia.Identificacao;
import jogo.inteligencia.No;
import jogo.inteligencia.Personagem;

public class Run {

	private static No raiz;

	public static void main(String[] args) {
		raiz = carregarDoArquivo();
		
		if (raiz == null)
			raiz = inicializarRaiz();
		
		do {
			tentarFazerUmaAdivinhacaoOuAprenderAlgoQuandoNaoConseguirAdivinhar();
			salvarEmArquivo();
		} while (desejarContinuar());
	}

	private static void salvarEmArquivo() {
		try {
			File file = new File("conhecimento.bin");
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(raiz);
			
			oos.close();
		} catch (Exception e) {
			mostrarFalha(e);
		}
	}

	private static void tentarFazerUmaAdivinhacaoOuAprenderAlgoQuandoNaoConseguirAdivinhar() {
		No anterior = null;
		No atual = raiz; 
		No proximo = null;
		
		do {
			boolean respondeuSim = atual.fazerPergunta();
			
			proximo = respondeuSim ? atual.getRespostaPositiva() : atual.getRespostaNegativa();
			
			boolean acabou = proximo == null;
			
			if (!acabou) {
				anterior = atual;
				atual = proximo;
			}
			
			if (acabou && respondeuSim) {
				JOptionPane.showMessageDialog(null, "Acertei!!!", "Uhull!!!", JOptionPane.INFORMATION_MESSAGE);
			}
			
			if (acabou && !respondeuSim) {
				String valorIdentificacao = showInputDialog(null, "O que identifica seu personagem?", 
						"Errei! Me ensina?", WARNING_MESSAGE);
				Identificacao identificacao = new Identificacao(valorIdentificacao);
				
				String valorPersonagem = showInputDialog(null, "Quem é seu personagem?", 
						"Errei! Me ensina?", WARNING_MESSAGE);
				Personagem personagem = new Personagem(valorPersonagem);
				
				identificacao.setRespostaPositiva(personagem);
				identificacao.setRespostaNegativa(atual);
				
				anterior.trocar(atual, identificacao);
			}
		} while (proximo != null);
	}

	private static boolean desejarContinuar() {
		return JOptionPane.showConfirmDialog(null, "Deseja continuar?", "Novo jogo", JOptionPane.YES_NO_OPTION) == 
				JOptionPane.YES_OPTION;
	}

	private static No inicializarRaiz() {
		No novaRaiz = new Identificacao("nacional");
		
		novaRaiz.setRespostaPositiva(new Personagem("Curupira"));
		novaRaiz.setRespostaNegativa(new Personagem("Pé Grande"));
		
		return novaRaiz;
	}

	private static No carregarDoArquivo() {
		try {
			File file = new File("conhecimento.bin");
			
			if (!file.exists())
				return null;
			
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			No raizCarregada = (No) ois.readObject();
			
			ois.close();
			
			return raizCarregada;
		} catch (Exception e) {
			mostrarFalha(e);
			return null;
		}
	}

	private static void mostrarFalha(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		
		e.printStackTrace(pw);
		
		JOptionPane.showMessageDialog(null, "Falha na execução:\n" + sw, "Algo deu errado", JOptionPane.ERROR_MESSAGE);
		System.exit(-10);
	}

}
