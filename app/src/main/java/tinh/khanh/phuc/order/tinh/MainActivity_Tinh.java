package tinh.khanh.phuc.order.tinh;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import phuc.khanh.tinh.order.R;
import tinh.khanh.phuc.order.ClassDuLieu.NguoiDung;

/**
 * Created by NGUYENTRITINH on 03/08/2017.
 */

public class MainActivity_Tinh extends AppCompatActivity {
    private DrawerLayout drawer;
    private ActionBarDrawerToggle actionBar;
    private Bundle bundle;
    private void Reference(){
        drawer = (DrawerLayout)findViewById(R.id.drawerLayout);
    }
    private void ReplaceFragment(Fragment fragment){
        FragmentManager fragM = getFragmentManager();
        FragmentTransaction fragT = fragM.beginTransaction();
        fragT.replace(R.id.frameContent, fragment);
        fragT.addToBackStack(null);
        fragT.commit();
    }

    @Override
    public void onBackPressed() {
        drawer.closeDrawer(Gravity.START);
        if(getFragmentManager().getBackStackEntryCount() > 1){
            getFragmentManager().popBackStack();

        }else {
            super.onBackPressed();
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startActivity(startMain);
            finish();
        }
    }

    private void CreateListCategory(){
        ListView lv = (ListView)findViewById(R.id.lv);
        final ArrayList<SiderBar_Tinh> arr = new ArrayList<>();
        SiderBar_Tinh Home = new SiderBar_Tinh(R.drawable.icon_home_tinh, "Màn Hình Chính");
        arr.add(Home);//0
        SiderBar_Tinh User = new SiderBar_Tinh(R.drawable.icon_user_tinh, "Quản Lý Người Dùng");
        arr.add(User);//1
        SiderBar_Tinh Table = new SiderBar_Tinh(R.drawable.icon_table_tinh, "Quản Lý Bàn Ăn");
        arr.add(Table);//2
        SiderBar_Tinh FoodGroup = new SiderBar_Tinh(R.drawable.tinh_mon9, "Quản Lý Loại Món Ăn");
        arr.add(FoodGroup);//3
        SiderBar_Tinh Food = new SiderBar_Tinh(R.drawable.tinh_mon1, "Quản Lý Món Ăn");
        arr.add(Food);//4
        SiderBar_Tinh BillOrder = new SiderBar_Tinh(R.drawable.icon_order_tinh, "Quản Lý Phiếu Order");
        arr.add(BillOrder);//5
        SiderBar_Tinh BillPay = new SiderBar_Tinh(R.drawable.icon_calculator_tinh, "Quản Lý Hóa Đơn Thanh Toán");
        arr.add(BillPay);//6
        SiderBar_Tinh DiscountCode = new SiderBar_Tinh(R.drawable.icon_discountcode_tinh, "Quản Lý Mã Giảm Giá");
        arr.add(DiscountCode);//7
        SiderBar_Tinh Statistical = new SiderBar_Tinh(R.drawable.tinh_thongke, "Thống Kê");
        arr.add(Statistical);//8
        Adapter_SiderBar_Tinh adap = new Adapter_SiderBar_Tinh(
                this,
                R.layout.item_sider_bar_tinh,
                arr
        );
        lv.setAdapter(adap);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        MainFragment_Tinh mainFragment = new MainFragment_Tinh();
                        mainFragment.setArguments(bundle);
                        ReplaceFragment(mainFragment);
                        break;
                    case 1:
                        ReplaceFragment(new User_Fragment_Tinh());
                        break;
                    case 2:
                        ReplaceFragment(new TableFragment_Tinh());
                        break;
                    case 3:
                        ReplaceFragment(new FoodGroupFragment_Tinh());
                        break;
                    case 4:
                        ReplaceFragment(new FoodFragment_Tinh());
                        break;
                    case 5:
                        ReplaceFragment(new BillOrder_Fragment_Tinh());
                        break;
                    case 6:
                        ReplaceFragment(new BillPay_Fragment_Tinh());
                        break;
                    case 7:
                        ReplaceFragment(new DiscountCode_Frament_Tinh());
                        break;
                    case 8:
                        ReplaceFragment(new ThongKe_Fragment_Tinh());
                        break;
                }
                drawer.closeDrawer(Gravity.START);
                if(i==0)
                    getSupportActionBar().setTitle("Quản Lý");
                else
                    getSupportActionBar().setTitle(arr.get(i).getText());
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tinh);
        Intent it = getIntent();
        bundle = it.getBundleExtra("packet");
        Reference();
        actionBar = new ActionBarDrawerToggle(this, drawer, R.string.Open, R.string.Close);
        drawer.addDrawerListener(actionBar);
        actionBar.syncState();
        getSupportActionBar().setTitle("Quản Lý");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CreateListCategory();
        MainFragment_Tinh mainFragment = new MainFragment_Tinh();
        mainFragment.setArguments(bundle);
        ReplaceFragment(mainFragment);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(actionBar.onOptionsItemSelected(item))
            return true;
        else if(itemId == R.id.logOut){
            NguoiDung us = new NguoiDung(
                    bundle.getString("UserName"),
                    bundle.getString("PassWord"),
                    bundle.getString("FullName"),
                    bundle.getString("ImageString"),
                    bundle.getString("Group"),
                    false
            );
            FirebaseDatabase.getInstance().getReference().child("NguoiDung").child(us.getTaiKhoan()).setValue(us, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if(databaseError == null){
                        finish();
                        return;
                    }
                    Toast.makeText(MainActivity_Tinh.this,
                            "Lỗi! Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
}
