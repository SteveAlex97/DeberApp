package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import com.example.authentication.Adapter.myAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListaProductos extends AppCompatActivity {
    private Button regresar;

    private RecyclerView recyclerView;
    private Toolbar customToobar;
    ArrayList<productos> list;
    DatabaseReference mDatabase;
    myAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);
        regresar=findViewById(R.id.btnRegresarLista);

        recyclerView = findViewById(R.id.rvListaProductos);
        customToobar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(customToobar);
        mDatabase = FirebaseDatabase.getInstance().getReference("Productos");
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new myAdapter(this,list);
        recyclerView.setAdapter(adapter);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Inventario.class);
                startActivity(i);
            }
        });
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    productos product = dataSnapshot.getValue(productos.class);
                    list.add(product);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menuoverflow,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ventas) {

            Intent i = new Intent(getApplicationContext(), ventas.class);
            i.putExtra("accion", "Ventas");
            startActivity(i);
        } else if (id == R.id.compras) {

            Intent i = new Intent(getApplicationContext(), ventas.class);
            i.putExtra("accion", "Compras");
            startActivity(i);
        }else if (id==R.id.lista_productos){
            Intent intent = new Intent(this, ListaProductos.class);
            startActivity(intent);
        } else if (id == R.id.cerrar) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


}