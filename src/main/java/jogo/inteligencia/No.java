package jogo.inteligencia;

import java.io.Serializable;

import javax.swing.JOptionPane;

public abstract class No implements Serializable {

	private static final long serialVersionUID = -1989794933381990263L;

	protected String valor;

	protected No respostaNegativa;
	protected No respostaPositiva;
	
	public No(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}

	public No getRespostaNegativa() {
		return respostaNegativa;
	}
	
	public void setRespostaNegativa(No respostaNegativa) {
		this.respostaNegativa = respostaNegativa;
	}
	
	public void setRespostaPositiva(No respostaPositiva) {
		this.respostaPositiva = respostaPositiva;
	}

	public No getRespostaPositiva() {
		return respostaPositiva;
	}
	
	public boolean fazerPergunta() {
		return JOptionPane.showConfirmDialog(null, pergunta(), "Pergunta", JOptionPane.YES_NO_OPTION) == 
				JOptionPane.YES_OPTION;
	}

	protected abstract String pergunta();
	
	@Override
	public String toString() {
		return String.format("%s(%s)\n\tSim: %s\n\tNão: %s", getClass().getSimpleName(), valor, respostaPositiva, respostaNegativa);
	}

	public void trocar(No atual, No novo) {
		if (respostaPositiva.getValor().equals(atual.getValor())) {
			respostaPositiva = novo;
		} else {
			respostaNegativa = novo;
		}
	}

}
