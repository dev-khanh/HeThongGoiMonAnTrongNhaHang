package tinh.khanh.phuc.order.khanh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import phuc.khanh.tinh.order.R;
import tinh.khanh.phuc.order.ClassDuLieu.ChiTietPhieuOrder;
import tinh.khanh.phuc.order.ClassDuLieu.MonAn;

/**
 * Created by saakh on 17/09/2017.
 */

public class Khanh_Adap_Da_Goi  extends BaseAdapter{
    private Context ct;
    private int Layout;
    private List<ChiTietPhieuOrder> list;
    private List<MonAn> list1;

    public Khanh_Adap_Da_Goi(Context ct, int layout, List<ChiTietPhieuOrder> list, List<MonAn> list1) {
        this.ct = ct;
        Layout = layout;
        this.list = list;
        this.list1 = list1;
    }

    @Override
    public int getCount() {
        return list.size();
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
        LayoutInflater inflater = (LayoutInflater) ct.getSystemService(ct.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(Layout, null);

        TextView tv = (TextView)view.findViewById(R.id.tv_tenmon1);
        TextView tv1 = (TextView)view.findViewById(R.id.tv_giatien1);
        TextView tv2 = (TextView)view.findViewById(R.id.tv_So_Luong);
        TextView tv3 = (TextView)view.findViewById(R.id.tv_thanhTien1);

        int SoLuong = list.get(i).getSoLuong();

        tv2.setText(String.valueOf(SoLuong));
        switch (list.get(i).getTrangThai()){
            case 0:
                tv3.setText("(Mới gọi)");
                break;
            case 1:
                tv3.setText("(Đang làm)");
                break;
            case 2:
                tv3.setText("(Đang dùng)");
                break;
        }


        for (int n = 0; n < list1.size(); n++){
            if (list.get(i).getIdMonAn() == list1.get(n).getIdMonAn()){
                tv.setText(list1.get(n).getTenMonAn());
                int GiaTien = list1.get(n).getGia();
                tv1.setText(GiaTien + " VNĐ ("+(GiaTien*SoLuong)+" VNĐ)");
                break;
            }
        }
        Animation a = AnimationUtils.loadAnimation(ct, R.anim.scale_xoay);
        view.startAnimation(a);
        return view;
    }
}
