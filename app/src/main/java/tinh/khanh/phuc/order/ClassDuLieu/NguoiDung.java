package tinh.khanh.phuc.order.ClassDuLieu;

/**
 * Created by NGUYENTRITINH on 03/08/2017.
 */

public class NguoiDung {
    private String TaiKhoan;
    private String MatKhau;
    private String HoTen;
    private String ChuoiHinh;
    private String NhomNguoiDung;
    private Boolean DangOnline;

    public NguoiDung() {
    }

    public NguoiDung(String taiKhoan, String matKhau, String hoTen, String chuoiHinh, String nhomNguoiDung, Boolean dangOnline) {
        TaiKhoan = taiKhoan;
        MatKhau = matKhau;
        HoTen = hoTen;
        ChuoiHinh = chuoiHinh;
        NhomNguoiDung = nhomNguoiDung;
        DangOnline = dangOnline;
    }

    public String getTaiKhoan() {
        return TaiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        TaiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getChuoiHinh() {
        return ChuoiHinh;
    }

    public void setChuoiHinh(String chuoiHinh) {
        ChuoiHinh = chuoiHinh;
    }

    public String getNhomNguoiDung() {
        return NhomNguoiDung;
    }

    public void setNhomNguoiDung(String nhomNguoiDung) {
        NhomNguoiDung = nhomNguoiDung;
    }

    public Boolean getDangOnline() {
        return DangOnline;
    }

    public void setDangOnline(Boolean dangOnline) {
        DangOnline = dangOnline;
    }
}
