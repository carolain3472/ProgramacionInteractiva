package files;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUIFiles extends JFrame {
	
	private JTextField linea;
	private JTextArea areaTexto;
	private ManagerFiles managerFiles;
	private Escucha escucha;
	
	public GUIFiles() {
		initGUI();
		
		setTitle("Manejando Archivos");
		pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initGUI() {
		// TODO Auto-generated method stub
		
		managerFiles = new ManagerFiles();
		escucha = new Escucha();
		
		linea = new JTextField(15);
		linea.addActionListener(escucha);
		add(linea, BorderLayout.NORTH);
		
		areaTexto= new JTextArea(10,30);
		JScrollPane scroll = new JScrollPane(areaTexto);
		areaTexto.setText(managerFiles.abrirArchivo());
        add(scroll,BorderLayout.CENTER);
	}
	
	private class Escucha implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			managerFiles.escribirArchivo(linea.getText());
			areaTexto.setText(managerFiles.abrirArchivo());
		}
		
	}

}
