package com.jolgorio.jolgorioapp.ui.videocall;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.jolgorio.jolgorioapp.R;
import com.jolgorio.jolgorioapp.repositories.LogedInUserRepository;
import com.jolgorio.jolgorioapp.tools.CallJavaScript;
import com.jolgorio.jolgorioapp.tools.VideoCallPeer;
import com.jolgorio.jolgorioapp.ui.main.MainActivity;

import java.nio.file.Path;

public class VideoCallFragment extends Fragment {
    private WebView webView;
    private boolean isAudio;
    private boolean isVideo;
    private CallJavaScript javaScriptInterface;
    private String connId;
    final int REQUEST_CAMERA = 1;
    final int REQUEST_AUDIO_MODIFY = 2;
    static final int REQUEST_RECORD_AUDIO = 3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        javaScriptInterface = new CallJavaScript();
        Bundle args = getArguments();
        connId = args.getString("connId");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_call, container, false);
        webView = view.findViewById(R.id.videocall_webview);
        setUpWebView(view);
        setUpButtons(view);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setUpWebView(View view){
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        webView.addJavascriptInterface(javaScriptInterface, "Android");
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onPermissionRequest(PermissionRequest request) {
                if(!(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)){
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                }
                if(!(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.MODIFY_AUDIO_SETTINGS) == PackageManager.PERMISSION_GRANTED)){
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.MODIFY_AUDIO_SETTINGS}, REQUEST_AUDIO_MODIFY);
                }
                if(!(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)){
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO},REQUEST_RECORD_AUDIO);
                }
                request.grant(request.getResources());
            }
        });
        loadVideoCall();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void loadVideoCall(){
        String filePath = "file:android_asset/call.html";
        webView.loadUrl(filePath);

        webView.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, String url){
                initializePeer();
            }
        });



    }

    private void initializePeer(){
        callJavaScriptFunction("javascript:init(\"" + VideoCallPeer.getInstance().getUserId() + "\")");
        if(connId != null){
            callJavaScriptFunction("javascript:startCall(\"" + connId + "\")");
        }
    }

    private void callJavaScriptFunction(String function){
        webView.loadUrl(function);
    }

    private void setUpButtons(View view){
        AppCompatButton toggleAudio = view.findViewById(R.id.videocall_audio);
        toggleAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAudio = !isAudio;
                callJavaScriptFunction("javascript:toggleAudio(\"" + isAudio + "\")");
                if(isAudio){
                    toggleAudio.setBackgroundResource(R.drawable.icon_mic_on);
                }else{
                    toggleAudio.setBackgroundResource(R.drawable.icon_mic_off);
                }
            }
        });

        AppCompatButton toggleVideo = view.findViewById(R.id.videocall_video);
        toggleVideo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                isVideo = !isVideo;
                callJavaScriptFunction("javascript:toggleVideo(\"" + isVideo + "\")");
                if(isVideo){
                    toggleVideo.setBackgroundResource(R.drawable.icon_camera_on);
                }else{
                    toggleVideo.setBackgroundResource(R.drawable.icon_camera_off);
                }
            }
        });
        AppCompatButton endCall = view.findViewById(R.id.videocall_end);
        endCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onDestroy() {
        VideoCallPeer.getInstance().getDatabaseReference().child(
                LogedInUserRepository.getInstance().getLogedInUser().getNumber()
        ).setValue(null);
        webView.loadUrl("about:blank");
        super.onDestroy();
    }
}
