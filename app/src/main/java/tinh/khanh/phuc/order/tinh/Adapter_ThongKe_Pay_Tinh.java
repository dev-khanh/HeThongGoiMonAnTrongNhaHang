package tinh.khanh.phuc.order.tinh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import phuc.khanh.tinh.order.R;
import tinh.khanh.phuc.order.ClassDuLieu.ChiTietPhieuOrder;
import tinh.khanh.phuc.order.ClassDuLieu.HDThanhToan;
import tinh.khanh.phuc.order.ClassDuLieu.MaGiamGia;
import tinh.khanh.phuc.order.ClassDuLieu.MonAn;
import tinh.khanh.phuc.order.ClassDuLieu.NguoiDung;

/**
 * Created by NGUYENTRITINH on 31/10/2017.
 */

public class Adapter_ThongKe_Pay_Tinh extends BaseAdapter {
    private Context activity;
    private int layout;
    private List<HDThanhToan> listPay;
    private List<NguoiDung> listUser;
    private List<ChiTietPhieuOrder> listFullOrder;
    private List<MonAn> listFood;
    private List<MaGiamGia> listDiscountCode;

    public Adapter_ThongKe_Pay_Tinh(Context activity, int layout, List<HDThanhToan> listPay, List<NguoiDung> listUser, List<ChiTietPhieuOrder> listFullOrder, List<MonAn> listFood, List<MaGiamGia> listDiscountCode) {
        this.activity = activity;
        this.layout = layout;
        this.listPay = listPay;
        this.listUser = listUser;
        this.listFullOrder = listFullOrder;
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
        TextView tvNumber = (TextView)view.findViewById(R.id.tvNumber);
        TextView tvPayNumber = (TextView)view.findViewById(R.id.tvPayNumber);
        TextView tvMoney = (TextView)view.findViewById(R.id.tvMoney);
        TextView tvNV = (TextView)view.findViewById(R.id.tvNV);
        tvNumber.setText(String.valueOf(i+1));
        tvPayNumber.setText(listPay.get(i).getIdHoaDon());
        String idFullOrder = listPay.get(i).getIdPhieu();
        int count = 0;
        long moneySum = 0;
        for(int n =0; n< listFullOrder.size(); n++){
            if(idFullOrder.equals(listFullOrder.get(n).getIdPhieu())){
                count = listFullOrder.get(n).getSoLuong();
                long moNey = 0;
                for(int m =0;m<listFood.size(); m++){
                    if(listFood.get(m).getIdMonAn() == listFullOrder.get(n).getIdMonAn()){
                        moNey = listFood.get(m).getGia();
                        break;
                    }
                }

                moneySum += count*moNey;
            }
        }

        String code = listPay.get(i).getMaGiamGia();
        long GiamGia = 0;
        if(!code.equals("")){
            int PhanTramGiam = 0;
            for(int n =0; n< listDiscountCode.size(); n++){
                if(listDiscountCode.get(n).getMaGiamGia().equals(code)){
                    PhanTramGiam = listDiscountCode.get(n).getSoPhanTram();
                    break;
                }
            }
            GiamGia = (long)(moneySum * ((float)PhanTramGiam / 100.0));
        }
        moneySum -= GiamGia;
        tvMoney.setText(moneySum + " VNĐ");
        for(int n =0; n< listUser.size(); n++){
            if(listUser.get(n).getTaiKhoan().equals(listPay.get(i).getTaiKhoan())){
                tvNV.setText(listUser.get(n).getHoTen());
                break;
            }
        }
        return view;
    }
}
