package krunal.com.example.petsapp.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


/**
 * Created by acer on 13-03-2018.
 */
/**
 * Database Build class.
 */
@Database(entities = {PetsEntity.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract PetsDao getDao();

    private static AppDatabase INSTANCE;

    static AppDatabase getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }

    private static AppDatabase buildDatabase(Context context){
        String dbname="db";
        return Room.databaseBuilder(context,AppDatabase.class,dbname).build();
    }
}
