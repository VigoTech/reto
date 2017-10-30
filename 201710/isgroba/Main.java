package lechera;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	static BufferedReader br =  new BufferedReader(new InputStreamReader(System.in));
	
	public static boolean comprobarDatos(int pequeno, int grande, int medida) {
		if(pequeno%2==0 && grande%2==0 && medida%2!=0) { 
			System.out.println("Erro, os caldeiros de contido PAR non poden calcular unha medida IMPAR");
			System.out.println();
			return true;
		}
		if(pequeno>grande){
			System.out.println("Erro, o caldeiro pequeno é maior que o grande...");
			System.out.println();
			return true;
		}
		if(medida<pequeno || medida>grande) {
			System.out.println("Erro, a medida ten que estar entre o caldeiro pequeno e o grande");
			System.out.println();
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		
		int caldeiroPequeno;
		int caldeiroGrande;
		int medida;
		
		do {
			caldeiroPequeno = lecturaDatos("Introduce os litros do caldeiro pequeno: ");
			caldeiroGrande = lecturaDatos("Introduce os litros do caldeiro grande: ");
			medida = lecturaDatos("Introduce a medida a conseguir: ");
		}while(comprobarDatos(caldeiroPequeno, caldeiroGrande, medida));
		
		Caldeiro b1 = new Caldeiro(caldeiroPequeno);
		Caldeiro b2 = new Caldeiro(caldeiroGrande);
		
		// Comeza o proceso de calculo
		System.out.println("Encher caldeiro grande");
		b2.encher();
		
		if(b2.traspaso(b1, medida))	System.out.println(" Contido conseguido --> " + b2.getContido());	
	}

	public static int lecturaDatos(String frase) {
		int litros=0;
		System.out.println(frase);
		try {
			litros = Integer.valueOf(br.readLine()).intValue();
			
		}catch(NumberFormatException nfe) {
			System.out.println("Error datos!!");
			lecturaDatos(frase);
		} catch (IOException e) {
			System.out.println("Error E/S");
			lecturaDatos(frase);
		}
		return litros;
	}
	
}
