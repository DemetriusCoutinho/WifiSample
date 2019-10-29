package com.example.wifisample;

import android.net.wifi.ScanResult;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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
