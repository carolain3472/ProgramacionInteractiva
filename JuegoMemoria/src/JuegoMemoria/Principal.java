/*==================================================
 Archivo: Principal.java
 Fecha de creacion: 05-Mar-2017
 Fecha de la ultima modificacion: 11-Mar-2017
 Autor: Esneider Manzano
==================================================*/
/*
Clase: Principal
Responsabilidad: Inicia el programa y crea un objeto Vista de GuiMemoria
Colaboracion: Ninguna
*/

package JuegoMemoria;

import javax.swing.JFrame;

public class Principal 
{
	public static void main(String[] args) 
	{
		MemoriaGUI vista = new MemoriaGUI();
		vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		System.out.println("hola xd");
	}
}
