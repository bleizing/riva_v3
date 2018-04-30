package bleizing.riva.model;

public class GulaDarah {
    private int id;
    private String angka;
    private String catatan;
    private long time;

    public GulaDarah(String angka, String catatan, long time) {
        this.angka = angka;
        this.catatan = catatan;
        this.time = time;
    }

    public GulaDarah(int id, String angka, String catatan, long time) {
        this.id = id;
        this.angka = angka;
        this.catatan = catatan;
        this.time = time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAngka(String angka) {
        this.angka = angka;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public long getTime() {
        return time;
    }

    public String getAngka() {
        return angka;
    }

    public String getCatatan() {
        return catatan;
    }
}
