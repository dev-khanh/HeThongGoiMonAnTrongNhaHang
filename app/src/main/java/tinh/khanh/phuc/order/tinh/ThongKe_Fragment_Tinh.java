package tinh.khanh.phuc.order.tinh;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

import phuc.khanh.tinh.order.R;
import tinh.khanh.phuc.order.ClassDuLieu.ChiTietPhieuOrder;
import tinh.khanh.phuc.order.ClassDuLieu.HDThanhToan;
import tinh.khanh.phuc.order.ClassDuLieu.MaGiamGia;
import tinh.khanh.phuc.order.ClassDuLieu.MonAn;
import tinh.khanh.phuc.order.ClassDuLieu.NguoiDung;

/**
 * Created by NGUYENTRITINH on 31/10/2017.
 */

public class ThongKe_Fragment_Tinh extends Fragment {
    private final String Pay = "HDThanhToan";
    private final String FullOrder = "ChiTietPhieuOrder";
    private final String Food = "MonAn";
    private final String User = "NguoiDung";
    private final String DiscountCode = "MaGiamGia";


    private DatabaseReference mData;
    private TextView tvDateStart, tvDateEnd;
    private Button btnStart;
    private RadioButton rdbPay, rdbFood;

    private void References(View v){
        tvDateStart = (TextView)v.findViewById(R.id.tvDateStart);
        tvDateEnd = (TextView)v.findViewById(R.id.tvDateEnd);
        btnStart = (Button)v.findViewById(R.id.btnStart);
        rdbPay = (RadioButton)v.findViewById(R.id.rdbPay);
        rdbFood = (RadioButton)v.findViewById(R.id.rdbFood);
    }
    private void SelectUser(final ArrayList<NguoiDung> arr, final Adapter_ThongKe_Pay_Tinh adap){
        mData.child(User).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arr.add(dataSnapshot.getValue(NguoiDung.class));
                adap.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                NguoiDung u = dataSnapshot.getValue(NguoiDung.class);
                for(int i =0; i< arr.size(); i++){
                    if(u.getTaiKhoan().equals(arr.get(i).getTaiKhoan())){
                        arr.set(i, u);
                        adap.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                NguoiDung u = dataSnapshot.getValue(NguoiDung.class);
                for(int i =0; i< arr.size(); i++){
                    if(u.getTaiKhoan().equals(arr.get(i).getTaiKhoan())){
                        arr.remove(i);
                        adap.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void SelectDiscountCode(final ArrayList<MaGiamGia> arr, final Adapter_ThongKe_Pay_Tinh adap){
        mData.child(DiscountCode).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arr.add(dataSnapshot.getValue(MaGiamGia.class));
                adap.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                MaGiamGia code = dataSnapshot.getValue(MaGiamGia.class);
                for(int i =0; i< arr.size(); i++){
                    if(code.equals(arr.get(i).getMaGiamGia())){
                        arr.set(i, code);
                        adap.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                MaGiamGia code = dataSnapshot.getValue(MaGiamGia.class);
                for(int i =0; i< arr.size(); i++){
                    if(code.equals(arr.get(i).getMaGiamGia())){
                        arr.remove(i);
                        adap.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void SelectFood(final ArrayList<MonAn> arr, final Adapter_ThongKe_Pay_Tinh adap){
        mData.child(Food).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arr.add(dataSnapshot.getValue(MonAn.class));
                adap.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                MonAn f = dataSnapshot.getValue(MonAn.class);
                for(int i =0; i< arr.size(); i++){
                    if(arr.get(i).getIdMonAn() == f.getIdMonAn()){
                        arr.set(i, f);
                        adap.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                MonAn f = dataSnapshot.getValue(MonAn.class);
                for(int i =0; i< arr.size(); i++){
                    if(arr.get(i).getIdMonAn() == f.getIdMonAn()){
                        arr.remove(i);
                        adap.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void SelectFood(final ArrayList<MonAn> arr, final Adapter_ThongKe_Food_Tinh adap){
        mData.child(Food).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arr.add(dataSnapshot.getValue(MonAn.class));
                adap.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                MonAn f = dataSnapshot.getValue(MonAn.class);
                for(int i =0; i< arr.size(); i++){
                    if(arr.get(i).getIdMonAn() == f.getIdMonAn()){
                        arr.set(i, f);
                        adap.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                MonAn f = dataSnapshot.getValue(MonAn.class);
                for(int i =0; i< arr.size(); i++){
                    if(arr.get(i).getIdMonAn() == f.getIdMonAn()){
                        arr.remove(i);
                        adap.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void SelectFullOrder(final ArrayList<ChiTietPhieuOrder> arr, final Adapter_ThongKe_Pay_Tinh adap){
        mData.child(FullOrder).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChiTietPhieuOrder o = dataSnapshot.getValue(ChiTietPhieuOrder.class);
                if(o.getTrangThai() == 3){
                    arr.add(o);
                    adap.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                ChiTietPhieuOrder o = dataSnapshot.getValue(ChiTietPhieuOrder.class);
                if(o.getTrangThai() == 3){
                    arr.add(o);
                    adap.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                ChiTietPhieuOrder o=dataSnapshot.getValue(ChiTietPhieuOrder.class);
                for(int i=0;i<arr.size();i++){
                    if (o.getIdChiTiet().equals(arr.get(i).getIdChiTiet())){
                        arr.remove(i);
                        adap.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void SelectFullOrder(final String dateStart, final String dateEnd,final ArrayList<ChiTietPhieuOrder> arr, final Adapter_ThongKe_Food_Tinh adap){
        mData.child(FullOrder).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChiTietPhieuOrder o = dataSnapshot.getValue(ChiTietPhieuOrder.class);
                if(o.getTrangThai() == 3){
                    arr.add(o);
                    adap.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                ChiTietPhieuOrder o = dataSnapshot.getValue(ChiTietPhieuOrder.class);
                if(o.getTrangThai() == 3){
                    arr.add(o);
                    adap.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                ChiTietPhieuOrder o=dataSnapshot.getValue(ChiTietPhieuOrder.class);
                for(int i=0;i<arr.size();i++){
                    if (o.getIdChiTiet().equals(arr.get(i).getIdChiTiet())){
                        arr.remove(i);
                        adap.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void SelectPay(final String dateStart, final String dateEnd, final ArrayList<HDThanhToan> arr, final Adapter_ThongKe_Pay_Tinh adap){
        mData.child(Pay).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                HDThanhToan p = dataSnapshot.getValue(HDThanhToan.class);
                if(!p.getTaiKhoan().equals("")){
                    String sDate = "";
                    try {
                        sDate = p.getNgayLap().substring(0,10);
                    }
                    catch (Exception io){
                        Toast.makeText(getActivity(),
                                "Lỗi ngày tháng lấy từ database", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(CompartSring_TypeDate(sDate, dateStart)>=0 && CompartSring_TypeDate(sDate, dateEnd) <= 0){
                        arr.add(p);
                        adap.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                HDThanhToan p = dataSnapshot.getValue(HDThanhToan.class);
                if(!p.getTaiKhoan().equals("")){
                    String sDate = "";
                    try {
                        sDate = p.getNgayLap().substring(0,10);
                    }
                    catch (Exception io){
                        Toast.makeText(getActivity(),
                                "Lỗi ngày tháng lấy từ database", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(CompartSring_TypeDate(sDate, dateStart)>=0 && CompartSring_TypeDate(sDate, dateEnd) <= 0){
                        arr.add(p);
                        adap.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                HDThanhToan p = dataSnapshot.getValue(HDThanhToan.class);
                for(int i=0;i<arr.size();i++){
                    if(p.getIdHoaDon().equals(arr.get(i).getIdHoaDon())){
                        arr.remove(i);
                        adap.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_thongke_tinh, container, false);
        mData = FirebaseDatabase.getInstance().getReference();
        References(v);
        String toDay = String.valueOf(DateFormat.format("dd/MM/yyyy",Calendar.getInstance().getTime()));
        tvDateStart.setText(toDay);
        tvDateEnd.setText(toDay);
        tvDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDatePickerDialog(tvDateStart);
            }
        });
        tvDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDatePickerDialog(tvDateEnd);
            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rdbPay.isChecked()) {
                    ShowDialog_Pay(tvDateStart.getText().toString().trim(),
                          tvDateEnd.getText().toString().trim());
                }
                else{
                    ShowDialog_Food(tvDateStart.getText().toString().trim(),
                            tvDateEnd.getText().toString().trim());
                }
            }
        });
        return v;
    }

    private Calendar StringToCalendar(String s){
        Calendar c = Calendar.getInstance();
        int day = Integer.parseInt(s.substring(0,2));
        int month = Integer.parseInt(s.substring(3, 5));
        int year = Integer.parseInt(s.substring(6, s.length()));
        c.set(year, month, day);
        return c;
    }

    private void ShowDatePickerDialog(final TextView tv){
        final Calendar calendar = StringToCalendar(tv.getText().toString());
        int Day = calendar.get(Calendar.DAY_OF_MONTH);
        int Month = calendar.get(Calendar.MONTH);
        int Year = calendar.get(Calendar.YEAR);

        DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i, i1, i2);
                        tv.setText(String.valueOf(DateFormat.format("dd/MM/yyyy", calendar.getTime())));
                    }
                }, Year, Month-1, Day);
        dialog.show();
    }

    private void ShowDialog_Pay(String dateStart, String dateEnd){
        final Dialog d = new Dialog(getActivity());
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setCanceledOnTouchOutside(false);
        d.setContentView(R.layout.dialog_thongke_pay_tinh);
        TextView tvTitle = (TextView)d.findViewById(R.id.tvTitle);
        GridView gv = (GridView)d.findViewById(R.id.gv);
        Button btnClose = (Button)d.findViewById(R.id.btnClose);
        ArrayList<ChiTietPhieuOrder> arrFullOrder = new ArrayList<>();
        ArrayList<MonAn> arrFood = new ArrayList<>();
        ArrayList<MaGiamGia> arrDiscoutcode = new ArrayList<>();
        ArrayList<NguoiDung> arrUser = new ArrayList<>();

        ArrayList<HDThanhToan> arrPay = new ArrayList<>();
        Adapter_ThongKe_Pay_Tinh adap = new Adapter_ThongKe_Pay_Tinh(getActivity(),
                R.layout.item_dialog_thongke_pay_tinh,
                arrPay, arrUser, arrFullOrder, arrFood, arrDiscoutcode);
        gv.setAdapter(adap);
        SelectPay(dateStart, dateEnd, arrPay, adap);
        SelectFullOrder(arrFullOrder, adap);
        SelectFood(arrFood, adap);
        SelectDiscountCode(arrDiscoutcode, adap);
        SelectUser(arrUser, adap);

        tvTitle.setText(dateStart + " - " + dateEnd);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.cancel();
            }
        });

        d.show();
    }
    private void ShowDialog_Food(String dateStart, String dateEnd){
        final Dialog d = new Dialog(getActivity());
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setCanceledOnTouchOutside(false);
        d.setContentView(R.layout.dialog_thongke_food_tinh);
        TextView tvTitle = (TextView)d.findViewById(R.id.tvTitle);
        GridView gv = (GridView)d.findViewById(R.id.gv);
        Button btnClose = (Button)d.findViewById(R.id.btnClose);
        ArrayList<ChiTietPhieuOrder> arrFullOrder = new ArrayList<>();
        ArrayList<MonAn> arrFood = new ArrayList<>();
        ArrayList<HDThanhToan> arrPay = new ArrayList<>();
        Adapter_ThongKe_Food_Tinh adap = new Adapter_ThongKe_Food_Tinh(
                getActivity(),
                R.layout.item_dialog_thongke_food_tinh,
                arrFullOrder, arrFood);
        gv.setAdapter(adap);
        SelectFood(arrFood, adap);
        SelectFullOrder(dateStart, dateEnd, arrFullOrder, adap);

        tvTitle.setText(dateStart + " - " + dateEnd);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.cancel();
            }
        });

        d.show();
    }
    private int CompartSring_TypeDate(String sd1, String sd2){
        int day1 = Integer.parseInt(sd1.substring(0,2));
        int month1 = Integer.parseInt(sd1.substring(3,5));
        int year1 = Integer.parseInt(sd1.substring(6, sd1.length()));
        int day2 = Integer.parseInt(sd2.substring(0,2));
        int month2 = Integer.parseInt(sd2.substring(3,5));
        int year2 = Integer.parseInt(sd2.substring(6, sd2.length()));
        if(day1 == day2 && month1 == month2 && year1 == year2)
            return 0;
        if(year1>year2)
            return 1;
        if(year1 == year2 && month1 > month2)
            return 1;
        if(year1 == year2 && month1 == month2 && day1 > day2)
            return 1;
        return -1;
    }
}
