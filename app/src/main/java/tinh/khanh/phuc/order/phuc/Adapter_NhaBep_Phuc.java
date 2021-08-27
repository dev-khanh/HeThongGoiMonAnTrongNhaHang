package tinh.khanh.phuc.order.phuc;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import phuc.khanh.tinh.order.R;
import tinh.khanh.phuc.order.ClassDuLieu.BanAn;
import tinh.khanh.phuc.order.ClassDuLieu.ChiTietPhieuOrder;
import tinh.khanh.phuc.order.ClassDuLieu.MonAn;


public class Adapter_NhaBep_Phuc extends BaseAdapter {

    private Context activity;
    private int layout;
    private List<ChiTietPhieuOrder> listOrder;
    private List<MonAn> listFood;
    private List<BanAn> listTable;

    public Adapter_NhaBep_Phuc(Context activity, int layout, List<ChiTietPhieuOrder> listOrder, List<MonAn> listFood, List<BanAn> listTable) {
        this.activity = activity;
        this.layout = layout;
        this.listOrder = listOrder;
        this.listFood = listFood;
        this.listTable = listTable;

    }

    @Override
    public int getCount() {
        return listOrder.size();
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
        TextView tvFood = (TextView)view.findViewById(R.id.tvfoodName);
        TextView tvCount = (TextView)view.findViewById(R.id.tvCount);
        TextView tvTable = (TextView)view.findViewById(R.id.tvTableName);

        for(int n =0; n < listFood.size();n++){
            if(listOrder.get(i).getIdMonAn() == listFood.get(n).getIdMonAn()){
                if(listOrder.get(i).getTrangThai() == 0) {
                    tvFood.setText(listFood.get(n).getTenMonAn());
                }
                else {
                    tvFood.setText(listFood.get(n).getTenMonAn() + "\t\t\t\t\t\t\t" + "Đang làm");
                    tvFood.setTextColor(Color.RED);
                }
                break;
            }
        }
        tvCount.setText(String.valueOf(listOrder.get(i).getSoLuong()));
        if(listOrder.get(i).getTrangThai() == 0) {
            tvCount.setTextColor(Color.BLACK);
        }
        else{
            tvCount.setTextColor(Color.RED);
        }
        for(int n =0; n< listTable.size(); n++){
            if(listOrder.get(i).getIdBan() == listTable.get(n).getIdBan()){
                if(listOrder.get(i).getTrangThai() == 0){
                    tvTable.setTextColor(Color.BLACK);
                }
                else{
                    tvTable.setTextColor(Color.RED);
                }
                tvTable.setText(listTable.get(n).getTenBan());
                break;
            }
        }
        return view;
    }
}
