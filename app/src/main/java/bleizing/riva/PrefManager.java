package bleizing.riva;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "Welcome";

    private static final String ID = "id";
    private static final String NO_HP = "no_hp";
    private static final String STATUS = "status";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setRegister(int id, String no_hp, int status) {
        editor.putInt(ID, id);
        editor.putString(NO_HP, no_hp);
        editor.putInt(STATUS, status);

        editor.commit();
    }

    public int getId() {
        return pref.getInt(ID, 0);
    }

    public String getNoHp() {
        return pref.getString(NO_HP, "");
    }

    public void changeStatus(int status) {
        editor.putInt(STATUS, status);

        editor.commit();
    }

    public int getStatus() {
        return pref.getInt(STATUS, 0);
    }
}
