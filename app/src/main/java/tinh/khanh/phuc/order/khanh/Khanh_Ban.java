package tinh.khanh.phuc.order.khanh;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import phuc.khanh.tinh.order.R;
import tinh.khanh.phuc.order.ClassDuLieu.BanAn;
import tinh.khanh.phuc.order.ClassDuLieu.NguoiDung;
import tinh.khanh.phuc.order.Public.Adapter_Table;

/**
 * Created by saakh on 06/08/2017.
 */

public class Khanh_Ban extends AppCompatActivity{

    GridView gv;

    DatabaseReference mDT;
    final String Ban_an = "BanAn";
    ArrayList<BanAn> mang;
    Adapter_Table adap ;
    private NguoiDung user;
    Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.khanh_ban);

        Intent it = getIntent();
        bundle = it.getBundleExtra("packet");
        if(bundle!=null) {
            user = new NguoiDung(
                    bundle.getString("UserName"),
                    bundle.getString("PassWord"),
                    bundle.getString("FullName"),
                    bundle.getString("ImageString"),
                    bundle.getString("Group"),
                    bundle.getBoolean("State")
            );
        }
        AnhXa();
        KhoiTao_DT();
        Click();

    }



    private void AnhXa(){
        gv = (GridView) findViewById(R.id.gv_khanh);
        mDT = FirebaseDatabase.getInstance().getReference();

        mang = new ArrayList<>();
        adap = new Adapter_Table(getApplicationContext(),R.layout.khanh_ban_an,mang,R.id.tv_khanh_ban); // Sài chung
        gv.setAdapter(adap);
    }


    // gọi menu
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logOut){
            user.setDangOnline(false);
            mDT.child("NguoiDung").child(user.getTaiKhoan()).setValue(user, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if(databaseError == null)
                    {
                        finish();
                    }
                    else
                    {
                        Toast.makeText(Khanh_Ban.this,
                                "Lỗi! Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    private void KhoiTao_DT(){
        mDT.child(Ban_an).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                mang.add(dataSnapshot.getValue(BanAn.class));

                Sap_Xep_Ban();

                adap.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                for(int n=0; n<mang.size(); n++){
                    if(Integer.parseInt(dataSnapshot.getKey()) == mang.get(n).getIdBan()) {
                        mang.set(n, dataSnapshot.getValue(BanAn.class));

                        Sap_Xep_Ban();

                        adap.notifyDataSetChanged();
                }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                for(int j=0; j<mang.size(); j++){
                    if (Integer.parseInt(dataSnapshot.getKey()) == mang.get(j).getIdBan()){
                        mang.remove(j);

                        Sap_Xep_Ban();

                        adap.notifyDataSetChanged();
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


    //sắp xếp thứ tự bàn ăn
    private void Sap_Xep_Ban(){
        for(int i = 0; i< mang.size() - 1; i++) {
            for (int j = i + 1; j < mang.size(); j++) {
                if (mang.get(i).getTenBan().length() > mang.get(j).getTenBan().length()) {
                    BanAn temp = mang.get(i);
                    mang.set(i, mang.get(j));
                    mang.set(j, temp);

                } else if (mang.get(i).getTenBan().length() == mang.get(j).getTenBan().length()) {
                    if (mang.get(i).getTenBan().compareTo(mang.get(j).getTenBan()) > 0) {
                        BanAn temp = mang.get(i);
                        mang.set(i, mang.get(j));
                        mang.set(j, temp);
                    }
                }
            }
        }
    }


    private void Click(){
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String s = mang.get(position).getTenBan();
                int d = mang.get(position).getIdBan();

                //Toast.makeText(getApplicationContext(), String.valueOf(d), Toast.LENGTH_SHORT).show();

                Intent it = new Intent(getApplicationContext(),Khanh_Mon.class);
                bundle.putString("TenBan", s);
                bundle.putInt("IdBan", d);

                it.putExtra("packet", bundle);

                startActivity(it);
                finish();
            }
        });
    }
}
