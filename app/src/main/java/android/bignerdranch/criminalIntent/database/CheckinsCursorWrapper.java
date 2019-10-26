package android.bignerdranch.criminalIntent.database;

import android.bignerdranch.criminalIntent.Checkins;
import android.bignerdranch.criminalIntent.database.CheckinsDbSchema.CrimeTable;
import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

public class CheckinsCursorWrapper extends CursorWrapper {
    public CheckinsCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Checkins getCrime() {
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        String place = getString(getColumnIndex(CrimeTable.Cols.PlACE));
        String details = getString(getColumnIndex(CrimeTable.Cols.DETAILS));
        int isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));
        String suspect = getString(getColumnIndex(CrimeTable.Cols.SUSPECT));
        Double latitude = getDouble(getColumnIndex(CrimeTable.Cols.LATITUDE));
        Double longitude = getDouble(getColumnIndex(CrimeTable.Cols.LONGITUDE));

        Checkins checkins = new Checkins(UUID.fromString(uuidString));
        checkins.setTitle(title);
        checkins.setDate(new Date(date));
        checkins.setPlace(place);
        checkins.setDetails(details);
        checkins.setSolved(isSolved != 0);
        checkins.setSuspect(suspect);
        checkins.setLatitude(latitude);
        checkins.setLongitude(longitude);

        return checkins;
        //return null;
    }
}
