package serializacion;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GUI extends JFrame {
   private Ficha ficha;
   private Serializar serializador;
   private Escucha escucha;
   
   public GUI() {
	   initGUI();
	   
	   this.setTitle("Serializar");
	   this.pack();
	   this.setResizable(false);
	   this.setLocationRelativeTo(null);
	   this.setVisible(true);
	   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

   private void initGUI() {
	   // TODO Auto-generated method stub
	   
	   serializador = new Serializar();
	   escucha = new Escucha();
	   
	   definirGUI();
	   
	   ficha.addKeyListener(escucha);
	   ficha.setFocusable(true);
	   add(ficha,BorderLayout.CENTER);   
	   this.addWindowListener(escucha);   
   }

   private void definirGUI() {
	   // TODO Auto-generated method stub
	   
	   String [] opciones = {"Restaurar Ficha", "Nueva Ficha"};
	   String opcion = (String) JOptionPane.showInputDialog(this, "Selecciona opcion", "Iniciar",
			                                                JOptionPane.QUESTION_MESSAGE,null,
			                                                opciones, opciones[1]);

	   if(opcion!=null) {
		   if(opcion.equals("Restaurar Ficha")){
			   ficha = serializador.deserailizarObjecto();
		   }else {
			   ficha = new Ficha();
		   }
	   }else {
		   System.exit(0);
	   }
	   
   }
   
   private class Escucha implements KeyListener, WindowListener{

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		ficha.setLetra(e.getKeyChar());
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	//Eventos de la Ventana
	
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		System.out.println("windowOpened");
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		System.out.println("windowClosing");
		serializador.serializarObjecto(ficha);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		System.out.println("windowClosed");
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		System.out.println("windowIconified");
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		System.out.println("windowDeiconified");
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		System.out.println("windowActivated");
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		System.out.println("windowDeactivated");
	}
	   
   }
   
}
