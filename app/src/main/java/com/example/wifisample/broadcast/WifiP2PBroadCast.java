package com.example.wifisample.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.widget.Toast;

import com.example.wifisample.MainActivity;

public class WifiP2PBroadCast extends BroadcastReceiver {
    private WifiP2pManager mWifiP2pManager;
    private WifiP2pManager.Channel mChannel;
    private MainActivity mMainActivity;
    private WifiP2pManager.PeerListListener mPeerListListener;
    private WifiP2pManager.ConnectionInfoListener mConnectionInfoListener;

    public WifiP2PBroadCast(WifiP2pManager mWifiP2pManager, WifiP2pManager.Channel mChannel,
                            MainActivity mMainActivity,
                            WifiP2pManager.PeerListListener peerListListener, WifiP2pManager.ConnectionInfoListener connectionInfoListener) {
        this.mWifiP2pManager = mWifiP2pManager;
        this.mChannel = mChannel;
        this.mMainActivity = mMainActivity;
        this.mPeerListListener = peerListListener;
        this.mConnectionInfoListener = connectionInfoListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
//                Toast.makeText(context, "Wifi P2P Ativo ! ", Toast.LENGTH_SHORT).show();
                Log.i("BroadCastWifiP2p", "Wifi P2p Ativo !");
            } else {
                Toast.makeText(context, "Wifi P2P Desativado ! ", Toast.LENGTH_SHORT).show();
            }
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            if (this.mWifiP2pManager != null) {
                this.mWifiP2pManager.requestPeers(this.mChannel, this.mPeerListListener);
            }
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            if (this.mWifiP2pManager == null) {
                return;
            }
            NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
            if (networkInfo.isConnected()) {
                this.mWifiP2pManager.requestConnectionInfo(this.mChannel, this.mConnectionInfoListener);
            }

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {

        }
    }
}
