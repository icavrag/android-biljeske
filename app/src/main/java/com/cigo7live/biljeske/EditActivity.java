package com.cigo7live.biljeske;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cigo7live.biljeske.baza_podataka.biljeskedb;
import com.cigo7live.biljeske.modeli.podaci;

public class EditActivity extends AppCompatActivity {

    EditText editText;
    EditText editText_naslov;
    biljeskedb biljeskeDB;
    podaci p;
    String intent;
    String p_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        if ( getSupportActionBar() != null ) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        editText = (EditText) findViewById(R.id.edit_editText);
        editText_naslov = (EditText) findViewById(R.id.edit_editText_label);

        biljeskeDB = new biljeskedb(this);

        if( getIntent().getStringExtra("intent") != null && getIntent().getStringExtra("intent").equals("add") ) {
            //add logic
            p = new podaci();
            intent = "add";
        }
        if( getIntent().getStringExtra("intent") != null && getIntent().getStringExtra("intent").equals("edit") ) {
            //edit logic
            p_id = getIntent().getStringExtra("id");
            intent = "edit";
            p = biljeskeDB.getJedanPodatak(p_id);
            editText_naslov.setText(p.getNaslov());
            editText.setText(p.getBiljeska());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_menu, menu);

        if( intent.equals("add") ) {
            menu.getItem(0).setVisible(false);
        }

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
        if (id == R.id.edit_finish) {
            //TODO provjeri prazan string

            if( p != null ){
                p.setBiljeska(editText.getText().toString());
                if(intent.equals("edit") ) {
                    p.setNaslov(editText_naslov.getText().toString());
                    biljeskeDB.update(p);
                }
                else if ( intent.equals("add") ){
                    p.setNaslov(editText_naslov.getText().toString());
                    biljeskeDB.dodaj(p);
                }
                finish();
                return true;
            }
        }

        if( id == R.id.edit_delete ) {
            biljeskeDB.obrisi(p_id);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
