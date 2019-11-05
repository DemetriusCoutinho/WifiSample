package com.example.wifisample.AsyncTask;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.wifisample.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class WifiP2PAsyncTask extends AsyncTask<Void, Void, String> {

    private Context mContext;
    private TextView mTextView;
    private int mRequestCode = 0;
    private MainActivity mMainActivity;

    public WifiP2PAsyncTask(Context c, View statusText, MainActivity main) {
        this.mContext = c;
        this.mTextView = (TextView) statusText;
        this.mMainActivity = main;
    }

    @Override
    protected String doInBackground(Void... voids) {

        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            serverSocket.getLocalSocketAddress();
            Socket client = new Socket();
            client.connect(serverSocket.getLocalSocketAddress());
            if (client.isConnected()) {
                Log.i("AsyncTask", client.getInetAddress().toString());
            }
//checar permissao
            
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                StorageManager storageManager = (StorageManager) this.mContext.getSystemService(Context.STORAGE_SERVICE);
                StorageVolume storageVolume = storageManager.getPrimaryStorageVolume();
                Intent intent = storageVolume.createAccessIntent(Environment.DIRECTORY_PICTURES);
                this.mMainActivity.startActivityForResult(intent, this.mRequestCode);
            }
            File f = new File(Environment.getExternalStorageDirectory(), "test" + ".jpg");
            if (f.exists()) {
                Log.i("AsynckTask", "Imagem Existe no Celular");
            } else {
                Log.i("AsynckTask", "Imagem não Existe no Celular -> " + f.getAbsolutePath());
            }
            File dir = new File(f.getParent());
            if (!f.exists())
                dir.mkdirs();
            f.createNewFile();
            InputStream inputStream = client.getInputStream();
            copyFile(inputStream, new FileOutputStream(f));
            serverSocket.close();
            return f.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("WifiAsyncTask", e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        if (s != null) {
            this.mTextView.setText("Filed copied - " + s);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse("file://" + s), "image/*");
            this.mContext.startActivity(intent);
        }
    }

    public static boolean copyFile(InputStream inputStream, OutputStream out) {
        byte[] buf = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            out.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
