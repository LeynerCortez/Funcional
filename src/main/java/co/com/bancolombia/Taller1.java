package co.com.bancolombia;

import java.util.Arrays;
import java.util.List;

class Persona {
    String nombre;
    int edad;
    String genero;

    public Persona(String nombre, int edad, String genero) {
        this.nombre = nombre;
        this.edad = edad;
        this.genero = genero;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getGenero() {
        return genero;
    }

    @Override
    public String toString() {
        return nombre + " - " + edad + " - " + genero;
    }
}

public class Taller1 {


    public static void main(String[] args) {
        List<Persona> personas = Arrays.asList(
                new Persona("Juan", 25, "Masculino"),
                new Persona("María", 30, "Femenino"),
                new Persona("Pedro", 40, "Masculino"),
                new Persona("Ana", 20, "Femenino")
        );

        System.out.println("Personas mayores que 25: ");
        personas.stream()
                .filter(persona -> persona.getEdad()>25)
                .forEach(System.out::println);

        System.out.println("Personas Nombres: ");
        personas.stream()
                .map(persona -> persona.getNombre())
                .forEach(System.out::println);

        System.out.println("suma de edades: ");
        System.out.println(personas.stream()
                .map(persona -> persona.getEdad())
                .reduce(0,(a,b) -> a+b)
                .intValue());


    }
}

