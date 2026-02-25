package org.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.IOException;

class Registro implements Serializable {
    private String mensaje;

    public Registro(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }
}

public class FicheroSER {

    public static void main(String[] args) {

        final String FILE_NAME = "prueba.ser";

        Registro miRegistro = new Registro("Esto es una prueba de fichero serializado en java");

        try {
            FileOutputStream fos = new FileOutputStream(FILE_NAME);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(miRegistro);

            oos.close();

            System.out.println("Objeto serializado y guardado correctamente en " + FILE_NAME);
            System.out.println("----------------------------------------");

        } catch (IOException e) {
            System.out.println("Error al guardar el archivo .ser: " + e.getMessage());
        }

        try {
            FileInputStream fis = new FileInputStream(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Registro registroRecuperado = (Registro) ois.readObject();

            ois.close();

            System.out.println("Leyendo el contenido del objeto recuperado:");
            System.out.println(registroRecuperado.getMensaje());

        } catch (IOException e) {
            System.out.println("Error de lectura del archivo: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("No se ha encontrado la clase del objeto: " + e.getMessage());
        }
    }
}
