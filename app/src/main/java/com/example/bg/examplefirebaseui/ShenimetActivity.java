package com.example.bg.examplefirebaseui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ShenimetActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shenimet);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Note");

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        numberPickerPriority = findViewById(R.id.number_picker_priority);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.shenimet_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                ruajShenimet();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void ruajShenimet() {
        String titulli = editTextTitle.getText().toString();
        String pershkrimi = editTextDescription.getText().toString();
        int prioriteti = numberPickerPriority.getValue();

        if (titulli.trim().isEmpty() || pershkrimi.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        CollectionReference notebookRef = FirebaseFirestore.getInstance()
                .collection("Shenimet");
        notebookRef.add(new Shenimet(titulli, pershkrimi, prioriteti));
        Toast.makeText(this, "Shenimet u shtuan", Toast.LENGTH_SHORT).show();
        finish();
    }
}