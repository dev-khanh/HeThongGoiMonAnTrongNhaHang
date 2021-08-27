package tinh.khanh.phuc.order.tinh;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.NumberPicker;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import phuc.khanh.tinh.order.R;
import tinh.khanh.phuc.order.ClassDuLieu.MaGiamGia;

/**
 * Created by NGUYENTRITINH on 01/10/2017.
 */

public class DiscountCode_Frament_Tinh extends Fragment {
    private final String DiscountCode = "MaGiamGia";
    private DatabaseReference mdata;
    private GridView gv;
    private TextView tvSum;
    private Button btnDelete, btnCreate;
    private ArrayList<MaGiamGia> arr;
    private Adapter_DiscountCode_Tinh adap;
    private void References(View v){
        gv = (GridView)v.findViewById(R.id.gv);
        tvSum = (TextView)v.findViewById(R.id.tvSum);
        btnDelete = (Button)v.findViewById(R.id.btnDetele);
        btnCreate = (Button)v.findViewById(R.id.btnCreate);
    }
    private void CreateList(){
        arr = new ArrayList<>();
        adap = new Adapter_DiscountCode_Tinh(getActivity(), R.layout.item_discountcode_tinh, arr);
        gv.setAdapter(adap);
        gv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShowPopupMenu(arr.get(i), view);
                return false;
            }
        });
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_discountcode_tinh, container, false);
        mdata = FirebaseDatabase.getInstance().getReference();
        References(v);
        CreateList();
        SelectDiscountCode();
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialogCreate();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialogDelete();
            }
        });
        return v;
    }
    private void SelectDiscountCode(){
        mdata.child(DiscountCode).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arr.add(dataSnapshot.getValue(MaGiamGia.class));
                tvSum.setText("Tổng số: " + arr.size());
                adap.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                MaGiamGia code = dataSnapshot.getValue(MaGiamGia.class);
                for(int n=0;n<arr.size();n++){
                    if(code.getMaGiamGia().equals(arr.get(n).getMaGiamGia())){
                        arr.set(n, code);
                        tvSum.setText("Tổng số: " + arr.size());
                        adap.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                MaGiamGia code = dataSnapshot.getValue(MaGiamGia.class);
                for(int n=0;n<arr.size();n++){
                    if(code.getMaGiamGia().equals(arr.get(n).getMaGiamGia())){
                        arr.remove(n);
                        tvSum.setText("Tổng số: " + arr.size());
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
    private void ShowDialogCreate(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_create_discountcode_tinh);
        dialog.setCanceledOnTouchOutside(false);
        final TextView tvStartDate = (TextView)dialog.findViewById(R.id.tvStartDate);
        final TextView tvEndDate = (TextView)dialog.findViewById(R.id.tvEndDate);
        final NumberPicker np = (NumberPicker)dialog.findViewById(R.id.np);
        final EditText edtPhanTram = (EditText)dialog.findViewById(R.id.edtPhanTram);
        Button btnSave = (Button)dialog.findViewById(R.id.btnSave);
        Button btnClose = (Button)dialog.findViewById(R.id.btnClose);
        final Calendar calendar = Calendar.getInstance();
        String DateString = String.valueOf(DateFormat.format("dd/MM/yyyy", calendar.getTime()));
        tvStartDate.setText(DateString);
        tvEndDate.setText(DateString);
        np.setMinValue(1);
        np.setMaxValue(1000);
        tvStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDatePickerDialog(tvStartDate);
            }
        });
        tvEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDatePickerDialog(tvEndDate);
            }
        });
        final int[] Count = {1};
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                Count[0] = np.getValue();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String PhanTram = edtPhanTram.getText().toString();
                if(PhanTram.isEmpty()){
                    Toast.makeText(getActivity(),
                            "Bạn chưa nhập số phần trăm giảm",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                String StartDate = tvStartDate.getText().toString();
                String EndDate = tvEndDate.getText().toString();
                if(CompartSring_TypeDate(StartDate, EndDate) > 0){
                    Toast.makeText(getActivity(),
                            "Lỗi! Ngày bắt đầu lớn hơn ngày kết thúc",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    for(int i=0;i<Count[0];i++){
                        Random r = new Random();
                        int max = 9999999;
                        int min = 1000000;
                        String code = "MGG";
                        code += String.valueOf(min + r.nextInt(max - min + 1));
                        while(CheckDiscountCode(code)){
                            code = "MGG";
                            code += String.valueOf(min + r.nextInt(max - min + 1));
                        }
                        mdata.child(DiscountCode).child(code).setValue(new MaGiamGia(code, StartDate, EndDate, Integer.parseInt(PhanTram), false), new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if(databaseError != null){
                                    Toast.makeText(getActivity(),
                                            "Lỗi! vui lòng thử lại",
                                            Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        });
                    }
                    Toast.makeText(getActivity(),
                            "Đã tạo thành công", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
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
    private Boolean CheckDiscountCode(String code){
        for(int i =0; i< arr.size(); i++){
            if(arr.get(i).getMaGiamGia().equals(code))
                return true;
        }
        return false;
    }
    private void ShowPopupMenu(final MaGiamGia code, View v){
        PopupMenu pop = new PopupMenu(getActivity(), v);
        pop.getMenu().add(0, 1, 0, "Xóa mã: " + code.getMaGiamGia());
        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AlertDialog.Builder Dialog = new AlertDialog.Builder(getActivity());
                Dialog.setTitle("Xóa Mã Giảm Giá ("+code.getMaGiamGia()+")");
                Dialog.setMessage("Bạn có chắc muốn xóa không?");
                Dialog.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mdata.child(DiscountCode).child(code.getMaGiamGia()).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if(databaseError == null){
                                    Toast.makeText(getActivity(),
                                            "Xóa thành công", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(getActivity(),
                                            "Lỗi! vui long thử lại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                Dialog.show();
                return false;
            }
        });
        pop.show();
    }
    private void ShowDialogDelete(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_delete_discountcode_tinh);
        dialog.setCanceledOnTouchOutside(false);
        final TextView tvStartDate = (TextView)dialog.findViewById(R.id.tvStartDate);
        final TextView tvEndDate = (TextView)dialog.findViewById(R.id.tvEndDate);
        Button btnSave = (Button)dialog.findViewById(R.id.btnSave);
        Button btnClose = (Button)dialog.findViewById(R.id.btnClose);
        Calendar calenderNow = Calendar.getInstance();
        tvStartDate.setText(String.valueOf(DateFormat.format("dd/MM/yyyy", calenderNow.getTime())));
        tvEndDate.setText(String.valueOf(DateFormat.format("dd/MM/yyyy", calenderNow.getTime())));
        tvStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDatePickerDialog(tvStartDate);
            }
        });
        tvEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDatePickerDialog(tvEndDate);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String StartDate = tvStartDate.getText().toString();
                String EndDate = tvEndDate.getText().toString();
                if(CompartSring_TypeDate(StartDate, EndDate) > 0){
                    Toast.makeText(getActivity(),
                            "Khoảng thời gian không hợp lệ\nXin kiểm tra lại",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                int count = 0;
                for(int n = 0; n< arr.size();){
                    String day = arr.get(n).getNgayKT();
                    if(CompartSring_TypeDate(StartDate, day) == 0 ||
                            CompartSring_TypeDate(EndDate, day) == 0 ||
                            (CompartSring_TypeDate(day, StartDate)>0 &&
                            CompartSring_TypeDate(day, EndDate)<0)){
                        mdata.child(DiscountCode).child(arr.get(n).getMaGiamGia()).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if(databaseError != null){
                                    Toast.makeText(getActivity(),
                                            "Lỗi! vui lòng thử lại",
                                            Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        });
                        for(int m = 0; m<arr.size(); m++){
                            if(arr.get(m).getMaGiamGia().equals(arr.get(n).getMaGiamGia())){
                                arr.remove(n);
                                adap.notifyDataSetChanged();
                                break;
                            }
                        }
                        count++;
                    }
                    else{n++;}
                }
                if(count == 0){
                    Toast.makeText(getActivity(),
                            "Không có mã nào trong khoảng thời gian này",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getActivity(),
                        "Xóa thành công " + count + " mã giảm giá", Toast.LENGTH_LONG).show();
                dialog.cancel();

            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
    private Calendar StringToCalendar(String s){
        Calendar c = Calendar.getInstance();
        int day = Integer.parseInt(s.substring(0,2));
        int month = Integer.parseInt(s.substring(3, 5));
        int year = Integer.parseInt(s.substring(6, s.length()));
        c.set(year, month, day);
        return c;
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
