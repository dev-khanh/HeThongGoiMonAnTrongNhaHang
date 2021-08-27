package tinh.khanh.phuc.order.khanh;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import phuc.khanh.tinh.order.R;
import tinh.khanh.phuc.order.ClassDuLieu.MonAn;
import tinh.khanh.phuc.order.Public.Convert_Image_String;

/**
 * Created by saakh on 30/07/2017.
 */

public class Khanh_Adap_Mon extends BaseAdapter{
    private Context ct;
    private int Layout;
    private List<MonAn> list;

    public Khanh_Adap_Mon(Context ct, int layout, List<MonAn> list) {
        this.ct = ct;
        Layout = layout;
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

        LayoutInflater inflater = (LayoutInflater) ct.getSystemService(ct.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(Layout, null);


        ImageView img = (ImageView)view.findViewById(R.id.img_khanh_Monan);
        TextView tv = (TextView)view.findViewById(R.id.tv_khanh_Tenban);
        TextView tv1 = (TextView)view.findViewById(R.id.tv_khanh_gia);

        String str = list.get(i).getChuoiHinh();
        Convert_Image_String cv = new Convert_Image_String();
        Bitmap bt = cv.convertStringToBitmap(str);
        img.setImageBitmap(bt);

//        img.setImageBitmap(new Convert_Image_String().convertStringToBitmap(list.get(i).getChuoiHinh()));

        tv.setText(list.get(i).getTenMonAn());
        tv1.setText(String.valueOf(list.get(i).getGia())); //giá kiểu int chuyển thành string

        Animation a = AnimationUtils.loadAnimation(ct, R.anim.scale_xoay);
        view.startAnimation(a);
        return view;
    }
}
