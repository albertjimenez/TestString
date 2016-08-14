package modelo.modelo.paciente;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class Paciente implements Serializable {

    // Atributos
    private String nombre;
    private String apellidos;
    private int SIP; // 8 cifras
    private Calendar fechaNacimiento;
    private String sexo;
    private int CP;
    private List<Ingreso> ingresos = new ArrayList<Ingreso>();
    //private String tipoIngreso;
    private boolean estaIngresado;

    /*
    *
    * Permite construir un paciente. Es obligado usar todos los parametros
    *
    *
     */
    // Constructor
    public Paciente(){

    }
    public Paciente(String nombre, String apellidos, int sIP, GregorianCalendar fechaNacimiento, String sexo,
                    int cP) {
        super();
        this.nombre = nombre;
        this.apellidos = apellidos;
        SIP = sIP;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        CP = cP;
        this.estaIngresado = false;

    }

    // metodos
    /*
    * Compara si dos pacientes son iguales
    * @parametros Un objeto que suponemos tipo Paciente
    *
    * @return Verdadero falso segun el resultado
     */
//    public boolean equals(Object obj) {
//        try {
//            Paciente p = (Paciente) obj;
//            return this.SIP == p.SIP;
//        } catch (Exception e) {
//            return false;
//        }
//
//    }

    // @Override
    public String toString2() {
        SimpleDateFormat formateador = new SimpleDateFormat("dd'/'MM'/'yyyy", new Locale("es_ES"));
        String ing = "";
        if (ingresos.isEmpty())
            ing = "No hay ingresos registrados";
        else
            ing = ingresos.toString();
        return "\nNombre: " + nombre + "\nApellidos: " + apellidos + "\nSIP: " + SIP + "\nFecha de nacimiento: "
                + formateador.format(fechaNacimiento.getTime()) + "\nSexo: " + sexo
                + "\nCP: " + CP + "\n======================\n: " + ing;
    }

    @Override
    public String toString() {
        return this.nombre + " " + this.apellidos + " SIP: " + this.SIP;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getSIP() {
        return SIP;
    }

    public Calendar getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Calendar fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getCP() {
        return CP;
    }

    public void setCP(int cP) {
        CP = cP;
    }


    public List<Ingreso> getIngresos() {
        return ingresos;
    }

    public boolean isEstaIngresado() {
        return estaIngresado;
    }

    public void setEstaIngresado(boolean estaIngresado) {
        this.estaIngresado = estaIngresado;
    }

    public String toStringAntiIngresos() {
        SimpleDateFormat formateador = new SimpleDateFormat("dd'/'MM'/'yyyy", new Locale("es_ES"));
        return "Nombre: " + nombre + " Apellidos: " + apellidos + " SIP: " + SIP + " Fecha de nacimiento: "
                + formateador.format(fechaNacimiento.getTime()) + " Sexo: " + sexo
                +" CP: " + CP ;
    }


    public boolean equals(Object obj){
        try {
            Paciente p = (Paciente) obj;
            return this.nombre.equals(p.nombre)&&
                    this.apellidos.equals(p.apellidos) &&
                    this.SIP == p.SIP &&
                    this.fechaNacimiento.equals(p.fechaNacimiento) &&
                    this.sexo.equals(p.sexo)&&
                    this.CP == p.CP &&
                    this.ingresos.equals(p.ingresos) &&
                    this.estaIngresado == p.estaIngresado;
        } catch (Exception e) {
            return false;
        }

    }

}
