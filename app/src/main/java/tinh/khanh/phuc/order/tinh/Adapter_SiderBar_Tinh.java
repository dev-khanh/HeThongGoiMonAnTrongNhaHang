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

/**
 * Created by NGUYENTRITINH on 03/08/2017.
 */

public class Adapter_SiderBar_Tinh extends BaseAdapter {
    private Context activity;
    private int layout;
    private List<SiderBar_Tinh> list;

    public Adapter_SiderBar_Tinh(Context activity, int layout, List<SiderBar_Tinh> list) {
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
    public View getView(int p, View v, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(layout, null);
        ImageView img = (ImageView)v.findViewById(R.id.img);
        TextView tv = (TextView)v.findViewById(R.id.tv);
        img.setImageResource(list.get(p).getImage());
        tv.setText(list.get(p).getText());
        return v;
    }
}
