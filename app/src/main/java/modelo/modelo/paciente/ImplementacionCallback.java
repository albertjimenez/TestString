package modelo.modelo.paciente;

/**
 * Created by Beruto on 15/8/16.
 */
public class ImplementacionCallback {
    private InterfazCallback evento;

    public ImplementacionCallback(InterfazCallback evento){
        this.evento = evento;

    }

    public void hazCosas(Medico medico){
        evento.notificame(medico);
    }
}
