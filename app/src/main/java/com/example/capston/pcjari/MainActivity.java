package com.example.capston.pcjari;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.capston.pcjari.fragment.FavoriteFragment;
import com.example.capston.pcjari.fragment.InformationFragment;
import com.example.capston.pcjari.fragment.SearchByAddressFragment;
import com.example.capston.pcjari.fragment.SearchByMeFragment;
import com.example.capston.pcjari.sqlite.DataBaseHelper;
import com.example.capston.pcjari.sqlite.DataBaseTables;

/**
 * Created by KangSeungho on 2017-09-25.
 */

public class MainActivity extends AppCompatActivity {
    public static int position;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_search_by_address:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, new SearchByAddressFragment()).commit();
                    return true;
                case R.id.navigation_search_by_me:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, new SearchByAddressFragment()).commit();
                    return true;
                case R.id.navigation_favorite:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, new SearchByAddressFragment()).commit();
                    return true;
                case R.id.navigation_information:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, new InformationFragment()).commit();
                    return true;
            }
            /*
            switch (item.getItemId()) {
                case R.id.navigation_search_by_address:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, new SearchByAddressFragment()).commit();
                    return true;
                case R.id.navigation_search_by_me:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, new SearchByMeFragment()).commit();
                    return true;
                case R.id.navigation_favorite:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, new FavoriteFragment()).commit();
                    return true;
                case R.id.navigation_information:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, new InformationFragment()).commit();
                    return true;
            }
            */
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.content, new SearchByAddressFragment()).commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        first_fragment(navigation);

        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  }, 0 );
        }
    }

    private void first_fragment(BottomNavigationView navigation) {
        DataBaseHelper DBHelper = new DataBaseHelper(getApplicationContext());
        SQLiteDatabase db = DBHelper.getWritableDatabase();

        try{
            String sql = "SELECT * FROM " + DataBaseTables.CreateDB_setting._TABLENAME;
            Cursor results = db.rawQuery(sql, null);
            results.moveToFirst();
            position = results.getInt(1);

            MenuItem prev = navigation.getMenu().getItem(position);
            prev.setChecked(true);

            results.close();
        } catch (Exception e) {

        }
    }
}
