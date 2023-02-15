package com.example.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Inventario extends AppCompatActivity {
    private Toolbar customToobar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);
        customToobar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(customToobar);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menuoverflow,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ventas) {
            Toast.makeText(this, "ventas", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), ventas.class);
            i.putExtra("accion", "Ventas");
            startActivity(i);
        } else if (id == R.id.compras) {
            Toast.makeText(this, "Compras", Toast.LENGTH_SHORT).show();
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