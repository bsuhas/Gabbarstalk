package com.gabbarstalk.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;
import android.widget.VideoView;

import com.gabbarstalk.R;

import java.util.List;

public class PlayVideoActivity extends Activity {
    private String video_url = "http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4";
    private ProgressDialog pd;
    private VideoView videoView;
    private int stopPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_video_layout);
        Uri uri =  Uri.parse( video_url );
        openVideoPlayer(this,uri);
       /* videoView = (VideoView) findViewById(R.id.video_view);
        pd = new ProgressDialog(PlayVideoActivity.this);
        pd.setMessage("Buffering video please wait...");
        pd.show();

        Uri uri = Uri.parse(video_url);
        videoView.setVideoURI(uri);
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                pd.dismiss();
            }
        });*/
    }

   /* @Override
    public void onPause() {
        super.onPause();
        stopPosition = videoView.getCurrentPosition(); //stopPosition is an int
        videoView.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        videoView.seekTo(stopPosition);
        videoView.start(); //Or use resume() if it doesn't work. I'm not sure
    }*/

    public void openVideoPlayer(Activity activity, Uri fileUri) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(fileUri, "video/*");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (intent.resolveActivity(activity.getPackageManager()) != null) {
                List<ResolveInfo> resInfos = activity.getPackageManager().queryIntentActivities(intent, 0);
                String packageName = null;
                if (resInfos != null && resInfos.size() > 0) {
                    packageName = resInfos.get(0).activityInfo.packageName;
                }
                if (packageName != null) {
                    intent.setPackage(packageName);
                    activity.startActivity(intent);
                }
            } else {
//                Toast.makeText(activity, activity.getString(R.string.no_application_found_to_play_video), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
//            Toast.makeText(activity, activity.getString(R.string.no_application_found_to_play_video), Toast.LENGTH_SHORT).show();
        }
    }
}
