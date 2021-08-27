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
 * Created by NGUYENTRITINH on 31/10/2017.
 */

public class Adapter_ThongKe_Food_Tinh extends BaseAdapter {
    private Context activity;
    private int layout;
    private List<ChiTietPhieuOrder> listFullOrder;
    private List<MonAn> listFood;

    public Adapter_ThongKe_Food_Tinh(Context activity, int layout,List<ChiTietPhieuOrder> listFullOrder, List<MonAn> listFood) {
        this.activity = activity;
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
        LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);
        TextView tvNumber = (TextView)view.findViewById(R.id.tvNumber);
        TextView tvFoodName = (TextView)view.findViewById(R.id.tvFoodName);
        TextView tvCount = (TextView)view.findViewById(R.id.tvCount);

        SapXepListFullOrder();
        tvNumber.setText(String.valueOf(i+1));
        for(int n = 0; n<listFood.size(); n++){
            if(listFullOrder.get(i).getIdMonAn() == listFood.get(n).getIdMonAn()){
                tvFoodName.setText(listFood.get(n).getTenMonAn());
                break;
            }
        }
        tvCount.setText(listFullOrder.get(i).getSoLuong() + "");


        return view;
    }
    private void SapXepListFullOrder(){
        for(int i=0;i<listFullOrder.size()-1;i++){
            for(int j=i+1;j<listFullOrder.size();){
                if(listFullOrder.get(i).getIdMonAn() == listFullOrder.get(j).getIdMonAn()){
                    int Count = listFullOrder.get(i).getSoLuong() + listFullOrder.get(j).getSoLuong();
                    listFullOrder.get(i).setSoLuong(Count);
                    listFullOrder.remove(j);
                }
                else{j++;}
            }
        }
        for(int i=0;i<listFullOrder.size()-1;i++){
            for(int j=i+1;j<listFullOrder.size(); j++){
                if(listFullOrder.get(i).getSoLuong() < listFullOrder.get(j).getSoLuong()){
                    ChiTietPhieuOrder o = new ChiTietPhieuOrder(listFullOrder.get(i).getIdChiTiet(),
                            listFullOrder.get(i).getIdPhieu(),
                            listFullOrder.get(i).getIdBan(),
                            listFullOrder.get(i).getTaiKhoan(),
                            listFullOrder.get(i).getIdMonAn(),
                            listFullOrder.get(i).getSoLuong(),
                            listFullOrder.get(i).getTrangThai());
                    listFullOrder.get(i).setIdChiTiet(listFullOrder.get(j).getIdChiTiet());
                    listFullOrder.get(i).setIdPhieu(listFullOrder.get(j).getIdPhieu());
                    listFullOrder.get(i).setIdBan(listFullOrder.get(j).getIdBan());
                    listFullOrder.get(i).setTaiKhoan(listFullOrder.get(j).getTaiKhoan());
                    listFullOrder.get(i).setIdMonAn(listFullOrder.get(j).getIdMonAn());
                    listFullOrder.get(i).setSoLuong(listFullOrder.get(j).getSoLuong());
                    listFullOrder.get(i).setTrangThai(listFullOrder.get(j).getTrangThai());
                    listFullOrder.set(j, o);

                }
            }
        }
    }
}
