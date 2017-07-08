package com.example.jay.myapplication.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jay.myapplication.Activity.SingleImageActivity;
import com.example.jay.myapplication.Constants;
import com.example.jay.myapplication.R;
import com.example.jay.myapplication.models.DatabaseGetSet;
import com.example.jay.myapplication.recyclerView.MyAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public abstract class ImageListFragment extends Fragment {
    private static final String TAG = "ImageListFragment";

    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private DatabaseGetSet databaseGetSet;
    private ArrayList<DatabaseGetSet> databaseGetSets;
    private MyAdapter adapter;
    //private FirebaseRecyclerAdapter<DatabaseGetSet, MyViewHolder> mAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager mManager;
    private ProgressDialog progressDialog;

    public ImageListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_image_list, container, false);

        //storageReference = FirebaseStorage.getInstance().getReference(Constants.STORAGE_PATH_FOLDER);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseGetSets = new ArrayList<>();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mManager);

        showProgressDialog();
        showData(savedInstanceState);
        //Query typeQuery = getTypeQuery(databaseReference);
//        typeQuery.limitToLast(200).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                //Log.e("jay", "dataSnapshot.getChildrenCount():" + dataSnapshot.getChildrenCount());
//                hideProgressDialog();
//                for (DataSnapshot mSnapshot : dataSnapshot.getChildren()) {
//                    //DatabaseGetSet databaseGetSet = mSnapshot.getValue(DatabaseGetSet.class);
//                    //Log.e("jay", "mSnapshot.getKey():" + mSnapshot.getKey());
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                hideProgressDialog();
//            }
//        });
//        mAdapter = new FirebaseRecyclerAdapter<DatabaseGetSet, MyViewHolder>(DatabaseGetSet.class, R.layout.layout_images, MyViewHolder.class, typeQuery) {
//            @Override
//            protected void populateViewHolder(MyViewHolder viewHolder, final DatabaseGetSet model, int position) {
//                final DatabaseReference postRef = getRef(position);
////                viewHolder.setTextViewName(model.getTypeName());
////                viewHolder.setImageView(model.getThumbPathUrl(), getContext());
//                viewHolder.bindToShow(getActivity(), model);
//                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(getActivity(), SingleImageActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putString(Constants.TYPENAME, model.getTypeName());
//                        bundle.putString(Constants.IMAGENAME, model.getImageName());
//                        bundle.putString(Constants.PATHURL, model.getPathUrl());
//                        bundle.putString(Constants.THUMBPATHURL, model.thumbPathUrl);
//                        intent.putExtras(bundle);
//                        startActivity(intent);
//                    }
//                });
//                viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(View v) {
//                        Log.e("jay", "model.getImageName()" + model.getImageName());
//                        return true;
//                    }
//                });
//            }
//        };
//        recyclerView.setAdapter(mAdapter);
    }

    public void showData(final Bundle savedInstanceState) {
        DatabaseReference typeQuery = getTypeQuery(databaseReference);

        typeQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hideProgressDialog();
                if (databaseGetSets.size() != 0) {
                    databaseGetSets.clear();
                }
                progressDialog.dismiss();

                Iterable<DataSnapshot> dataSnapshotChildren1 = dataSnapshot.getChildren();
                for (DataSnapshot dataSnapshotChildren2 : dataSnapshotChildren1) {
                    String key1 = dataSnapshotChildren2.getKey();
                    //Log.e("jay", "key1 Type:" + key1);
                    databaseGetSet = new DatabaseGetSet();
                    databaseGetSet.setTypeName(dataSnapshotChildren2.getKey());
                    Iterable<DataSnapshot> dataSnapshotChildren3 = dataSnapshotChildren2.getChildren();
                    for (DataSnapshot dataSnapshotChildren4 : dataSnapshotChildren3) {

                        String key2 = dataSnapshotChildren4.getKey();
                        //Log.e("jay", "key2 Hash key:" + key2);

                        Iterable<DataSnapshot> dataSnapshotChildren5 = dataSnapshotChildren4.getChildren();
                        for (DataSnapshot dataSnapshotChildren6 : dataSnapshotChildren5) {
                            dataSnapshotChildren6.child(key2);
                            String key3 = dataSnapshotChildren6.getKey();
                            //Log.e("jay", "key3:" + key3);
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
                Log.e("jay", "databaseGetSets.size():" + databaseGetSets.size() + "");
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView parent, View view, int position) {
                        Log.e("jay", databaseGetSets.get(position).getTypeName());
                        Intent intent = new Intent(getActivity(), SingleImageActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.TYPENAME, databaseGetSets.get(position).getTypeName());
                        bundle.putString(Constants.IMAGENAME, databaseGetSets.get(position).getImageName());
                        bundle.putString(Constants.PATHURL, databaseGetSets.get(position).getPathUrl());
                        bundle.putString(Constants.THUMBPATHURL, databaseGetSets.get(position).getThumbPathUrl());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

                adapter.setOnItemLongClickListener(new MyAdapter.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(RecyclerView parent, View view, int position) {
                        Log.e("jay", "LongClick " + databaseGetSets.get(position).getTypeName());
                        return true;
                    }
                });

//                Log.e("jay", "0,getType():" + databaseGetSets.get(0).getType());
//                Log.e("jay", "0,getTypeName():" + databaseGetSets.get(0).getTypeName());
//                Log.e("jay", "0,getImageName():" + databaseGetSets.get(0).getImageName());
//                Log.e("jay", "0,getPathUrl():" + databaseGetSets.get(0).getPathUrl());
//                Log.e("jay", "0,getThumbPathUrl():" + databaseGetSets.get(0).getThumbPathUrl());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideProgressDialog();
            }
        });

    }


    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(getString(R.string.process_dialog_wait));
            progressDialog.show();
        }
    }

    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    //public abstract Query getTypeQuery(DatabaseReference databaseReference);
    public abstract DatabaseReference getTypeQuery(DatabaseReference databaseReference);

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (mAdapter != null) {
//            mAdapter.cleanup();
//        }

    }
}
