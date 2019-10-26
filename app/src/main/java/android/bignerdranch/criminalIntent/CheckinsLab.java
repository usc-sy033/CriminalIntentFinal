package android.bignerdranch.criminalIntent;

import android.bignerdranch.criminalIntent.database.CheckinsBaseHelper;
import android.bignerdranch.criminalIntent.database.CheckinsCursorWrapper;
import android.bignerdranch.criminalIntent.database.CheckinsDbSchema.CrimeTable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CheckinsLab {
    private static CheckinsLab sCheckinsLab;

    private List<Checkins> mCheckins;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CheckinsLab get(Context context) {
        if (sCheckinsLab == null) {
            sCheckinsLab = new CheckinsLab(context);
        }
        return sCheckinsLab;
    }

    private CheckinsLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CheckinsBaseHelper(mContext)
                .getWritableDatabase();
        //mCheckins = new ArrayList<>();
        /*for (int i = 0; i < 100; i++) {
            Checkins crime = new Checkins();
            crime.setTitle("Checkins #" + i);
            crime.setSolved(i % 2 == 0); // every other one
            mCheckins.add(crime);
        }*/

    }

    public void addCrime(Checkins c) {
        //mCheckins.add(c);
        ContentValues values = getContentValues(c);

        mDatabase.insert(CrimeTable.NAME, null, values);
    }

    //stuff for delete crime
    public void removeCrime(Checkins c) {
        mDatabase.delete(
                CrimeTable.NAME,
                CrimeTable.Cols.UUID + "=?",
                new String[] {c.getId().toString()}
        );

    }

    public List<Checkins> getCrimes() {
        //return mCheckins;
        //return new ArrayList<>();
        List<Checkins> checkins = new ArrayList<>();

        CheckinsCursorWrapper cursor = queryCrimes(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                checkins.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return checkins;


    }
    public Checkins getCrime(UUID id) {
        /*for (Checkins crime : mCheckins) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }*/
        //return null;
        CheckinsCursorWrapper cursor = queryCrimes(
                CrimeTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        } finally {
            cursor.close();
        }

    }

    public File getPhotoFile(Checkins checkins) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, checkins.getPhotoFilename());
    }

    public void updateCrime(Checkins checkins){
        String uuidString = checkins.getId().toString();
        ContentValues values = getContentValues(checkins);

        mDatabase.update(CrimeTable.NAME, values,
                CrimeTable.Cols.UUID + " = ?",
                new String[] {uuidString});
    }

    private CheckinsCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null, //columns - null selects all columns
                whereClause,
                whereArgs,
                null, //groupBy
                null, //having
                null // orderBy
        );

        //return cursor;
        return new CheckinsCursorWrapper(cursor);
    }


    private static ContentValues getContentValues(Checkins checkins) {
        ContentValues values = new ContentValues();
        //values.put(CrimeTable.Cols.UUID, checkins.getId().toString());
        values.put(CrimeTable.Cols.UUID, checkins.getId().toString());
        values.put(CrimeTable.Cols.TITLE, checkins.getTitle());
        values.put(CrimeTable.Cols.DATE, checkins.getDate().getTime());
        values.put(CrimeTable.Cols.PlACE, checkins.getPlace());
        values.put(CrimeTable.Cols.DETAILS, checkins.getDetails());
        values.put(CrimeTable.Cols.SOLVED, checkins.isSolved() ? 1 : 0);
        values.put(CrimeTable.Cols.SUSPECT, checkins.getSuspect());
        values.put(CrimeTable.Cols.LONGITUDE, checkins.getLongitude());
        values.put(CrimeTable.Cols.LATITUDE, checkins.getLatitude());

        return values;
    }

}
