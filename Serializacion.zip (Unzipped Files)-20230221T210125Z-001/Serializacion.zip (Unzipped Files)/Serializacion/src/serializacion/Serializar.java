package serializacion;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serializar {
    private FileInputStream fileInput;
    private ObjectInputStream input;
    private FileOutputStream fileOutput;
    private ObjectOutputStream output;
    
   public void serializarObjecto(Ficha ficha) {
	   try {
		fileOutput = new FileOutputStream("src/objectoSerializado/fichaSerializada");
		output = new ObjectOutputStream(fileOutput);
		output.writeObject(ficha);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		try {
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	   
   } 
   
   public Ficha deserailizarObjecto() {
	   Ficha ficha = null;

	   try {
		   fileInput = new FileInputStream("src/objectoSerializado/fichaSerializada");
		   input = new ObjectInputStream(fileInput);
		   ficha = (Ficha)input.readObject();
	   } catch (FileNotFoundException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
	   } catch (IOException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
	   } catch (ClassNotFoundException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
	   }   
	   return ficha;  
   }
   
}
