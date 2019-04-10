package com.gabbarstalk.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gabbarstalk.R;
import com.gabbarstalk.models.VideoDetailsModel;
import com.gabbarstalk.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by SUHAS
 */
public class MyVideosListAdapter extends RecyclerView.Adapter<MyVideosListAdapter.ViewHolder> {

    private static int position = -1;
    private List<VideoDetailsModel> videoDetailsModelList;
    private final Context mContext;
    private Activity mActivity;

    public static int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public MyVideosListAdapter(Activity activity, Context context, List<VideoDetailsModel> videoDetailsModelList) {
        this.videoDetailsModelList = videoDetailsModelList;
        this.mContext = context;
        this.mActivity = activity;
    }

    public void refreshAdapter(List<VideoDetailsModel> videoDetailsModelList) {
        this.videoDetailsModelList = videoDetailsModelList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.my_videos_list_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final VideoDetailsModel model = videoDetailsModelList.get(position);

        holder.tvAgendaTitle.setText(model.getAgendaTitle());
        holder.tvVideoCaption.setText(model.getCaption());
        holder.tvLikeCount.setText("" + model.getLikeCount());
        if (!TextUtils.isEmpty(model.getVideoThumbnail()))
            Picasso.with(mContext).load(model.getVideoThumbnail()).placeholder(R.color.md_black_1000).into(holder.imgVideoThumb);

        holder.imgVideoPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.getInstance().openVideoPlayer(mActivity, model.getVideoUrl());
//                Utils.getInstance().showToast(mContext, "Coming Soon");
            }
        });

        holder.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.getInstance().shareUrl(view.getContext(), model.getShareUrl());
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoDetailsModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvAgendaTitle;
        private TextView tvLikeCount;

        private TextView tvVideoCaption;
        private ImageView imgVideoThumb;
        private ImageView imgVideoPlay;
        private ImageView ivShare;

        ViewHolder(View view) {
            super(view);
            tvLikeCount = (TextView) view.findViewById(R.id.tv_like);
            tvVideoCaption = (TextView) view.findViewById(R.id.tv_video_caption);
            tvAgendaTitle = (TextView) view.findViewById(R.id.tv_agenda_title);
            imgVideoThumb = (ImageView) view.findViewById(R.id.img_video_thumb);
            imgVideoPlay = (ImageView) view.findViewById(R.id.img_video_play);
            ivShare = (ImageView) view.findViewById(R.id.iv_share);
        }
    }
}
