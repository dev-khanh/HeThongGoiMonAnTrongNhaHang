package tinh.khanh.phuc.order.tinh;

import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
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
import tinh.khanh.phuc.order.Public.Convert_Image_String;

import static android.app.Activity.RESULT_OK;
import static phuc.khanh.tinh.order.R.id.imgThumnail;

/**
 * Created by NGUYENTRITINH on 14/08/2017.
 */

public class User_Fragment_Tinh extends Fragment {
    private final String User = "NguoiDung";
    private final int REQUEST_CODE_ADD_CAMERA = 1;
    private final int REQUEST_CODE_ADD_FOLDER = 2;
    private final int REQUEST_CODE_UPDATE_CAMERA = 3;
    private final int REQUEST_CODE_UPDATE_FOLDER = 4;
    private GridView gv;
    private TextView tvSum;
    private Button btnAddUser;
    private ArrayList<NguoiDung> arrUser;
    private Adapter_User_Tinh adapUser;
    private DatabaseReference mData;
    private ImageView imgThumnailAdd, imgThumnailUpdate;
    private void Reference(View v){
        gv = (GridView)v.findViewById(R.id.gv);
        tvSum = (TextView)v.findViewById(R.id.tvSum);
        btnAddUser = (Button)v.findViewById(R.id.btnAdd);
    }
    private void CreateListUser(){
        arrUser = new ArrayList<>();
        adapUser = new Adapter_User_Tinh(getActivity(),
                R.layout.item_user_tinh, arrUser);
        gv.setAdapter(adapUser);
        gv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(arrUser.get(i).getDangOnline())
                    Toast.makeText(getActivity(),
                            "Tài khoản \""+arrUser.get(i).getTaiKhoan()+"\" đang sử dụng\nKhông thể tác động",
                            Toast.LENGTH_LONG).show();
                else
                    ShowPopupMenu(arrUser.get(i), view);
                return false;
            }
        });
    }
    private void ShowUser(){
        mData.child(User).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arrUser.add(dataSnapshot.getValue(NguoiDung.class));
                tvSum.setText("Tổng số: " + arrUser.size());
                adapUser.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                NguoiDung U = dataSnapshot.getValue(NguoiDung.class);
                for(int i = 0; i < arrUser.size(); i++){
                    if(U.getTaiKhoan().equals(arrUser.get(i).getTaiKhoan())){
                        arrUser.set(i, U);
                        adapUser.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                NguoiDung U = dataSnapshot.getValue(NguoiDung.class);
                for(int i =0; i< arrUser.size(); i++){
                    if(U.getTaiKhoan().equals(arrUser.get(i).getTaiKhoan())){
                        arrUser.remove(i);
                        tvSum.setText("Tổng số: " + arrUser.size());
                        adapUser.notifyDataSetChanged();
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
        View v = inflater.inflate(R.layout.fragment_user_tinh, container, false);
        mData = FirebaseDatabase.getInstance().getReference();
        Reference(v);
        CreateListUser();
        ShowUser();
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialogAdd();
            }
        });
        return v;
    }
    private void TakePicture(int REQUEST_CODE){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE);
    }
    private void ChoosePicture(int REQUEST_CODE){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CODE_ADD_CAMERA){
                Bitmap bm = (Bitmap)data.getExtras().get("data");
                imgThumnailAdd.setImageBitmap(bm);
            }
            else if(requestCode == REQUEST_CODE_ADD_FOLDER){
                Uri uri = data.getData();
                imgThumnailAdd.setImageURI(uri);
            }else if(requestCode == REQUEST_CODE_UPDATE_CAMERA){
                Bitmap bm = (Bitmap)data.getExtras().get("data");
                imgThumnailUpdate.setImageBitmap(bm);
            }
            else if(requestCode == REQUEST_CODE_UPDATE_FOLDER){
                Uri uri = data.getData();
                imgThumnailUpdate.setImageURI(uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void ShowPopupMenu(final NguoiDung user, View v){
        PopupMenu pop = new PopupMenu(getActivity(), v);
        pop.getMenu().add(0, 1, 0, "Đổi mật khẩu: " + user.getTaiKhoan());
        pop.getMenu().add(1, 2, 1, "Sửa tài khoản: " + user.getTaiKhoan());
        pop.getMenu().add(2, 3, 2, "Xóa tài khoản: " + user.getTaiKhoan());
        pop.show();
        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case 1:
                        ShowDialogChangePass(user);
                        break;
                    case 2:
                        ShowDialogUpdate(user);
                        break;
                    case 3:
                        ShowDialogDelete(user);
                        break;
                }
                return false;
            }
        });
    }
    private Boolean isUserName(String uName){
        for(int i = 0; i< arrUser.size(); i++)
            if(arrUser.get(i).getTaiKhoan().equals(uName))
                return true;
        return false;
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
    private void ShowDialogAdd(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_user_tinh);
        dialog.setCanceledOnTouchOutside(false);
        imgThumnailAdd = (ImageView)dialog.findViewById(imgThumnail);
        ImageView imgCamera = (ImageView)dialog.findViewById(R.id.imgOpenCamera);
        ImageView imgFolder = (ImageView)dialog.findViewById(R.id.imgOpenFolder);
        final EditText edtFullName = (EditText)dialog.findViewById(R.id.edtFullName);
        final EditText edtUserName = (EditText)dialog.findViewById(R.id.edtUserName);
        final EditText edtPassWord = (EditText)dialog.findViewById(R.id.edtPassWord);
        final EditText edtPassWord2 = (EditText)dialog.findViewById(R.id.edtPassWord2);
        final Spinner spUserGroup = (Spinner)dialog.findViewById(R.id.spUserGroup);
        Button btnAdd = (Button)dialog.findViewById(R.id.btnAdd);
        Button btnClose =(Button)dialog.findViewById(R.id.btnClose);
        final String[] UserGroup = {""};
        final ArrayList<String> arrUserG = new ArrayList<>();
        arrUserG.add("Phục Vụ");
        arrUserG.add("Thu Ngân");
        arrUserG.add("Nhà Bếp");
        arrUserG.add("Quản Lý");
        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TakePicture(REQUEST_CODE_ADD_CAMERA);
            }
        });
        imgFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChoosePicture(REQUEST_CODE_ADD_FOLDER);
            }
        });
        Adapter_UserG_Tinh adapUserG = new Adapter_UserG_Tinh(getActivity(),
                R.layout.item_spinner_userg_tinh, arrUserG);
        spUserGroup.setAdapter(adapUserG);
        spUserGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                UserGroup[0] = arrUserG.get(spUserGroup.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String FullName = edtFullName.getText().toString();
                String UserName = edtUserName.getText().toString();
                String PassWord = edtPassWord.getText().toString();
                String PassWord2 = edtPassWord2.getText().toString();
                if(FullName.isEmpty()){
                    Toast.makeText(getActivity(),
                            "Bạn chưa nhập họ tên", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(UserName.isEmpty()){
                    Toast.makeText(getActivity(),
                            "Bạn chưa nhập tài khoản", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(PassWord.isEmpty()){
                    Toast.makeText(getActivity(),
                            "Bạn chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(PassWord2.isEmpty()){
                    Toast.makeText(getActivity(),
                            "Bạn chưa nhập mật khẩu lần 2", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(UserName.length() < 6 || UserName.length() > 30){
                    Toast.makeText(getActivity(),
                            "Tài khoản không hợp lệ",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(isUserName(UserName)){
                    Toast.makeText(getActivity(),
                            "Tài khoản người dùng đã có", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(PassWord.length() < 6 || PassWord.length() > 30){
                    Toast.makeText(getActivity(),
                            "Mật khẩu không hợp lệ",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!PassWord.equals(PassWord2)){
                    Toast.makeText(getActivity(),
                            "Mật khẩu không khớp",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                PassWord = StringToMD5(PassWord);
                NguoiDung U = new NguoiDung(UserName,
                        PassWord,
                        FullName,
                        new Convert_Image_String().convertImageToString(imgThumnailAdd)
                        , UserGroup[0]
                        , false);
                mData.child(User).child(UserName).setValue(U, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError == null){
                            Toast.makeText(getActivity(),
                                    "Thêm thành công", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                        else{
                            Toast.makeText(getActivity(),
                                    "Lỗi! vui lòng thử lại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
    private void ShowDialogUpdate(final NguoiDung user){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_user_tinh);
        dialog.setCanceledOnTouchOutside(false);
        imgThumnailUpdate = (ImageView)dialog.findViewById(R.id.imgThumnail);
        ImageView imgCamera = (ImageView)dialog.findViewById(R.id.imgOpenCamera);
        ImageView imgOpenFolder = (ImageView)dialog.findViewById(R.id.imgOpenFolder);
        TextView tvTitle = (TextView)dialog.findViewById(R.id.tvTitle);
        final EditText edtFullName = (EditText)dialog.findViewById(R.id.edtFullName);
        final Spinner spUserGroup = (Spinner)dialog.findViewById(R.id.spUserGroup);
        Button btnUpdate = (Button)dialog.findViewById(R.id.btnUpdate);
        Button btnCloae = (Button)dialog.findViewById(R.id.btnClose);
        final ArrayList<String> arrUserG = new ArrayList<>();
        arrUserG.add("Phục Vụ");
        arrUserG.add("Thu Ngân");
        arrUserG.add("Nhà Bếp");
        arrUserG.add("Quản Lý");
        Adapter_UserG_Tinh adapUserG = new Adapter_UserG_Tinh(
                getActivity(),
                R.layout.item_spinner_userg_tinh,
                arrUserG);
        spUserGroup.setAdapter(adapUserG);
        tvTitle.append(" ("+user.getTaiKhoan()+")");
        imgThumnailUpdate.setImageBitmap(new Convert_Image_String().convertStringToBitmap(user.getChuoiHinh()));
        edtFullName.setText(user.getHoTen());
        final String[] UserGroup = {user.getNhomNguoiDung()};
        for(int n = 0; n< arrUserG.size(); n++) {
            if (user.getNhomNguoiDung().equals(arrUserG.get(n).toString())) {
                spUserGroup.setSelection(n);
                break;
            }
        }
        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TakePicture(REQUEST_CODE_UPDATE_CAMERA);
            }
        });
        imgOpenFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChoosePicture(REQUEST_CODE_UPDATE_FOLDER);
            }
        });

        spUserGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                UserGroup[0] = arrUserG.get(spUserGroup.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String FullName = edtFullName.getText().toString();
                String ImageString = new Convert_Image_String().convertImageToString(imgThumnailUpdate);
                if(FullName.isEmpty()){
                    Toast.makeText(getActivity(),
                            "Họ và tên không được bỏ trống",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(FullName.equals(user.getHoTen()) && ImageString.equals(user.getChuoiHinh()) && UserGroup[0].equals(user.getNhomNguoiDung())){
                    dialog.cancel();
                }
                else{
                    String UserName = user.getTaiKhoan();
                    String PassWord = user.getMatKhau();
                    Boolean Online = user.getDangOnline();
                    NguoiDung U = new NguoiDung(UserName,
                            PassWord, FullName, ImageString, UserGroup[0], Online);
                    mData.child(User).child(UserName).setValue(U, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if(databaseError == null){
                                Toast.makeText(getActivity(),
                                        "Sửa thành công", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                            else{
                                Toast.makeText(getActivity(),
                                        "Lỗi! Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

        btnCloae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
    private void ShowDialogDelete(final NguoiDung user){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Xóa Tài Khoản ("+user.getTaiKhoan()+")");
        dialog.setMessage("Sở hữu: " + user.getHoTen() + "\nBạn có chắc muốn xóa không?");
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mData.child(User).child(user.getTaiKhoan()).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError == null){
                            Toast.makeText(getActivity(),
                                    "Xóa thành công", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getActivity(),
                                    "Lỗi! vui lòng thử lại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        dialog.show();
    }
    private void ShowDialogChangePass(final NguoiDung user){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_change_pass_tinh);
        dialog.setCanceledOnTouchOutside(false);
        TextView tvTitle = (TextView)dialog.findViewById(R.id.tvTitle);
        final EditText edtPass = (EditText)dialog.findViewById(R.id.edtPassWord);
        final EditText edtPassNew = (EditText)dialog.findViewById(R.id.edtPassWordNew);
        final EditText edtPassNewAgain = (EditText)dialog.findViewById(R.id.edtPassWordNewAgain);
        Button btnSave = (Button)dialog.findViewById(R.id.btnSave);
        Button btnClose = (Button)dialog.findViewById(R.id.btnClose);
        tvTitle.append(" ("+user.getTaiKhoan()+")");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Pass = edtPass.getText().toString();
                String PassNew = edtPassNew.getText().toString();
                String PassNewAgain = edtPassNewAgain.getText().toString();
                if(Pass.isEmpty()){
                    Toast.makeText(getActivity(),
                            "Bạn chưa nhập mật khẩu củ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(PassNew.isEmpty()){
                    Toast.makeText(getActivity(),
                            "Bạn chưa nhập mật khẩu mới", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(PassNewAgain.isEmpty()){
                    Toast.makeText(getActivity(),
                            "Bạn chưa nhập lại mật khẩu mới", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Pass.length() < 6 || Pass.length() > 30){
                    Toast.makeText(getActivity(),
                            "Mật khẩu củ không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(PassNew.length() < 6 || PassNew.length() > 30){
                    Toast.makeText(getActivity(),
                            "Mật khẩu mới không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!(StringToMD5(Pass).equals(user.getMatKhau()))){
                    Toast.makeText(getActivity(),
                            "Mật khẩu củ không đúng", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!(PassNew.equals(PassNewAgain))){
                    Toast.makeText(getActivity(),
                            "Mật khẩu mới không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }
                String UserName = user.getTaiKhoan();
                NguoiDung U = new NguoiDung(UserName,
                        StringToMD5(PassNew),user.getHoTen(),
                        user.getChuoiHinh(), user.getNhomNguoiDung(),
                        user.getDangOnline());
                mData.child(User).child(UserName).setValue(U, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError == null){
                            Toast.makeText(getActivity(),
                                    "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                        else{
                            Toast.makeText(getActivity(),
                                    "Lỗi! vui lòng thử lại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
}
