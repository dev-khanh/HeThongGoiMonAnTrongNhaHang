package tinh.khanh.phuc.order.ClassDuLieu;

/**
 * Created by NGUYENTRITINH on 03/08/2017.
 */

public class BanAn {
    private int IdBan;
    private String TenBan;

    public BanAn() {
    }

    public BanAn(int idBan, String tenBan) {
        IdBan = idBan;
        TenBan = tenBan;
    }


    public int getIdBan() {
        return IdBan;
    }

    public void setIdBan(int idBan) {
        IdBan = idBan;
    }

    public String getTenBan() {
        return TenBan;
    }

    public void setTenBan(String tenBan) {
        TenBan = tenBan;
    }
}
