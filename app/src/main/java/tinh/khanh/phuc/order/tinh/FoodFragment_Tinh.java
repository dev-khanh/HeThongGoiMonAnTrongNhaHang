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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.Spinner;
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
import static phuc.khanh.tinh.order.R.id.btnAdd;

/**
 * Created by NGUYENTRITINH on 06/08/2017.
 */

public class FoodFragment_Tinh extends Fragment {
    private final int REQUEST_CODE_ADD_CAMERA = 1;
    private final int REQUEST_CODE_ADD_FOLDER = 2;
    private final int REQUEST_CODE_UPDATE_CAMERA=3;
    private final int REQUEST_CODE_UPDATE_FOLDER = 4;
    private final String Food = "MonAn";
    private final String FoodGroup = "LoaiMon";
    private DatabaseReference mData;
    private boolean FindFood = false;
    private int FindToFoodGId;
    private GridView gv;
    private ArrayList<MonAn> arrFood, arrFoodFull;
    private ArrayList<LoaiMon> arrFoodG;
    private Adapter_Food_Tinh adap;
    private Adapter_SpinnerFoodG_Tinh adapSpinnerFoodG;
    private TextView tvSum;
    private Button btnAddFood;
    private ImageView imgViewAddFood, imgViewUpdateFood;
    private void Reference(View v){
        gv = (GridView)v.findViewById(R.id.gv);
        tvSum = (TextView)v.findViewById(R.id.tvSum);
        btnAddFood = (Button)v.findViewById(btnAdd);
    }
    private void SelectFoodGroup(){
        arrFoodG = new ArrayList<>();
        adapSpinnerFoodG = new Adapter_SpinnerFoodG_Tinh(getActivity(),
                R.layout.item_spinner_foodg_tinh, arrFoodG);
        mData.child(FoodGroup).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arrFoodG.add(dataSnapshot.getValue(LoaiMon.class));
                SortListFoodG();
                adapSpinnerFoodG.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                LoaiMon foodG = dataSnapshot.getValue(LoaiMon.class);
                for(int i=0; i< arrFoodG.size();i++){
                    if(foodG.getIdLoaiMon() == arrFoodG.get(i).getIdLoaiMon()){
                        arrFoodG.set(i, foodG);
                        adap.notifyDataSetChanged();
                        SortListFoodG();
                        adapSpinnerFoodG.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                LoaiMon foodG = dataSnapshot.getValue(LoaiMon.class);
                for(int i=0; i< arrFoodG.size();i++){
                    if(foodG.getIdLoaiMon() == arrFoodG.get(i).getIdLoaiMon()){
                        arrFoodG.remove(i);
                        SortListFoodG();
                        adapSpinnerFoodG.notifyDataSetChanged();
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
    private void CreateListFood(){
        SelectFoodGroup();
        arrFoodFull = new ArrayList<>();
        arrFood = new ArrayList<>();
        adap = new Adapter_Food_Tinh(getActivity(),
                R.layout.item_food_tinh, arrFood, arrFoodG);
        gv.setAdapter(adap);
        gv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShowPopupMenu(arrFood.get(i), view);
                return false;
            }
        });
    }

    private void ShowFood(final DatabaseReference data){
        data.child(Food).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arrFoodFull.add(dataSnapshot.getValue(MonAn.class));
                arrFood.add(dataSnapshot.getValue(MonAn.class));
                if(FindFood){
                    FindData();
                    return;
                }
                SortListFood();
                adap.notifyDataSetChanged();
                tvSum.setText("Tổng số: " + arrFood.size());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                MonAn food = dataSnapshot.getValue(MonAn.class);
                for(int i=0;i<arrFoodFull.size();i++){
                    if(food.getIdMonAn() == arrFoodFull.get(i).getIdMonAn()){
                        arrFoodFull.set(i, food);
                        break;
                    }
                }
                if(FindFood){
                    FindData();
                    return;
                }
                for(int i=0;i<arrFood.size();i++){
                    if(food.getIdMonAn() == arrFood.get(i).getIdMonAn()){
                        arrFood.set(i, food);
                        SortListFood();
                        adap.notifyDataSetChanged();
                        tvSum.setText("Tổng số: " + arrFood.size());
                        break;
                    }
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                MonAn food = dataSnapshot.getValue(MonAn.class);
                for(int i=0;i<arrFoodFull.size();i++){
                    if(food.getIdMonAn() == arrFoodFull.get(i).getIdMonAn()){
                        arrFoodFull.remove(i);
                        break;
                    }
                }
                for(int i=0;i<arrFood.size();i++){
                    if(food.getIdMonAn() == arrFood.get(i).getIdMonAn()){
                        arrFood.remove(i);
                        SortListFood();
                        adap.notifyDataSetChanged();
                        tvSum.setText("Tổng số: " + arrFood.size());
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
        View v = inflater.inflate(R.layout.fragment_food_tinh, container, false);
        mData = FirebaseDatabase.getInstance().getReference();
        Reference(v);
        CreateListFood();
        ShowFood(mData);
        btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialogAdd();
            }
        });
        Button btnFind = (Button)v.findViewById(R.id.btnFind);
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialogFind();
            }
        });
        return v;
    }


    private boolean isFoodName(String NewFoodName){
        for(int i=0;i<arrFoodFull.size();i++){
            if(NewFoodName.equals(arrFoodFull.get(i).getTenMonAn()))
                return true;
        }
        return false;
    }
    private boolean isFoodId(int FoodId){
        for(int i=0;i<arrFoodFull.size();i++){
            if(FoodId == arrFoodFull.get(i).getIdMonAn())
                return true;
        }
        return false;
    }

    private void ShowPopupMenu(final MonAn food, View v){
        PopupMenu pop = new PopupMenu(getActivity(), v);
        pop.getMenu().add(0, 1, 0, "Sửa \"" + food.getTenMonAn()+ "\"");
        pop.getMenu().add(1,2,1, "Xóa \"" + food.getTenMonAn() + "\"");
        pop.show();
        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case 1:
                        ShowDialogUpdate(food);
                        break;
                    case 2:
                        ShowDialogDelete(food);
                        break;
                }
                return false;
            }
        });
    }
    private void FindData(){
        arrFood.clear();
        for(int n=0;n<arrFoodFull.size();n++){
            if(arrFoodFull.get(n).getIdLoaiMon() == FindToFoodGId){
                arrFood.add(arrFoodFull.get(n));
            }
        }
        SortListFood();
        tvSum.setText("Tổng số: " + arrFood.size());
        adap.notifyDataSetChanged();
    }
    private void ShowDialogFind(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_find_food_tinh);
        dialog.setCanceledOnTouchOutside(false);
        RadioButton rdbFind = (RadioButton)dialog.findViewById(R.id.rdbFindToFoodGroup);
        final RadioButton rdbFull = (RadioButton)dialog.findViewById(R.id.rdbFull);
        final Spinner sp = (Spinner)dialog.findViewById(R.id.spFoodG);
        Button btnFind = (Button)dialog.findViewById(R.id.btnFind);
        Button btnClose = (Button)dialog.findViewById(R.id.btnClose);
        sp.setAdapter(adapSpinnerFoodG);
        sp.setEnabled(false);
        rdbFull.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(rdbFull.isChecked()) {
                    sp.setEnabled(false);
                    FindFood = false;
                }
                else {
                    sp.setEnabled(true);
                    FindFood = true;
                }
            }
        });
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!rdbFull.isChecked()) {
                    FindToFoodGId = arrFoodG.get(sp.getSelectedItemPosition()).getIdLoaiMon();
                    FindData();
                    dialog.cancel();
                    return;
                }

                arrFood.clear();
                for(int n=0;n<arrFoodFull.size();n++){
                    arrFood.add(arrFoodFull.get(n));
                }
                SortListFood();
                tvSum.setText("Tổng số: " + arrFood.size());
                adap.notifyDataSetChanged();
                dialog.cancel();
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
    private void ShowDialogAdd(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_food_tinh);
        dialog.setCanceledOnTouchOutside(false);
        imgViewAddFood = (ImageView)dialog.findViewById(R.id.imgThumnail);
        ImageView imgCamera = (ImageView)dialog.findViewById(R.id.imgOpenCamera);
        ImageView imgFolder = (ImageView)dialog.findViewById(R.id.imgOpenFolder);
        final EditText edtFoodName = (EditText)dialog.findViewById(R.id.edtFoodName);
        final EditText edtFoodMoney = (EditText)dialog.findViewById(R.id.edtFoodMoney);
        final Spinner spFoodGName = (Spinner)dialog.findViewById(R.id.spFoodG);
        final CheckBox chkState = (CheckBox) dialog.findViewById(R.id.chkState);
        Button btnAdd = (Button)dialog.findViewById(R.id.btnAdd);
        Button btnClose = (Button)dialog.findViewById(R.id.btnCloseAdd);
        spFoodGName.setAdapter(adapSpinnerFoodG);
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
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String FoodName = edtFoodName.getText().toString();
                String FoodMoney = edtFoodMoney.getText().toString();
                if(FoodName.isEmpty()){
                    Toast.makeText(getActivity(),
                            "Bạn chưa nhập tên món ăn", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(FoodMoney.isEmpty()){
                    Toast.makeText(getActivity(),
                            "Bạn chưa nhập giá tiền", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(spFoodGName.getCount() == 0){
                    Toast.makeText(getActivity(),
                            "Bạn chưa chọn loại món nào", Toast.LENGTH_SHORT).show();
                    return;
                }
                int FoodGId = arrFoodG.get(spFoodGName.getSelectedItemPosition()).getIdLoaiMon();
                if(FoodMoney.length() > 7){
                    Toast.makeText(getActivity(),
                            "Giá tiền không được lớn hơn 9.999.999 VNĐ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(isFoodName(FoodName)){
                    Toast.makeText(getActivity(),
                            "Tên món ăn đã có", Toast.LENGTH_SHORT).show();
                    return;
                }
                Random r = new Random();
                int id = r.nextInt(100000);
                while(isFoodId(id)){
                    id = r.nextInt(100000);
                }
                MonAn F = new MonAn(id,
                        FoodName,
                        new Convert_Image_String().convertImageToString(imgViewAddFood),
                        Integer.parseInt(FoodMoney),
                        chkState.isChecked(),
                        FoodGId);
                mData.child(Food).child(String.valueOf(id)).setValue(F, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError == null){
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
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
    private void ShowDialogUpdate(final MonAn food){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_food_tinh);
        dialog.setCanceledOnTouchOutside(false);
        TextView tvTitle = (TextView)dialog.findViewById(R.id.tvTitle);
        imgViewUpdateFood = (ImageView)dialog.findViewById(R.id.imgThumnail);
        ImageView imgFolder = (ImageView)dialog.findViewById(R.id.imgOpenFolder);
        ImageView imgCamera = (ImageView)dialog.findViewById(R.id.imgOpenCamera);
        final EditText edtFoodName = (EditText)dialog.findViewById(R.id.edtFoodName);
        final EditText edtFoodMoney = (EditText)dialog.findViewById(R.id.edtFoodMoney);
        final Spinner spFoodG = (Spinner)dialog.findViewById(R.id.spFoodG);
        final CheckBox chkState = (CheckBox) dialog.findViewById(R.id.chkState);
        Button btnUpdate = (Button)dialog.findViewById(R.id.btnUpdate);
        Button btnClose = (Button)dialog.findViewById(R.id.btnCloseUpdate);
        tvTitle.append(" ("+food.getTenMonAn()+")");
        imgViewUpdateFood.setImageBitmap(new Convert_Image_String().convertStringToBitmap(food.getChuoiHinh()));
        imgFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChoosePicture(REQUEST_CODE_UPDATE_FOLDER);
            }
        });
        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TakePicture(REQUEST_CODE_UPDATE_CAMERA);
            }
        });
        edtFoodName.setText(food.getTenMonAn());
        edtFoodMoney.setText(String.valueOf(food.getGia()));
        spFoodG.setAdapter(adapSpinnerFoodG);
        for(int n =0;n<arrFoodG.size();n++){
            if(food.getIdLoaiMon() == arrFoodG.get(n).getIdLoaiMon()){
                spFoodG.setSelection(n);
                break;
            }
        }
        chkState.setChecked(food.getTinhTrang());
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String FoodName = edtFoodName.getText().toString();
                String FoodMoney = edtFoodMoney.getText().toString();
                if(FoodName.isEmpty()){
                    Toast.makeText(getActivity(),
                            "Bạn chưa nhập tên món ăn", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(FoodMoney.isEmpty()){
                    Toast.makeText(getActivity(),
                            "Bạn chưa giá tiền", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(FoodMoney.length() > 7){
                    Toast.makeText(getActivity(),
                            "Giá tiền không được lớn hơn 9.999.999 VNĐ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(spFoodG.getCount() == 0){
                    Toast.makeText(getActivity(),
                            "Bạn chưa chọn loại món", Toast.LENGTH_SHORT).show();
                    return;
                }
                String ImgString = new Convert_Image_String().convertImageToString(imgViewUpdateFood);
                int FoodGId = arrFoodG.get(spFoodG.getSelectedItemPosition()).getIdLoaiMon();
                boolean State = chkState.isChecked();
                if(FoodName.equals(food.getTenMonAn()) &&
                        Integer.parseInt(FoodMoney) == food.getGia() &&
                        FoodGId == food.getIdLoaiMon() &&
                        State == food.getTinhTrang()){
                    dialog.cancel();
                }
                else{
                    if(!FoodName.equals(food.getTenMonAn())){
                        if(isFoodName(FoodName)){
                            Toast.makeText(getActivity(),
                                    "Tên món ăn này đã có", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    int FoodId = food.getIdMonAn();
                    MonAn F = new MonAn(FoodId,
                            FoodName,
                            ImgString,
                            Integer.parseInt(FoodMoney),
                            State,
                            FoodGId);
                    mData.child(Food).child(String.valueOf(food.getIdMonAn())).setValue(F, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if(databaseError == null){
                                Toast.makeText(getActivity(),
                                        "Sửa Thành Công", Toast.LENGTH_SHORT).show();
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

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
    private void ShowDialogDelete(final MonAn food){
        AlertDialog.Builder Dialog = new AlertDialog.Builder(getActivity());
        Dialog.setTitle("Xóa Món ("+food.getTenMonAn()+")");
        Dialog.setMessage("Bạn có chắc muốn xóa Không?");
        Dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mData.child(Food).child(String.valueOf(food.getIdMonAn())).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError == null){
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
        Dialog.show();
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
    private void SortListFood(){
        int size = arrFood.size();
        for(int i=0;i<size - 1; i++){
            for(int j = i + 1; j < size; j++){
                if(arrFood.get(i).getTenMonAn().compareTo(arrFood.get(j).getTenMonAn()) > 0){
                    MonAn temp = arrFood.get(i);
                    arrFood.set(i, arrFood.get(j));
                    arrFood.set(j, temp);
                }
            }
        }
    }
    private void SortListFoodG(){
        int size = arrFoodG.size();
        for(int i=0;i<size - 1; i++){
            for(int j = i + 1; j < size; j++){
                if(arrFoodG.get(i).getTenLoaiMon().compareTo(arrFoodG.get(j).getTenLoaiMon()) > 0){
                    LoaiMon temp = arrFoodG.get(i);
                    arrFoodG.set(i, arrFoodG.get(j));
                    arrFoodG.set(j, temp);
                }
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CODE_ADD_CAMERA){
                Bitmap bm = (Bitmap)data.getExtras().get("data");
                imgViewAddFood.setImageBitmap(bm);
            }
            else if(requestCode == REQUEST_CODE_ADD_FOLDER){
                Uri uri = data.getData();
                imgViewAddFood.setImageURI(uri);
            }
            else if(requestCode == REQUEST_CODE_UPDATE_CAMERA){
                Bitmap bm = (Bitmap)data.getExtras().get("data");
                imgViewUpdateFood.setImageBitmap(bm);
            }
            else if(requestCode == REQUEST_CODE_UPDATE_FOLDER){
                Uri uri = data.getData();
                imgViewUpdateFood.setImageURI(uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
