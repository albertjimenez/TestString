package modelo.modelo.paciente;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Ingreso implements Serializable {
	// Atributos
	private String motivo;
	private Calendar fechaIngreso;
	private String tipo;

	// Constructor

	public Ingreso(String motivo, String tipo) {
		this.motivo = motivo;
		fechaIngreso = Calendar.getInstance();
		this.tipo = tipo;

	}

	@Override
	public String toString() {
		SimpleDateFormat formateador = new SimpleDateFormat("dd'/'MM'/'yyyy",
				new Locale("es_ES"));
		return "Fecha de Ingreso: "
				+ formateador.format(fechaIngreso.getTime()) + "\nMotivo: "
				+ motivo + "\nTipo: " + tipo + "\n";
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Calendar getFechaIngreso() {
		return fechaIngreso;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


}
