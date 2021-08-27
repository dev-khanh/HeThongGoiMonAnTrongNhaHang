package tinh.khanh.phuc.order.tinh;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import phuc.khanh.tinh.order.R;
import tinh.khanh.phuc.order.Public.Convert_Image_String;

/**
 * Created by NGUYENTRITINH on 26/09/2017.
 */

public class MainFragment_Tinh extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_tinh, container, false);
        ImageView img = (ImageView) v.findViewById(R.id.img);
        TextView tvFullName = (TextView)v.findViewById(R.id.tvFullName);
        Bundle bundle = getArguments();
        if(bundle!=null) {
            String name = bundle.getString("FullName").toString();
            String image = bundle.getString("ImageString").toString();
            tvFullName.setText(name);
            img.setImageBitmap(new Convert_Image_String().convertStringToBitmap(image));
        }
        return v;
    }
}
