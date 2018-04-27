package bleizing.riva.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Artikel implements Parcelable {
    private int id;
    private String judul;
    private String isi;
    private String foto;
    private String tanggal;
    private String bulan;

    public Artikel(int id, String judul, String isi, String foto, String tanggal, String bulan) {
        this.id = id;
        this.judul = judul;
        this.isi = isi;
        this.foto = foto;
        this.tanggal = tanggal;
        this.bulan = bulan;
    }

    public Artikel(Parcel in) {
        id = in.readInt();
        judul = in.readString();
        isi = in.readString();
        foto = in.readString();
        tanggal = in.readString();
        bulan = in.readString();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getId() {
        return id;
    }

    public String getIsi() {
        return isi;
    }

    public String getJudul() {
        return judul;
    }

    public String getBulan() {
        return bulan;
    }

    public String getFoto() {
        return foto;
    }

    public String getTanggal() {
        return tanggal;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(judul);
        parcel.writeString(isi);
        parcel.writeString(foto);
        parcel.writeString(tanggal);
        parcel.writeString(bulan);
    }

    public static final Parcelable.Creator<Artikel> CREATOR = new Parcelable.Creator<Artikel>() {
        public Artikel createFromParcel(Parcel in) {
            return new Artikel(in);
        }

        @Override
        public Artikel[] newArray(int i) {
            return new Artikel[i];
        }
    };
}
