package com.jolgorio.jolgorioapp.ui.videocall;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jolgorio.jolgorioapp.R;
import com.jolgorio.jolgorioapp.data.model.JolgorioUser;
import com.jolgorio.jolgorioapp.repositories.LogedInUserRepository;
import com.jolgorio.jolgorioapp.tools.CallJavaScript;
import com.jolgorio.jolgorioapp.ui.main.MainActivity;

import java.nio.file.Path;

public class VideoCallFragment extends Fragment {
    static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("user");
    private WebView webView;
    private boolean isAudio;
    private boolean isVideo;
    private CallJavaScript javaScriptInterface;
    private String connId;
    private String userCalledId;
    final int REQUEST_CAMERA = 1;
    final int REQUEST_AUDIO_MODIFY = 2;
    static final int REQUEST_RECORD_AUDIO = 3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        javaScriptInterface = new CallJavaScript();
        Bundle args = getArguments();
        userCalledId = args.getString("userCalledId");
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
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onPermissionRequest(final PermissionRequest request) {

                Log.d("VIDEOCALL", "onPermissionRequest");
                request.grant(request.getResources());
            }

        });

        webView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 4.4; Nexus 5 Build/_BuildID_) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/30.0.0.0 Mobile Safari/537.36");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode( WebSettings.MIXED_CONTENT_ALWAYS_ALLOW );
        }


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
        callJavaScriptFunction("javascript:init(\"" + LogedInUserRepository.getInstance().getUserUniqueId() + "\")");
        if(connId != null){
            Log.d("VIDEOCALL", "ESPERANDO RESPUESTA DE: " + userCalledId);
            mDatabase.child(userCalledId).child("isPeerConnected").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.getValue() != null){
                        if(snapshot.getValue().toString().equals("true")){
                            Log.d("VIDEOCALL", "LLAMADA ACEPTADA");
                            callJavaScriptFunction("javascript:startCall(\"" + connId + "\")");
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else{
            JolgorioUser user = LogedInUserRepository.getInstance().getLogedInUser();

            mDatabase.child(user.getNumber()).child("isPeerConnected").setValue(true);
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
        if(userCalledId != null){
            mDatabase.child(userCalledId).setValue(null);
        }
        webView.loadUrl("about:blank");
        super.onDestroy();
    }
}
