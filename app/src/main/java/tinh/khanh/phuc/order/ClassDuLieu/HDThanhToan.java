package tinh.khanh.phuc.order.ClassDuLieu;

/**
 * Created by NGUYENTRITINH on 03/08/2017.
 */

public class HDThanhToan {
    private String IdHoaDon;
    private String NgayLap;
    private String TaiKhoan;
    private String IdPhieu;
    private String MaGiamGia;

    public HDThanhToan() {
    }

    public HDThanhToan(String idHoaDon, String ngayLap, String taiKhoan, String idPhieu, String maGiamGia) {
        IdHoaDon = idHoaDon;
        NgayLap = ngayLap;
        TaiKhoan = taiKhoan;
        IdPhieu = idPhieu;
        MaGiamGia = maGiamGia;
    }

    public String getIdHoaDon() {
        return IdHoaDon;
    }

    public void setIdHoaDon(String idHoaDon) {
        IdHoaDon = idHoaDon;
    }

    public String getNgayLap() {
        return NgayLap;
    }

    public void setNgayLap(String ngayLap) {
        NgayLap = ngayLap;
    }

    public String getTaiKhoan() {
        return TaiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        TaiKhoan = taiKhoan;
    }

    public String getIdPhieu() {
        return IdPhieu;
    }

    public void setIdPhieu(String idPhieu) {
        IdPhieu = idPhieu;
    }

    public String getMaGiamGia() {
        return MaGiamGia;
    }

    public void setMaGiamGia(String maGiamGia) {
        MaGiamGia = maGiamGia;
    }
}
