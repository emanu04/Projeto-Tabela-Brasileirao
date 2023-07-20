package time;
import java.io.Serializable;

public class Time implements Serializable {
	private String nomeTime;
	private int numJogos;
	private int numEmpates;
	private int numDerrotas;
	private int numVitorias;
	private int golPro;
	private int golContra;
	
	public Time(String nomeTime, int numJogos, int numEmpates, int numDerrotas, int numVitorias, int golPro, int golContra) {
		this.nomeTime = nomeTime;
		this.numJogos = numJogos;
		this.numEmpates = numEmpates;
		this.numDerrotas = numDerrotas;
		this.numVitorias = numVitorias;
		this.golPro = golPro;
		this.golContra = golContra;
	}

	public String getNomeTime() {
		return nomeTime;
	}

	public void setNomeTime(String nomeTime) {
		this.nomeTime = nomeTime;
	}

	public int getNumJogos() {
		return numJogos;
	}

	public void setNumJogos(int numJogos) {
		this.numJogos = numJogos;
	}

	public int getNumEmpates() {
		return numEmpates;
	}

	public void setNumEmpates(int numEmpates) {
		this.numEmpates = numEmpates;
	}

	public int getNumDerrotas() {
		return numDerrotas;
	}

	public void setNumDerrotas(int numDerrotas) {
		this.numDerrotas = numDerrotas;
	}

	public int getNumVitorias() {
		return numVitorias;
	}

	public void setNumVitorias(int numVitorias) {
		this.numVitorias = numVitorias;
	}

	
	public int getGolPro() {
		return golPro;
	}

	public void setGolPro(int golPro) {
		this.golPro = golPro;
	}

	public int getGolContra() {
		return golContra;
	}

	public void setGolContra(int golContra) {
		this.golContra = golContra;
	}

	
	//pontos
	public int getPontos() {
		return ((numEmpates * 1) + (numVitorias * 3));

	}
	
	//percentual de aproveitamento
	public double getPercentual() {
		return ((getPontos() / (double) (getNumJogos() * 3)) * 100 );
	}
	
	//Saldo gols
	public int getSaldoGols() {
		return golPro - golContra;
	}
	
	  public void registrarPartida(int numGols1, int numGols2) {
		  numJogos++;
		  
	      golPro += numGols1;
	      golContra += numGols2;

	      if (numGols1 > numGols2) {
	          numVitorias++;
	      } else if (numGols1 < numGols2) {
	          numDerrotas++;
	      } else {
	          numEmpates++;
	      }
	  }

	public String toString() {
		return "Time: " + nomeTime + "\nNúmero de Jogos: " + numJogos + "\nEmpates: " + numEmpates + "\nDerrotas: "
				+ numDerrotas + "\nVitorias: " + numVitorias + "\n";
	}
	  
	  

	
}
