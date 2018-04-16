package bleizing.riva.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bleizing on 4/17/2018.
 */

public class Lokasi implements Parcelable {
    private int id;
    private String nama;
    private String alamat;
    private Double lat;
    private Double lng;
    private String type;
    private String noTelp;

    public Lokasi(int id, String nama, String alamat, Double lat, Double lng, String type, String noTelp) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.lat = lat;
        this.lng = lng;
        this.type = type;
        this.noTelp = noTelp;
    }

    public Lokasi(Parcel in) {
        id = in.readInt();
        nama = in.readString();
        alamat = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
        type = in.readString();
        noTelp = in.readString();
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public String getType() {
        return type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nama);
        parcel.writeString(alamat);
        parcel.writeDouble(lat);
        parcel.writeDouble(lng);
        parcel.writeString(type);
        parcel.writeString(noTelp);
    }

    public static final Parcelable.Creator<Lokasi> CREATOR = new Parcelable.Creator<Lokasi>() {
        public Lokasi createFromParcel(Parcel in) {
            return new Lokasi(in);
        }

        @Override
        public Lokasi[] newArray(int i) {
            return new Lokasi[i];
        }
    };
}
