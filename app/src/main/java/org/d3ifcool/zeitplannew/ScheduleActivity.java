package org.d3ifcool.zeitplannew;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.d3ifcool.zeitplannew.data.Jadwal;
import org.d3ifcool.zeitplannew.data.JadwalContract;

import java.util.ArrayList;

public class ScheduleActivity extends AppCompatActivity {

    String hari;
    TextView tvJudul;
    RecyclerView recyclerViewDetail;
    DetailAdapter mAdapter;
    ArrayList<Jadwal> listJadwal;


    private static final int ZEITPLAN_LOADER = 44;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        listJadwal = new ArrayList<>();
        hari = getIntent().getStringExtra("hari");
        tvJudul = findViewById(R.id.tv_judul_detail);
        tvJudul.setText("Jadwal Hari "+hari);

//        LoaderManager.getInstance(this).initLoader(ZEITPLAN_LOADER, null, this);
        String[] projection = {
                JadwalContract.JadwalEntry._ID,
                JadwalContract.JadwalEntry.COLUMN_WAKTU,
                JadwalContract.JadwalEntry.COLUMN_DOSEN,
                JadwalContract.JadwalEntry.COLUMN_MATAKULIAH,
                JadwalContract.JadwalEntry.COLUMN_RUANGAN
        };
        String selection = JadwalContract.JadwalEntry.COLUMN_HARI + "=?";
        String[] selectionArgs = {hari};
        Cursor cursor = getContentResolver().query(JadwalContract.JadwalEntry.CONTENT_URI,projection,selection,selectionArgs,null);

//        while (cursor.moveToNext()){
//            int kelasColumnIndex = cursor.getColumnIndex(JadwalContract.JadwalEntry.COLUMN_KELAS);
//            int dosenColumnIndex = cursor.getColumnIndex(JadwalContract.JadwalEntry.COLUMN_DOSEN);
//            int matakuliahColumnIndex = cursor.getColumnIndex(JadwalContract.JadwalEntry.COLUMN_MATAKULIAH);
//            int ruanganColumnIndex = cursor.getColumnIndex(JadwalContract.JadwalEntry.COLUMN_RUANGAN);
//
//            String kelas = cursor.getString(kelasColumnIndex);
//            String dosen = cursor.getString(dosenColumnIndex);
//            String matakuliah = cursor.getString(matakuliahColumnIndex);
//            String ruangan = cursor.getString(ruanganColumnIndex);
//
//            String hasil = kelas +", "+ dosen +", "+ matakuliah +", "+ ruangan +"\n";
//            Log.i("ISI CURSOR", hasil);
//            Log.i("HARI",hari);
//            tvJadwal.append(hasil);
//        }

        while (cursor.moveToNext()){
            int waktuColumnIndex = cursor.getColumnIndex(JadwalContract.JadwalEntry.COLUMN_WAKTU);
            int dosenColumnIndex = cursor.getColumnIndex(JadwalContract.JadwalEntry.COLUMN_DOSEN);
            int matakuliahColumnIndex = cursor.getColumnIndex(JadwalContract.JadwalEntry.COLUMN_MATAKULIAH);
            int ruanganColumnIndex = cursor.getColumnIndex(JadwalContract.JadwalEntry.COLUMN_RUANGAN);

            String waktu = cursor.getString(waktuColumnIndex);
            String dosen = cursor.getString(dosenColumnIndex);
            String matakuliah = cursor.getString(matakuliahColumnIndex);
            String ruangan = cursor.getString(ruanganColumnIndex);

            listJadwal.add(new Jadwal(matakuliah,ruangan,dosen,waktu));

            recyclerViewDetail = findViewById(R.id.rv_detail);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerViewDetail.setLayoutManager(layoutManager);
            mAdapter = new DetailAdapter(this,listJadwal);
            recyclerViewDetail.setAdapter(mAdapter);
            recyclerViewDetail.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
//            String hasil = kelas +", "+ dosen +", "+ matakuliah +", "+ ruangan +"\n";
//            Log.i("ISI CURSOR", hasil);
//            Log.i("HARI",hari);
        }
    }

//    @NonNull
//    @Override
//    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
//
//        String[] projection = {
//                JadwalContract.JadwalEntry._ID,
//                JadwalContract.JadwalEntry.COLUMN_MATAKULIAH,
//                JadwalContract.JadwalEntry.COLUMN_KELAS,
//                JadwalContract.JadwalEntry.COLUMN_DOSEN,
//                JadwalContract.JadwalEntry.COLUMN_RUANGAN,
//        };
//
//        switch (i){
//            case ZEITPLAN_LOADER:
//                Uri scheduleQueryUri = JadwalContract.JadwalEntry.CONTENT_URI;
//                String selection = JadwalContract.JadwalEntry.COLUMN_HARI + "=?";
//                String[] selectionArgs = {hari};
//
//                return new CursorLoader(this, scheduleQueryUri, projection, selection, selectionArgs, null);
//
//            default:
//                throw new RuntimeException("Loader Not Implemented: " + i);
//        }
//    }
//
//    @Override
//    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
//        mAdapter.swapCursor(cursor);
//    }
//
//    @Override
//    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
//        mAdapter.swapCursor(null);
//    }
}