package org.example;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FicheroTXT {

    public static void main(String[] args) {

        final String FILE_NAME = "prueba.txt";

        try {
            FileWriter fw = new FileWriter(FILE_NAME);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("Esto es una prueba de fichero txt en java");

            bw.close();

            System.out.println("Fichero creado y guardado correctamente.");
            System.out.println("----------------------------------------");

            FileReader fr = new FileReader(FILE_NAME);
            BufferedReader br = new BufferedReader(fr);

            String line = br.readLine();

            System.out.println("Leyendo el contenido del fichero:");
            while (line != null) {
                System.out.println(line);
                line = br.readLine();
            }

            br.close();

        } catch (IOException e) {
            System.out.println("Error procesando el archivo: " + e.getMessage());
        }
    }
}
