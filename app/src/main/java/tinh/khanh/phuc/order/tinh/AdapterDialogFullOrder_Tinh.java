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
import tinh.khanh.phuc.order.ClassDuLieu.MonAn;

/**
 * Created by NGUYENTRITINH on 23/09/2017.
 */

public class AdapterDialogFullOrder_Tinh extends BaseAdapter {
    private Context actiity;
    private int layout;
    private List<ChiTietPhieuOrder> listFullOrder;
    private List<MonAn> listFood;

    public AdapterDialogFullOrder_Tinh(Context actiity, int layout, List<ChiTietPhieuOrder> listFullOrder, List<MonAn> listFood) {
        this.actiity = actiity;
        this.layout = layout;
        this.listFullOrder = listFullOrder;
        this.listFood = listFood;
    }


    @Override
    public int getCount() {
        return listFullOrder.size();
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
        LayoutInflater inflater = (LayoutInflater) actiity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);
        TextView tvNumber = (TextView)view.findViewById(R.id.tvNumber);
        TextView tvFoodName = (TextView)view.findViewById(R.id.tvFoodName);
        TextView tvCount = (TextView)view.findViewById(R.id.tvCount);
        TextView tvState = (TextView)view.findViewById(R.id.tvstate);
        switch (listFullOrder.get(i).getTrangThai()){
            case 0:
                tvState.setText("Mới gọi");
                break;
            case 1:
                tvState.setText("Đang làm");
                break;
            case 2:
                tvState.setText("Đang dùng");
                break;
            case 3:
                tvState.setText("Đã Thanh Toán");
                break;
        }
        for(int n =0; n<listFood.size(); n++){
            if(listFullOrder.get(i).getIdMonAn() == listFood.get(n).getIdMonAn()){
                tvFoodName.setText(listFood.get(n).getTenMonAn());
                break;
            }
        }
        tvNumber.setText(String.valueOf(i + 1));

        tvCount.setText(String.valueOf(listFullOrder.get(i).getSoLuong()));
        return view;
    }
}
