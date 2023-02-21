package fierritoControl;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.SwingUtilities;

import fierritoModelo.Baraja;
import fierritoModelo.Carta;
import fierritoModelo.JugadorSimulado;
import fierritoVista.MesaJuego;
import fierritoVista.VistaFierrito;

public class ControlFierrito {
	public static final int TOTAL_JUGADORES=3;
	public static final String[] NOMBRE_JUGADORES = {"Javier","Valentina"};
	public static final int NUMERO_CARTAS=2;
	
	//Vista
	private VistaFierrito vistaFierrito;
		
	//variables del juego
	private Baraja baraja;
	private List<List<Carta>> manosJugadores;
	private Carta cartaComun;
	
	//viariable para el manejo de hilos
	private int turno; //variable de control de turno hilos
	private int[] descarte = new int[TOTAL_JUGADORES-1];
	private Lock bloqueo = new ReentrantLock(); //manejo de sincronizacion
	private Condition esperarTurno = bloqueo.newCondition(); //manejo de sincronizacion	
	
		
	public ControlFierrito() {
		//generar manos iniciales de los jugadores, generar carta común, iniciar la Vista
		baraja = new Baraja();
		manosJugadores = new ArrayList<List<Carta>>();
		
		for(int i=0; i<TOTAL_JUGADORES;i++) {
			manosJugadores.add(seleccionarCartas());
		}
		
		cartaComun = baraja.getCarta();	
		
		vistaFierrito = new VistaFierrito(NOMBRE_JUGADORES, manosJugadores,cartaComun,this);
	}
	
	
	public void descarteJugadorHumano(List<Carta> manoJugadorHumano) {
		//cambiar la mano del jugador humano 
		manosJugadores.remove(2); //se quita la mano antigua
		manosJugadores.add(manoJugadorHumano); // se agrega la mano nueva
				
		//iniciarHilos
		 iniciarJugadoresSimulados();
	}
	
	private void iniciarJugadoresSimulados() {
		// TODO Auto-generated method stub
		    turno=1;
		  //crear los hilos e iniciarlos
		
		  JugadorSimulado jugador1 = new JugadorSimulado(1, JugadorSimulado.ARRIESGADO,ControlFierrito.NOMBRE_JUGADORES[0], this); 
		  JugadorSimulado jugador2 = new JugadorSimulado(2, JugadorSimulado.MESURADO,ControlFierrito.NOMBRE_JUGADORES[1], this);
		  
		  ExecutorService ejecutorSubprocesos = Executors.newCachedThreadPool();
		  ejecutorSubprocesos.execute(jugador1); 
		  ejecutorSubprocesos.execute(jugador2);
		  
		  ejecutorSubprocesos.shutdown();
	}
	
	//método a sincronizar - condition es el turno 
	public void turnos(int jugador, int cartasPedidas, String nombreJugador) {
		//bloquear la clase
		bloqueo.lock();
		try {
			//validar condición de ejecucion para el hilo
			while(jugador!=turno) { //turno= 1 le toca a jugador 1 y turno=2 le toca a jugador 2
				//dormir al jugador porque no es su turno
				System.out.println("Jugador "+nombreJugador+" intenta entrar y es mandado a esperar turno");
				esperarTurno.await();	  
			}

			//ejecutar tarea, variar condición de ejecucion, desbloquear el objeto
			descarte[turno-1]=cartasPedidas;
			System.out.println("Jugador "+nombreJugador+" pidió "+descarte[turno-1]+" cartas");
			turno++;
			esperarTurno.signalAll();		 

		}catch(InterruptedException e) {
			e.printStackTrace();
			System.out.println("aqui");
		}finally {
			bloqueo.unlock();
			if(turno==3) {
				darCartas();
			}
		} 
	}

	private void darCartas() {	
        //cartas para jugadores simulados
		for(int i=0;i<2;i++) { 
			if(descarte[i]==2) { //dar dos cartas al jugador
				//primero elimina las cartas de la mano del jugador
				manosJugadores.get(i).clear();
				//ahora si asigna las nuevas cartas
				asignarCartas(manosJugadores.get(i)); 
			}else { //dar al jugador la carta pedida
				//elimina el descarte de la mano original
				manosJugadores.get(i).remove(descarte[i]);
				//asigna la nueva carta
				asignarCartas(manosJugadores.get(i));
			}
		}

		
		  System.out.println(" Javier cartas ");
		  System.out.println(manosJugadores.get(0).get(0).toString());
		  System.out.println(manosJugadores.get(0).get(1).toString());
		  
		  
		  System.out.println(" Valentina cartas ");
		  
		  System.out.println(manosJugadores.get(1).get(0).toString());
		  System.out.println(manosJugadores.get(1).get(1).toString());
		  
		  //cartas para jugador Humano 
		  asignarCartas(manosJugadores.get(2));
		  
		  System.out.println(" Humano cartas ");
		  
		  System.out.println(manosJugadores.get(2).get(0).toString());
		  System.out.println(manosJugadores.get(2).get(1).toString());
		 
		
			int ganador = determinarGanador();
			
			vistaFierrito.actualizarVistaFierrito(manosJugadores,ganador);
	}
	
