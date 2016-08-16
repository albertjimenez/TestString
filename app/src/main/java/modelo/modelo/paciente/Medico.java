package modelo.modelo.paciente;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Beruto on 15/8/16.
 */
public class Medico {
    private String nombre, apellidos;
    private int dni;
    private char letraDNI;


    private ConcurrentHashMap<Integer, Paciente> mapaMisPacientes;

    public Medico() {
        mapaMisPacientes = new ConcurrentHashMap<>();
    }

    public Medico(String nombre, String apellidos, int dni) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.letraDNI = calcularLetraDNI(dni);
    }

    public static char calcularLetraDNI(int dni) {
        final char[] vectorLetras = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};

        return vectorLetras[dni % 23];
    }

    public Paciente buscarPaciente(int dni) {
        return mapaMisPacientes.get(dni);

    }

    public boolean eliminarPaciente(int dni) {
        Paciente p = mapaMisPacientes.get(dni);
        if (p != null)
            mapaMisPacientes.remove(dni);
        else
            return false;
        return true;

    }

    public ArrayList<Ingreso> ingresosPaciente(int dni) {
        Paciente p = mapaMisPacientes.get(dni);
        if (p != null)
            return p.getIngresos();
        return null;

    }

    public boolean ingresar(int dni, Ingreso unIngreso) {
        Paciente p = mapaMisPacientes.get(dni);

        if (p == null)
            return false;
        p.getIngresos().add(unIngreso);
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Medico))
            return false;
        Medico medico = (Medico) o;
        return this.dni == medico.dni;
    }
}
