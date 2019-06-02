package com.example.bg.examplefirebaseui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity
{
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference noteBookRef = db.collection("Shenimet");

    private ShenimetAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton btnShtoShenimet = findViewById(R.id.button_add_note);
        btnShtoShenimet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ShenimetActivity.class));
            }
        });

        setUpRecyclerView();
    }

    private void setUpRecyclerView()
    {
        Query query = noteBookRef.orderBy("prioriteti", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Shenimet> opsionet = new FirestoreRecyclerOptions.Builder<Shenimet>().setQuery(query, Shenimet.class).build();
        adapter = new ShenimetAdapter(opsionet);

        RecyclerView rv = findViewById(R.id.recycler_view);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction)
            {
                adapter.fshijShenimet(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(rv);
        adapter.setOnItemClickListener(new ShenimetAdapter.onItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int pozita) {
                Shenimet shenimet = documentSnapshot.toObject(Shenimet.class);
                String id = documentSnapshot.getId();
                String shtegu = documentSnapshot.getReference().getPath();
                Toast.makeText(MainActivity.this, "Pozita e shenimit: " + pozita + "\n" + "ID: " + id, Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
