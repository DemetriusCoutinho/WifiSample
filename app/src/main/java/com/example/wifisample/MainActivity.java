package com.example.wifisample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wifisample.RecyclerViewAdapter.WifiP2PrecyclerAdapter;
import com.example.wifisample.RecyclerViewAdapter.WifisRecyclerAdapter;
import com.example.wifisample.broadcast.WifiP2PBroadCast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private WifiManager mWifiManager;
    private ViewHolder mViewHolder;
    private WifisRecyclerAdapter mWifisRecyclerAdapter;
    private List<ScanResult> mListScanResults;

    // WifiP2p
    private WifiP2pManager mWifiP2pManager;
    private WifiP2pManager.Channel mChannel;
    private BroadcastReceiver mBroadcastReceiver;
    private IntentFilter mIntentFilter;
    private WifiP2pManager.PeerListListener mPeerListListener;
    private WifiP2pConfig mConfig;
    private WifiP2PrecyclerAdapter mWifiP2PrecyclerAdapter;
    private List<WifiP2pDevice> mListDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mContext = this;
        this.mViewHolder = new ViewHolder();
//        this.mViewHolder = new ViewHolder();
//        mWifiManager = (WifiManager) this.mContext.getSystemService(Context.WIFI_SERVICE);
//        this.mViewHolder.mRecyclerView = this.findViewById(R.id.recycler_wifis);
//        this.mViewHolder.mRecyclerView.setHasFixedSize(true);
//        this.mViewHolder.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        BroadcastReceiver wifiScanReceptor = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                boolean sucesso = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);
//                if (sucesso) {
//                    scanSucesso();
//                } else {
//                    scanFalha();
//                }
//            }
//        };
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
//        this.mContext.registerReceiver(wifiScanReceptor, intentFilter);
//        boolean sucesso = mWifiManager.startScan();
//        if (!sucesso) {
//            this.scanFalha();
//        }

        this.mViewHolder.mRecyclerView = findViewById(R.id.recycler_wifis);
        this.mViewHolder.mBtnAtualizarWifi = findViewById(R.id.btn_atualizar_wifis);
        this.mPeerListListener = new WifiP2pManager.PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {
                mConfig = new WifiP2pConfig();
                mListDevice = new ArrayList<>();
                mListDevice.addAll(wifiP2pDeviceList.getDeviceList());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setAdapter();
                    }
                });
//                WifiP2pDevice device = mListDevice.get(0);
//                mConfig.deviceAddress = device.deviceAddress;
//                mWifiP2pManager.connect(mChannel, mConfig, new WifiP2pManager.ActionListener() {
//                    @Override
//                    public void onSuccess() {
//
//                    }
//
//                    @Override
//                    public void onFailure(int i) {
//
//                    }
//                });
            }
        };
        this.mIntentFilter = new IntentFilter();
        this.mWifiP2pManager = (WifiP2pManager) this.mContext.getSystemService(Context.WIFI_P2P_SERVICE);
        this.mChannel = this.mWifiP2pManager.initialize(this.mContext, getMainLooper(), null);
        this.mBroadcastReceiver = new WifiP2PBroadCast(this.mWifiP2pManager, this.mChannel, this, this.mPeerListListener);
        this.mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        this.mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        this.mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        this.mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);

        this.mWifiP2pManager.discoverPeers(this.mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(mContext, "Sucesso ! ", Toast.LENGTH_SHORT).show();
//                setAdapter();
            }

            @Override
            public void onFailure(int i) {

            }
        });

        this.mViewHolder.mBtnAtualizarWifi.setOnClickListener(this);
        this.mViewHolder.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
    }

    public void setAdapter() {
        this.mWifiP2PrecyclerAdapter = new WifiP2PrecyclerAdapter(this.mContext, this.mListDevice);
        this.mViewHolder.mRecyclerView.setAdapter(this.mWifiP2PrecyclerAdapter);
        this.mWifiP2PrecyclerAdapter.notifyDataSetChanged();
    }

    private void scanSucesso() {
        this.mListScanResults = this.mWifiManager.getScanResults();
        this.mWifisRecyclerAdapter = new WifisRecyclerAdapter(this.mListScanResults, this.mContext);
        this.mViewHolder.mRecyclerView.setAdapter(this.mWifisRecyclerAdapter);
        this.mViewHolder.mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(this.mBroadcastReceiver, this.mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(this.mBroadcastReceiver);
    }

    private void scanFalha() {
        this.mListScanResults = this.mWifiManager.getScanResults();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btn_atualizar_wifis) {
            this.mWifiP2pManager.discoverPeers(this.mChannel, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(mContext, "Sucesso ! ", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int i) {

                }
            });
        }
    }

    public static class ViewHolder {
        private RecyclerView mRecyclerView;
        private Button mBtnAtualizarWifi;
    }

}
