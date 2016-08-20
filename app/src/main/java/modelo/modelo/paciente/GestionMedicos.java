package modelo.modelo.paciente;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Beruto on 18/8/16.
 */
public class GestionMedicos implements Serializable {

    private ConcurrentHashMap<String, Medico> mapaMedicos;
    private Gson gson;

    public GestionMedicos() {
        mapaMedicos = new ConcurrentHashMap<>();
        gson = new Gson();
    }

    public GestionMedicos(ConcurrentHashMap<String, Medico> mapaMedicos) {
        this.mapaMedicos = mapaMedicos;
    }
    public boolean addMedico(Medico m, Paciente p){

        if(!mapaMedicos.isEmpty() && !mapaMedicos.containsKey(m.getUid())){
            mapaMedicos.put(m.getUid(), m);
            m.addPaciente(p);
            return true;
        }
        return false;
    }

}
