package tinh.khanh.phuc.order.phuc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupMenu;
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


public class Activity_NhaBep_Phuc extends AppCompatActivity {
    private final String Food = "MonAn";
    private final String Table = "BanAn";
    private final String FullOrder = "ChiTietPhieuOrder";
    private ArrayList<ChiTietPhieuOrder> arrFullOrder, arrOrderTemp;
    private Adapter_NhaBep_Phuc adapNhaBep;
    private ArrayList<MonAn> arrFood;
    private ArrayList<BanAn> arrTable;
    private GridView gv;
    private DatabaseReference mdata;
    private NguoiDung user;

    private void References() {
        gv = (GridView) findViewById(R.id.gv);
    }
    private void CreateList() {
        arrFood = new ArrayList<>();
        arrTable = new ArrayList<>();
        arrFullOrder = new ArrayList<>();
        arrOrderTemp = new ArrayList<>();
        adapNhaBep = new Adapter_NhaBep_Phuc(getApplicationContext(),
                R.layout.item_nhabep_phuc, arrOrderTemp, arrFood, arrTable);
        gv.setAdapter(adapNhaBep);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ChiTietPhieuOrder c = new ChiTietPhieuOrder(
                        arrOrderTemp.get(i).getIdChiTiet(),
                        arrOrderTemp.get(i).getIdPhieu(),
                        arrOrderTemp.get(i).getIdBan(),
                        arrOrderTemp.get(i).getTaiKhoan(),
                        arrOrderTemp.get(i).getIdMonAn(),
                        arrOrderTemp.get(i).getSoLuong(),
                        arrOrderTemp.get(i).getTrangThai()
                );
                ShowPopupMenu(c, view);
            }
        });
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhabep_phuc);
        getSupportActionBar().setTitle("Nhà Bếp");
        Intent it = getIntent();
        Bundle bundle = it.getBundleExtra("packet");
        user = new NguoiDung(
                bundle.getString("UserName"),
                bundle.getString("PassWord"),
                bundle.getString("FullName"),
                bundle.getString("ImageString"),
                bundle.getString("Group"),
                bundle.getBoolean("State")
        );
        mdata = FirebaseDatabase.getInstance().getReference();
        References();
        CreateList();
        SelectFood();
        SelectTable();
        SelectOrder();

    }
    private void ShowPopupMenu(final ChiTietPhieuOrder c, View v){
        PopupMenu pop = new PopupMenu(getApplicationContext(), v);
        String foodName = "";
        for(int n =0; n< arrFood.size(); n++){
            if(c.getIdMonAn() == arrFood.get(n).getIdMonAn()){
                foodName = arrFood.get(n).getTenMonAn();
            }
        }
        String s = "";
        final int state = c.getTrangThai();
        if(state == 0)
            s = "Bắt đầu làm: " + foodName;
        else if(state == 1)
            s = "Làm xong: " + foodName;
        pop.getMenu().add(0,1,0, s);
        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                if(state == 0){
                    for(int n =0; n< arrFullOrder.size();n++){
                        if((c.getIdMonAn() == arrFullOrder.get(n).getIdMonAn()) &&
                                (c.getIdBan() == arrFullOrder.get(n).getIdBan()) &&
                                c.getTrangThai() == arrFullOrder.get(n).getTrangThai()){
                            c.setTrangThai(1);
                            c.setSoLuong(arrFullOrder.get(n).getSoLuong());
                            c.setIdChiTiet(arrFullOrder.get(n).getIdChiTiet());
                            mdata.child(FullOrder).child(arrFullOrder.get(n).getIdChiTiet()).setValue(c, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if(databaseError !=null){
                                        Toast.makeText(Activity_NhaBep_Phuc.this,
                                                "Lỗi! vui lòng thử lại", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            });
                            c.setTrangThai(0);
                        }
                    }
                }
                else if(state == 1){
                    for(int n =0; n< arrFullOrder.size();n++){
                        if((c.getIdMonAn() == arrFullOrder.get(n).getIdMonAn()) &&
                                (c.getIdBan() == arrFullOrder.get(n).getIdBan()) &&
                                c.getTrangThai() == arrFullOrder.get(n).getTrangThai()){
                            c.setTrangThai(2);
                            c.setSoLuong(arrFullOrder.get(n).getSoLuong());
                            c.setIdChiTiet(arrFullOrder.get(n).getIdChiTiet());
                            mdata.child(FullOrder).child(arrFullOrder.get(n).getIdChiTiet()).setValue(c, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if(databaseError !=null){
                                        Toast.makeText(Activity_NhaBep_Phuc.this,
                                                "Lỗi! vui lòng thử lại", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }

                            });
                            c.setTrangThai(1);
                        }
                    }
                }
                return false;
            }
        });
        pop.show();
    }


    private void SelectOrder(){
        mdata.child(FullOrder).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChiTietPhieuOrder c = dataSnapshot.getValue(ChiTietPhieuOrder.class);
                if(c.getTrangThai() < 2) {
                    arrFullOrder.add(c);
                    for(int n=0;n<arrOrderTemp.size();n++){
                        if((c.getIdMonAn() == arrOrderTemp.get(n).getIdMonAn()) &&
                                (c.getIdBan() == arrOrderTemp.get(n).getIdBan())&&
                                c.getTrangThai() == arrOrderTemp.get(n).getTrangThai()){
                            arrOrderTemp.get(n).setSoLuong(arrOrderTemp.get(n).getSoLuong() + c.getSoLuong());
                            adapNhaBep.notifyDataSetChanged();
                            return;
                        }
                    }
                    ChiTietPhieuOrder c1 = new ChiTietPhieuOrder(
                            c.getIdChiTiet(),
                            c.getIdPhieu(),
                            c.getIdBan(),
                            c.getTaiKhoan(),
                            c.getIdMonAn(),
                            c.getSoLuong(),
                            c.getTrangThai());
                    arrOrderTemp.add(c1);
                    adapNhaBep.notifyDataSetChanged();

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                ChiTietPhieuOrder c = dataSnapshot.getValue(ChiTietPhieuOrder.class);
                if(c.getTrangThai() > 1){
                    for(int n=0; n< arrFullOrder.size();n++){
                        if(arrFullOrder.get(n).getIdChiTiet().equals(c.getIdChiTiet())){
                            arrFullOrder.remove(n);
                            break;
                        }
                    }
                    for(int n=0;n< arrOrderTemp.size();n++){
                        if((c.getIdMonAn() == arrOrderTemp.get(n).getIdMonAn())&&
                                (c.getIdBan() == arrOrderTemp.get(n).getIdBan())&&
                                (arrOrderTemp.get(n).getTrangThai() == 1)){
                            arrOrderTemp.remove(n);
                            adapNhaBep.notifyDataSetChanged();
                            break;
                        }
                    }
                }
                else {
                    for (int n = 0; n < arrFullOrder.size(); n++) {
                        if (arrFullOrder.get(n).getIdChiTiet().equals(c.getIdChiTiet())) {
                            arrFullOrder.set(n, c);
                            break;
                        }
                    }
                    for(int n=0;n< arrOrderTemp.size();n++){
                        if((c.getIdMonAn() == arrOrderTemp.get(n).getIdMonAn())&&
                                (c.getIdBan() == arrOrderTemp.get(n).getIdBan())){
                            arrOrderTemp.get(n).setTrangThai(c.getTrangThai());
                            for(int a =0; a<arrOrderTemp.size() -1;a++){
                                for(int b =a+1; b<arrOrderTemp.size();){
                                    if(arrOrderTemp.get(a).getIdMonAn() == arrOrderTemp.get(b).getIdMonAn() &&
                                            arrOrderTemp.get(a).getIdBan() == arrOrderTemp.get(b).getIdBan()){
                                        arrOrderTemp.get(a).setSoLuong(arrOrderTemp.get(a).getSoLuong() + arrOrderTemp.get(b).getSoLuong());
                                        arrOrderTemp.remove(b);
                                    }
                                    else b++;
                                }
                            }
                            adapNhaBep.notifyDataSetChanged();
                            break;
                        }
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                ChiTietPhieuOrder c = dataSnapshot.getValue(ChiTietPhieuOrder.class);
                if(c.getTrangThai() < 2){
                    for(int n=0; n< arrFullOrder.size();n++){
                        if(arrFullOrder.get(n).getIdChiTiet().equals(c.getIdChiTiet())){
                            arrFullOrder.remove(n);
                            break;
                        }
                    }
                    for(int n=0;n< arrOrderTemp.size();n++){
                        if((c.getIdMonAn() == arrOrderTemp.get(n).getIdMonAn())&&
                                (c.getIdBan() == arrOrderTemp.get(n).getIdBan())){
                            arrOrderTemp.remove(n);
                            adapNhaBep.notifyDataSetChanged();
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
    private void SelectFood(){

        mdata.child(Food).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arrFood.add(dataSnapshot.getValue(MonAn.class));
                adapNhaBep.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

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
                adapNhaBep.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startActivity(startMain);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logOut:
                user.setDangOnline(false);
                mdata.child("NguoiDung").child(user.getTaiKhoan()).setValue(user, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError != null){
                            Toast.makeText(Activity_NhaBep_Phuc.this,
                                    "Lỗi! Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            finish();
                        }
                    }
                });
        }
        return super.onOptionsItemSelected(item);
    }
}
