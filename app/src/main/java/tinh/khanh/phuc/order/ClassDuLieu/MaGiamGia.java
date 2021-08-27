package tinh.khanh.phuc.order.ClassDuLieu;

/**
 * Created by NGUYENTRITINH on 04/08/2017.
 */

public class MaGiamGia {
    private String MaGiamGia;
    private String NgayBD;
    private String NgayKT;
    private int SoPhanTram;
    private boolean DaDung;

    public MaGiamGia() {
    }

    public MaGiamGia(String maGiamGia, String ngayBD, String ngayKT, int soPhanTram, boolean daDung) {
        MaGiamGia = maGiamGia;
        NgayBD = ngayBD;
        NgayKT = ngayKT;
        SoPhanTram = soPhanTram;
        DaDung = daDung;
    }

    public String getMaGiamGia() {
        return MaGiamGia;
    }

    public void setMaGiamGia(String maGiamGia) {
        MaGiamGia = maGiamGia;
    }

    public String getNgayBD() {
        return NgayBD;
    }

    public void setNgayBD(String ngayBD) {
        NgayBD = ngayBD;
    }

    public String getNgayKT() {
        return NgayKT;
    }

    public void setNgayKT(String ngayKT) {
        NgayKT = ngayKT;
    }

    public int getSoPhanTram() {
        return SoPhanTram;
    }

    public void setSoPhanTram(int soPhanTram) {
        SoPhanTram = soPhanTram;
    }

    public boolean isDaDung() {
        return DaDung;
    }

    public void setDaDung(boolean daDung) {
        DaDung = daDung;
    }
}
