package tinh.khanh.phuc.order.khanh;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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
import tinh.khanh.phuc.order.ClassDuLieu.ChiTietPhieuOrder;
import tinh.khanh.phuc.order.ClassDuLieu.HDThanhToan;
import tinh.khanh.phuc.order.ClassDuLieu.LoaiMon;
import tinh.khanh.phuc.order.ClassDuLieu.MaGiamGia;
import tinh.khanh.phuc.order.ClassDuLieu.MonAn;
import tinh.khanh.phuc.order.ClassDuLieu.NguoiDung;
import tinh.khanh.phuc.order.ClassDuLieu.PhieuOrder;
import tinh.khanh.phuc.order.Login;

/**
 * Created by saakh on 06/08/2017.
 */

public class Khanh_Mon extends AppCompatActivity{

    private ListView lv_mon_an,  lv_loai , lv_khanh_Da_Goi;
    private String TenBan = "";

    private DatabaseReference mDT;
    private final String Mon_an = "MonAn";
    private final String Chi_Tiet_OD = "ChiTietPhieuOrder";
    private final String HDTT = "HDThanhToan";
    private Button btn_Goi_mon, Click_Thanh_Toan;

    private int idBan;
    private String NguoiDungDangNhap = "";
    private String idPhieuOrder = "";
    private String idHDTT = "";

    //goimon
    ArrayList<ChiTietPhieuOrder> dsCacMonDaGoi; //phục vụ cho việc xóa
    ArrayList<ChiTietPhieuOrder> dsMon_da_Goi;
    Khanh_Adap_Da_Goi adap_da_goi;


    TextView tvTongTienDangGoi, tv_tongTienDaGoi;
    // <--ds chứa
    ArrayList<ChiTietPhieuOrder> DanhSachPhieuOrder;
    Khanh_Adap_Order adapterOrder;
    ListView lv_KhanhShow;

    ArrayList<LoaiMon> danhSachLoaiMon;
    Khanh_Adap_Loai adapterLoaiMon;
    ArrayList<MonAn> danhSachMonAn_Chinh, danhSachMonAn_Phu;
    Khanh_Adap_Mon adapterMon;

    ArrayList<HDThanhToan> dsHoaDon;



    final String Loai_Mon = "LoaiMon";

    DrawerLayout dra_loai;
    RelativeLayout rela_loai;
    ActionBarDrawerToggle acti_loai;

    private NguoiDung user;
    public Bundle bundle;

