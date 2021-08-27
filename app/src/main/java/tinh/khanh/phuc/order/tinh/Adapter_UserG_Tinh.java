package tinh.khanh.phuc.order.tinh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import phuc.khanh.tinh.order.R;

/**
 * Created by NGUYENTRITINH on 15/08/2017.
 */

public class Adapter_UserG_Tinh extends BaseAdapter {
    private Context activity;
    private int layout;
    private List<String> list;

    public Adapter_UserG_Tinh(Context activity, int layout, List<String> list) {
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
        TextView tv = (TextView)view.findViewById(R.id.tv);
        tv.setText(list.get(i).toString());
        return view;
    }
}
