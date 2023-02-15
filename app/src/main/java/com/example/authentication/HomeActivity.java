package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    private String user,id;
    private TextView tvid;

    private EditText cedula, nombre, provincia;
    private TextView correo;
    private RadioButton hombre, mujer;
    private Spinner spPais;
    private Button  actualizar, salir;
    private FirebaseAuth mAuth;
    private DatabaseReference UserRef;
    private ProgressDialog dialog;
    private String CurrentUserId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth=FirebaseAuth.getInstance();
        CurrentUserId = mAuth.getCurrentUser().getUid();
        UserRef=FirebaseDatabase.getInstance().getReference();
        dialog=new ProgressDialog(this);


        cedula = (EditText) findViewById(R.id.etcedula);
        nombre = (EditText) findViewById(R.id.etnombres);
        provincia = (EditText) findViewById(R.id.etprovincia);
        correo = (TextView) findViewById(R.id.etcorreo);
        hombre = (RadioButton) findViewById(R.id.rbtnhombre);
        mujer = (RadioButton) findViewById(R.id.rbtnmujer);
        spPais = (Spinner) findViewById(R.id.spPais);
        salir=(Button) findViewById(R.id.btnsalir) ;
        actualizar = (Button) findViewById(R.id.btnActualizar);
        tvid=(TextView)findViewById(R.id.tvid);

        String[] opciones={"Ecuador","Colombia","Perú","Brasil"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
        spPais.setAdapter(adapter);
        id=getIntent().getStringExtra("id");
        tvid.setText(id);
        buscaruser(id);


        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarinformacion(id);

            }


        });

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(HomeActivity.this, "Sesion cerrada", Toast.LENGTH_SHORT).show();
                gologin();


            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            gologin();
        }
    }

    private void gologin() {
        mAuth.signOut();
        Intent i = new Intent(this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
    private void actualizarinformacion(String id) {
        UserRef=FirebaseDatabase.getInstance().getReference();

        String ced=cedula.getText().toString();
        String nom=nombre.getText().toString();
        String prov=provincia.getText().toString();
        String sexo=sexo();
        String pais=spPais.getSelectedItem().toString();
        HashMap<String,Object> usuario=new HashMap<>();
        usuario.put("cedula",ced);
        usuario.put("nombre",nom);
        usuario.put("sexo",sexo);
        usuario.put("pais",pais);
        usuario.put("provincia",prov);
        UserRef.child("Usuarios").child(id).updateChildren(usuario);




    }
    private void buscaruser(String id){
        UserRef=FirebaseDatabase.getInstance().getReference().child("Usuarios");
        UserRef.child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    String cedul=String.valueOf(task.getResult().child("cedula").getValue());
                    String nombr=String.valueOf(task.getResult().child("nombre").getValue());

                    String sex=String.valueOf(task.getResult().child("sexo").getValue());
                    String pai=String.valueOf(task.getResult().child("pais").getValue());
                    String provinci=String.valueOf(task.getResult().child("provincia").getValue());
                    String corre=String.valueOf(task.getResult().child("correo").getValue());

                    cedula.setText(cedul);
                    nombre.setText(nombr);
                    getSexo(sex);
                    getPais(pai);
                    provincia.setText(provinci);
                    correo.setText(corre);



                }
            }
        });

    }
    private void getPais(String pais){
        switch (pais){
            case "Ecuador":
                spPais.setSelection(0);
                break;
            case "Colombia":
                spPais.setSelection(1);
                break;
            case "Perú":
                spPais.setSelection(2);
                break;
            case "Brasil":
                spPais.setSelection(3);
                break;

        }
       // Ecuador","Colombia","Perú","Brasil
    }
    private void getSexo(String sexo){
        if (sexo.equals("Hombre")){
            hombre.setChecked(true);
            mujer.setChecked(false);
        } else {
            hombre.setChecked(false);
            mujer.setChecked(true);
        }

    }

    private String sexo() {
        String sexo = "";
        if (hombre.isChecked() == true) {
            sexo = "Hombre";
        } else if (mujer.isChecked() == true) {
            sexo = "Mujer";
        }
        return sexo;
    }
}