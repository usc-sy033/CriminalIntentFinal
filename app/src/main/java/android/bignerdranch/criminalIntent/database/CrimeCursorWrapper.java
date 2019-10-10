package android.bignerdranch.criminalIntent.database;

import android.bignerdranch.criminalIntent.Crime;
import android.bignerdranch.criminalIntent.database.CrimeDbSchema.CrimeTable;
import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

public class CrimeCursorWrapper extends CursorWrapper {
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Crime getCrime() {
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        String place = getString(getColumnIndex(CrimeTable.Cols.PlACE));
        String details = getString(getColumnIndex(CrimeTable.Cols.DETAILS));
        int isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));
        String suspect = getString(getColumnIndex(CrimeTable.Cols.SUSPECT));

        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setPlace(place);
        crime.setDetails(details);
        crime.setSolved(isSolved != 0);
        crime.setSuspect(suspect);

        return crime;
        //return null;
    }
}
