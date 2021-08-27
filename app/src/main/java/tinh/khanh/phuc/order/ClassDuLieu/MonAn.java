package tinh.khanh.phuc.order.ClassDuLieu;

/**
 * Created by NGUYENTRITINH on 03/08/2017.
 */

public class MonAn {
    private int IdMonAn;
    private String TenMonAn;
    private String ChuoiHinh;
    private int Gia;
    private boolean TinhTrang;
    private int IdLoaiMon;

    public MonAn() {
    }

    public MonAn(int idMonAn, String tenMonAn, String chuoiHinh, int gia, boolean tinhTrang, int idLoaiMon) {
        IdMonAn = idMonAn;
        TenMonAn = tenMonAn;
        ChuoiHinh = chuoiHinh;
        Gia = gia;
        TinhTrang = tinhTrang;
        IdLoaiMon = idLoaiMon;
    }

    public int getIdMonAn() {
        return IdMonAn;
    }

    public void setIdMonAn(int idMonAn) {
        IdMonAn = idMonAn;
    }

    public String getTenMonAn() {
        return TenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        TenMonAn = tenMonAn;
    }

    public String getChuoiHinh() {
        return ChuoiHinh;
    }

    public void setChuoiHinh(String chuoiHinh) {
        ChuoiHinh = chuoiHinh;
    }

    public int getGia() {
        return Gia;
    }

    public void setGia(int gia) {
        Gia = gia;
    }

    public boolean getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(boolean tinhTrang) {
        TinhTrang = tinhTrang;
    }

    public int getIdLoaiMon() {
        return IdLoaiMon;
    }

    public void setIdLoaiMon(int idLoaiMon) {
        IdLoaiMon = idLoaiMon;
    }
}
