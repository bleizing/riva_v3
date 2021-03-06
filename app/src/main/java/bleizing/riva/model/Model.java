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

    private static ArrayList<Artikel> artikelArrayList;

    private static LatLng latLng;

    private static ArrayList<GulaDarah> gds;

    private static int id;

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

    public static void setGds(ArrayList<GulaDarah> gds) {
        Model.gds = gds;
    }

    public static ArrayList<GulaDarah> getGds() {
        return gds;
    }

    public static void setArtikelArrayList(ArrayList<Artikel> artikelArrayList) {
        Model.artikelArrayList = artikelArrayList;
    }

    public static ArrayList<Artikel> getArtikelArrayList() {
        return artikelArrayList;
    }

    public static void setId(int id) {
        Model.id = id;
    }

    public static int getId() {
        return id;
    }
}
