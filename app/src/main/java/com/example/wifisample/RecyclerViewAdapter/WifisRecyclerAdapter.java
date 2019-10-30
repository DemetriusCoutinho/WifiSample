package com.example.wifisample.RecyclerViewAdapter;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wifisample.R;
import com.example.wifisample.RecyclerViewHolder.WifisViewHolder;

import java.util.List;

public class WifisRecyclerAdapter extends RecyclerView.Adapter<WifisViewHolder> {
    private List<ScanResult> mResults;
    private Context mContext;
    private WifisViewHolder mWifisViewHolder;

    public WifisRecyclerAdapter(List<ScanResult> result, Context context) {
        this.mResults = result;
        this.mContext = context;
    }

    @NonNull
    @Override
    public WifisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(this.mContext).inflate(R.layout.itens_wifis_recycler, parent, false);
        this.mWifisViewHolder = new WifisViewHolder(v);

        return this.mWifisViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WifisViewHolder holder, int position) {

        this.mWifisViewHolder.onBindData(this.mResults.get(position));
    }

    @Override
    public int getItemCount() {
        return this.mResults.size() - 1;
    }
}
