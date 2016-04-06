package modelo.modelo.paciente;

import java.io.Serializable;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Beruto on 26/1/16.
 */
public class GestionPaciente implements Serializable {

    private ConcurrentHashMap<Integer, Paciente> mapaPacientes;
    private HashSet<Paciente> conjuntoIngresados;

    public GestionPaciente() {
        mapaPacientes = new ConcurrentHashMap<Integer, Paciente>();
        conjuntoIngresados = new HashSet<Paciente>();
    }
    public GestionPaciente(boolean test) {
        mapaPacientes = new ConcurrentHashMap<Integer, Paciente>();
        conjuntoIngresados = new HashSet<Paciente>();
        //TEST
        if(test)
            iniciarBase();
    }

    public boolean addPaciente(Paciente unPaciente) {

        int clave = unPaciente.getSIP();
        Paciente p = mapaPacientes.putIfAbsent(clave, unPaciente);
        return p == null;

    }


    public void eliminarElemConjunto(Paciente p) {

        conjuntoIngresados.remove(p);

    }


    public void eliminarTodoConjunto(Collection<Paciente> coleccion) {
        conjuntoIngresados.removeAll(coleccion);

    }


    public boolean removePaciente(Paciente unPaciente) {

        int clave = unPaciente.getSIP();

        if (!mapaPacientes.containsKey(clave))
            return false;
        mapaPacientes.remove(clave);
        conjuntoIngresados.remove(unPaciente);
        return true;
    }


    public boolean editPaciente(Paciente unPaciente) {
        int clave = unPaciente.getSIP();
        Paciente viejoPaciente = mapaPacientes.get(clave);
        if (!mapaPacientes.containsKey(clave))
            return false;
        return mapaPacientes.replace(clave, viejoPaciente, unPaciente);

    }


    public Paciente searchPaciente(int SIP) {

        return mapaPacientes.get(SIP);
    }


    public boolean addIngreso(int SIP, String Ingreso, String tipo) {
        Ingreso e = new Ingreso(Ingreso, tipo);
        Paciente p = mapaPacientes.get(SIP);
        if (p != null) {
            p.getIngresos().add(e);
            conjuntoIngresados.add(p);
            return true;
        }
        return false;

    }


    public boolean altaIngreso(int SIP) {
        Paciente p = mapaPacientes.get(SIP);
        if (p != null)
            return conjuntoIngresados.remove(p);

        return false;
    }


    public boolean esMapaVacio() {
        return mapaPacientes.isEmpty();
    }


    public boolean esConjuntoVacio() {

        return conjuntoIngresados.isEmpty();
    }


    public Collection<Paciente> todosPacientes() {
        return mapaPacientes.values();
    }


    public HashSet<Paciente> devolverPacientes() {

        return conjuntoIngresados;
    }


    public int numeroIngresados() {
        return conjuntoIngresados.size();
    }


    public int numeroPacientes() {
        return mapaPacientes.values().size();
    }

    public void setConjuntoIngresados(HashSet<Paciente> conjuntoIngresados) {
        this.conjuntoIngresados = conjuntoIngresados;
    }

    public void setMapaPacientes(ConcurrentHashMap<Integer, Paciente> mapaPacientes) {
        this.mapaPacientes = mapaPacientes;
    }

    public HashSet<Paciente> buscarNombre(String pattern) {
        HashSet<Paciente> conjunto = new HashSet<Paciente>();
        if (!mapaPacientes.isEmpty()) {
            for (Paciente p : mapaPacientes.values())
                if (p.getApellidos().toLowerCase().equals(pattern.toLowerCase())
                        || p.getApellidos().toLowerCase().startsWith(pattern))
                    conjunto.add(p);
        }
        return conjunto;
    }
    private void iniciarBase() {
        for (int i = 0; i < 3; i++)
            addPaciente(new Paciente("Santi", "Bernabeuses", i,
                    new GregorianCalendar(), "H", 12500));
    }
    public void vaciar(){
        mapaPacientes.clear();
        conjuntoIngresados.clear();
    }
}