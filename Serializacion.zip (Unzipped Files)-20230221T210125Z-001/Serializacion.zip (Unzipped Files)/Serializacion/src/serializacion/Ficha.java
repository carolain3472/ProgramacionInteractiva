package serializacion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Ficha extends JPanel {
	public final Font FUENTE = new Font(Font.DIALOG,Font.BOLD+Font.ITALIC,27);
	private char letra;
	
	public Ficha() {
		letra = ' ';
		this.setPreferredSize(new Dimension(100,100));
	}
	
	public void setLetra(char letra) {
		this.letra = letra;
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g.setFont(FUENTE);
		g.setColor(Color.YELLOW);
		g.drawString(String.valueOf(letra),this.getWidth()/2-10 , this.getHeight()/2+7);
		
		if(letra=='M') {
			g.setColor(Color.MAGENTA);
			g.drawRect(40, 30, 40, 35);	
		}
		
	}
	

}
