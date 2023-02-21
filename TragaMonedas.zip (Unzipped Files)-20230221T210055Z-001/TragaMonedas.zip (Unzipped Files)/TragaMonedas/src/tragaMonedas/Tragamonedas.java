package tragaMonedas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Tragamonedas extends JFrame {
     private JPanel botones, canales;
     private Canal canal1, canal2, canal3;
     private JButton iniciar, parar;
     private ImageIcon imagen;
     private Escucha escucha;
     
     public Tragamonedas() {
    	 initGUI();
    	 
    	this.setTitle("Traga Monedas");
 		this.pack(); //setSize(200,300);
 		this.setResizable(false);
 		this.setLocationRelativeTo(null);
 		this.setVisible(true);
 		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
     }

	private void initGUI() {
		// TODO Auto-generated method stub
		
		escucha = new Escucha();
		
		canales = new JPanel();
		canales.setLayout(new GridLayout(1,3));
		canales.setBackground(Color.WHITE);
		canal1 = new Canal();
		canal2 = new Canal();
		canal3 = new Canal();
		canales.add(canal1);
		canales.add(canal2);
		canales.add(canal3);
		
		add(canales,BorderLayout.CENTER);
		
		botones = new JPanel();
		imagen = new ImageIcon(getClass().getResource("/recursos/iniciar-1.png"));
		iniciar = new JButton(imagen);
		iniciar.setBorder(null);
		iniciar.setContentAreaFilled(false);
		iniciar.addActionListener(escucha);
		
		imagen = new ImageIcon(getClass().getResource("/recursos/parar-1.png"));
		parar = new JButton(imagen);
		parar.setBorder(null);
		parar.setContentAreaFilled(false);
		parar.addActionListener(escucha);
		
		botones.add(iniciar);
		botones.add(parar);
		
		add(botones,BorderLayout.SOUTH);
		
		JOptionPane.showMessageDialog(this,"Jugador: \nBoton verde inicia el juego \nBoton rojo para el juego",
				                      "Instrucciones del Juego",JOptionPane.INFORMATION_MESSAGE);
	}
     
   private class Escucha implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==iniciar) {
			imagen = new ImageIcon(getClass().getResource("/recursos/iniciar-2.png"));
			iniciar.setIcon(imagen);
			iniciar.removeActionListener(escucha);
			canal1.iniciarHilo();
			canal2.iniciarHilo();
			canal3.iniciarHilo();
		}else {
			canal1.pararHilo();
			canal2.pararHilo();
			canal3.pararHilo();
			imagen = new ImageIcon(getClass().getResource("/recursos/parar-2.png"));
			parar.setIcon(imagen);
			parar.removeActionListener(escucha);
			validarJuego();
		}
	}	   
   }
   
   private void validarJuego() {
		// TODO Auto-generated method stub
		if(canal1.getIndexImagen()==canal2.getIndexImagen()&&canal1.getIndexImagen()==canal3.getIndexImagen()) {
			String mensaje = "Has ganado $1000 \nDesea seguir jugando?";
			estadoJuego(mensaje);
		}else {
			if(canal1.getIndexImagen()==canal2.getIndexImagen() ||
			   canal1.getIndexImagen()==canal3.getIndexImagen() ||
			   canal2.getIndexImagen()==canal3.getIndexImagen()) {
			   String mensaje = "Has ganado $200 \nDesea seguir jugando?";
			   estadoJuego(mensaje);
			}else {
				String mensaje = "Has perdido!! \nDesea seguir jugando?";
				estadoJuego(mensaje);
			}
		}
	}

   private void estadoJuego(String mensaje) {
	   // TODO Auto-generated method stub
        SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				int option = JOptionPane.showConfirmDialog(botones, mensaje,"Estado del Juego",JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE);
				if(option==JOptionPane.YES_OPTION) {
					reiniciarJuego();
				}else {
					System.exit(0);
				}
			}
        });
   }

   private void reiniciarJuego() {
	   // TODO Auto-generated method stub
	   SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				imagen = new ImageIcon(getClass().getResource("/recursos/iniciar-1.png"));
				iniciar.setIcon(imagen);
				iniciar.addActionListener(escucha);
				
				imagen = new ImageIcon(getClass().getResource("/recursos/parar-1.png"));
				parar.setIcon(imagen);
				parar.addActionListener(escucha);	
			}
       });

   }
}
