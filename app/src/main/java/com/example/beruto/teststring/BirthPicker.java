package com.example.beruto.teststring;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

import modelo.modelo.paciente.Paciente;

/**
 * Created by Beruto on 26/8/16.
 */
public class BirthPicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private Paciente p;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        final Calendar c = Calendar.getInstance();
        c.set(year,monthOfYear,dayOfMonth);
        p.setFechaNacimiento(c);
    }

    public void setPaciente(Paciente p) {
        this.p = p;
    }
}
