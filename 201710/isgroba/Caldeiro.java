
public class Caldeiro {

	private int capacidade;
	private int contido;

	public Caldeiro() {
		super();
		this.capacidade=0;
		this.contido=0;
	}
	
	public Caldeiro(int capacidade) {
		super();
		this.capacidade = capacidade;
		this.contido = 0;
	}

	public void encher() {
		contido=capacidade;
	}
	public void vaciar() {
		contido=0;
	}
	
	public boolean traspaso(Caldeiro caldeiro, int medida) {
		boolean tempoOk = true;
		int aux=0;
		long tempoInicial = System.currentTimeMillis();
		long tempoTotal = 0;
		long limiteSeg = 10;

		while(this.getContido() != 0 && this.getContido() != medida && tempoTotal <= limiteSeg) {
			aux = caldeiro.getCapacidade() - caldeiro.getContido();
			if(aux!=0) {
				if(this.getContido()>aux) {
					this.setContido(this.getContido() - aux);
					caldeiro.setContido(caldeiro.getContido()+aux);
					System.out.println("Paso do caldeiro grande ao pequeno");
					System.out.print("Caldeiro grande " + this.getContido());
					System.out.println(" --> Caldeiro pequeno " + caldeiro.getContido());
				}else {
					caldeiro.setContido(caldeiro.getContido()+this.getContido());
					vaciar();
					System.out.println("Paso do caldeiro grande ao pequeno");
					System.out.print("Caldeiro grande " + this.getContido());
					System.out.println(" --> Caldeiro pequeno " + caldeiro.getContido());
					encher();
					System.out.println("Encher caldeiro grande");
				}
			}else {
				caldeiro.vaciar();
				System.out.println("Vaciar caldeiro pequeno");
			}
			long tempoExecucion = System.currentTimeMillis();
			tempoTotal = (tempoExecucion - tempoInicial)/1000;
			System.out.println(tempoTotal);
			if(tempoTotal > limiteSeg) {
				tempoOk = false;
				System.out.println("Demasiado tempo para o cálculo");
			}
		}
		return tempoOk;
	}
	
	//getters and setters
	public int getCapacidade() {
		return capacidade;
	}

	public void setCapacidad(int capacidade) {
		this.capacidade = capacidade;
	}
	
	public int getContido() {
		return contido;
	}

	public void setContido(int contido) {
		this.contido = contido;
	}
	
	
}
