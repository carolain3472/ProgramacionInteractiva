package serializacion;

import java.awt.EventQueue;

public class PrincipalSerializacion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				GUI gui = new GUI();
			}
        	
        });
	}

}
