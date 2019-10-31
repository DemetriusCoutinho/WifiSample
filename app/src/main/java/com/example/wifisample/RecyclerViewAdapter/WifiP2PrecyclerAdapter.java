package com.example.wifisample.RecyclerViewAdapter;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wifisample.OnConnectionWifiP2P;
import com.example.wifisample.R;
import com.example.wifisample.RecyclerViewHolder.WifiP2PViewHolder;

import java.util.List;

public class WifiP2PrecyclerAdapter extends RecyclerView.Adapter<WifiP2PViewHolder> {
    private Context mContext;
    private List<WifiP2pDevice> mResult;
    private WifiP2PViewHolder mViewHolder;
    private OnConnectionWifiP2P mOnConnectionWifiP2P;

    public WifiP2PrecyclerAdapter(Context context, List<WifiP2pDevice> result, OnConnectionWifiP2P listener) {
        this.mContext = context;
        this.mResult = result;
        this.mOnConnectionWifiP2P = listener;
    }

    @NonNull
    @Override
    public WifiP2PViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(this.mContext).inflate(R.layout.itens_wifip2p_recycler, parent, false);
        this.mViewHolder = new WifiP2PViewHolder(v);

        return this.mViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull WifiP2PViewHolder holder, int position) {
        this.mViewHolder.onBindData(this.mResult.get(position), this.mOnConnectionWifiP2P);
    }

    @Override
    public int getItemCount() {
        return this.mResult.size();
    }
}
