package com.example.jay.myapplication.fragment;

import com.example.jay.myapplication.Constants;
import com.google.firebase.database.DatabaseReference;


public class GlatimaFragment extends ImageListFragment {


    public GlatimaFragment() {

    }

    //    @Override
//    public Query getTypeQuery(DatabaseReference databaseReference) {
//        return databaseReference.child(Constants.DATABASE_PATH_FOLDER).child(Constants.COSMO);
//    }

    @Override
    public DatabaseReference getTypeQuery(DatabaseReference databaseReference) {
        return databaseReference.child(Constants.DATABASE_PATH_FOLDER).child(Constants.GLATIMA);
    }


}
