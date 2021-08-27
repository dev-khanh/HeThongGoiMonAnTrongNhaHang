package tinh.khanh.phuc.order.khanh;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import phuc.khanh.tinh.order.R;
import tinh.khanh.phuc.order.ClassDuLieu.BanAn;
import tinh.khanh.phuc.order.ClassDuLieu.ChiTietPhieuOrder;
import tinh.khanh.phuc.order.ClassDuLieu.HDThanhToan;

/**
 * Created by saakh on 22/09/2017.
 */

public class Khanh_Adap_Thu_Ngan extends BaseAdapter{
    private Context ct;
    private int lo;
    private List<ChiTietPhieuOrder> dt;
    private List<BanAn> dtBan;
    private List<HDThanhToan> listThanhToan;

    public Khanh_Adap_Thu_Ngan(Context ct, int lo, List<ChiTietPhieuOrder> dt, List<BanAn> dtBan, List<HDThanhToan> listThanhToan) {
        this.ct = ct;
        this.lo = lo;
        this.dt = dt;
        this.dtBan = dtBan;
        this.listThanhToan = listThanhToan;
    }



    @Override
    public int getCount() {
        return listThanhToan.size();
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
        View v = View.inflate(ct, R.layout.khanh_ban_an, null);
        TextView tv =(TextView) v.findViewById(R.id.tv_khanh_ban);

        String idChiTietPhieuOrder = listThanhToan.get(i).getIdPhieu();
        for(int n = 0; n< dt.size(); n++){
            if(dt.get(n).getIdPhieu().equals(idChiTietPhieuOrder)){
                for (int m = 0; m < dtBan.size(); m++){
                    if (dt.get(n).getIdBan() == dtBan.get(m).getIdBan()){
                        tv.setText(dtBan.get(m).getTenBan());
                        if(!listThanhToan.get(i).getNgayLap().equals("")){
                            v.setBackgroundColor(Color.RED);
                        }
                        else{
                            v.setBackgroundResource(R.drawable.customs_listview_gridview);
                        }
                        break;
                    }
                }
                break;
            }
        }
        return v;
    }
}
