package tinh.khanh.phuc.order.Public;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import tinh.khanh.phuc.order.ClassDuLieu.BanAn;

/**
 * Created by NGUYENTRITINH on 03/08/2017.
 */

public class Adapter_Table extends BaseAdapter {
    private Context activity;
    private int layout;
    private List<BanAn> list;
    private int IdTextView;

    public Adapter_Table(Context activity, int layout, List<BanAn> list, int idTextView) {
        this.activity = activity;
        this.layout = layout;
        this.list = list;
        IdTextView = idTextView;
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
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);
        TextView tv = (TextView)view.findViewById(IdTextView);
        tv.setText(list.get(i).getTenBan());
        return view;
    }
}
