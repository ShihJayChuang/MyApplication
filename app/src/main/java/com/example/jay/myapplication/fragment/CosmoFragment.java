package com.example.jay.myapplication.fragment;


import com.example.jay.myapplication.Constants;
import com.google.firebase.database.DatabaseReference;

public class CosmoFragment extends ImageListFragment {


    public CosmoFragment() {

    }

//    @Override
//    public Query getTypeQuery(DatabaseReference databaseReference) {
//        return databaseReference.child(Constants.DATABASE_PATH_FOLDER).child(Constants.COSMO);
//    }

    @Override
    public DatabaseReference getTypeQuery(DatabaseReference databaseReference) {
        return databaseReference.child(Constants.DATABASE_PATH_FOLDER).child(Constants.COSMO);
    }


}
