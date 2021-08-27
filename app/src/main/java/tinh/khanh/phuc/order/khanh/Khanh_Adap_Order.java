package tinh.khanh.phuc.order.khanh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import phuc.khanh.tinh.order.R;
import tinh.khanh.phuc.order.ClassDuLieu.ChiTietPhieuOrder;
import tinh.khanh.phuc.order.ClassDuLieu.MonAn;

/**
 * Created by NGUYENTRITINH on 07/08/2017.
 */

public class Khanh_Adap_Order extends BaseAdapter {
    private Context activity;
    private int layout;
    private List<ChiTietPhieuOrder> listPhieuOrder;
    private List<MonAn> listMonAn;

    public Khanh_Adap_Order(Context activity, int layout, List<ChiTietPhieuOrder> listPhieuOrder, List<MonAn> listMonAn) {
        this.activity = activity;
        this.layout = layout;
        this.listPhieuOrder = listPhieuOrder;
        this.listMonAn = listMonAn;
    }

    @Override
    public int getCount() {
        return listPhieuOrder.size();
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
        LayoutInflater inflater = (LayoutInflater)activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);
        TextView tvSoLuong = (TextView)view.findViewById(R.id.tv_Soluong);
        TextView tvTenMon = (TextView)view.findViewById(R.id.tv_tenmon);
        TextView tvGiaTien = (TextView)view.findViewById(R.id.tv_giatien);
        TextView tvThanhTien = (TextView)view.findViewById(R.id.tv_thanhTien);
        int GiaTien = 0;

        for(int n =0 ;n < listMonAn.size(); n++){
            if(listPhieuOrder.get(i).getIdMonAn() == listMonAn.get(n).getIdMonAn()){
                tvTenMon.setText(listMonAn.get(n).getTenMonAn());
                GiaTien = listMonAn.get(n).getGia();
                tvGiaTien.setText(GiaTien+ " VNĐ");
                break;
            }
        }
        tvSoLuong.setText(String.valueOf(listPhieuOrder.get(i).getSoLuong()));
        int ThanhTien = GiaTien * Integer.parseInt(tvSoLuong.getText().toString());
        tvThanhTien.setText("(" + ThanhTien + " VNĐ)");
        return view;
    }
}
