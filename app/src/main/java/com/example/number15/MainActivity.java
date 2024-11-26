package com.example.number15;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import com.example.number15.BooksAdapter;




public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button btnAddData;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerView);
        btnAddData = findViewById(R.id.btnAddData);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateRecyclerView();

        btnAddData.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateRecyclerView();
    }

    private void updateRecyclerView() {
        List<String> books = new ArrayList<>();
        List<Integer> bookIds = new ArrayList<>();

        Cursor cursor = dbHelper.getAllBooksCursor();

        if (cursor != null) {

            while (cursor.moveToNext()) {
                String bookName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME));
                String authorName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AUTHOR));
                int bookId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));

                books.add(bookName + " - " + authorName);
                bookIds.add(bookId);
            }
            cursor.close();
        }


        BooksAdapter adapter = new BooksAdapter(this, books, bookIds);
        recyclerView.setAdapter(adapter);
    }

}
