package bleizing.riva.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import bleizing.riva.activity.HomecareActivity;
import bleizing.riva.activity.RumatActivity;

/**
 * Created by Bleizing on 3/31/2018.
 */

public class Model {
    private static HomecareActivity homecareActivity;
    private static RumatActivity rumatActivity;

    private static ArrayList<Lokasi> lokasiArrayList;

    private static LatLng latLng;

    public static void setHomecareActivity(HomecareActivity homecareActivity) {
        Model.homecareActivity = homecareActivity;
    }

    public static HomecareActivity getHomecareActivity() {
        return homecareActivity;
    }

    public static void setRumatActivity(RumatActivity rumatActivity) {
        Model.rumatActivity = rumatActivity;
    }

    public static RumatActivity getRumatActivity() {
        return rumatActivity;
    }

    public static void clearHomecareActivity() {
        homecareActivity = null;
    }

    public static void clearRumatActivity() {
        rumatActivity = null;
    }

    public static void setLatLng(LatLng latLng) {
        Model.latLng = latLng;
    }

    public static LatLng getLatLng() {
        return latLng;
    }

    public static void setLokasiArrayList(ArrayList<Lokasi> lokasiArrayList) {
        Model.lokasiArrayList = lokasiArrayList;
    }

    public static ArrayList<Lokasi> getLokasiArrayList() {
        return lokasiArrayList;
    }
}
