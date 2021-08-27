package tinh.khanh.phuc.order.tinh;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
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
import tinh.khanh.phuc.order.ClassDuLieu.BanAn;
import tinh.khanh.phuc.order.Public.Adapter_Table;

/**
 * Created by NGUYENTRITINH on 03/08/2017.
 */

public class TableFragment_Tinh extends Fragment {
    private final String Table = "BanAn";
    private DatabaseReference mData;
    private GridView gv;
    private TextView tvSum;
    private Button btnAddTable;
    private void Reference(View v){
        gv = (GridView)v.findViewById(R.id.gv);
        tvSum = (TextView)v.findViewById(R.id.tvSum);
        btnAddTable = (Button)v.findViewById(R.id.btnAdd);
    }
    private ArrayList<BanAn> arr;
    private Adapter_Table adap;
    private void CreateListTable(){
        arr = new ArrayList<>();
        adap = new Adapter_Table(
                getActivity(),
                R.layout.item_table_tinh,
                arr,
                R.id.tv
        );
        gv.setAdapter(adap);
        gv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShowPopupMenu(arr.get(i), view);
                return false;
            }
        });
    }
    private boolean isTableName(String TableNewName){
        for(int i =0 ; i< arr.size(); i++){
            if(TableNewName.equals(arr.get(i).getTenBan()))
                return true;
        }
        return false;
    }
    private boolean isTableId(int IdTable){
        for(int i = 0; i<arr.size();i++){
            if(arr.get(i).getIdBan() == IdTable)
                return true;
        }
        return false;
    }
    private void ShowTable(DatabaseReference data){
        data.child(Table).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arr.add(dataSnapshot.getValue(BanAn.class));
                SortListTable();
                adap.notifyDataSetChanged();
                tvSum.setText("Tổng số: " + arr.size());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                for(int i=0;i<arr.size();i++){
                    if(Integer.parseInt(dataSnapshot.getKey()) == arr.get(i).getIdBan()){
                        arr.set(i, dataSnapshot.getValue(BanAn.class));
                        SortListTable();
                        adap.notifyDataSetChanged();
                        tvSum.setText("Tổng số: " + arr.size());
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                for(int i=0;i<arr.size();i++){
                    if(Integer.parseInt(dataSnapshot.getKey()) == arr.get(i).getIdBan()){
                        arr.remove(i);
                        SortListTable();
                        adap.notifyDataSetChanged();
                        tvSum.setText("Tổng số: " + arr.size());
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

    private void SortListTable(){
        for(int i = 0; i< arr.size() - 1; i++) {
            for (int j = i + 1; j < arr.size(); j++) {
                if (arr.get(i).getTenBan().length() > arr.get(j).getTenBan().length()) {
                    BanAn temp = arr.get(i);
                    arr.set(i, arr.get(j));
                    arr.set(j, temp);

                } else if (arr.get(i).getTenBan().length() == arr.get(j).getTenBan().length()) {
                    if (arr.get(i).getTenBan().compareTo(arr.get(j).getTenBan()) > 0) {
                        BanAn temp = arr.get(i);
                        arr.set(i, arr.get(j));
                        arr.set(j, temp);
                    }
                }
            }
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_table_tinh, container, false);
        Reference(v);
        CreateListTable();
        mData = FirebaseDatabase.getInstance().getReference();
        ShowTable(mData);
        btnAddTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialogAdd();
            }
        });
        return v;
    }
    private void ShowDialogAdd(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_table_tinh);
        dialog.setCanceledOnTouchOutside(false);
        final EditText edt = (EditText)dialog.findViewById(R.id.edt);
        Button btnAdd = (Button)dialog.findViewById(R.id.btnAdd);
        Button btnClose = (Button)dialog.findViewById(R.id.btnClose);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = edt.getText().toString();
                if(s.isEmpty()){
                    Toast.makeText(getActivity(), "Bạn chưa nhập tên bàn",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    if(isTableName(s)){
                        Toast.makeText(getActivity(),
                                "Tên bàn đã có", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Random r = new Random();
                        int id = r.nextInt(10000);
                        while(isTableId(id)){
                            id = r.nextInt();
                        }
                        mData.child(Table).child(String.valueOf(id)).setValue(new BanAn(id, s), new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if(databaseError == null) {
                                    Toast.makeText(getActivity(), "Thêm Thành Công",
                                            Toast.LENGTH_SHORT).show();
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
    private void ShowDialogUpdate(final BanAn ban){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_table_tinh);
        dialog.setCanceledOnTouchOutside(false);
        TextView tv = (TextView)dialog.findViewById(R.id.tvTitle);
        tv.append(" ("+ban.getTenBan()+")");
        final EditText edt = (EditText)dialog.findViewById(R.id.edt);
        edt.setText(ban.getTenBan());
        Button btnUpadete = (Button)dialog.findViewById(R.id.btnUpdate);
        Button btnClose = (Button)dialog.findViewById(R.id.btnClose);
        btnUpadete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = edt.getText().toString();
                if(s.isEmpty()){
                    Toast.makeText(getActivity(),
                            "Bạn chưa nhập tên bàn", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(!s.equals(ban.getTenBan())){
                        if(isTableName(s)){
                            Toast.makeText(getActivity(),
                                    "Tên bàn đã có", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            mData.child(Table).child(String.valueOf(ban.getIdBan())).setValue(new BanAn(ban.getIdBan(), s), new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if(databaseError == null) {
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
                    else{
                        dialog.cancel();
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
    void ShowDialogDelete(final BanAn ban){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Xóa Bàn ("+ban.getTenBan()+")");
        dialog.setMessage("Bạn có chắc muốn xóa không?");
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mData.child(Table).child(String.valueOf(ban.getIdBan())).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError == null) {
                            Toast.makeText(getActivity(),
                                    "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getActivity(),
                                    "Lỗi! Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        dialog.show();
    }
    private void ShowPopupMenu(final BanAn ban, View v){
        PopupMenu pop = new PopupMenu(getActivity(), v);
        pop.getMenu().add(0, 1, 0, "Sửa " + ban.getTenBan());
        pop.getMenu().add(0,2,1,"Xóa " + ban.getTenBan());
        pop.show();
        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case 1:
                        ShowDialogUpdate(ban);
                        break;
                    case 2:
                        ShowDialogDelete(ban);
                        break;
                }
                return false;
            }
        });
    }
}
