package tinh.khanh.phuc.order.ClassDuLieu;

/**
 * Created by NGUYENTRITINH on 03/08/2017.
 */

public class ChiTietPhieuOrder {
    private String IdChiTiet;
    private String IdPhieu;
    private int IdBan;
    private String TaiKhoan;
    private int IdMonAn;
    private int SoLuong;
    //Trạng thái nhận 1 trong 4 giá trị (0,1,2,3)
    //0: mới gọi, 1: đang làm, 2: đang dùng, 3: đã thanh toán
    private int TrangThai;

    public ChiTietPhieuOrder() {
    }

    public ChiTietPhieuOrder(String idChiTiet, String idPhieu, int idBan, String taiKhoan, int idMonAn, int soLuong, int trangThai) {
        IdChiTiet = idChiTiet;
        IdPhieu = idPhieu;
        IdBan = idBan;
        TaiKhoan = taiKhoan;
        IdMonAn = idMonAn;
        SoLuong = soLuong;
        TrangThai = trangThai;
    }

    public String getIdChiTiet() {
        return IdChiTiet;
    }

    public void setIdChiTiet(String idChiTiet) {
        IdChiTiet = idChiTiet;
    }

    public String getIdPhieu() {
        return IdPhieu;
    }

    public void setIdPhieu(String idPhieu) {
        IdPhieu = idPhieu;
    }

    public int getIdBan() {
        return IdBan;
    }

    public void setIdBan(int idBan) {
        IdBan = idBan;
    }

    public String getTaiKhoan() {
        return TaiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        TaiKhoan = taiKhoan;
    }

    public int getIdMonAn() {
        return IdMonAn;
    }

    public void setIdMonAn(int idMonAn) {
        IdMonAn = idMonAn;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int trangThai) {
        TrangThai = trangThai;
    }
}
