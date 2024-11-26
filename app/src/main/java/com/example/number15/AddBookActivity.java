package com.example.number15;

import android.os.Bundle;
import android.view.View;
import com.example.number15.DatabaseHelper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddBookActivity extends AppCompatActivity {

    private EditText etBookName;
    private EditText etAuthorName;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        etBookName = findViewById(R.id.etBookName);
        etAuthorName = findViewById(R.id.etAuthorName);
        btnSave = findViewById(R.id.btnSave);

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        btnSave.setOnClickListener(v -> {
            String bookName = etBookName.getText().toString().trim();
            String authorName = etAuthorName.getText().toString().trim();

            if (bookName.isEmpty() || authorName.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            dbHelper.insertBook(bookName, authorName);
            Toast.makeText(this, "Данные добавлены", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
