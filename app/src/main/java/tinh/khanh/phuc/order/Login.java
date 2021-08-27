package tinh.khanh.phuc.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import phuc.khanh.tinh.order.R;
import tinh.khanh.phuc.order.ClassDuLieu.NguoiDung;
import tinh.khanh.phuc.order.khanh.Khanh_Ban;
import tinh.khanh.phuc.order.khanh.Khanh_Thu_Ngan;
import tinh.khanh.phuc.order.phuc.Activity_NhaBep_Phuc;
import tinh.khanh.phuc.order.tinh.MainActivity_Tinh;

public class Login extends AppCompatActivity {

    private final String User = "NguoiDung";
    private DatabaseReference mdata;
    private ArrayList<NguoiDung> arr;


    private void SelectUser(){
        mdata.child(User).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arr.add(dataSnapshot.getValue(NguoiDung.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                NguoiDung u = dataSnapshot.getValue(NguoiDung.class);
                for(int i = 0; i < arr.size(); i++){
                    if(u.getTaiKhoan().equals(arr.get(i).getTaiKhoan())){
                        arr.set(i, u);
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                NguoiDung u = dataSnapshot.getValue(NguoiDung.class);
                for(int i = 0; i < arr.size(); i++){
                    if(u.getTaiKhoan().equals(arr.get(i).getTaiKhoan())){
                        arr.remove(i);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Đăng Nhập Hệ Thống");
        mdata = FirebaseDatabase.getInstance().getReference();
        arr = new ArrayList<>();
        SelectUser();
        final EditText edtUser = (EditText) findViewById(R.id.edtUserName);
        final EditText edtPass = (EditText)findViewById(R.id.edtPassWord);
        Button btnLogin = (Button)findViewById(R.id.btnLogin);
        Button btnCancel = (Button)findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Application_Exit();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserName = edtUser.getText().toString();
                String Pass = edtPass.getText().toString();
                if(UserName.isEmpty()){
                    Toast.makeText(Login.this, "Chưa nhập tài khoản", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Pass.isEmpty()){
                    Toast.makeText(Login.this, "Chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(UserName.length() < 6 || UserName.length() > 30){
                    Toast.makeText(Login.this, "Tài khoản không hợp lệ",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Pass.length() < 6 || Pass.length() > 30){
                    Toast.makeText(Login.this,
                            "Mật khẩu không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }
                Pass = StringToMD5(Pass);
                CheckLogin(UserName, Pass);
            }
        });
    }
    private String StringToMD5(String s){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(s.getBytes());
            BigInteger bigInteger = new BigInteger(1, md.digest());
            return bigInteger.toString(16);
        }
        catch (NoSuchAlgorithmException e){

        }
        return "";
    }
    private void CheckLogin(String user, final String pass){
        for(int i = 0; i< arr.size(); i++){
            final String userName = arr.get(i).getTaiKhoan();
            String passWord = arr.get(i).getMatKhau();
            if(userName.equals(user) && passWord.equals(pass)){
//                if(arr.get(i).getDangOnline()){
//                    Toast.makeText(this,
//                            "Tài khoản đang Online", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                final String fullName = arr.get(i).getHoTen();
                final String imageString = arr.get(i).getChuoiHinh();
                final String group = arr.get(i).getNhomNguoiDung();
                final boolean state = arr.get(i).getDangOnline();

                NguoiDung u = new NguoiDung(
                        userName, passWord, fullName, imageString,
                        group, true
                );
                mdata.child(User).child(userName).setValue(u, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError != null){
                            Toast.makeText(Login.this,
                                    "Lỗi! Vui lòng thử lại",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString("UserName", userName);
                        bundle.putString("PassWord", pass);
                        bundle.putString("FullName", fullName);
                        bundle.putString("ImageString",imageString);
                        bundle.putString("Group", group);
                        bundle.putBoolean("State", state);
                        if(group.equals("Phục Vụ")){
                            Intent it = new Intent(getApplicationContext(),
                                    Khanh_Ban.class);
                            it.putExtra("packet", bundle);
                            startActivity(it);
                        }
                        else if(group.equals("Thu Ngân")){
                            Intent it = new Intent(getApplicationContext(),
                                    Khanh_Thu_Ngan.class);
                            it.putExtra("packet", bundle);
                            startActivity(it);
                        }
                        else if(group.equals("Nhà Bếp")){
                            Intent it = new Intent(getApplicationContext(),
                                    Activity_NhaBep_Phuc.class);
                            it.putExtra("packet", bundle);
                            startActivity(it);
                        }
                        else if(group.equals("Quản Lý")){
                            Intent it = new Intent(getApplicationContext(),
                                    MainActivity_Tinh.class);
                            it.putExtra("packet", bundle);
                            startActivity(it);
                        }

                    }
                });


                return;
            }
        }
        Toast.makeText(this,
                "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
    }
    private void Application_Exit(){
        Intent it = new Intent(Intent.ACTION_MAIN);
        it.addCategory(Intent.CATEGORY_HOME);
        startActivity(it);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Application_Exit();
    }
}
