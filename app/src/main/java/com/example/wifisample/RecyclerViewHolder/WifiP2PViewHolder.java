package com.example.wifisample.RecyclerViewHolder;

import android.net.wifi.p2p.WifiP2pDevice;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wifisample.R;

public class WifiP2PViewHolder extends RecyclerView.ViewHolder {

    private ViewHolder mViewHolder;

    public WifiP2PViewHolder(@NonNull View itemView) {
        super(itemView);
        this.mViewHolder = new ViewHolder();
        this.mViewHolder.mTextNomeWifiP2p = itemView.findViewById(R.id.text_name_wifip2p);
    }

    public void onBindData(final WifiP2pDevice device) {

        this.mViewHolder.mTextNomeWifiP2p.setText(device.deviceName);


        this.mViewHolder.mTextNomeWifiP2p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(itemView.getContext(), device.deviceName, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class ViewHolder {
        private TextView mTextNomeWifiP2p;
    }
}
