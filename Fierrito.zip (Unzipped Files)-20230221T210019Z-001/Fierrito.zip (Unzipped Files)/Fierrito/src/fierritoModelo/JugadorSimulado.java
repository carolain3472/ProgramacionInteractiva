package fierritoModelo;

import java.util.Random;

import fierritoControl.ControlFierrito;

//Simula un jugador, de acuerdo a su tipo indica si es arriesgado (elimina las 2 cartas) o mesurado (elimina 1 carta)
//los hilos deben ejecutarse cuando les corresponda el turno

public class JugadorSimulado implements Runnable {
	public static final int ARRIESGADO = 2;
	public static final int MESURADO = 1;
		
	private String nombreJugador;
	private int turno;
	private int tipo;
	private int descarte;
	private Random random;
	private ControlFierrito controlFierrito; //recurso compartido
	
	public JugadorSimulado(int turno, int tipo, String nombreJugador, ControlFierrito controlFierrito) {
		this.turno = turno;
		this.tipo = tipo;
		this.nombreJugador = nombreJugador;
		this.controlFierrito=controlFierrito;
		random = new Random();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(tipo==MESURADO) {
			descarte = random.nextInt(ControlFierrito.NUMERO_CARTAS); //como es moderado indica el subindex de la carta que cambia
		}else {
			descarte=2;//si es arriesgado indica que cambia las 2 cartas
		}
		
		controlFierrito.turnos(turno,descarte,nombreJugador);
		System.out.println("Hilo "+nombreJugador+" termina "+descarte);
	}
	

}
