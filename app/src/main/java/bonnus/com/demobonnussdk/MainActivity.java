package bonnus.com.demobonnussdk;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;

import mx.aidc.bonnussdk.Bonnus;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Se hace esta llamada para hacer setup del SDK. Se debe hacer solo una vez cada que corra la app
        Bonnus.getInstance().initWithCredentials(getApplicationContext(),
                "PartnerId",
                "Token",
                "SDKid");

        TelephonyManager manager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String carrierName = manager.getNetworkOperatorName();
        String manufacturer = Build.MANUFACTURER;

        TextView carrierView = (TextView)findViewById(R.id.textView);
        TextView manufacturerView = (TextView)findViewById(R.id.textView2);

        carrierView.setText("Carrier: "+carrierName);
        manufacturerView.setText("Manufactured: "+manufacturer);

    }

    public void onResume(){
        super.onResume();

        //Esta llamada debe hacerse cada que entremos a una actividad, para poder poner elementos graficos. Las dos lineas.
        View topView = getWindow().getDecorView().findViewById(android.R.id.content);
        Bonnus.getInstance().registerActivityForPopup(topView, MainActivity.this);
    }

    public void onStop(){
        super.onStop();

        //Esto se debe hacer cada vez que salgamos de una actividad registrada
        Bonnus.getInstance().unregisterActivityForPopup();
    }

    public void process(View view) {

        String tag = view.getTag().toString();

        //Este metodo nos permite registrar una funcion. Mandamos un texto dependiendo de la accion.
        //Si se termino las veces necesarias para la accion, mostrara un banner con la promocion
        Bonnus.getInstance().triggerAction(tag);
    }

    public void recompensas(View view) {

        Bonnus.getInstance().showEarnedBonnusList();

    }
}
