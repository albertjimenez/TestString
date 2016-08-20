package modelo.modelo.paciente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Beruto on 15/8/16.
 */
public class Medico implements Serializable{
    private String nombreYapellidos;
    private String profilePicture;
    private String uid;
    private ConcurrentHashMap<String, Paciente> mapaMisPacientes;

    public Medico() {
        mapaMisPacientes = new ConcurrentHashMap<>();
    }

    public Medico(String nombreYapellidos, String uid, String profilePicture) {
        this.nombreYapellidos= nombreYapellidos;
        this.uid = uid;
        this.profilePicture = profilePicture;

    }
    public boolean isPhotoReal(){
        return profilePicture == null;
    }

    public String getNombreYapellidos() {
        return nombreYapellidos;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getUid() {
        return uid;
    }

    public void setNombreYapellidos(String nombreYapellidos) {
        this.nombreYapellidos = nombreYapellidos;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public static char calcularLetraDNI(int dni) {
        final char[] vectorLetras = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};

        return vectorLetras[dni % 23];
    }

    public Paciente buscarPaciente(String dni) {
        return mapaMisPacientes.get(dni);

    }
    public void addPaciente(Paciente p){
        if(mapaMisPacientes.get(uid)!=null)
            mapaMisPacientes.put(uid, p);
    }

    public boolean eliminarPaciente(String uid) {
        Paciente p = mapaMisPacientes.get(uid);
        if (p != null)
            mapaMisPacientes.remove(uid);
        else
            return false;
        return true;

    }

    public ArrayList<Ingreso> ingresosPaciente(String uid) {
        Paciente p = mapaMisPacientes.get(uid);
        if (p != null)
            return p.getIngresos();
        return null;

    }

    public boolean ingresar(String uid, Ingreso unIngreso) {
        Paciente p = mapaMisPacientes.get(uid);

        if (p == null)
            return false;
        p.getIngresos().add(unIngreso);
        return true;
    }

    @Override
    public String toString() {



        return nombreYapellidos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Medico))
            return false;
        Medico medico = (Medico) o;
        return this.uid == medico.uid;
    }
}
