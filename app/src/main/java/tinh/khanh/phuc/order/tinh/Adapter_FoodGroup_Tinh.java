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
import tinh.khanh.phuc.order.Public.Convert_Image_String;

/**
 * Created by NGUYENTRITINH on 04/08/2017.
 */

public class Adapter_FoodGroup_Tinh extends BaseAdapter {
    private Context activity;
    private int layout;
    private List<LoaiMon> list;

    public Adapter_FoodGroup_Tinh(Context activity, int layout, List<LoaiMon> list) {
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
        LayoutInflater inflater = (LayoutInflater)activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);
        ImageView img = (ImageView)view.findViewById(R.id.img);
        TextView tv = (TextView)view.findViewById(R.id.tv);
        img.setImageBitmap(new Convert_Image_String().convertStringToBitmap(list.get(i).getChuoiHinh()));
        tv.setText(list.get(i).getTenLoaiMon());
        return view;
    }
}
