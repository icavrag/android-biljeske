package com.cigo7live.biljeske;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cigo7live.biljeske.adapteri.biljeskeAdapter;
import com.cigo7live.biljeske.baza_podataka.biljeskedb;
import com.cigo7live.biljeske.modeli.podaci;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    biljeskeAdapter adapter;
    LinearLayoutManager layoutManager;
    TextView praznaLista;

    Context context;
    ArrayList<podaci> lista;
    biljeskedb baza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        praznaLista = (TextView) findViewById(R.id.praznaLista);
        recyclerView = (RecyclerView) findViewById(R.id.main_recyclerView);

        baza = new biljeskedb(this);
        lista = new ArrayList<>();

        //Adapter
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        adapter = new biljeskeAdapter(this, lista);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume(){
        super.onResume();

        lista = baza.getbiljeska();
        adapter.setItemList(lista);
        adapter.notifyDataSetChanged();

        if(lista.isEmpty()){
            praznaLista.setVisibility(View.VISIBLE);
            praznaLista.setText("Lista je prazna");
        } else {
            praznaLista.setVisibility(View.GONE);
        }

        //Toast.makeText(this, "List size: " + lista.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (id == R.id.main_menu_add) {
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra("intent", "add");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


}
