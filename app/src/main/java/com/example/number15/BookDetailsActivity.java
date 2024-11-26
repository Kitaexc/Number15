package com.example.number15;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;

import androidx.appcompat.app.AppCompatActivity;

public class BookDetailsActivity extends AppCompatActivity {

    private TextView tvBookName, tvAuthorName;
    private Button btnDelete, btnEdit;
    private DatabaseHelper dbHelper;
    private int bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        dbHelper = new DatabaseHelper(this);

        tvBookName = findViewById(R.id.tvBookName);
        tvAuthorName = findViewById(R.id.tvAuthorName);
        btnDelete = findViewById(R.id.btnDelete);
        btnEdit = findViewById(R.id.btnEdit);

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
            tvBookName.setText(bookName);
            tvAuthorName.setText(authorName);
            cursor.close();
        } else {
            Toast.makeText(this, "Книга не найдена", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnDelete.setOnClickListener(v -> {
            dbHelper.deleteBook(bookId);
            Toast.makeText(this, "Книга удалена", Toast.LENGTH_SHORT).show();
            finish();
        });

        btnEdit.setOnClickListener(v -> {
            Intent editIntent = new Intent(BookDetailsActivity.this, EditBookActivity.class);
            editIntent.putExtra("BOOK_ID", bookId);
            startActivity(editIntent);
        });
    }
}
