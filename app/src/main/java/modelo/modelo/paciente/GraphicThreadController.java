package modelo.modelo.paciente;

import android.graphics.Bitmap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Beruto on 25/8/16.
 */
public class GraphicThreadController {

    public static void actualizaTexto(final TextView unTexto, final String cadena){
        unTexto.post(new Runnable() {
            @Override
            public void run() {
                unTexto.setText(cadena);
            }
        });
    }

    public static void actualizaImagen(final ImageView unaImagen, final Bitmap bitmap){
        unaImagen.post(new Runnable() {
            @Override
            public void run() {
                unaImagen.setImageBitmap(bitmap);
            }
        });
    }
}
