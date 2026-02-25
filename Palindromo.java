package org.example;


import java.util.Scanner;

public class Palindromo {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        System.out.println("Introduce la palabra: ");
        String palabra=sc.nextLine();

        String palabraIzquierda="";
        for(char c : palabra.toCharArray()) {
            palabraIzquierda+=c;
        }
        String palabraDerecha="";
        for (int i=palabra.length()-1;i>=0;i--){
            palabraDerecha+=palabra.charAt(i);
        }
        boolean isPalindrome=palabraIzquierda.equals(palabraDerecha);
        if(isPalindrome) {
            System.out.println("Es un palindromo");
        }
        else{
            System.out.println("No es un palindromo");
        }

    }
}
