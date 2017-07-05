package com.example.jay.myapplication.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jay.myapplication.Constants;
import com.example.jay.myapplication.DatabaseGetSet;
import com.example.jay.myapplication.R;
import com.example.jay.myapplication.recyclerView.MyAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public abstract class ImageListFragment extends Fragment {
    private static final String TAG = "ImageListFragment";

    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private DatabaseGetSet databaseGetSet;
    private ArrayList<DatabaseGetSet> databaseGetSets;
    private MyAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager mManager;
    private ProgressDialog progressDialog;

    public ImageListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_image_list, container, false);

        storageReference = FirebaseStorage.getInstance().getReference(Constants.STORAGE_PATH_FOLDER);
        databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.DATABASE_PATH_FOLDER);
        databaseGetSets = new ArrayList<>();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mManager);
        recyclerView.setAdapter(adapter);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.process_dialog_wait));
        progressDialog.show();
        showData();

    }

    public void showData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (databaseGetSets.size() != 0) {
                    databaseGetSets.clear();
                }
                progressDialog.dismiss();

                Iterable<DataSnapshot> dataSnapshotChildren1 = dataSnapshot.getChildren();
                for (DataSnapshot dataSnapshotChildren2 : dataSnapshotChildren1) {
                    String key1 = dataSnapshotChildren2.getKey();
                    //Log.e("jay", "key1:" + key1);

                    Iterable<DataSnapshot> dataSnapshotChildren3 = dataSnapshotChildren2.getChildren();
                    for (DataSnapshot dataSnapshotChildren4 : dataSnapshotChildren3) {
                        databaseGetSet = new DatabaseGetSet();
                        String key2 = dataSnapshotChildren4.getKey();
                        //Type
                        String typeQuery = getTypeQuery();
                        if (!key1.equals(typeQuery))
                            continue;
                        databaseGetSet.setType(dataSnapshotChildren2.getKey());
                        //TypeName
                        databaseGetSet.setTypeName(dataSnapshotChildren4.getKey());
                        //Log.e("jay", "key2:" + key2);

                        Iterable<DataSnapshot> dataSnapshotChildren5 = dataSnapshotChildren4.getChildren();
                        for (DataSnapshot dataSnapshotChildren6 : dataSnapshotChildren5) {
                            dataSnapshotChildren6.child(key2);
                            String key3 = dataSnapshotChildren6.getKey();
                            //Log.e("jay", key3 + ":" + value);

                            if (key3.equals(Constants.IMAGENAME)) {
                                //imageName
                                databaseGetSet.setImageName(dataSnapshotChildren6.getValue().toString());
                            }
                            if (key3.equals(Constants.PATHURL)) {
                                //pathUrl
                                databaseGetSet.setPathUrl(dataSnapshotChildren6.getValue().toString());
                            }
                            if (key3.equals(Constants.THUMBPATHURL)) {
                                //thumbPathUrl
                                databaseGetSet.setThumbPathUrl(dataSnapshotChildren6.getValue().toString());
                            }
                        }
                        databaseGetSets.add(databaseGetSet);
                    }
                }
                adapter = new MyAdapter(getActivity(), databaseGetSets);

                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView parent, View view, int position) {
                        Log.e("jay", databaseGetSets.get(position).getTypeName());
                    }
                });

                //recyclerView.setAdapter(adapter);

//                Log.e("jay", "0,getType():" + databaseGetSets.get(0).getType());
//                Log.e("jay", "0,getTypeName():" + databaseGetSets.get(0).getTypeName());
//                Log.e("jay", "0,getImageName():" + databaseGetSets.get(0).getImageName());
//                Log.e("jay", "0,getPathUrl():" + databaseGetSets.get(0).getPathUrl());
//                Log.e("jay", "0,getThumbPathUrl():" + databaseGetSets.get(0).getThumbPathUrl());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public abstract String getTypeQuery();
}
