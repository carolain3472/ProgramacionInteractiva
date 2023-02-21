package fierritoVista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import fierritoControl.ControlFierrito;
import fierritoModelo.Carta;

public class VistaFierrito extends JFrame {
	
	private MesaJuego mesaJuego;
	private JPanel panelBotones;
	private JButton pedirCartas,ayuda,otraRonda;
	private Escucha escucha; 
	private Titulos rotulo;
	
	private ControlFierrito controlFierrito;
	
	public VistaFierrito(String[] jugadoresSimulados, List<List<Carta>> manosJugadores,Carta cartaComun, ControlFierrito controlFierrito) {
	    this.controlFierrito = controlFierrito;
		
        initGUI(jugadoresSimulados, manosJugadores, cartaComun);
		
		setTitle("Fierrito");
		setResizable(true);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initGUI(String[] jugadoresSimulados, List<List<Carta>> manosJugadores,Carta cartaComun) {
		// TODO Auto-generated method stub
		
		String jugadorHumano = "Mi mano";
		escucha = new Escucha();
		
		rotulo = new Titulos("Fierrito", 25, Color.BLACK);
		
		add(rotulo,BorderLayout.NORTH);
		
		mesaJuego = new MesaJuego(jugadorHumano, jugadoresSimulados, manosJugadores, cartaComun);
		add(mesaJuego,BorderLayout.CENTER);
		
		panelBotones = new JPanel();
		panelBotones.setBackground(Color.BLACK);
		panelBotones.setToolTipText("¿Ya clickeaste las cartas a eliminar?");
		
		pedirCartas = new JButton("Cartas");
		pedirCartas.setToolTipText("¿Ya clickeaste las cartas a eliminar?");
		pedirCartas.addMouseListener(escucha);
		
		otraRonda = new JButton("Otra Ronda?");
		otraRonda.addMouseListener(escucha);
		otraRonda.setVisible(false);
		panelBotones.add(otraRonda);
		
		ayuda = new JButton("Ayuda");
		ayuda.addMouseListener(escucha);
		
			
		panelBotones.add(pedirCartas);
		panelBotones.add(ayuda);
		
		
		add(panelBotones,BorderLayout.SOUTH);	
		
	}
	
	//se debe llamar para actualizar la GUI una vez se tenga el resultado
	public void actualizarVistaFierrito(List<List<Carta>> manosJugadores, int ganador) {
		//debe llamarse cuanto el control tenga las nuevas manos y el resultado
		//mesaJuego.mesaActualizar(manosJugadores);
		mesaJuego.mesaActualizar(manosJugadores,ganador);
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				otraRonda.setVisible(true);
			}
			
		});
		
		
	}
	
	private class Escucha extends MouseAdapter{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==pedirCartas) { //deben jugar los jugadores simulados, recibir nueva cartas y mostrar el resultado
				controlFierrito.descarteJugadorHumano(mesaJuego.getManoHumano());	
				pedirCartas.setVisible(false);
				mesaJuego.desactivarEscuchas();
			}else {
				if(e.getSource()==ayuda) {
					//mostrar Ayuda
				    JOptionPane.showMessageDialog(null, "La idea es armar un trío o al menos la sumatoria mayor"
				    		+ "\nObserva que todos tienen una carta común (Carta Abierta)!!"
				    		+ "\n Las cartas valen su número y la J=11, Q=12, K=13 y As=1"
				    		+ "\nDebes elegir de tu mano las cartas que quieras eliminar y luego pides carta");	
				}else {
					//otra ronda
					pedirCartas.setVisible(true);
					otraRonda.setVisible(false);
					mesaJuego.reiniciarJuego(controlFierrito.reiniciarJuego());
				}
			}		
		}		
	}

}
