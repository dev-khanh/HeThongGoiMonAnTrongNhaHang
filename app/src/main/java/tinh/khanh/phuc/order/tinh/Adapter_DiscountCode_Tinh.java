package tinh.khanh.phuc.order.tinh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import phuc.khanh.tinh.order.R;
import tinh.khanh.phuc.order.ClassDuLieu.MaGiamGia;

/**
 * Created by NGUYENTRITINH on 01/10/2017.
 */

public class Adapter_DiscountCode_Tinh extends BaseAdapter {
    private Context activity;
    private int layout;
    private List<MaGiamGia> list;

    public Adapter_DiscountCode_Tinh(Context activity, int layout, List<MaGiamGia> list) {
        this.activity = activity;
        this.layout = layout;
        this.list = list;
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
        LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);
        TextView tvCode = (TextView)view.findViewById(R.id.tvCode);
        TextView tvStartDate = (TextView)view.findViewById(R.id.tvStartDate);
        TextView tvEndDate = (TextView)view.findViewById(R.id.tvEndDate);
        TextView tvGiam = (TextView)view.findViewById(R.id.tvGiam);
        TextView tvState = (TextView)view.findViewById(R.id.tvState);
        tvCode.setText(list.get(i).getMaGiamGia());
        tvStartDate.setText(list.get(i).getNgayBD());
        tvEndDate.setText(list.get(i).getNgayKT());
        tvGiam.setText(list.get(i).getSoPhanTram() + " %");
        if(list.get(i).isDaDung()){
            tvState.setText("Đã dùng");
        }
        else
            tvState.setText("Chưa dùng");
        return view;
    }
}
