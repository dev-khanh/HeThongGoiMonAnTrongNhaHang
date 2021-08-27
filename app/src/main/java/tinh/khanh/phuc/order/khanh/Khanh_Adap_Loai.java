package tinh.khanh.phuc.order.khanh;

import android.content.Context;
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
import tinh.khanh.phuc.order.ClassDuLieu.LoaiMon;
import tinh.khanh.phuc.order.Public.Convert_Image_String;

/**
 * Created by saakh on 08/08/2017.
 */

public class Khanh_Adap_Loai extends BaseAdapter{
    private Context ct;
    private int lo;
    private List<LoaiMon> dt;

    public Khanh_Adap_Loai(Context ct, int lo, List<LoaiMon> dt) {
        this.ct = ct;
        this.lo = lo;
        this.dt = dt;
    }

    @Override
    public int getCount() {
        return dt.size();
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
        View v = inflater.inflate(lo,null);
        ImageView img = (ImageView) v.findViewById(R.id.img_dt_hinh);
        TextView tv = (TextView) v.findViewById(R.id.tv_dt_chui);

//
//        String str = dt.get(i).getChuoiHinh();
//        Convert_Image_String cv = new Convert_Image_String();
//        Bitmap bt = cv.convertStringToBitmap(str);
//        img.setImageBitmap(bt);
        img.setImageBitmap(new Convert_Image_String().convertStringToBitmap(dt.get(i).getChuoiHinh()));
        tv.setText(dt.get(i).getTenLoaiMon());

        Animation a = AnimationUtils.loadAnimation(ct, R.anim.scale_xoay);
        v.startAnimation(a);

        return v;
    }
}
