package com.yash.notetaking.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.yash.notetaking.database.AppRepository;
import com.yash.notetaking.database.NoteEntity;
import com.yash.notetaking.utils.SampleDataProvider;

import java.util.List;

public class ListActivityViewModel extends AndroidViewModel {


    public LiveData<List<NoteEntity>> mNotesList;
    private AppRepository mRepository;

    public ListActivityViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(application.getApplicationContext());
        mNotesList = mRepository.mNotesList;
    }

    public void addSampleData() {
        mRepository.addSampleData();
    }

    public void deleteAllData() {
        mRepository.deleteAllData();
    }

    public void deleteNote(NoteEntity noteEntity) {

        mRepository.deleteNote(noteEntity);

    }
}
