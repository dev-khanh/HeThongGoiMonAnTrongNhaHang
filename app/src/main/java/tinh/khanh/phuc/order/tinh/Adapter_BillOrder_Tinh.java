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
import tinh.khanh.phuc.order.ClassDuLieu.PhieuOrder;

/**
 * Created by NGUYENTRITINH on 22/09/2017.
 */

public class Adapter_BillOrder_Tinh extends BaseAdapter {
    private Context activity;
    private int layout;
    private List<PhieuOrder> listBillOrder;
    private List<ChiTietPhieuOrder> listFullOrder;
    private List<BanAn> listTable;

    public Adapter_BillOrder_Tinh(Context activity, int layout, List<PhieuOrder> listBillOrder, List<ChiTietPhieuOrder> listFullOrder, List<BanAn> listTable) {
        this.activity = activity;
        this.layout = layout;
        this.listBillOrder = listBillOrder;
        this.listFullOrder = listFullOrder;
        this.listTable = listTable;
    }

    public Adapter_BillOrder_Tinh(Context activity, int layout, List<PhieuOrder> listBillOrder) {
        this.activity = activity;
        this.layout = layout;
        this.listBillOrder = listBillOrder;
    }

    @Override
    public int getCount() {
        return listBillOrder.size();
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
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);
        TextView tvIdOrder = (TextView)view.findViewById(R.id.tvIdOrder);
        TextView tvDate = (TextView)view.findViewById(R.id.tvDate);
        TextView tvTable = (TextView)view.findViewById(R.id.tvTable);
        int idTable = -1;

        tvIdOrder.setText(listBillOrder.get(i).getIdPhieu());
        tvDate.setText(listBillOrder.get(i).getNgayLap());
        for(int n =0 ;n< listFullOrder.size(); n++){
            if(listBillOrder.get(i).getIdPhieu().equals(listFullOrder.get(n).getIdPhieu())){
                for(int m = 0; m< listTable.size(); m++){
                    if(listTable.get(m).getIdBan()==listFullOrder.get(n).getIdBan()){
                        tvTable.setText(listTable.get(m).getTenBan());
                        break;
                    }
                }
                break;
            }
        }
        return view;
    }
}
