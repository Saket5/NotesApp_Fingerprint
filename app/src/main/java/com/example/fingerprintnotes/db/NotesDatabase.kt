package com.example.fingerprintnotes.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fingerprintnotes.model.Note

@Database(entities = [Note::class],version = 1)
abstract class NotesDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object{
        @Volatile
        private var INSTANCE : NotesDatabase? = null
        fun getInstance(context: Context):NotesDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NotesDatabase::class.java,
                        "notes_data_database"
                    ).build()
                }
                return instance
            }
        }

    }
}

