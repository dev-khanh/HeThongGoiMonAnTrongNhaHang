package tinh.khanh.phuc.order.ClassDuLieu;

/**
 * Created by NGUYENTRITINH on 03/08/2017.
 */

public class PhieuOrder {
    private String IdPhieu;
    private String NgayLap;

    public PhieuOrder() {
    }

    public PhieuOrder(String idPhieu, String ngayLap) {
        IdPhieu = idPhieu;
        NgayLap = ngayLap;
    }

    public String getIdPhieu() {
        return IdPhieu;
    }

    public void setIdPhieu(String idPhieu) {
        IdPhieu = idPhieu;
    }

    public String getNgayLap() {
        return NgayLap;
    }

    public void setNgayLap(String ngayLap) {
        NgayLap = ngayLap;
    }
}
