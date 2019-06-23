package gerenciador;

public class Aluno implements Comparable<Aluno> {

	int numeroUSP;
	String nome;

	public Aluno(int nUsp, String nome) {
		if(nome == null) {
			throw new NullPointerException();
		}
		this.numeroUSP = nUsp;
		this.nome = nome;
	}

	public int getNumeroUSP() {
		return this.numeroUSP;
	}

	public String getName() {
		return this.nome;
	}

	@Override
	public String toString() {
		return this.nome + " (Nº " + this.numeroUSP + ")";
	}

	@Override
	public int compareTo(Aluno a) {
		return this.getName().compareTo(a.getName());
	}

}
