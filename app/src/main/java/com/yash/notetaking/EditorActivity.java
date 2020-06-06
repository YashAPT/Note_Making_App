package com.yash.notetaking;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.yash.notetaking.database.NoteEntity;
import com.yash.notetaking.utils.Constants;
import com.yash.notetaking.viewmodels.EditorViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

import static com.yash.notetaking.utils.Constants.EDITING_KEY;

public class EditorActivity extends AppCompatActivity {

    private EditorViewModel mViewModel;
    EditText mEditText;
    private boolean mNewNote;
    private boolean isEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mEditText = findViewById(R.id.edit_note_text);

        if (savedInstanceState != null) {
            isEditing = savedInstanceState.getBoolean(EDITING_KEY);
        }

        initViewModel();


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putBoolean(EDITING_KEY, true);

        super.onSaveInstanceState(outState);
    }

    private void initViewModel() {

        mViewModel = new ViewModelProvider(this)
                .get(EditorViewModel.class);

        mViewModel.mLiveNote.observe(this, new Observer<NoteEntity>() {
            @Override
            public void onChanged(NoteEntity noteEntity) {

                if (noteEntity != null && !isEditing) {
                    mEditText.setText(noteEntity.getText());
                }
            }
        });


        Bundle bundle = getIntent().getExtras();

        if (bundle == null) {
            setTitle("New Note");
            mNewNote = true;
        } else {
            setTitle("Edit Note");
            int noteId = bundle.getInt(Constants.NOTE_ID_KEY);
            mViewModel.loadNote(noteId);
            mNewNote = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (!mNewNote) {
            getMenuInflater().inflate(R.menu.menu_editor_, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            saveAndExit();
        } else if (item.getItemId() == R.id.action_delete_note) {
            deleteNote();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteNote() {

        mViewModel.deleteNote();
        Toast.makeText(this, "NOTE DELETED", Toast.LENGTH_SHORT).show();
        finish();

    }

    private void saveAndExit() {
        mViewModel.saveAndExit(mEditText.getText().toString());
        Toast.makeText(this, "NOTE SAVED", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveAndExit();
    }
}

