package org.example;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Diccionario{
    public static void main(String[] args) {

        char[] abecedario = {
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
        };

        String[] listaPalabras = {
                // A
                "abeja", "arbol", "avión",
                // B
                "balón", "barco", "bota",
                // C
                "cama", "casa", "coche",
                // D
                "dado", "dedo", "diente",
                // E
                "elefante", "estrella", "escuela",
                // F
                "foca", "fuego", "fruta",
                // G
                "gato", "gorra", "guante",
                // H
                "hielo", "huevo", "hoja",
                // I
                "iglesia", "iglú", "isla",
                // J
                "jabón", "jardín", "jirafa",
                // K
                "karate", "kilo", "koala",
                // L
                "lápiz", "libro", "luna",
                // M
                "mano", "mesa", "mono",
                // N
                "nariz", "nieve", "nube",
                // O
                "ojo", "oreja", "oso",
                // P
                "pato", "perro", "piña",
                // Q
                "queso", "quiosco", "química",
                // R
                "rana", "ratón", "reloj",
                // S
                "sol", "silla", "sopa",
                // T
                "taza", "tigre", "tren",
                // U
                "uno", "uva", "uña",
                // V
                "vaca", "vaso", "vela",
                // W
                "waffle", "web", "wifi",
                // X
                "xenón", "xilófono", "xilografía",
                // Y
                "yate", "yema", "yoyó",
                // Z
                "zanahoria", "zapato", "zorro"
        };

        Map<Character, List<String>> diccionario = new HashMap<>();

        for (char letra : abecedario) {
            diccionario.put(letra, new ArrayList<>());
        }

        for (String palabra : listaPalabras) {
            char primeraLetra = palabra.charAt(0);

            if (diccionario.containsKey(primeraLetra)) {
                diccionario.get(primeraLetra).add(palabra);
            }
        }
        System.out.println(diccionario);
    }
}