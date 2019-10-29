package com.example.wifisample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private WifiManager mWifiManager;
    private ViewHolder mViewHolder;
    private WifisRecyclerAdapter mWifisRecyclerAdapter;
    private List<ScanResult> mListScanResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mContext = this;
        this.mViewHolder = new ViewHolder();
        mWifiManager = (WifiManager) this.mContext.getSystemService(Context.WIFI_SERVICE);

        this.mViewHolder.mRecyclerView = this.findViewById(R.id.recycler_wifis);

        this.mViewHolder.mRecyclerView.setHasFixedSize(true);

        this.mViewHolder.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        BroadcastReceiver wifiScanReceptor = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean sucesso = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);

                if (sucesso) {
                    scanSucesso();
                } else {
                    scanFalha();
                }

            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        this.mContext.registerReceiver(wifiScanReceptor, intentFilter);

        boolean sucesso = mWifiManager.startScan();

        if (!sucesso) {
            this.scanFalha();
        }

    }

    private void scanSucesso() {
        this.mListScanResults = this.mWifiManager.getScanResults();
        this.mWifisRecyclerAdapter = new WifisRecyclerAdapter(this.mListScanResults, this.mContext);
        this.mViewHolder.mRecyclerView.setAdapter(this.mWifisRecyclerAdapter);
        this.mViewHolder.mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    private void scanFalha() {
        this.mListScanResults = this.mWifiManager.getScanResults();
    }

    public static class ViewHolder {
        private RecyclerView mRecyclerView;
    }

}
