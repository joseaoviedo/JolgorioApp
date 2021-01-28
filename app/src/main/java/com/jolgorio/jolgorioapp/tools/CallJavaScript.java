package com.jolgorio.jolgorioapp.tools;

import android.util.Log;
import android.webkit.JavascriptInterface;

public class CallJavaScript {
    @JavascriptInterface
    public void onPeerConnected(){

    }

    @JavascriptInterface
    public void informPeerConnection(){
        Log.d("PEER", "PEER CONECTADO EXITOSAMENTE");
    }
}
