package tinh.khanh.phuc.order.khanh;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import phuc.khanh.tinh.order.R;
import tinh.khanh.phuc.order.ClassDuLieu.ChiTietPhieuOrder;
import tinh.khanh.phuc.order.ClassDuLieu.MonAn;

/**
 * Created by saakh on 21/10/2017.
 */

public class Khanh_Adap_Hoa_Don extends BaseAdapter{

    private Context ct;
    private int lo;
    private List<ChiTietPhieuOrder> dt;
    private List<MonAn> dt_mon;


    public Khanh_Adap_Hoa_Don(Context ct, int lo, List<ChiTietPhieuOrder> dt, List<MonAn> dt_mon) {
        this.ct = ct;
        this.lo = lo;
        this.dt = dt;
        this.dt_mon = dt_mon;

    }

    @Override
    public int getCount() {
        return dt.size();
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
        View v = View.inflate(ct, R.layout.khanh_dialog_adap_hoa_don, null);
        TextView tv1 =(TextView) v.findViewById(R.id.tv_dialog_Hoa_don);
        TextView tv2 = (TextView) v.findViewById(R.id.tv_dialog_so_luong);
        TextView tv3 = (TextView) v.findViewById(R.id.tv_dialog_gia);
        TextView tv4 = (TextView) v.findViewById(R.id.tv_dialog_thanh_tien);


        tv2.setText(String.valueOf(dt.get(i).getSoLuong()));
        for (int m = 0; m< dt_mon.size(); m++){
            if (dt.get(i).getIdMonAn() == dt_mon.get(m).getIdMonAn()){
                tv1.setText(dt_mon.get(m).getTenMonAn());
                tv3.setText(dt_mon.get(m).getGia()+ " VNĐ");
                tv4.setText((dt_mon.get(m).getGia() * dt.get(i).getSoLuong())+ " VNĐ");
            }
        }

        return v;
    }
}
