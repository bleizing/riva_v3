package bleizing.riva.model;

/**
 * Created by Bleizing on 4/17/2018.
 */

public class NETApi {
    public static final String BASE_URL = "http://182.23.42.196/rumat/riva/";
    public static final String ID_USER = "&id_user=";
    public static final String GET_LOKASI_RUMAT = "unit?rqid=" + NETApi.RQID;
    public static final String RQID = "PAYCUBE-RUMAT";
    public static final String POST_HOMECARE = "booking/homecare";
    public static final String POST_BOOKING = "booking/unit";
    public static final String GET_ARTICLE = "artikel?rqid=" + NETApi.RQID;
    public static final String REGISTRASI = "user/registrasi";
}
