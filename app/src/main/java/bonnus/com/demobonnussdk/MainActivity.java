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
                "E5uL2TCGweNnbn0FayON74jkkffA1PwI",
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6Ik1qUTFNVVJEUVVWRlEwUXpRekl3TXpNek9EVkROa1ZHUkRKRk16Y3lSREU1UWtNelJrTkNRUSJ9.eyJpc3MiOiJodHRwczovL3ZhbG1hbnMuYXV0aDAuY29tLyIsInN1YiI6InhTYzdraFBPdE8xMFMzUG0xMlFTRHM4R3JvOVNuZWJnQGNsaWVudHMiLCJhdWQiOiJodHRwOi8vYm9ubnVzYXBpLmF6dXJld2Vic2l0ZXMubmV0L2FwaSIsImlhdCI6MTUxMTQwNDI0MCwiZXhwIjoxNTI2OTU2MjQwLCJzY29wZSI6Im9wZW5pZDpwcm9maWxlOmVtYWlsIG9wZW5pZCIsImd0eSI6ImNsaWVudC1jcmVkZW50aWFscyJ9.UQtiSqK2gwBS2EA-vYjPK3941BZe5u-eEeyGRraWO5XMo_kz8e1f92q1I9giqXyhM1FfMCSV-bSdKW8Rvaw3M-J7tqMW_yx0y20YkJj5-rUiWjNBW5PG_7wgNqIDdDXjgIPp2Sid9O0-u5uS5iOn03l9cEfM75kMGjqoPDoFHURb2QOKeXXUzqqJR7Ax3BhSEn4cZBda_JbgTeC-9JC6CeBVLkv8-r463t7IUkrfC44iEKEH27eW3_9fGN_RWUB9pwOYf-APyMKYXEDx0MEOiG1brkNWvfG_1r0ot9z1O_5aodXyhHd22UpllVVlzQNCKmU2hvhSc8nGJcDiLnvS_w",
                "51b30e60eab944eabac3d91e04fb006f");

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
