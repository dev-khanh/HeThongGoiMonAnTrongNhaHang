package tinh.khanh.phuc.order.tinh;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import phuc.khanh.tinh.order.R;
import tinh.khanh.phuc.order.ClassDuLieu.BanAn;
import tinh.khanh.phuc.order.ClassDuLieu.ChiTietPhieuOrder;
import tinh.khanh.phuc.order.ClassDuLieu.HDThanhToan;
import tinh.khanh.phuc.order.ClassDuLieu.MaGiamGia;
import tinh.khanh.phuc.order.ClassDuLieu.MonAn;
import tinh.khanh.phuc.order.ClassDuLieu.NguoiDung;

/**
 * Created by NGUYENTRITINH on 27/09/2017.
 */

public class BillPay_Fragment_Tinh extends Fragment {
    private final String Pay = "HDThanhToan";
    private final String FullOrder = "ChiTietPhieuOrder";
    private final String Table = "BanAn";
    private final String User = "NguoiDung";
    private final String Food = "MonAn";
    private final String DisCountCode = "MaGiamGia";
    private DatabaseReference mdata;
    private GridView gv;
    private TextView tvSum;
    private ArrayList<ChiTietPhieuOrder> arrFullOrder;
    private ArrayList<BanAn> arrTable;
    private ArrayList<NguoiDung> arrUser;
    private ArrayList<MonAn> arrFood;
    private ArrayList<MaGiamGia> arrDiscoutCode;
    private ArrayList<HDThanhToan> arrPay;
    private Adapter_BillPay_Tinh adapPay;
    private void Reference(View v){
        gv = (GridView)v.findViewById(R.id.gv);
        tvSum = (TextView)v.findViewById(R.id.tvSum);
    }
    private void CreateList(){
        arrFullOrder = new ArrayList<>();
        arrTable = new ArrayList<>();
        arrUser = new ArrayList<>();
        arrFood = new ArrayList<>();
        arrDiscoutCode = new ArrayList<>();
        arrPay = new ArrayList<>();
        adapPay = new Adapter_BillPay_Tinh(getActivity(),
                R.layout.item_billpay_tinh, arrPay, arrFullOrder, arrTable,
                arrUser, arrFood, arrDiscoutCode);
        gv.setAdapter(adapPay);
        gv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShowPopupMenu(arrPay.get(i), view);
                return false;
            }
        });
    }
    private void SelectFullOrder(){
        mdata.child(FullOrder).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChiTietPhieuOrder o = dataSnapshot.getValue(ChiTietPhieuOrder.class);
                if(o.getTrangThai() == 3) {
                    arrFullOrder.add(o);
                    adapPay.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                ChiTietPhieuOrder o = dataSnapshot.getValue(ChiTietPhieuOrder.class);
                if(o.getTrangThai() == 3) {
                    arrFullOrder.add(o);
                    adapPay.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                ChiTietPhieuOrder o = dataSnapshot.getValue(ChiTietPhieuOrder.class);
                if(o.getTrangThai() == 3){
                    for(int n =0; n< arrFullOrder.size(); n++){
                        if(o.getIdChiTiet().equals(arrFullOrder.get(n).getIdChiTiet())){
                            arrFullOrder.remove(n);
                            adapPay.notifyDataSetChanged();
                            break;
                        }
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
    private void SelectTable(){
        mdata.child(Table).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arrTable.add(dataSnapshot.getValue(BanAn.class));
                adapPay.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                BanAn t = dataSnapshot.getValue(BanAn.class);
                for(int i =0;i< arrTable.size();i++){
                    if(arrTable.get(i).getIdBan() == t.getIdBan()){
                        arrTable.set(i, t);
                        adapPay.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                BanAn t = dataSnapshot.getValue(BanAn.class);
                for(int i =0;i< arrTable.size();i++){
                    if(arrTable.get(i).getIdBan() == t.getIdBan()){
                        arrTable.remove(i);
                        adapPay.notifyDataSetChanged();
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
    private void SelectPay(){
        mdata.child(Pay).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                HDThanhToan p = dataSnapshot.getValue(HDThanhToan.class);
                if(!p.getTaiKhoan().equals("")){
                    arrPay.add(p);
                    tvSum.setText("Tổng Số: " + arrPay.size());
                    adapPay.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                HDThanhToan p = dataSnapshot.getValue(HDThanhToan.class);
                if(!p.getTaiKhoan().equals("")){
                    arrPay.add(p);
                    tvSum.setText("Tổng Số: " + arrPay.size());
                    adapPay.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                HDThanhToan p = dataSnapshot.getValue(HDThanhToan.class);
                for(int i =0;i<arrPay.size(); i++){
                    if(p.getIdHoaDon().equals(arrPay.get(i).getIdHoaDon())){
                        arrPay.remove(i);
                        tvSum.setText("Tổng Số: " + arrPay.size());
                        adapPay.notifyDataSetChanged();
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
    private void SelectUser(){
        mdata.child(User).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arrUser.add(dataSnapshot.getValue(NguoiDung.class));
                adapPay.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                NguoiDung u = dataSnapshot.getValue(NguoiDung.class);
                for(int i =0; i< arrUser.size(); i++){
                    if(u.getTaiKhoan().equals(arrUser.get(i).getTaiKhoan())){
                        arrUser.set(i, u);
                        adapPay.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                NguoiDung u = dataSnapshot.getValue(NguoiDung.class);
                for(int i =0; i< arrUser.size(); i++){
                    if(u.getTaiKhoan().equals(arrUser.get(i).getTaiKhoan())){
                        arrUser.remove(i);
                        adapPay.notifyDataSetChanged();
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
    private void SelectFood(){
        mdata.child(Food).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arrFood.add(dataSnapshot.getValue(MonAn.class));
                adapPay.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                MonAn f = dataSnapshot.getValue(MonAn.class);
                for(int i =0;i<arrFood.size(); i++){
                    if(f.getIdMonAn() == arrFood.get(i).getIdMonAn()){
                        arrFood.set(i, f);
                        adapPay.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                MonAn f = dataSnapshot.getValue(MonAn.class);
                for(int i =0;i<arrFood.size(); i++){
                    if(f.getIdMonAn() == arrFood.get(i).getIdMonAn()){
                        arrFood.remove(i);
                        adapPay.notifyDataSetChanged();
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
    private void SelectDiscountCode(){
        mdata.child(DisCountCode).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arrDiscoutCode.add(dataSnapshot.getValue(MaGiamGia.class));
                adapPay.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                MaGiamGia c = dataSnapshot.getValue(MaGiamGia.class);
                for(int i =0; i< arrDiscoutCode.size();i++){
                    if(c.getMaGiamGia().equals(arrDiscoutCode.get(i).getMaGiamGia())){
                        arrDiscoutCode.set(i, c);
                        adapPay.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                MaGiamGia c = dataSnapshot.getValue(MaGiamGia.class);
                for(int i =0; i< arrDiscoutCode.size();i++){
                    if(c.getMaGiamGia().equals(arrDiscoutCode.get(i).getMaGiamGia())){
                        arrDiscoutCode.remove(i);
                        adapPay.notifyDataSetChanged();
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
        View v = inflater.inflate(R.layout.fragment_billpay_tinh, container, false);
        mdata = FirebaseDatabase.getInstance().getReference();
        Reference(v);
        CreateList();
        SelectFullOrder();
        SelectUser();
        SelectTable();
        SelectFood();
        SelectDiscountCode();
        SelectPay();
        return v;
    }

    private void ShowPopupMenu(final HDThanhToan pay, View v){
        PopupMenu pop = new PopupMenu(getActivity(), v);
        pop.getMenu().add("Xóa: " + pay.getIdHoaDon());
        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                ShowDialogDelete(pay);
                return false;
            }
        });
        pop.show();
    }
    private void ShowDialogDelete(final HDThanhToan pay){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Lựa Chọn");
        dialog.setMessage("Bạn muốn xóa HĐ ("+pay.getIdHoaDon()+")?");
        dialog.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mdata.child(Pay).child(pay.getIdHoaDon()).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError==null){
                            Toast.makeText(getActivity(),
                                    "Xóa xong", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getActivity(),
                                    "Lỗi! Vui lòng thử lại",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        dialog.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.setCancelable(true);
            }
        });
        dialog.show();
    }
}
