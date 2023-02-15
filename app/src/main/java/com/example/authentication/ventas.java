package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ventas extends AppCompatActivity {

    private EditText etCodigoProducto;
    private TextView tvNombreProducto,tvStockVentas,tvPrecioVenta,tvTotalPagar,tvAccion;
    private EditText etCantidad;
    private Button btnTotal,btnFinalizar,btnBuscar;
    String accion=null,id=null;
    DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas);
        etCodigoProducto=findViewById(R.id.etCodigoProductoVentas);
        tvNombreProducto=findViewById(R.id.tvNombreProductoVenta);
        tvStockVentas=findViewById(R.id.tvStockVentas);
        tvPrecioVenta=findViewById(R.id.tvPrecioVentas);
        tvTotalPagar=findViewById(R.id.tvTotalPagar);
        tvAccion=findViewById(R.id.tvAccion);
        btnTotal=findViewById(R.id.btnTotal);
        etCantidad=findViewById(R.id.etCantidad);
        btnFinalizar=findViewById(R.id.btnFinalizar);
        btnBuscar=findViewById(R.id.btnBuscarVentas);


        accion=getIntent().getStringExtra("accion");
        nameboton(accion);
        tvAccion.setText(accion);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id=etCodigoProducto.getText().toString();
                buscarProducto(id);
            }
        });
        btnTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calcularTotal();
            }
        });
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restarStock(id);
            }
        });
    }

    private void buscarProducto(String code){
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Productos");
        mDatabase.child(code).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()){
                    Log.e("firebase ","Error getting data",task.getException());
                }else{

                    String name=String.valueOf(task.getResult().child("nomproducto").getValue());
                    String stock=String.valueOf(task.getResult().child("stockproducto").getValue());
                    String costo=String.valueOf(task.getResult().child("precioproducto").getValue());
                    //String venta=String.valueOf(task.getResult().child("ventaproducto").getValue());
                    tvNombreProducto.setText(name);
                    tvStockVentas.setText(stock);
                    tvPrecioVenta.setText(costo);

                    Toast.makeText(getApplicationContext(),"Mis datos",Toast.LENGTH_LONG).show();
                    Log.d("firebase", "findUser");
                }
            }
        });
    }
    private void calcularTotal(){
        Double precio=Double.parseDouble(tvPrecioVenta.getText().toString());
        Double cantidad=Double.parseDouble(etCantidad.getText().toString());
        Double total=precio*cantidad;
        tvTotalPagar.setText(String.valueOf(total));
    }
    private void restarStock(String id){
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Productos");
        Double cantidad=Double.parseDouble(etCantidad.getText().toString());
        Double stock=Double.parseDouble(tvStockVentas.getText().toString());
        if(accion.equals("Ventas")){

            if(cantidad>stock){
                Toast.makeText(getApplicationContext(),"La cantidad excede el stock del producto", Toast.LENGTH_LONG).show();


            }else {
                Double totalStock = stock - cantidad;
                HashMap<String, Object> result = new HashMap<>();
                //result.put("id", usuario.getIdUsuario().toString());
                result.put("stockproducto", String.valueOf(totalStock));
                mDatabase.child(id).updateChildren(result);
                buscarProducto(id);
            }
        }else if(accion.equals("Compras")){
            Double totalStock = stock + cantidad;
            HashMap<String, Object> result = new HashMap<>();
            //result.put("id", usuario.getIdUsuario().toString());
            result.put("stockproducto", String.valueOf(totalStock));
            mDatabase.child(id).updateChildren(result);
            buscarProducto(id);
        }
}
private void nameboton(String accion){
 if(accion.equals("Ventas"))  {
     btnFinalizar.setText("Vender");
 }else{
     btnFinalizar.setText("Comprar");
 }
}
}