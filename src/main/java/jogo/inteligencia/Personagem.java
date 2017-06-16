package jogo.inteligencia;

public class Personagem extends No {

	private static final long serialVersionUID = 533930002921381376L;

	public Personagem(String valor) {
		super(valor);
	}

	@Override
	protected String pergunta() {
		return "Por acaso o seu personagem é o(a) " + valor + "?";
	}

}
