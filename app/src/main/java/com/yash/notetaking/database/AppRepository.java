package com.yash.notetaking.database;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.yash.notetaking.utils.SampleDataProvider;

import java.net.ContentHandler;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static android.content.ContentValues.TAG;

public class AppRepository {

    private static AppRepository ourInstance;

    public LiveData<List<NoteEntity>> mNotesList;

    private AppDatabase mDatabase;

    private Executor mExecutor = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context) {

        return ourInstance = new AppRepository(context);
    }

    private AppRepository(Context context) {

        mDatabase = AppDatabase.getInstance(context);
        mNotesList = getAllNotes();
    }

    public void addSampleData() {

        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.notesDao().insertAll(SampleDataProvider.getSampleData());
            }
        });

    }

    private LiveData<List<NoteEntity>> getAllNotes() {
        return mDatabase.notesDao().getAllNotes();
    }

    public void deleteAllData() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int notes = mDatabase.notesDao().deleteAllNotes();

            }
        });
    }

    public NoteEntity loadNote(int noteId) {

        return mDatabase.notesDao().getNoteById(noteId);

    }

    public void insertNote(final NoteEntity noteEntity) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.notesDao().insertNote(noteEntity);
            }
        });
    }

    public void deleteNote(final NoteEntity noteEntity) {

        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.notesDao().deleteNode(noteEntity);
            }
        });

    }
}
