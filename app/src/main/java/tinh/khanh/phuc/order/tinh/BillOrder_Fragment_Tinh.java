package tinh.khanh.phuc.order.tinh;

import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
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
import tinh.khanh.phuc.order.ClassDuLieu.MonAn;
import tinh.khanh.phuc.order.ClassDuLieu.NguoiDung;
import tinh.khanh.phuc.order.ClassDuLieu.PhieuOrder;

/**
 * Created by NGUYENTRITINH on 19/09/2017.
 */

public class BillOrder_Fragment_Tinh extends Fragment {
    private final String Order = "PhieuOrder";
    private final String FullOrder = "ChiTietPhieuOrder";
    private final String Table = "BanAn";
    private final String User = "NguoiDung";
    private final String Food = "MonAn";
    private DatabaseReference mdata;
    private GridView gv;
    private TextView tvSum;
    private Adapter_BillOrder_Tinh adapOrder;
    private ArrayList<PhieuOrder> arrOder;
    private ArrayList<ChiTietPhieuOrder> arrFullOrder;
    private ArrayList<BanAn> arrTable;
    private ArrayList<MonAn> arrFood;
    private ArrayList<NguoiDung> arrUser;
    private void Reference(View v){
        gv = (GridView) v.findViewById(R.id.gv);
        tvSum = (TextView)v.findViewById(R.id.tvSum);
    }
    private void CreateList(){
        arrFullOrder = new ArrayList<>();
        arrTable = new ArrayList<>();
        arrFood = new ArrayList<>();
        arrUser = new ArrayList<>();
        arrOder = new ArrayList<>();
        adapOrder = new Adapter_BillOrder_Tinh(getActivity(),
                R.layout.item_billorder_tinh,
                arrOder, arrFullOrder, arrTable);
        gv.setAdapter(adapOrder);
        gv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShowPopupMenu(view, arrOder.get(i));
                return false;
            }
        });
    }
    private void SelectOrder(){
        mdata.child(Order).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arrOder.add(dataSnapshot.getValue(PhieuOrder.class));
                tvSum.setText("Tổng số: " + arrOder.size());
                adapOrder.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                PhieuOrder o = dataSnapshot.getValue(PhieuOrder.class);
                for(int n =0; n< arrOder.size(); n++){
                    if(o.getIdPhieu().equals(arrOder.get(n).getIdPhieu())){
                        arrOder.remove(n);
                        tvSum.setText("Tổng số: " + arrOder.size());
                        adapOrder.notifyDataSetChanged();
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
    private void SelectFullOrder(){
        mdata.child(FullOrder).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arrFullOrder.add(dataSnapshot.getValue(ChiTietPhieuOrder.class));
                adapOrder.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                ChiTietPhieuOrder o = dataSnapshot.getValue(ChiTietPhieuOrder.class);
                for(int n = 0; n < arrFullOrder.size(); n++){
                    if(o.getIdChiTiet().equals(arrFullOrder.get(n).getIdChiTiet())){
                        arrFullOrder.set(n, o);
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                ChiTietPhieuOrder o = dataSnapshot.getValue(ChiTietPhieuOrder.class);
                for(int n = 0; n < arrFullOrder.size(); n++){
                    if(o.getIdChiTiet().equals(arrFullOrder.get(n).getIdChiTiet())){
                        arrFullOrder.remove(n);
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
    private void SelectTable(){
        mdata.child(Table).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arrTable.add(dataSnapshot.getValue(BanAn.class));
                adapOrder.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                BanAn t = dataSnapshot.getValue(BanAn.class);
                for(int n =0; n< arrTable.size();n++){
                    if(t.getIdBan() == arrTable.get(n).getIdBan()){
                        arrTable.set(n, t);
                        break;
                    }
                }
                adapOrder.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                BanAn t = dataSnapshot.getValue(BanAn.class);
                for(int n =0; n< arrTable.size();n++){
                    if(t.getIdBan() == arrTable.get(n).getIdBan()){
                        arrTable.remove(n);
                        break;
                    }
                }
                adapOrder.notifyDataSetChanged();
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
                adapOrder.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                MonAn f = dataSnapshot.getValue(MonAn.class);
                for(int n =0;n<arrFood.size(); n++){
                    if(f.getIdMonAn() == arrFood.get(n).getIdMonAn()){
                        arrFood.set(n, f);
                        break;
                    }
                }
                adapOrder.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                MonAn f = dataSnapshot.getValue(MonAn.class);
                for(int n =0;n<arrFood.size(); n++){
                    if(f.getIdMonAn() == arrFood.get(n).getIdMonAn()){
                        arrFood.remove(n);
                        break;
                    }
                }
                adapOrder.notifyDataSetChanged();
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
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                NguoiDung u = dataSnapshot.getValue(NguoiDung.class);
                for(int n = 0; n< arrUser.size(); n++){
                    if(u.getTaiKhoan().equals(arrUser.get(n).getTaiKhoan())){
                        arrUser.set(n, u);
                        break;
                    }
                }
                adapOrder.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                NguoiDung u = dataSnapshot.getValue(NguoiDung.class);
                for(int n = 0; n< arrUser.size(); n++){
                    if(u.getTaiKhoan().equals(arrUser.get(n).getTaiKhoan())){
                        arrUser.remove(n);
                        break;
                    }
                }
                adapOrder.notifyDataSetChanged();
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
        View v = inflater.inflate(R.layout.fragment_billoder_tinh, container, false);
        mdata = FirebaseDatabase.getInstance().getReference();
        Reference(v);
        CreateList();
        SelectFullOrder();
        SelectTable();
        SelectFood();
        SelectUser();
        SelectOrder();
        return v;
    }
    private void ShowPopupMenu(View v, final PhieuOrder order){
        PopupMenu pop = new PopupMenu(getActivity(), v);
        pop.getMenu().add(0, 1, 0, "Xem chi tiết: " + order.getIdPhieu());
        pop.getMenu().add(1, 2, 1, "Xóa: " + order.getIdPhieu());
        pop.show();
        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case 1:
                        ShowDialogChiTiet(order);
                        break;
                    case 2:
                        ShowDialogDeleteOrder(order);
                        break;
                }
                return false;
            }
        });
    }
    private void ShowDialogChiTiet(final PhieuOrder order){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_full_order_tinh);
        dialog.setCanceledOnTouchOutside(false);
        TextView tvIdOrder = (TextView)dialog.findViewById(R.id.tvIdOrder);
        TextView tvDate = (TextView)dialog.findViewById(R.id.tvDate);
        final TextView tvTableName = (TextView)dialog.findViewById(R.id.tvTableName);
        final TextView tvUser = (TextView)dialog.findViewById(R.id.tvUser);
        GridView gridView = (GridView)dialog.findViewById(R.id.gv);
        Button btnClose = (Button)dialog.findViewById(R.id.btnClose);
        tvIdOrder.setText(order.getIdPhieu());
        tvDate.setText(order.getNgayLap());
        for(int n=0;n<arrFullOrder.size();n++){
            if(arrFullOrder.get(n).getIdPhieu().equals(order.getIdPhieu())){
                for(int t = 0; t < arrTable.size(); t++) {
                    if (arrFullOrder.get(n).getIdBan() == arrTable.get(t).getIdBan()) {
                        tvTableName.setText(arrTable.get(t).getTenBan());
                        break;
                    }
                }
                for(int u = 0; u < arrUser.size(); u++){
                    if(arrFullOrder.get(n).getTaiKhoan().equals(arrUser.get(u).getTaiKhoan())){
                        tvUser.setText(arrUser.get(u).getHoTen());
                        break;
                    }
                }
                break;
            }
        }
        ArrayList<ChiTietPhieuOrder> arrayList = new ArrayList<>();
        for(int n =0; n< arrFullOrder.size(); n++){
            if(arrFullOrder.get(n).getIdPhieu().equals(order.getIdPhieu())){
                ChiTietPhieuOrder c = new ChiTietPhieuOrder(
                        arrFullOrder.get(n).getIdChiTiet(),
                        arrFullOrder.get(n).getIdPhieu(),
                        arrFullOrder.get(n).getIdBan(),
                        arrFullOrder.get(n).getTaiKhoan(),
                        arrFullOrder.get(n).getIdMonAn(),
                        arrFullOrder.get(n).getSoLuong(),
                        arrFullOrder.get(n).getTrangThai()
                );
                arrayList.add(c);
            }
        }
        for(int n=0;n< arrayList.size() - 1; n++){
            for(int m = n+ 1; m < arrayList.size();){
                if((arrayList.get(n).getIdMonAn() == arrayList.get(m).getIdMonAn())&&
                        (arrayList.get(n).getTrangThai() == arrayList.get(m).getTrangThai())){
                    arrayList.get(n).setSoLuong(
                            arrayList.get(n).getSoLuong() + arrayList.get(m).getSoLuong()
                    );
                    arrayList.remove(m);
                }
                else{
                    m++;
                }
            }
        }
        AdapterDialogFullOrder_Tinh a = new AdapterDialogFullOrder_Tinh(getActivity(),
                R.layout.item_dialog_orderfull_tinh, arrayList, arrFood);
        gridView.setAdapter(a);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
    private void ShowDialogDeleteOrder(final PhieuOrder order){
        for(int n =0; n<arrFullOrder.size(); n++){
            if(order.getIdPhieu().equals(arrFullOrder.get(n).getIdPhieu())){
                if(arrFullOrder.get(n).getTrangThai() != 3){
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle("Không Xóa Được (Phiếu này chưa thanh toán)");
                    dialog.setMessage("Bạn có muốn xem chi tiết không?");
                    dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialog.setCancelable(true);
                            ShowDialogChiTiet(order);
                        }
                    });
                    dialog.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialog.setCancelable(true);
                        }
                    });
                    dialog.show();
                    return;
                }
            }
        }
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Xác Nhận Xóa");
        dialog.setMessage("Bạn có chắc muốn xóa " + order.getIdPhieu() + "?");
        dialog.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                for(int n=0;n<arrFullOrder.size();n++){
                    if(arrFullOrder.get(n).getIdPhieu().equals(order.getIdPhieu())){
                        mdata.child(FullOrder).child(arrFullOrder.get(n).getIdChiTiet()).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if(databaseError != null){
                                    Toast.makeText(getActivity(),
                                            "Lỗi! Vui lòng thử lại",
                                            Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        });
                    }
                }
                mdata.child(Order).child(order.getIdPhieu()).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError == null){
                            Toast.makeText(getActivity(),
                                    "Xóa thành công", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getActivity(),
                                    "Lỗi! Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        dialog.show();
    }
}
