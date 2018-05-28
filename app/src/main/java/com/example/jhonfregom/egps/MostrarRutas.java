package com.example.jhonfregom.egps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class MostrarRutas extends AppCompatActivity {

    RecyclerView rv;
    List<GPSActivity>ubicacion;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_rutas);
        rv =(RecyclerView) findViewById(R.id.recycler);

        rv.setLayoutManager(new LinearLayoutManager(this));

        ubicacion = new ArrayList<>();

        FirebaseDatabase mDatabase= FirebaseDatabase.getInstance();

        adapter = new Adapter(ubicacion);

        rv.setAdapter(adapter);

       mDatabase.getReference().getRoot().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ubicacion.removeAll(ubicacion);
                for(DataSnapshot snapshot:
                        dataSnapshot.getChildren()){
                    GPSActivity gps = snapshot.getValue(GPSActivity.class);
                    ubicacion.add(gps);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
