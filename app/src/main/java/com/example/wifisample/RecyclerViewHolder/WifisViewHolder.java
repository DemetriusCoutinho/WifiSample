package com.example.wifisample.RecyclerViewHolder;

import android.net.wifi.ScanResult;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wifisample.R;

public class WifisViewHolder extends RecyclerView.ViewHolder {

    private ViewHolder mViewHolder;

    public WifisViewHolder(@NonNull View itemView) {
        super(itemView);
        this.mViewHolder = new ViewHolder();
        this.mViewHolder.mTextNomeRede = itemView.findViewById(R.id.text_nome_rede);
    }

    public void onBindData(ScanResult result) {


        this.mViewHolder.mTextNomeRede.setText(result.SSID);


    }


    public static class ViewHolder {
        private TextView mTextNomeRede;
    }

}
