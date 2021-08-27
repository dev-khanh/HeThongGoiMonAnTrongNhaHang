package tinh.khanh.phuc.order.ClassDuLieu;

/**
 * Created by NGUYENTRITINH on 03/08/2017.
 */

public class LoaiMon {
    private int IdLoaiMon;
    private String TenLoaiMon;
    private String ChuoiHinh;

    public LoaiMon() {
    }

    public LoaiMon(int idLoaiMon, String tenLoaiMon, String chuoiHinh) {
        IdLoaiMon = idLoaiMon;
        TenLoaiMon = tenLoaiMon;
        ChuoiHinh = chuoiHinh;
    }

    public int getIdLoaiMon() {
        return IdLoaiMon;
    }

    public void setIdLoaiMon(int idLoaiMon) {
        IdLoaiMon = idLoaiMon;
    }

    public String getTenLoaiMon() {
        return TenLoaiMon;
    }

    public void setTenLoaiMon(String tenLoaiMon) {
        TenLoaiMon = tenLoaiMon;
    }

    public String getChuoiHinh() {
        return ChuoiHinh;
    }

    public void setChuoiHinh(String chuoiHinh) {
        ChuoiHinh = chuoiHinh;
    }
}
