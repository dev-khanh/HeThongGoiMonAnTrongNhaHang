package tinh.khanh.phuc.order.khanh;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
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
 * Created by saakh on 22/09/2017.
 */

public class Khanh_Thu_Ngan extends AppCompatActivity {
    private GridView gv_khanh_thu_ngan;
    private DatabaseReference mDT;
    private final String Chi_Tiet_OD = "ChiTietPhieuOrder";
    private final String Ban_an = "BanAn";
    private final String Mon_an = "MonAn";
    private final String HoaDonThanhToan = "HDThanhToan";
    private final String MaGG = "MaGiamGia";
    private String Name_NhanVien = "";
    private NguoiDung user;


    private ArrayList<ChiTietPhieuOrder> arrChiTietPhieuOrder;
    private ArrayList<BanAn> arrBanAn;
    private ArrayList<MonAn> arrMonAn;
    private ArrayList<HDThanhToan> arrHoaDonThanhToan;
    private ArrayList<MaGiamGia> arrMaGiamGia;
    private Khanh_Adap_Thu_Ngan adapBanAn;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.khanh_thu_ngan);
        Bundle bundle = getIntent().getBundleExtra("packet");
        if(bundle!=null) {
            user = new NguoiDung(
                    bundle.getString("UserName"),
                    bundle.getString("PassWord"),
                    bundle.getString("FullName"),
                    bundle.getString("ImageString"),
                    bundle.getString("Group"),
                    bundle.getBoolean("State")
            );
            Name_NhanVien = user.getHoTen();
        }
        setTitle("Thu Ngân");
        AnhXa();
        KhoiTaoDanhSach();

        LayMonAn();
        LayPhieuOrder();
        LayBanAn();
        LayHoaDonThanhToan();
        LayMaGiamGia();
        Click();
    }

    private void KhoiTaoDanhSach(){
        arrHoaDonThanhToan = new ArrayList<>();
        arrChiTietPhieuOrder = new ArrayList<>();
        arrBanAn = new ArrayList<>();
        arrMonAn = new ArrayList<>();
        arrMaGiamGia = new ArrayList<>();
        adapBanAn = new Khanh_Adap_Thu_Ngan(this, R.layout.khanh_ban_an, arrChiTietPhieuOrder, arrBanAn, arrHoaDonThanhToan);
        gv_khanh_thu_ngan.setAdapter(adapBanAn);
    }
    private void AnhXa() {
        gv_khanh_thu_ngan = (GridView) findViewById(R.id.gv_khanh_thu_ngan);
        mDT = FirebaseDatabase.getInstance().getReference();



    }

    private void LayMaGiamGia(){
        mDT.child(MaGG).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MaGiamGia gg = dataSnapshot.getValue(MaGiamGia.class);
                if(!gg.isDaDung()) {
                    arrMaGiamGia.add(dataSnapshot.getValue(MaGiamGia.class));
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                MaGiamGia gg = dataSnapshot.getValue(MaGiamGia.class);
                if(gg.isDaDung()){
                    for(int i = 0; i< arrMaGiamGia.size(); i++){
                        if(gg.getMaGiamGia().equals(arrMaGiamGia.get(i).getMaGiamGia())){
                            arrMaGiamGia.remove(i);
                            break;
                        }
                    }
                }
                else{
                    for(int i = 0; i< arrMaGiamGia.size(); i++){
                        if(gg.getMaGiamGia().equals(arrMaGiamGia.get(i).getMaGiamGia())){
                            arrMaGiamGia.set(i, gg);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                MaGiamGia gg = dataSnapshot.getValue(MaGiamGia.class);
                for(int i = 0; i< arrMaGiamGia.size(); i++){
                    if(gg.getMaGiamGia().equals(arrMaGiamGia.get(i).getMaGiamGia())){
                        arrMaGiamGia.remove(i);
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
    private void LayHoaDonThanhToan(){

        mDT.child(HoaDonThanhToan).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                HDThanhToan hd = dataSnapshot.getValue(HDThanhToan.class);
                if(hd.getTaiKhoan().equals("")) {
                    arrHoaDonThanhToan.add(dataSnapshot.getValue(HDThanhToan.class));
                    adapBanAn.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                HDThanhToan hd = dataSnapshot.getValue(HDThanhToan.class);
                for(int i =0 ;i< arrHoaDonThanhToan.size(); i++){
                    if(arrHoaDonThanhToan.get(i).getIdHoaDon().equals(hd.getIdHoaDon())){
                        if(!hd.getTaiKhoan().equals("")){
                            arrHoaDonThanhToan.remove(i);
                        }
                        else{
                            //thong bao thanh toan
                            arrHoaDonThanhToan.set(i, hd);
                        }
                        adapBanAn.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                HDThanhToan hd = dataSnapshot.getValue(HDThanhToan.class);
                for(int i =0 ;i< arrHoaDonThanhToan.size(); i++){
                    if(arrHoaDonThanhToan.get(i).getIdHoaDon().equals(hd.getIdHoaDon())){
                        arrHoaDonThanhToan.remove(i);
                        adapBanAn.notifyDataSetChanged();
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


    private void LayPhieuOrder(){

        mDT.child(Chi_Tiet_OD).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChiTietPhieuOrder ct = dataSnapshot.getValue(ChiTietPhieuOrder.class);
                if(ct.getTrangThai() < 3){
                    arrChiTietPhieuOrder.add(ct);
                    adapBanAn.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                ChiTietPhieuOrder o=dataSnapshot.getValue(ChiTietPhieuOrder.class);

                for (int i = 0; i < arrChiTietPhieuOrder.size(); i++) {
                    if (arrChiTietPhieuOrder.get(i).getIdChiTiet().equals(o.getIdChiTiet())) {
                        if(o.getTrangThai() < 3)
                            arrChiTietPhieuOrder.set(i, o);
                        else
                            arrChiTietPhieuOrder.remove(i);
                        adapBanAn.notifyDataSetChanged();
                        break;
                    }
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                ChiTietPhieuOrder o=dataSnapshot.getValue(ChiTietPhieuOrder.class);
                for(int i =0; i< arrChiTietPhieuOrder.size(); i++){
                    if(arrChiTietPhieuOrder.get(i).getIdChiTiet().equals(o.getIdChiTiet())){
                        arrChiTietPhieuOrder.remove(i);
                        break;
                    }
                }
                for(int i =0 ; i< arrChiTietPhieuOrder.size(); i++){
                    if(arrChiTietPhieuOrder.get(i).getIdPhieu().equals(o.getIdPhieu())){
                        return;
                    }
                }
                for(int i=0; i< arrHoaDonThanhToan.size(); i++){
                    if(o.getIdPhieu().equals(arrHoaDonThanhToan.get(i).getIdPhieu())){
                        mDT.child(HoaDonThanhToan).child(arrHoaDonThanhToan.get(i).getIdHoaDon()).removeValue();
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
    private void LayBanAn(){

        mDT.child(Ban_an).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arrBanAn.add(dataSnapshot.getValue(BanAn.class));
                adapBanAn.notifyDataSetChanged();
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
    private void LayMonAn(){

        mDT.child(Mon_an).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arrMonAn.add(dataSnapshot.getValue(MonAn.class));
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


    // gọi menu
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }


    private void Click() {
       gv_khanh_thu_ngan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


              HienThiDiaLog_HoaDon(arrHoaDonThanhToan.get(i));


           }
       });
    }


    private void HienThiDiaLog_HoaDon(final HDThanhToan hd) {
        int PhanTramGG = 0;
        final String MaGG = hd.getMaGiamGia();
        if(!MaGG.equals("")){
            for(int i =0; i< arrMaGiamGia.size(); i++){
                if(arrMaGiamGia.get(i).getMaGiamGia().equals(MaGG)){
                    PhanTramGG = arrMaGiamGia.get(i).getSoPhanTram();
                    break;
                }
            }
        }
        final Dialog d = new Dialog(this);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.khanh_dialog_hoa_don);


        TextView tv_SHD = (TextView) d.findViewById(R.id.tv_SHD);
        TextView tv_ten_nhan_vien = (TextView) d.findViewById(R.id.tv_ten_nhan_vien);
        TextView tv_So_ban = (TextView) d.findViewById(R.id.tv_So_ban);
        TextView tv_NgayLapPhieu = (TextView) d.findViewById(R.id.tv_NgayLapPhieu);
        ListView lv_danhSach_chiTiec = (ListView) d.findViewById(R.id.lv_danhSach_chiTiec);
        TextView tvTongTien = (TextView)d.findViewById(R.id.tvTongTien);
        TextView tvTongThanhToan = (TextView)d.findViewById(R.id.tvTongThanhToan);
        final EditText edtTienTra = (EditText)d.findViewById(R.id.edt_TienTra);
        final TextView tvTienDu = (TextView)d.findViewById(R.id.tvTienDu);
        Button btnLuu = (Button)d.findViewById(R.id.btn_Luu);
        Button btnDong = (Button)d.findViewById(R.id.btnDong);

        tv_SHD.setText(hd.getIdHoaDon());
        tv_ten_nhan_vien.setText(Name_NhanVien);

        for(int n = 0; n< arrChiTietPhieuOrder.size(); n++){
            if(arrChiTietPhieuOrder.get(n).getIdPhieu().equals(hd.getIdPhieu())){
                for (int m = 0; m < arrBanAn.size(); m++){
                    if (arrChiTietPhieuOrder.get(n).getIdBan() == arrBanAn.get(m).getIdBan()){
                        tv_So_ban.setText(arrBanAn.get(m).getTenBan());
                        break;
                    }
                }
                break;
            }
        }

        tv_NgayLapPhieu.setText(hd.getNgayLap());
        final ArrayList<ChiTietPhieuOrder> arr = new ArrayList<>();
        for(int n =0; n< arrChiTietPhieuOrder.size(); n++){
            if(arrChiTietPhieuOrder.get(n).getIdPhieu().equals(hd.getIdPhieu())){
                ChiTietPhieuOrder c = new ChiTietPhieuOrder(arrChiTietPhieuOrder.get(n).getIdChiTiet(),
                        arrChiTietPhieuOrder.get(n).getIdPhieu(), arrChiTietPhieuOrder.get(n).getIdBan(),
                        arrChiTietPhieuOrder.get(n).getTaiKhoan(), arrChiTietPhieuOrder.get(n).getIdMonAn(),
                        arrChiTietPhieuOrder.get(n).getSoLuong(), arrChiTietPhieuOrder.get(n).getTrangThai());
                arr.add(c);

            }
        }
        for(int n =0;n< arr.size() - 1; n++){
            for(int m = n+1; m < arr.size();){
                if(arr.get(n).getIdMonAn() == arr.get(m).getIdMonAn()){
                    int soLuong = arr.get(n).getSoLuong() + arr.get(m).getSoLuong();
                    arr.get(n).setSoLuong(soLuong);
                    arr.remove(m);
                }
                else{m++;}
            }
        }
        final Khanh_Adap_Hoa_Don adap = new Khanh_Adap_Hoa_Don(this, R.layout.khanh_dialog_adap_hoa_don, arr, arrMonAn);
        lv_danhSach_chiTiec.setAdapter(adap);

        long TongTien = 0;
        for(int n = 0; n< arr.size(); n++){
            for(int m = 0; m< arrMonAn.size() ; m++){
                if(arr.get(n).getIdMonAn() == arrMonAn.get(m).getIdMonAn()){
                    TongTien += arr.get(n).getSoLuong() * arrMonAn.get(m).getGia();
                }
            }
        }
        long giamGia = (long) (TongTien * (PhanTramGG / 100.0));
        tvTongTien.setText(TongTien + " VNĐ (Giảm "+PhanTramGG+"%: "+giamGia+" VNĐ)");
        TongTien -= giamGia;
        tvTongThanhToan.setText(TongTien + " VNĐ");
        final long finalTongTien = TongTien;
        tvTienDu.setText((- finalTongTien) + " VNĐ");
        edtTienTra.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String TienTra = edtTienTra.getText().toString();
                if(!TienTra.equals("")) {

                    tvTienDu.setText((Long.parseLong(TienTra) - finalTongTien) + " VNĐ");
                }
                else{
                    tvTienDu.setText((- finalTongTien) + " VNĐ");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hd.getNgayLap().equals("")){
                    final AlertDialog.Builder aler = new AlertDialog.Builder(Khanh_Thu_Ngan.this);
                    aler.setTitle("Cảnh Báo");
                    aler.setMessage("Bàn này chưa gọi thanh toán");
                    aler.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            aler.setCancelable(true);
                        }
                    });
                    aler.show();
                }
                else{
                    String tienTra = edtTienTra.getText().toString();
                    if(tienTra.equals("")){
                        Toast.makeText(Khanh_Thu_Ngan.this,
                                "Chưa nhập tiền trả", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(Integer.parseInt(edtTienTra.getText().toString()) - finalTongTien < 0){
                        Toast.makeText(Khanh_Thu_Ngan.this,
                                "Tiền trả chưa đủ",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for(int i =0; i< arrChiTietPhieuOrder.size(); i++){
                        if(hd.getIdPhieu().equals(arrChiTietPhieuOrder.get(i).getIdPhieu())){
                            arrChiTietPhieuOrder.get(i).setTrangThai(3);
                            mDT.child(Chi_Tiet_OD).child(arrChiTietPhieuOrder.get(i).getIdChiTiet()).setValue(arrChiTietPhieuOrder.get(i), new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if(databaseError!=null){
                                        Toast.makeText(Khanh_Thu_Ngan.this,
                                                "Lỗi! Vui lòng thử lại",
                                                Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            });
                        }
                    }
                    hd.setTaiKhoan(user.getTaiKhoan());
                    mDT.child(HoaDonThanhToan).child(hd.getIdHoaDon()).setValue(hd, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if(databaseError!=null){
                                Toast.makeText(Khanh_Thu_Ngan.this,
                                        "Lỗi! vui lòng thử lại",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Toast.makeText(Khanh_Thu_Ngan.this,
                                    "Lưu xong", Toast.LENGTH_SHORT).show();
                            d.cancel();
                        }
                    });
                    if(!MaGG.equals("")){
                        MaGiamGia gg = null;
                        for(int i =0; i< arrMaGiamGia.size();i++){
                            if(MaGG.equals(arrMaGiamGia.get(i).getMaGiamGia())){
                                gg = arrMaGiamGia.get(i);
                                break;
                            }
                        }
                        gg.setDaDung(true);
                        mDT.child("MaGiamGia").child(gg.getMaGiamGia()).setValue(gg);
                    }
                }
            }
        });
        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.cancel();
            }
        });

        d.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logOut){
            user.setDangOnline(false);
            mDT.child("NguoiDung").child(user.getTaiKhoan()).setValue(user, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if(databaseError==null){
                        finish();
                    }
                    else{
                        Toast.makeText(Khanh_Thu_Ngan.this,
                                "Lỗi! Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent it = new Intent(Intent.ACTION_MAIN);
        it.addCategory(Intent.CATEGORY_HOME);
        startActivity(it);
        finish();
    }
}