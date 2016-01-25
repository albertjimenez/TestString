package modelo.modelo.paciente;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Paciente implements Serializable {

    // Atributos
    private String nombre;
    private String apellidos;
    private int SIP; // 8 cifras

    private Calendar fechaNacimiento;
    private String sexo;
    private String estado;
    private String poblacion;
    private String provincia;
    private int CP;
    private String doctor;
    private List<Ingreso> ingresos = new ArrayList<Ingreso>();
    private String tipoIngreso;

    /*
    *
    * Permite construir un paciente. Es obligado usar todos los parametros
    *
    *
     */
    // Constructor
    public Paciente(String nombre, String apellidos, int sIP, Calendar fechaNacimiento, String sexo, String estado,
                    String poblacion, String provincia, int cP, String doctor) {
        super();
        this.nombre = nombre;
        this.apellidos = apellidos;
        SIP = sIP;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.estado = estado;
        this.poblacion = poblacion;
        this.provincia = provincia;
        CP = cP;
        this.doctor = doctor;
        // this.tipoIngreso = tipoIngreso;
        // ++Paciente.numHistoria;

        // ingresos.add(new Ingreso(ingreso, tipoIngreso));
    }

    // metodos
    /*
    * Compara si dos pacientes son iguales
    * @parametros Un objeto que suponemos tipo Paciente
    *
    * @return Verdadero falso segun el resultado
     */
    @Override
    public boolean equals(Object obj) {
        try {
            Paciente p = (Paciente) obj;
            return this.SIP == p.SIP;
        } catch (Exception e) {
            return false;
        }

    }

    // @Override
    public String toString2() {
        SimpleDateFormat formateador = new SimpleDateFormat("dd'/'MM'/'yyyy", new Locale("es_ES"));
        String ing = "";
        if (ingresos.isEmpty())
            ing = "No hay ingresos registrados";
        else
            ing = ingresos.toString();
        return "\nNombre: " + nombre + "\nApellidos: " + apellidos + "\nSIP: " + SIP + "\nFecha de nacimiento: "
                + formateador.format(fechaNacimiento.getTime()) + "\nSexo: " + sexo + "\nEstado: " + estado
                + "\nPoblación: " + poblacion + "\nProvíncia: " + provincia + "\nCP: " + CP + "\nDoctor: " + doctor
                + "\n======================\n: " + ing;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public int getCP() {
        return CP;
    }

    public void setCP(int cP) {
        CP = cP;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public List<Ingreso> getIngresos() {
        return ingresos;
    }

    public String toStringAntiIngresos() {
        SimpleDateFormat formateador = new SimpleDateFormat("dd'/'MM'/'yyyy", new Locale("es_ES"));
        return "Nombre: " + nombre + " Apellidos: " + apellidos + " SIP: " + SIP + " Fecha de nacimiento: "
                + formateador.format(fechaNacimiento.getTime()) + " Sexo: " + sexo + " Estado: " + estado
                + " Población: " + poblacion + " Província: " + provincia + " CP: " + CP + " Doctor: " + doctor;
    }

    public String getTipoIngreso() {
        return tipoIngreso;
    }

	/*@Override
    public int compareTo(Paciente o) {
		if (this.getApellidos().compareTo(o.getApellidos()) > 1)
			return 1;
		else if (this.getApellidos().compareTo(o.getApellidos()) < 1)
			return -1;
		return 0;
	}*/
}
