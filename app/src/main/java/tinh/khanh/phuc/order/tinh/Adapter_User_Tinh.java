package tinh.khanh.phuc.order.tinh;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import phuc.khanh.tinh.order.R;
import tinh.khanh.phuc.order.ClassDuLieu.NguoiDung;
import tinh.khanh.phuc.order.Public.Convert_Image_String;

/**
 * Created by NGUYENTRITINH on 14/08/2017.
 */

public class Adapter_User_Tinh extends BaseAdapter {
    private Context activity;
    private int layout;
    private List<NguoiDung> list;

    public Adapter_User_Tinh(Context activity, int layout, List<NguoiDung> list) {
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
        TextView tvFullName = (TextView)view.findViewById(R.id.tvFullName);
        TextView tvUserName = (TextView)view.findViewById(R.id.tvUserName);
        TextView tvGroup = (TextView)view.findViewById(R.id.tvGroupUser);
        TextView tvstate = (TextView)view.findViewById(R.id.tvstate);
        img.setImageBitmap(new Convert_Image_String().convertStringToBitmap(list.get(i).getChuoiHinh()));
        tvFullName.setText(list.get(i).getHoTen());
        tvUserName.setText(list.get(i).getTaiKhoan());
        tvGroup.setText(list.get(i).getNhomNguoiDung());
        boolean state = list.get(i).getDangOnline();
        if(state){
            tvstate.setText("Online");
            tvstate.setTextColor(Color.parseColor("#FFFA0303"));
        }
        else{
            tvstate.setText("Offline");
            tvstate.setTextColor(Color.parseColor("#8e8b8b"));
        }
        return view;
    }
}
