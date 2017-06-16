package jogo.inteligencia;

public class Identificacao extends No {

	private static final long serialVersionUID = -4046917372393127903L;

	public Identificacao(String valor) {
		super(valor);
	}

	@Override
	protected String pergunta() {
		return "O seu personagem é " + valor + "?";
	}

}
