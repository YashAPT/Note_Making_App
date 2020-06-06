package com.yash.notetaking.utils;

import com.yash.notetaking.database.NoteEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SampleDataProvider {

    private static final String SAMPLE_TEXT = "A material metaphor is the unifying theory of a rationalized space and a system of motion.";


    private static Date getDate(int diffAmount) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(Calendar.MILLISECOND, diffAmount);
        return calendar.getTime();
    }

    public static List<NoteEntity> getSampleData(){
        List<NoteEntity> notesList = new ArrayList<>();

        notesList.add(new NoteEntity( getDate(0), SAMPLE_TEXT));
        notesList.add(new NoteEntity( getDate(-1), SAMPLE_TEXT));
        notesList.add(new NoteEntity( getDate(-2), SAMPLE_TEXT));


        return notesList;
    }

}