    private ArrayList<MaGiamGia> dsMaGiamGia;
    private void LayDanhSachMaGiamGia(){
        dsMaGiamGia = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        final String ngayHeThong = String.valueOf(android.text.format.DateFormat.format("dd/MM/yyyy", c.getTime()));
        mDT.child("MaGiamGia").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MaGiamGia gg = dataSnapshot.getValue(MaGiamGia.class);
                if((KiemTraNgay_DangChuoi(ngayHeThong, gg.getNgayBD()) >= 0) && (KiemTraNgay_DangChuoi(ngayHeThong, gg.getNgayKT()) <= 0) && !gg.isDaDung()){
                    dsMaGiamGia.add(gg);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                MaGiamGia gg = dataSnapshot.getValue(MaGiamGia.class);
                if(!gg.isDaDung()){
                    for(int i = 0; i< dsMaGiamGia.size(); i++){
                        if(dsMaGiamGia.get(i).getMaGiamGia().equals(gg.getMaGiamGia())){
                            dsMaGiamGia.set(i, gg);
                            break;
                        }
                    }
                }
                else{
                    for(int i = 0; i< dsMaGiamGia.size(); i++){
                        if(dsMaGiamGia.get(i).getMaGiamGia().equals(gg.getMaGiamGia())){
                            dsMaGiamGia.remove(i);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                MaGiamGia gg = dataSnapshot.getValue(MaGiamGia.class);
                for(int i = 0; i< dsMaGiamGia.size(); i++){
                    if(dsMaGiamGia.get(i).getMaGiamGia().equals(gg.getMaGiamGia())){
                        dsMaGiamGia.remove(i);
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.khanh_mon);
        Intent it = getIntent();
        bundle = it.getBundleExtra("packet");
        if(bundle!=null){
            user = new NguoiDung(
                    bundle.getString("UserName"),
                    bundle.getString("PassWord"),
                    bundle.getString("FullName"),
                    bundle.getString("ImageString"),
                    bundle.getString("Group"),
                    bundle.getBoolean("State")
            );
            idBan = bundle.getInt("IdBan");
            TenBan = bundle.getString("TenBan");
        }
        setTitle(TenBan + " - Gọi món");
        NguoiDungDangNhap = user.getTaiKhoan();


        AnhXa();
        KhoiTaoDanhSach();
        KhoiTao_DT_mon();
        Goi_Menu();
        Click();

        LayDanhSachMaGiamGia();

    }

    private void AnhXa(){
        btn_Goi_mon = (Button) findViewById(R.id.btn_Goi_mon);

        tvTongTienDangGoi = (TextView)findViewById(R.id.tv_tongTienDangGoi);
        tv_tongTienDaGoi = (TextView) findViewById(R.id.tv_tongTienDaGoi);

        lv_mon_an = (ListView) findViewById(R.id.lv_khanh_ds);

        Click_Thanh_Toan = (Button) findViewById(R.id.Click_Thanh_Toan);

        lv_loai = (ListView) findViewById(R.id.lv_menu);
        dra_loai = (DrawerLayout) findViewById(R.id.widget);
        rela_loai = (RelativeLayout) findViewById(R.id.rela);
        acti_loai = new ActionBarDrawerToggle(this, dra_loai, R.string.Open, R.string.Close);
        lv_khanh_Da_Goi = (ListView) findViewById(R.id.lv_khanh_Da_Goi);

        mDT = FirebaseDatabase.getInstance().getReference();


    }
    private void Goi_Menu(){
        dra_loai.addDrawerListener(acti_loai);
        acti_loai.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    //<--ds chứa
    private void KhoiTaoDanhSach(){

        dsHoaDon = new ArrayList<>();

        danhSachLoaiMon = new ArrayList<>();
        adapterLoaiMon = new Khanh_Adap_Loai(this, R.layout.khanh_menu, danhSachLoaiMon);
        lv_loai.setAdapter(adapterLoaiMon);

        danhSachMonAn_Chinh = new ArrayList<>();

        danhSachMonAn_Phu = new ArrayList<>();
        adapterMon = new Khanh_Adap_Mon(this, R.layout.khanh_mon_an, danhSachMonAn_Phu);
        lv_mon_an.setAdapter(adapterMon);


        //goimon
        dsCacMonDaGoi = new ArrayList<>();
        dsMon_da_Goi = new ArrayList<>();
        adap_da_goi = new Khanh_Adap_Da_Goi(this, R.layout.khanh_adap_da_goi, dsMon_da_Goi,danhSachMonAn_Chinh );
        lv_khanh_Da_Goi.setAdapter(adap_da_goi);
        lv_khanh_Da_Goi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int TrangThai = dsMon_da_Goi.get(i).getTrangThai();
                if(TrangThai==0){
                    ShowDialogXoaMonAnDaGoi(dsMon_da_Goi.get(i).getIdMonAn());
                }
                else if(TrangThai == 1){
                    Toast.makeText(Khanh_Mon.this,
                            "Món ăn của bạn đang làm\nXin liên hệ với nhân viên",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(Khanh_Mon.this,
                            "Món ăn bạn đang dùng\nXin liên hệ với nhân viên",
                            Toast.LENGTH_LONG).show();
                }
            }
        });





        lv_KhanhShow = (ListView)findViewById(R.id.lv_khanh_Show);
        DanhSachPhieuOrder = new ArrayList<>();
        adapterOrder = new Khanh_Adap_Order(getApplicationContext(),
                R.layout.khanh_ds_mon_an,
                DanhSachPhieuOrder, danhSachMonAn_Chinh);
        lv_KhanhShow.setAdapter(adapterOrder);
        lv_KhanhShow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int Position = i;
                String tenMonAn = "";
                for(int n=0;n<danhSachMonAn_Chinh.size(); n++){
                    if(danhSachMonAn_Chinh.get(n).getIdMonAn() == DanhSachPhieuOrder.get(i).getIdMonAn()){
                        tenMonAn = danhSachMonAn_Chinh.get(n).getTenMonAn();
                        break;
                    }
                }
                AlertDialog.Builder dialog = new AlertDialog.Builder(Khanh_Mon.this);
                dialog.setTitle("Lựa Chọn");
                dialog.setMessage("Xóa món ăn: " + tenMonAn);
                dialog.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DanhSachPhieuOrder.remove(Position);
                        tvTongTienDangGoi.setText(String.valueOf(TinhTongTienMonAn(DanhSachPhieuOrder)));
                        adapterOrder.notifyDataSetChanged();
                    }
                });
                dialog.show();
            }
        });

    }
    //-->ds chứa
    private void ShowDialogXoaMonAnDaGoi(final int idMonAn){
        String tenMonAn = "";
        for(int i =0;i<danhSachMonAn_Chinh.size();i++){
            if(danhSachMonAn_Chinh.get(i).getIdMonAn() == idMonAn){
                tenMonAn = danhSachMonAn_Chinh.get(i).getTenMonAn();
                break;
            }
        }
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Lựa Chọn");
        dialog.setMessage("Xóa món ăn: " + tenMonAn);
        dialog.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int n = 0;
                while(n < dsCacMonDaGoi.size()){
                    if(dsCacMonDaGoi.get(n).getIdMonAn() == idMonAn){
                        mDT.child(Chi_Tiet_OD).child(dsCacMonDaGoi.get(n).getIdChiTiet()).removeValue();
                        dsCacMonDaGoi.remove(n);
                    }else{
                        n++;
                    }
                }
            }
        });
        dialog.show();
    }
    private void KhoiTao_DT_mon(){
        mDT.child(Mon_an).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                danhSachMonAn_Chinh.add(dataSnapshot.getValue(MonAn.class));
                Sap_Xep_Mon_ds_Chinh();
                danhSachMonAn_Phu.add(dataSnapshot.getValue(MonAn.class));
                Sap_Xep_Mon_ds_Phu();
                adapterMon.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                for (int u = 0; u<danhSachMonAn_Chinh.size(); u++){
                    if(Integer.parseInt(dataSnapshot.getKey()) == danhSachMonAn_Chinh.get(u).getIdMonAn()){
                        danhSachMonAn_Chinh.set(u, dataSnapshot.getValue(MonAn.class));
                        Sap_Xep_Mon_ds_Chinh();
                        adapterMon.notifyDataSetChanged();
                    }
                }
                for (int e = 0; e<danhSachMonAn_Phu.size(); e++){
                    if(Integer.parseInt(dataSnapshot.getKey()) == danhSachMonAn_Phu.get(e).getIdMonAn()){
                        danhSachMonAn_Phu.set(e, dataSnapshot.getValue(MonAn.class));
                        Sap_Xep_Mon_ds_Phu();
                        adapterMon.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                for (int o=0; o<danhSachMonAn_Chinh.size(); o++){
                    if(Integer.parseInt(dataSnapshot.getKey()) == danhSachMonAn_Chinh.get(o).getIdMonAn()){
                        danhSachMonAn_Chinh.remove(o);
                        Sap_Xep_Mon_ds_Chinh();
                        adapterMon.notifyDataSetChanged();
                    }
                }
                for (int p=0; p<danhSachMonAn_Phu.size(); p++){
                    if(Integer.parseInt(dataSnapshot.getKey()) == danhSachMonAn_Phu.get(p).getIdMonAn()){
                        danhSachMonAn_Phu.remove(p);
                        Sap_Xep_Mon_ds_Phu();
                        adapterMon.notifyDataSetChanged();
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


        mDT.child(Loai_Mon).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                danhSachLoaiMon.add(dataSnapshot.getValue(LoaiMon.class));
                Sap_Xep_Loai();
                adapterLoaiMon.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                for (int h = 0; h<danhSachLoaiMon.size(); h++){
                    if(Integer.parseInt(dataSnapshot.getKey()) == danhSachLoaiMon.get(h).getIdLoaiMon()){
                        danhSachLoaiMon.set(h ,dataSnapshot.getValue(LoaiMon.class));
                        Sap_Xep_Loai();
                        adapterLoaiMon.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                for (int y = 0; y<danhSachLoaiMon.size(); y++){
                    if(Integer.parseInt(dataSnapshot.getKey()) == danhSachLoaiMon.get(y).getIdLoaiMon()){
                        danhSachLoaiMon.remove(y);
                        Sap_Xep_Loai();
                        adapterLoaiMon.notifyDataSetChanged();
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



        //goimon
        mDT.child(Chi_Tiet_OD).addChildEventListener(new ChildEventListener() {


            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChiTietPhieuOrder c = dataSnapshot.getValue(ChiTietPhieuOrder.class);
                if (idBan == c.getIdBan() && c.getTrangThai() < 3){
                    idPhieuOrder = c.getIdPhieu();
                    idHDTT="HD"+idPhieuOrder.substring(5);
                    dsCacMonDaGoi.add(c);
                    for(int n=0;n<dsMon_da_Goi.size();n++){
                        if(c.getIdMonAn() == dsMon_da_Goi.get(n).getIdMonAn()
                                && c.getTrangThai() == dsMon_da_Goi.get(n).getTrangThai()){
                            //lấy số lượng hiện tại
                            int soLuong = dsMon_da_Goi.get(n).getSoLuong();

                            c.setSoLuong(soLuong + c.getSoLuong());
                            dsMon_da_Goi.set(n, c);

                            tv_tongTienDaGoi.setText(String.valueOf(TinhTongTienMonAn(dsMon_da_Goi)));
                            adap_da_goi.notifyDataSetChanged();
                            return;
                        }

                    }
                    dsMon_da_Goi.add(c);
                    tv_tongTienDaGoi.setText(String.valueOf(TinhTongTienMonAn(dsMon_da_Goi)));
                    adap_da_goi.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                ChiTietPhieuOrder c = dataSnapshot.getValue(ChiTietPhieuOrder.class);
                for(int n=0;n<dsCacMonDaGoi.size(); n++){
                    if(dsCacMonDaGoi.get(n).getIdChiTiet().equals(c.getIdChiTiet())){
                        dsCacMonDaGoi.get(n).setTrangThai(c.getTrangThai());
                        break;
                    }
                }
                for (int h = 0; h<dsMon_da_Goi.size(); h++){
                    if(c.getIdChiTiet().equals(dsMon_da_Goi.get(h).getIdChiTiet())){
                        if(c.getTrangThai() == 3){
                            dsMon_da_Goi.remove(h);
                            tv_tongTienDaGoi.setText(String.valueOf(TinhTongTienMonAn(dsMon_da_Goi)));
                        }
                        else {
                            c.setSoLuong(dsMon_da_Goi.get(h).getSoLuong());
                            dsMon_da_Goi.set(h, c);
                            for(int n =0;n < dsMon_da_Goi.size() -1 ;n++){
                                for(int m =n+1;m<dsMon_da_Goi.size();){
                                    if(dsMon_da_Goi.get(n).getIdMonAn() == dsMon_da_Goi.get(m).getIdMonAn() &&
                                            dsMon_da_Goi.get(n).getTrangThai() == dsMon_da_Goi.get(m).getTrangThai()){
                                        dsMon_da_Goi.get(n).setSoLuong(dsMon_da_Goi.get(n).getSoLuong() + dsMon_da_Goi.get(m).getSoLuong());
                                        dsMon_da_Goi.remove(m);
                                    }
                                    else{m++;}
                                }
                            }
                        }
                        adap_da_goi.notifyDataSetChanged();
                        break;
                    }
                }
                if(dsCacMonDaGoi.size() == 0){
                    idPhieuOrder = "";
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                for (int y = 0; y<dsMon_da_Goi.size(); y++){
                    if(dataSnapshot.getKey().equals(dsMon_da_Goi.get(y).getIdChiTiet())){
                        dsMon_da_Goi.remove(y);
                        Sap_Xep_Loai();
                        adap_da_goi.notifyDataSetChanged();
                        break;
                    }
                }
                tv_tongTienDaGoi.setText(String.valueOf(TinhTongTienMonAn(dsMon_da_Goi)));
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


    }
    private int TinhTongTienMonAn(ArrayList<ChiTietPhieuOrder> arr){
        int tong = 0;
        for(int n = 0; n < arr.size(); n++){
            for(int m = 0; m<danhSachMonAn_Chinh.size(); m++){
                if(arr.get(n).getIdMonAn() == danhSachMonAn_Chinh.get(m).getIdMonAn()){
                    tong += arr.get(n).getSoLuong() * danhSachMonAn_Chinh.get(m).getGia();
                    break;
                }
            }
        }
        return tong;
    }

    public void Click(){
        btn_Goi_mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click_GoiMon();
            }
        });


        Click_Thanh_Toan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dsMon_da_Goi.size() <= 0){
                    Toast.makeText(Khanh_Mon.this, "Chưa có món nào", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(idPhieuOrder.equals("")){
                    final AlertDialog.Builder d = new AlertDialog.Builder(Khanh_Mon.this);
                    d.setTitle("Thông Báo");
                    d.setMessage("Đang thanh toán. Xin vui lòng chờ");
                    d.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            d.setCancelable(true);
                        }
                    });
                    d.show();
                    return;
                }

                for(int n =0; n < dsMon_da_Goi.size(); n++){
                    if(dsMon_da_Goi.get(n).getTrangThai() < 2){
                        Toast.makeText(Khanh_Mon.this, "Còn một số món chưa dùng. Hãy xử lý nó trước",
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                HienThiDiaLog_ThanhToan();
            }
        });


        lv_mon_an.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //lấy id món ăn
                int idMon = danhSachMonAn_Phu.get(i).getIdMonAn();
                //kiểm tra trong danh sách
                //nếu có món đã chọn, thì chỉ cập nhật lại số lượng và dừng các lệnh phía dưới
                for(int n=0;n<DanhSachPhieuOrder.size();n++){
                    if(idMon == DanhSachPhieuOrder.get(n).getIdMonAn()){
                        DanhSachPhieuOrder.get(n).setSoLuong(DanhSachPhieuOrder.get(n).getSoLuong()+1);
                        tvTongTienDangGoi.setText(String.valueOf(TinhTongTienMonAn(DanhSachPhieuOrder)));
                        adapterOrder.notifyDataSetChanged();
                        return;
                    }

                }

                if(idPhieuOrder.equals("")){
                    Random ran = new Random();
                    String idOrder = "Order";
                    idHDTT = "HD";
                    for(int n =0; n< 10;n++){
                        int num=ran.nextInt(9);
                        idOrder+= num;
                        idHDTT += num;
                    }
                    while(KiemTraIdPhieuOrder(idOrder)){
                        idOrder = "Order";
                        idHDTT = "HD";
                        for(int n =0; n< 10;n++){
                            int num=ran.nextInt(9);
                            idOrder+= num;
                            idHDTT += num;
                        }
                    }
                    idPhieuOrder = idOrder;
                }
                //tạo chi tiết phiếu order
                Random ran = new Random();
                String idChiTiet = "IdCT";
                for(int n = 0; n< 10;n++)
                    idChiTiet+=ran.nextInt(10);
                while(KiemTraIdChiTietPhieuOrder(idChiTiet)){
                    idChiTiet = "IdCT";
                    for(int n = 0; n< 10;n++)
                        idChiTiet+=ran.nextInt(10);
                }
                ChiTietPhieuOrder chiTietPhieuOrder = new ChiTietPhieuOrder(idChiTiet,
                        idPhieuOrder, idBan, NguoiDungDangNhap, idMon,
                        1, 0);
                DanhSachPhieuOrder.add(chiTietPhieuOrder);
                tvTongTienDangGoi.setText(String.valueOf(TinhTongTienMonAn(DanhSachPhieuOrder)));
                adapterOrder.notifyDataSetChanged();
            }
        });


        lv_loai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dra_loai.closeDrawer(Gravity.START);
                danhSachMonAn_Phu.clear();
                String TenLoaiMon = danhSachLoaiMon.get(i).getTenLoaiMon();
                setTitle(TenBan + " - Chọn món ("+TenLoaiMon+")" );
                int IdLoaiMon = danhSachLoaiMon.get(i).getIdLoaiMon();

                for(int n=0; n < danhSachMonAn_Chinh.size(); n++){
                    if(IdLoaiMon == danhSachMonAn_Chinh.get(n).getIdLoaiMon()){
                        danhSachMonAn_Phu.add(danhSachMonAn_Chinh.get(n));
                    }
                }
                adapterMon.notifyDataSetChanged();
            }
        });
    }

    private boolean KiemTraIdPhieuOrder(String id_PhieuOrder){
        for(int n=0;n<DanhSachPhieuOrder.size();n++){
            if(DanhSachPhieuOrder.get(n).getIdPhieu().equals(id_PhieuOrder))
                return true;
        }
        return false;
    }
    private boolean KiemTraIdChiTietPhieuOrder(String id_ChiTietPhieuOrder){
        for(int n=0;n<DanhSachPhieuOrder.size();n++){
            if(DanhSachPhieuOrder.get(n).getIdChiTiet().equals(id_ChiTietPhieuOrder))
                return true;
        }
        return false;
    }




    //sap xep
    private void Sap_Xep_Mon_ds_Phu(){
        for(int i = 0; i< danhSachMonAn_Phu.size() - 1; i++){
            for(int j = i + 1; j < danhSachMonAn_Phu.size(); j++){
                if(danhSachMonAn_Phu.get(i).getTenMonAn().compareTo(danhSachMonAn_Phu.get(j).getTenMonAn()) > 0){
                    MonAn temp = danhSachMonAn_Phu.get(i);
                    danhSachMonAn_Phu.set(i, danhSachMonAn_Phu.get(j));
                    danhSachMonAn_Phu.set(j, temp);
                }
            }
        }
    }

    //sap xep
    private void Sap_Xep_Mon_ds_Chinh(){
        for(int i = 0; i< danhSachMonAn_Chinh.size() - 1; i++){
            for(int j = i + 1; j < danhSachMonAn_Chinh.size(); j++){
                if(danhSachMonAn_Chinh.get(i).getTenMonAn().compareTo(danhSachMonAn_Chinh.get(j).getTenMonAn()) > 0){
                    MonAn temp = danhSachMonAn_Chinh.get(i);
                    danhSachMonAn_Chinh.set(i, danhSachMonAn_Chinh.get(j));
                    danhSachMonAn_Chinh.set(j, temp);
                }
            }
        }
    }


//sap xep
    private void Sap_Xep_Loai(){
        for(int i = 0; i< danhSachLoaiMon.size() - 1; i++){
            for(int j = i + 1; j < danhSachLoaiMon.size(); j++){
                if(danhSachLoaiMon.get(i).getTenLoaiMon().compareTo(danhSachLoaiMon.get(j).getTenLoaiMon()) > 0){
                    LoaiMon temp = danhSachLoaiMon.get(i);
                    danhSachLoaiMon.set(i, danhSachLoaiMon.get(j));
                    danhSachLoaiMon.set(j, temp);
                }
            }
        }
    }


    private void Click_GoiMon(){

        int SoLuongPhanTu = DanhSachPhieuOrder.size();
        if(SoLuongPhanTu == 0){
            Toast.makeText(this, "Bạn chưa chọn món ăn nào", Toast.LENGTH_SHORT).show();
            return;

        }
        //cập nhật cho phiếu order
        String idOrder = DanhSachPhieuOrder.get(0).getIdPhieu();
        PhieuOrder pOrder = new PhieuOrder(idOrder,
                String.valueOf(android.text.format.DateFormat.format("dd/MM/yyyy - hh:mm:ss a",
                        Calendar.getInstance())));
        mDT.child("PhieuOrder").child(idOrder).setValue(pOrder);

        //cập nhật chi tiết phiếu order
        for(int n =0; n< SoLuongPhanTu;n++){
            String idChiTiet = DanhSachPhieuOrder.get(n).getIdChiTiet();
            mDT.child(Chi_Tiet_OD).child(idChiTiet).setValue(DanhSachPhieuOrder.get(n));
        }



        HDThanhToan h = new HDThanhToan(idHDTT, "","", idPhieuOrder, "");
        mDT.child(HDTT).child(idHDTT).setValue(h);


        DanhSachPhieuOrder.clear();
        tvTongTienDangGoi.setText("0");
        adapterOrder.notifyDataSetChanged();
    }

    // gọi menu
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (acti_loai.onOptionsItemSelected(item)){
            return true;
        }
        else if(item.getItemId() == R.id.logOut){

            user.setDangOnline(false);
            mDT.child("NguoiDung").child(user.getTaiKhoan()).setValue(user, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if(databaseError!=null){
                        Toast.makeText(Khanh_Mon.this,
                                "Lỗi! Vui lòng thử lại",
                                Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Intent it = new Intent(Khanh_Mon.this, Login.class);
                        startActivity(it);
                        finish();
                    }
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }


    private void HienThiDiaLog_ThanhToan(){
        final Dialog d = new Dialog(this);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.khanh_dialog_thanh_toan);


        Button btn_HuyThanhToan =(Button) d.findViewById(R.id.btn_HuyThanhToan);
        final EditText edt_ma_giam_gia =(EditText) d.findViewById(R.id.edt_ma_giam_gia);
        final RadioGroup radio_gro = (RadioGroup) d.findViewById(R.id.radio_gro);
        Button btn_thanh_Toan =(Button) d.findViewById(R.id.btn_thanhtoan);
        final RadioButton radio_co = (RadioButton) d.findViewById(R.id.radio_co);
        final RadioButton radio_khong = (RadioButton)d.findViewById(R.id.radio_khong);



        radio_co.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio_khong.setChecked(false);
                radio_co.setChecked(true);
                edt_ma_giam_gia.setEnabled(true);
            }
        });
        radio_khong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio_co.setChecked(false);
                radio_khong.setChecked(true);
                edt_ma_giam_gia.setEnabled(false);
            }
        });





        btn_HuyThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.cancel();
            }
        });

        btn_thanh_Toan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maGiamGia = "";

                if (radio_co.isChecked()) {
                    boolean isMaGiamGia = false;
                    maGiamGia = edt_ma_giam_gia.getText().toString();
                    if (maGiamGia.isEmpty()) {
                        Toast.makeText(Khanh_Mon.this, "Chưa nhập mã giảm giá", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    maGiamGia = maGiamGia.toUpperCase();
                    for(int n = 0; n < dsMaGiamGia.size(); n++){
                        if(dsMaGiamGia.get(n).getMaGiamGia().equals(maGiamGia)){
                            isMaGiamGia = true;
                            break;
                        }
                    }
                    if(!isMaGiamGia){
                        Toast.makeText(Khanh_Mon.this, "Mã giảm giá không có", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                HDThanhToan h = new HDThanhToan(idHDTT, String.valueOf(android.text.format.DateFormat.format("dd/MM/yyyy - hh:mm:ss a",
                        Calendar.getInstance())),"", idPhieuOrder, maGiamGia);
                mDT.child(HDTT).child(idHDTT).setValue(h);
                Toast.makeText(Khanh_Mon.this,
                        "Vui lòng chờ thanh toán", Toast.LENGTH_SHORT).show();
                idPhieuOrder="";
                d.cancel();


            }
        });
        d.show();

    }
    private int KiemTraNgay_DangChuoi(String d1, String d2){
        int Ngay1  = Integer.parseInt(d1.substring(0,2));
        int Thang1  = Integer.parseInt(d1.substring(3,5));
        int Nam1  = Integer.parseInt(d1.substring(6,10));
        int Ngay2  = Integer.parseInt(d2.substring(0,2));
        int Thang2  = Integer.parseInt(d2.substring(3,5));
        int Nam2  = Integer.parseInt(d2.substring(6,10));


        if(Nam1 == Nam2 && Thang1 == Thang2 && Ngay1 == Ngay2)
            return 0;
        if(Nam1 > Nam2)
            return 1;
        if(Nam1 == Nam2 && Thang1 > Thang2)
            return 1;
        if(Nam1 == Nam2 && Thang1 == Thang2 && Ngay1 > Ngay2)
            return 1;
        return -1;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent it =new Intent(Khanh_Mon.this, Khanh_Ban.class);
        it.putExtra("packet", bundle);
        startActivity(it);
        finish();
    }
}
