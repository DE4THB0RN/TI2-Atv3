package model;

public class Beys {
	private int id;
	private String descricao;
	private float preco;
	private String nome;	
	private String tipo;
	
	public Beys() {
		id = -1;
		descricao = "";
		preco = 0.00F;
		nome = "";
		tipo = ""; 
	}

	public Beys(int id, String nome, String tipo , String descricao, float preco) {
		setId(id);
		setDescricao(descricao);
		setPreco(preco);
		setNome(nome);
		setTipo(tipo);
	}		
	
	public int getID() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public float getPreco() {
		return preco;
	}

	public void setPreco(float preco) {
		this.preco = preco;
	}
	
	public String getTipo() {
		return tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return "Nome: " + nome + "   Preço: R$" + preco +  "   Descrição: "
				+ descricao  + "   Tipo: " + tipo;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getID() == ((Beys) obj).getID());
	}	
}
