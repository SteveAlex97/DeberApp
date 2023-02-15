package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Producto extends AppCompatActivity {

    private EditText codigo,pronombre,stock,precosto,preventa;
    private Button guardar,actualizar,buscar,borrar,regresar;
    FirebaseAuth mAuth;

    private DatabaseReference mDatabase;

    productos producto;
    String codproducto;
    String nomproducto;
    String stockproducto;
    String precioproducto;
    String ventaproducto;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        mAuth=FirebaseAuth.getInstance();


        codigo=(EditText) findViewById(R.id.etcodigo);
        pronombre=(EditText)findViewById(R.id.etnombreproducto);
        stock=(EditText) findViewById(R.id.etstock);
        precosto=(EditText) findViewById(R.id.etprecosto);
        preventa=(EditText)findViewById(R.id.etpreventa);
        guardar=(Button) findViewById(R.id.btnguardar);
        actualizar=(Button) findViewById(R.id.btnactualizar);
        buscar=(Button) findViewById(R.id.btnbuscar);
        borrar=(Button) findViewById(R.id.btnborrar);
        regresar=(Button) findViewById(R.id.btnregresar);


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenervalores();
                nuevoProducto(producto);

            }
        });
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenervalores();
                actualizarProducto();

            }
        });
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code=codigo.getText().toString();
                buscarProducto(code);

            }
        });
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code=codigo.getText().toString();
                borrarProducto(code);

            }
        });
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Producto.this, "Sesion cerrada", Toast.LENGTH_SHORT).show();
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
    private productos obtenervalores() {
        codproducto = codigo.getText().toString();
        nomproducto = pronombre.getText().toString();
        stockproducto=stock.getText().toString();
        precioproducto=precosto.getText().toString();
        ventaproducto=preventa.getText().toString();
        producto=new productos(codproducto,nomproducto,stockproducto,precioproducto,ventaproducto);
        return producto;

    }
    private void nuevoProducto(productos producto){
        String codePush=producto.getCodproducto();
        mDatabase=FirebaseDatabase.getInstance().getReference().child("Productos");
        HashMap<String,String> result=new HashMap<>();
        result.put("codproducto",producto.getCodproducto().toString());
        result.put("nomproducto",producto.getNomproducto().toString());
        result.put("stockproducto",producto.getStockproducto().toString());
        result.put("precioproducto",producto.getPrecioproducto().toString());
        result.put("ventaproducto",producto.getVentaproducto().toString());
        mDatabase.child(codePush).setValue(result);
    }
    private void actualizarProducto(){
        mDatabase=FirebaseDatabase.getInstance().getReference().child("Productos");
        String codePush=producto.getCodproducto();
        HashMap<String,Object>result=new HashMap<>();
        result.put("nomproducto",producto.getNomproducto().toString());
        result.put("stockproducto",producto.getStockproducto().toString());
        result.put("precioproducto",producto.getPrecioproducto().toString());
        result.put("ventaproducto",producto.getVentaproducto().toString());
        mDatabase.child(codePush).updateChildren(result);
    }
    private void buscarProducto(String code){
        mDatabase=FirebaseDatabase.getInstance().getReference().child("Productos");
        mDatabase.child(code).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()){
                    Log.e("firebase","Error getting data",task.getException());
                }else{
                    String name=String.valueOf(task.getResult().child("nomproducto").getValue());
                    String stockp=String.valueOf(task.getResult().child("stockproducto").getValue());
                    String costo=String.valueOf(task.getResult().child("precioproducto").getValue());
                    String venta=String.valueOf(task.getResult().child("ventaproducto").getValue());
                    pronombre.setText(name);
                    stock.setText(stockp);
                    precosto.setText(costo);
                    preventa.setText(venta);
                    Toast.makeText(Producto.this, "Mis datos", Toast.LENGTH_SHORT).show();
                    Log.d("firebase","findUser");

                }

            }
        });
    }
    private void borrarProducto(String code){
        mDatabase=FirebaseDatabase.getInstance().getReference().child("Productos");
        mDatabase.child(code).removeValue();
        Toast.makeText(getApplicationContext(),"check the console",Toast.LENGTH_SHORT).show();

    }
}