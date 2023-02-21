package tragaMonedas;

import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class Canal extends JLabel implements Runnable {
    
	private boolean rodar;
	private ImageIcon image;
	private Random aleatorio;
	private int indexImagen;
	
	public Canal() {
		aleatorio = new Random();
		indexImagen = aleatorio.nextInt(16)+1;
		image = new ImageIcon(getClass().getResource("/recursos/"+indexImagen+".png"));
		this.setIcon(image);
		
		Border raised = BorderFactory.createRaisedBevelBorder();
		Border lowered = BorderFactory.createLoweredBevelBorder();
		this.setBorder(BorderFactory.createCompoundBorder(raised,lowered));
	}
	
	public int getIndexImagen() {
		return indexImagen;
	}
	
	public void iniciarHilo() {
		rodar=true;
		Thread hilo = new Thread(this);
		hilo.start();
	}
	
	public void pararHilo() {
		rodar=false;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
        while(rodar) {
        	indexImagen = aleatorio.nextInt(16)+1;
    		image = new ImageIcon(getClass().getResource("/recursos/"+indexImagen+".png"));
    		this.setIcon(image);
    		try {
				Thread.sleep(aleatorio.nextInt(100));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}

}
