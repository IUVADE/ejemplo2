package com.iuvade.iuvade3.ejemplo2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //se establece el boton para que pueda hacer la conexion a la clase Maps
    private Button enlace;
    private static int REQUEST_CODE_FINE_GPS = 8889;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.enlace).setOnClickListener(this);
        if(!checkPermisos()) {
            perdirPermisos();
        }
    }

    private boolean checkPermisos() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void perdirPermisos(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_CODE_FINE_GPS);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.enlace:
                if(checkPermisos()){
                    Intent inten = new Intent(LoginActivity.this, Maps.class);
                    inten.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(inten);
                }
                else {
                    perdirPermisos();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_FINE_GPS) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(),
                        "Application will not run without location services!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
