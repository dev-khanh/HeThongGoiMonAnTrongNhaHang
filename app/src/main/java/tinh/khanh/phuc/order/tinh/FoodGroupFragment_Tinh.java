package tinh.khanh.phuc.order.tinh;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

import phuc.khanh.tinh.order.R;
import tinh.khanh.phuc.order.ClassDuLieu.LoaiMon;
import tinh.khanh.phuc.order.ClassDuLieu.MonAn;
import tinh.khanh.phuc.order.Public.Convert_Image_String;

import static android.app.Activity.RESULT_OK;

/**
 * Created by NGUYENTRITINH on 04/08/2017.
 */

public class FoodGroupFragment_Tinh extends Fragment {
    private final int REQUEST_CODE_CAMERA_ADD = 1;
    private final int REQUEST_CODE_FOLDER_ADD = 2;
    private final int REQUEST_CODE_CAMERA_UPDATE = 3;
    private final int REQUEST_CODE_FOLDER_UPDATE = 4;
    private final String FoodGroup = "LoaiMon";
    private final String Food = "MonAn";
    private DatabaseReference mData;
    private ImageView imgDialogAddView;
    private ImageView imgDialogUpdateView;
    private GridView gv;
    private TextView tvSum;
    private Button btnAddFoodG;
    private ArrayList<LoaiMon> arrFoodG;
    private ArrayList<MonAn> arrFood;
    private Adapter_FoodGroup_Tinh adap;
    private void Reference(View v){
        gv = (GridView)v.findViewById(R.id.gv);
        tvSum = (TextView)v.findViewById(R.id.tvSum);
        btnAddFoodG = (Button)v.findViewById(R.id.btnAdd);
    }
    private void CreateListFoodG(){
        arrFoodG = new ArrayList<>();
        adap = new Adapter_FoodGroup_Tinh(getActivity(),
                R.layout.item_foodgroup_tinh, arrFoodG);
        gv.setAdapter(adap);
        gv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShowPopupMenu(arrFoodG.get(i), view);
                return false;
            }
        });
    }
    private void ShowFoodGroup(DatabaseReference data){
        data.child(FoodGroup).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arrFoodG.add(dataSnapshot.getValue(LoaiMon.class));
                SortListFoodG();
                adap.notifyDataSetChanged();
                tvSum.setText("Tổng số: " + arrFoodG.size());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                for(int i=0;i<arrFoodG.size();i++){
                    if(Integer.parseInt(dataSnapshot.getKey()) == arrFoodG.get(i).getIdLoaiMon()){
                        arrFoodG.set(i, dataSnapshot.getValue(LoaiMon.class));
                        SortListFoodG();
                        adap.notifyDataSetChanged();
                        tvSum.setText("Tổng số: " + arrFoodG.size());
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                for(int i=0;i<arrFoodG.size();i++){
                    if(Integer.parseInt(dataSnapshot.getKey()) == arrFoodG.get(i).getIdLoaiMon()){
                        arrFoodG.remove(i);
                        SortListFoodG();
                        adap.notifyDataSetChanged();
                        tvSum.setText("Tổng số: " + arrFoodG.size());
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
    private void SortListFoodG(){
        for(int i = 0; i< arrFoodG.size() - 1; i++){
            for(int j = i + 1; j < arrFoodG.size(); j++){
                if(arrFoodG.get(i).getTenLoaiMon().compareTo(arrFoodG.get(j).getTenLoaiMon()) > 0){
                    LoaiMon temp = arrFoodG.get(i);
                    arrFoodG.set(i, arrFoodG.get(j));
                    arrFoodG.set(j, temp);
                }
            }
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_foodgroup_tinh, container, false);
        mData = FirebaseDatabase.getInstance().getReference();
        Reference(v);
        CreateListFoodG();
        ShowFoodGroup(mData);
        btnAddFoodG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialogAdd();

            }
        });
        return v;
    }
    private void ShowPopupMenu(final LoaiMon FoodG, View v){
        PopupMenu pop = new PopupMenu(getActivity(), v);
        pop.getMenu().add(0, 1, 0, "Sửa \"" + FoodG.getTenLoaiMon() + "\"");
        pop.getMenu().add(0, 2, 1, "Xóa \"" + FoodG.getTenLoaiMon() + "\"");
        pop.show();
        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case 1:
                        ShowDialogUpdate(FoodG);
                        break;
                    case 2:
                        ShowDialogDelete(FoodG);
                        break;
                }
                return false;
            }
        });
    }
    private boolean isFoodGroupName(String FoodGroupNewName){
        for(int i =0;i< arrFoodG.size();i++){
            if(FoodGroupNewName.equals(arrFoodG.get(i).getTenLoaiMon())){
                return true;
            }
        }
        return false;
    }
    private boolean isFoodGroupId(int FoodGroupId){
        for(int i = 0;i<arrFoodG.size();i++){
            if(FoodGroupId == arrFoodG.get(i).getIdLoaiMon())
                return true;
        }
        return false;
    }

    private void ShowDialogAdd(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_foodg_tinh);
        dialog.setCanceledOnTouchOutside(false);
        imgDialogAddView = (ImageView)dialog.findViewById(R.id.imgThumnail);
        ImageView imgCamera = (ImageView)dialog.findViewById(R.id.imgOpenCamera);
        ImageView imgFolder = (ImageView)dialog.findViewById(R.id.imgOpenFolder);
        final EditText edt = (EditText) dialog.findViewById(R.id.edt);
        Button btnAdd = (Button)dialog.findViewById(R.id.btnAdd);
        Button btnClose = (Button)dialog.findViewById(R.id.btnClose);
        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TakePicture(REQUEST_CODE_CAMERA_ADD);
            }
        });
        imgFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChoosePicture(REQUEST_CODE_FOLDER_ADD);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = edt.getText().toString();
                if(s.isEmpty()){
                    Toast.makeText(getActivity(),
                            "Bạn chưa nhập loại món ăn", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(isFoodGroupName(s)){
                        Toast.makeText(getActivity(),
                                "Loại món ăn này đã có", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Random r = new Random();
                        int id = r.nextInt(10000);
                        while(isFoodGroupId(id)){
                            id = r.nextInt(10000);
                        }
                        LoaiMon FG = new LoaiMon(id, s, new Convert_Image_String().convertImageToString(imgDialogAddView));
                        mData.child(FoodGroup).child(String.valueOf(id)).setValue(FG, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if(databaseError == null) {
                                    Toast.makeText(getActivity(),
                                            "Thêm Thành Công", Toast.LENGTH_SHORT).show();
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
    private void ShowDialogUpdate(final LoaiMon FoodG){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_foodg_tinh);
        dialog.setCanceledOnTouchOutside(false);
        TextView tv = (TextView)dialog.findViewById(R.id.tvTitle);
        tv.append(" ("+FoodG.getTenLoaiMon()+")");
        ImageView imgCamera = (ImageView)dialog.findViewById(R.id.imgOpenCamera);
        ImageView imgFolder = (ImageView)dialog.findViewById(R.id.imgOpenFolder);
        imgDialogUpdateView = (ImageView)dialog.findViewById(R.id.imgThumnail);
        imgDialogUpdateView.setImageBitmap(new Convert_Image_String().convertStringToBitmap(FoodG.getChuoiHinh()));
        final EditText edt = (EditText)dialog.findViewById(R.id.edt);
        edt.setText(FoodG.getTenLoaiMon());
        Button btnUpdate = (Button)dialog.findViewById(R.id.btnUpdate);
        Button btnClose = (Button)dialog.findViewById(R.id.btnClose);
        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TakePicture(REQUEST_CODE_CAMERA_UPDATE);
            }
        });
        imgFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChoosePicture(REQUEST_CODE_FOLDER_UPDATE);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringImg = new Convert_Image_String().convertImageToString(imgDialogUpdateView);
                final String s = edt.getText().toString();
                if(s.isEmpty()){
                    Toast.makeText(getActivity(),
                            "Bạn chưa nhập loại món ăn", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(stringImg.equals(FoodG.getChuoiHinh()) && s.equals(FoodG.getTenLoaiMon())){
                    dialog.cancel();
                }
                else{
                    if(!s.equals(FoodG.getTenLoaiMon())){
                        if(isFoodGroupName(s)){
                            Toast.makeText(getActivity(),
                                    "Loại món đã có", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    final android.support.v7.app.AlertDialog.Builder aDialog = new android.support.v7.app.AlertDialog.Builder(getActivity());
                    aDialog.setTitle("Cảnh Báo");
                    aDialog.setMessage(
                            "Các món ăn trong " + "\""+FoodG.getTenLoaiMon()+"\"" +
                                    " sẽ được chuyển sang \""+s+"\"\n" +
                                    "Bạn có chắc muốn thay đổi không?");
                    aDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            LoaiMon FG = new LoaiMon(FoodG.getIdLoaiMon(), s, new Convert_Image_String().convertImageToString(imgDialogUpdateView));
                            mData.child(FoodGroup).child(String.valueOf(FoodG.getIdLoaiMon())).setValue(FG, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if(databaseError == null) {
                                        Toast.makeText(getActivity(),
                                                "Sửa Thành công", Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                    }
                                    else{
                                        Toast.makeText(getActivity(),
                                                "Lỗi! Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                    aDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            aDialog.setCancelable(true);
                        }
                    });
                    aDialog.show();

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
    private void ShowDialogDelete(final LoaiMon FoodG){
        SelectFood(mData);
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Cảnh Báo");
        dialog.setMessage("Các món ăn liên quan đến \n" +
                "\"" + FoodG.getTenLoaiMon() + "\"\nSẽ bị xóa theo\n" +
                "Bạn có chắc muốn xóa không?");
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                for(int n=0;n<arrFood.size();){
                    if(FoodG.getIdLoaiMon() == arrFood.get(n).getIdLoaiMon()){
                        mData.child(Food).child(String.valueOf(arrFood.get(n).getIdMonAn())).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if(databaseError != null){
                                    Toast.makeText(getActivity(),
                                            "Quá trình các món ăn đã xãy ra lỗi", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        });
                        arrFood.remove(n);
                    }
                    else{
                        n++;
                    }
                }
                mData.child(FoodGroup).child(String.valueOf(FoodG.getIdLoaiMon())).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError == null) {

                            Toast.makeText(getActivity(),
                                    "Xóa Thành Công", Toast.LENGTH_SHORT).show();
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

    private void TakePicture(int RESQUEST_CODE){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, RESQUEST_CODE);
    }
    private void ChoosePicture(int RESQUEST_CODE){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, RESQUEST_CODE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CODE_CAMERA_ADD){
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgDialogAddView.setImageBitmap(bitmap);
            }
            else if (requestCode == REQUEST_CODE_FOLDER_ADD){
                Uri uri = data.getData();
                imgDialogAddView.setImageURI(uri);
            }
            else if(requestCode == REQUEST_CODE_CAMERA_UPDATE){
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgDialogUpdateView.setImageBitmap(bitmap);
            }
            else if(requestCode == REQUEST_CODE_FOLDER_UPDATE){
                Uri uri = data.getData();
                imgDialogUpdateView.setImageURI(uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void SelectFood(DatabaseReference databaseReference){
        arrFood = new ArrayList<>();
        databaseReference.child(Food).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arrFood.add(dataSnapshot.getValue(MonAn.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                MonAn food = dataSnapshot.getValue(MonAn.class);
                for(int i=0;i<arrFood.size();i++){
                    if(food.getIdMonAn() == arrFood.get(i).getIdMonAn()){
                        arrFood.set(i, food);
                        break;
                    }
                }
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
}
