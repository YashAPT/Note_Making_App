package com.yash.notetaking;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yash.notetaking.database.NoteEntity;
import com.yash.notetaking.model.NotesAdapter;
import com.yash.notetaking.utils.SampleDataProvider;
import com.yash.notetaking.viewmodels.ListActivityViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private ListActivityViewModel mViewModel;
    NotesAdapter mNotesAdapter;

    RecyclerView mRecyclerView;
    FloatingActionButton fab;
    private List<NoteEntity> mNoteList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mRecyclerView = findViewById(R.id.recycler_view);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        fab = findViewById(R.id.fab_add_note);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Main2Activity.this, EditorActivity.class);
                startActivity(i);
            }
        });


        initRecyclerView();
        initViewModel();


    }

    private void initViewModel() {
        Observer<List<NoteEntity>> notesObserver = new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(List<NoteEntity> noteEntities) {

                mNoteList.clear();
                mNoteList.addAll(noteEntities);

                if (mNotesAdapter == null) {
                    mNotesAdapter = new NotesAdapter(Main2Activity.this, mNoteList);
                    mRecyclerView.setAdapter(mNotesAdapter);
                } else {
                    mNotesAdapter.notifyDataSetChanged();
                }
            }
        };

        mViewModel = new ViewModelProvider(this)
                .get(ListActivityViewModel.class);

        mViewModel.mNotesList.observe(Main2Activity.this, notesObserver);

    }


    private void initRecyclerView() {
        mRecyclerView.hasFixedSize();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),layoutManager
        .getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        ItemTouchHelper itemTouchHelper =new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                deleteNote(mNotesAdapter.getNoteAtPosition(viewHolder.getAdapterPosition()));
            }
        });
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    private void deleteNote(NoteEntity noteEntity) {
        mViewModel.deleteNote(noteEntity);
        Toast.makeText(this, "NOTE DELETED", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.id.add_sample_data: {
                addSampleData();
                return true;
            }
            case R.id.delete_all_data: {
                deleteAllData();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAllData() {
        mViewModel.deleteAllData();
    }

    private void addSampleData() {

        mViewModel.addSampleData();
    }


}
