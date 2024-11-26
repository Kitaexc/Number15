package com.example.number15;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.database.Cursor;

import androidx.appcompat.app.AppCompatActivity;

public class EditBookActivity extends AppCompatActivity {

    private EditText etBookName, etAuthorName;
    private Button btnSaveChanges;
    private DatabaseHelper dbHelper;
    private int bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        dbHelper = new DatabaseHelper(this);

        etBookName = findViewById(R.id.etBookName);
        etAuthorName = findViewById(R.id.etAuthorName);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        Intent intent = getIntent();
        bookId = intent.getIntExtra("BOOK_ID", -1);

        if (bookId == -1) {
            Toast.makeText(this, "Ошибка: ID книги не найден", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Cursor cursor = dbHelper.getBookById(bookId);
        if (cursor != null && cursor.moveToFirst()) {
            String bookName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME));
            String authorName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AUTHOR));
            etBookName.setText(bookName);
            etAuthorName.setText(authorName);
            cursor.close();
        } else {
            Toast.makeText(this, "Книга не найдена", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnSaveChanges.setOnClickListener(v -> {
            String newBookName = etBookName.getText().toString().trim();
            String newAuthorName = etAuthorName.getText().toString().trim();

            if (newBookName.isEmpty() || newAuthorName.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            dbHelper.updateBook(bookId, newBookName, newAuthorName);
            Toast.makeText(this, "Изменения сохранены", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
