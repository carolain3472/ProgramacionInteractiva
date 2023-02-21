package fierritoVista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fierritoModelo.Baraja;
import fierritoModelo.Carta;
import fierritoModelo.FileIO;

public class MesaJuego extends JPanel {
	
	public static final String CARTA_TAPADA_FILE="/resources/cardBack.png";
	
	private PanelJugador jugador1, jugador2, jugador3;
	private JLabel labelMazo;
	private Carta cartaComun;
	private JPanel comunes, jugadorSimulados;
	
	public MesaJuego(String jugadorHumano, String[] jugadoresSimulados, List<List<Carta>>manosJugadores, Carta cartaComun) {
		setLayout(new BorderLayout());
				
		jugador1 = new PanelJugador(jugadoresSimulados[0],manosJugadores.get(0),false);
		jugador2 = new PanelJugador(jugadoresSimulados[1],manosJugadores.get(1),false);
		
		jugadorSimulados = new JPanel();
		jugadorSimulados.setBackground(Color.GREEN);
		jugadorSimulados.add(jugador1);
		jugadorSimulados.add(jugador2);
		
		jugador3 = new PanelJugador(jugadorHumano,manosJugadores.get(2),true);
		
		labelMazo = new JLabel(new ImageIcon(FileIO.readImageFile(this,CARTA_TAPADA_FILE)));
		this.cartaComun = cartaComun;
		
		comunes = new JPanel();
		comunes.setBackground(Color.GREEN);
		comunes.add(labelMazo);
		comunes.add(cartaComun);
		
		add(jugadorSimulados,BorderLayout.NORTH);
		add(comunes, BorderLayout.CENTER);
		add(jugador3,BorderLayout.SOUTH);
		
		setBackground(Color.GREEN);
	}
	
	//devuelve la mano del jugador humano
	public List<Carta> getManoHumano(){
		return jugador3.getMano();
	}
   
	
	
	//actualiza la GUI con el estado del juego
	public void mesaActualizar(List<List<Carta>> manosJugadores, int ganador) {
		//determinarResultado(manosJugadores);
		if(ganador==0) {
			jugador1.recibirCartas(manosJugadores.get(0),"Ganaste");
			jugador2.recibirCartas(manosJugadores.get(1),"Perdiste");
			jugador3.recibirCartas(manosJugadores.get(2),"Perdiste");
		}else {
			if(ganador==1) {
				jugador1.recibirCartas(manosJugadores.get(0),"Perdiste");
				jugador2.recibirCartas(manosJugadores.get(1),"Ganaste");
				jugador3.recibirCartas(manosJugadores.get(2),"Perdiste");
			}else {
				jugador1.recibirCartas(manosJugadores.get(0),"Perdiste");
				jugador2.recibirCartas(manosJugadores.get(1),"Perdiste");
				jugador3.recibirCartas(manosJugadores.get(2),"Ganaste");
			}
		}
		
	}
	
	public void reiniciarJuego(List<List<Carta>> manosJugadores) {
		jugador1.recibirCartas(manosJugadores.get(0),"");
		jugador2.recibirCartas(manosJugadores.get(1),"");
		jugador3.recibirCartas(manosJugadores.get(2),"Inicias...");
		jugador3.activarEscuchas();
	}
	
	public void desactivarEscuchas() {
		jugador3.desactivarEscuchas();
	}
	
}