	//calcula cuantas cartas dar a cada jugador, luego del descarte
	private void asignarCartas(List<Carta> manoJugador) {
		if(manoJugador.size()==0) {//asignar 2 cartas
			manoJugador.add(baraja.getCarta());
			manoJugador.add(baraja.getCarta());
		}else {
			if(manoJugador.size()==1) {//dar 1 carta
				manoJugador.add(baraja.getCarta());
			}
		}			
	}
		
	private int determinarGanador() {
		 System.out.println("determinar ganador ");
		//busca el trio mayor o la mano mas alta
		
		int[][] trios = new int[3][2];
		int[][] sumas = new int[3][2];
		int ganador;
		
		int[] resultadoJugador = new int[2];
		
		for(int i=0;i<3;i++) {
		  resultadoJugador = validarJugador(manosJugadores.get(i));
		  trios[i][0]=resultadoJugador[0];
		  trios[i][1]=i;
		  sumas[i][0]=resultadoJugador[1];
		  sumas[i][1]=i;
		}
		
		//ordenar las matrices de manera ascendente para determinar el ganador
		
		if(trios[0][0] > 0 ||trios[1][0] > 0 ||trios[2][0] > 0) {
			//ordenar matriz
			int[][] otro = ordenar(trios);
			System.out.println("Trios Inicial");
			for(int i=0;i<3;i++) {
			  System.out.println(trios[i][0]+"-"+trios[i][1]);	
			}
			
			System.out.println("Trios final");
			for(int i=0;i<3;i++) {
			  System.out.println(trios[i][0]+"-"+trios[i][1]);	
			}
			ganador=otro[0][1];
              		
		}else {
			//ordenar matriz
			int[][] otro = ordenar(sumas);
			System.out.println("sumas Inicial");
			for(int i=0;i<3;i++) {
			  System.out.println(sumas[i][0]+"-"+sumas[i][1]);	
			}
			
			System.out.println("Sumas final");
			for(int i=0;i<3;i++) {
			  System.out.println(sumas[i][0]+"-"+sumas[i][1]);	
			}
			ganador = otro[0][1];
		}
		 System.out.println("ganador "+ganador);
		 System.out.println("manosJugadores "+manosJugadores.size());
		 
		return ganador;
	}
	
	private int[][] ordenar(int[][] matriz) {
		int temporal1, temporal2;
		
		for(int i=0; i<3 ; i++) {
			for(int j=i+1; j<3; j++) {
				if(matriz[i][0] < matriz[j][0]) {
					temporal1 = matriz[i][0];
					temporal2 = matriz[i][1];
					matriz[i][0] = matriz[j][0];
					matriz[i][1] = matriz[j][1];
					matriz[j][0] = temporal1;
					matriz[j][1] = temporal2;
				}	
			}
		}
		return matriz;
	}
	
	private int[] validarJugador(List<Carta> manoJugador) {
		
        int[] resultado = new int[2];
        int total = 0;
		
		if(manoJugador.get(0).getValorNumerico()==manoJugador.get(1).getValorNumerico() &&
		   manoJugador.get(0).getValorNumerico()==cartaComun.getValorNumerico())  {//valida si hay un trio
			resultado[0]=cartaComun.getValorNumerico(); 
			resultado[1]=total; //guarda en puntaje acumulado 0 lo que indica que tiene trio.
		}else {//Guarda la sumatoria de las cartas
			resultado[0]=total;//agrega 0 para indicar que no tiene trio
			total = total + manoJugador.get(0).getValorNumerico() + manoJugador.get(1).getValorNumerico() 
					      + cartaComun.getValorNumerico();  
			resultado[1]=total;//Guarda en segunda posición el puntaje acumulado
		}
		return resultado;
	}

	private ArrayList<Carta> seleccionarCartas() {
		// TODO Auto-generated method stub
		ArrayList<Carta> manoJugador = new ArrayList<Carta>();
		//se dan 2 cartas al jugador
		manoJugador.add(baraja.getCarta());
		manoJugador.add(baraja.getCarta());
		return manoJugador;
	}

	//Hilo principal - main
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 EventQueue.invokeLater(new Runnable() {
	        	public void run() {
	        		new ControlFierrito();
	        	}
	        });
	}
	
	public List<List<Carta>> reiniciarJuego() {
		manosJugadores.clear();
		for(int i=0; i<TOTAL_JUGADORES;i++) {
			manosJugadores.add(seleccionarCartas());
		}
		return manosJugadores;
	}	
}
