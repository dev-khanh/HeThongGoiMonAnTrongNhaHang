package tinh.khanh.phuc.order.tinh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import phuc.khanh.tinh.order.R;
import tinh.khanh.phuc.order.ClassDuLieu.BanAn;
import tinh.khanh.phuc.order.ClassDuLieu.ChiTietPhieuOrder;
import tinh.khanh.phuc.order.ClassDuLieu.HDThanhToan;
import tinh.khanh.phuc.order.ClassDuLieu.MaGiamGia;
import tinh.khanh.phuc.order.ClassDuLieu.MonAn;
import tinh.khanh.phuc.order.ClassDuLieu.NguoiDung;

/**
 * Created by NGUYENTRITINH on 27/09/2017.
 */

public class Adapter_BillPay_Tinh extends BaseAdapter {
    private Context activity;
    private int layout;
    private List<HDThanhToan> listPay;
    private List<ChiTietPhieuOrder> listOrder;
    private List<BanAn> listTable;
    private List<NguoiDung> listUser;
    private List<MonAn> listFood;
    private List<MaGiamGia> listDiscountCode;

    public Adapter_BillPay_Tinh(Context activity, int layout, List<HDThanhToan> listPay, List<ChiTietPhieuOrder> listOrder, List<BanAn> listTable, List<NguoiDung> listUser, List<MonAn> listFood, List<MaGiamGia> listDiscountCode) {
        this.activity = activity;
        this.layout = layout;
        this.listPay = listPay;
        this.listOrder = listOrder;
        this.listTable = listTable;
        this.listUser = listUser;
        this.listFood = listFood;
        this.listDiscountCode = listDiscountCode;
    }

    @Override
    public int getCount() {
        return listPay.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);
        TextView tvIdPay = (TextView)view.findViewById(R.id.tvIdPay);
        TextView tvDate = (TextView)view.findViewById(R.id.tvDate);
        TextView tvTable = (TextView)view.findViewById(R.id.tvTable);
        TextView tvNV = (TextView)view.findViewById(R.id.tvNV);
        TextView tvSumMoney = (TextView)view.findViewById(R.id.tvSumMoney);
        tvIdPay.setText(listPay.get(i).getIdHoaDon());
        tvDate.setText(listPay.get(i).getNgayLap());
        for(int n =0; n< listUser.size(); n++){
            if(listUser.get(n).getTaiKhoan().equals(listPay.get(i).getTaiKhoan())){
                tvNV.setText(listUser.get(n).getHoTen());
                break;
            }
        }
        String idOrder = listPay.get(i).getIdPhieu();
        int idTable = -1;
        for(int n =0; n< listOrder.size(); n++){
            if(idOrder.equals(listOrder.get(n).getIdPhieu())){
                idTable = listOrder.get(n).getIdBan();
                break;
            }
        }
        for(int m = 0; m< listTable.size(); m++){
            if(idTable == listTable.get(m).getIdBan()){
                tvTable.setText(listTable.get(m).getTenBan());
                break;
            }
        }
        int count = 0;
        long money = 0;
        for(int n =0; n< listOrder.size(); n++){
            if(idOrder.equals(listOrder.get(n).getIdPhieu())){
                count = listOrder.get(n).getSoLuong();
                int idFood = listOrder.get(n).getIdMonAn();
                for(int m =0;m<listFood.size(); m++){
                    if(listFood.get(m).getIdMonAn() == idFood){
                        money+= listFood.get(m).getGia();
                    }
                }
            }
        }
        String code = listPay.get(i).getMaGiamGia();
        int PhanTramGiamGia = 0;
        if(!code.isEmpty()) {
            for (int n = 0; n < listDiscountCode.size(); n++) {
                if (listDiscountCode.get(n).getMaGiamGia().equals(code)) {
                    PhanTramGiamGia = listDiscountCode.get(n).getSoPhanTram();
                }
            }
        }
        long sumMoney = count*money;
        tvSumMoney.setText((sumMoney) + " VNĐ");
        if(PhanTramGiamGia > 0){
            long giam = (long)(sumMoney * ((float)PhanTramGiamGia/100.0));
            tvSumMoney.append(" (Giảm "+giam+" VNĐ Còn "+(sumMoney - giam)+" VNĐ)");
        }

        return view;
    }
}
