package com.yash.notetaking.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import static android.icu.text.MessagePattern.ArgType.SELECT;

@Dao
public interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(NoteEntity noteEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<NoteEntity> noteEntity);

    @Delete
    void deleteNode(NoteEntity noteEntity);

    @Query("SELECT * FROM notes WHERE id= :id")
    NoteEntity getNoteById(int id);

    @Query("SELECT * FROM notes ORDER BY date DESC")
    LiveData<List<NoteEntity>> getAllNotes();

    @Query("DELETE FROM notes")
    int deleteAllNotes();

    @Query("SELECT COUNT(*) FROM notes")
    int getCount();

}
