package tinh.khanh.phuc.order.tinh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import phuc.khanh.tinh.order.R;
import tinh.khanh.phuc.order.ClassDuLieu.LoaiMon;
import tinh.khanh.phuc.order.ClassDuLieu.MonAn;
import tinh.khanh.phuc.order.Public.Convert_Image_String;

/**
 * Created by NGUYENTRITINH on 07/08/2017.
 */

public class Adapter_Food_Tinh extends BaseAdapter {
    private Context activity;
    private int layout;
    private List<MonAn> listFood;
    private List<LoaiMon> listFoodG;

    public Adapter_Food_Tinh(Context activity, int layout, List<MonAn> listFood, List<LoaiMon> listFoodG) {
        this.activity = activity;
        this.layout = layout;
        this.listFood = listFood;
        this.listFoodG = listFoodG;
    }

    @Override
    public int getCount() {
        return listFood.size();
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
        ImageView img = (ImageView)view.findViewById(R.id.img);
        TextView tvFoodName = (TextView)view.findViewById(R.id.tvFoodName);
        TextView tvFoodMoney = (TextView)view.findViewById(R.id.tvFoodMoney);
        TextView tvFoodGName = (TextView)view.findViewById(R.id.tvFoodGroupName);
        img.setImageBitmap(new Convert_Image_String().convertStringToBitmap(listFood.get(i).getChuoiHinh()));
        boolean bTinhTrang = listFood.get(i).getTinhTrang();
        String sTinhTrang = "";
        if(bTinhTrang){
            sTinhTrang = "Còn";
        }
        else{
            sTinhTrang = "Hết";
        }
        tvFoodName.setText("Tên món: " + listFood.get(i).getTenMonAn() + " ("+sTinhTrang+")");
        tvFoodMoney.setText("Giá: " + listFood.get(i).getGia() + " VNĐ");
        for(int n=0;n<listFoodG.size();n++){
            if(listFood.get(i).getIdLoaiMon() == listFoodG.get(n).getIdLoaiMon()){
                tvFoodGName.setText("Loại món: " + listFoodG.get(n).getTenLoaiMon());
                break;
            }
        }
        return view;
    }
}
