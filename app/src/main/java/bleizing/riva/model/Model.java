package bleizing.riva.model;

import bleizing.riva.activity.HomecareActivity;
import bleizing.riva.activity.RumatActivity;

/**
 * Created by Bleizing on 3/31/2018.
 */

public class Model {
    private static HomecareActivity homecareActivity;
    private static RumatActivity rumatActivity;

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
}
