package atividade01;
import java.util.Scanner;
public class Somar {
	public static void main (String args[]) {
		Scanner scan = new Scanner(System.in);
		int num1,num2,resultado;
		System.out.print("Digite o primeiro número: ");
		num1=scan.nextInt();
		System.out.print("Digite o segundo número: ");
		num2=scan.nextInt();
		resultado=num1+num2;
		System.out.println("O resultado da soma é: " + resultado);
		scan.close();
	}
}
