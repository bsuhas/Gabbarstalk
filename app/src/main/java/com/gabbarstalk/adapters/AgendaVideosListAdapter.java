package com.gabbarstalk.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gabbarstalk.R;
import com.gabbarstalk.models.VideoDetailsModel;
import com.gabbarstalk.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by SUHAS
 */
public class AgendaVideosListAdapter extends RecyclerView.Adapter<AgendaVideosListAdapter.ViewHolder> {

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

    public AgendaVideosListAdapter(Activity activity, Context context, List<VideoDetailsModel> videoDetailsModelList) {
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
                inflate(R.layout.agenda_video_list_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final VideoDetailsModel model = videoDetailsModelList.get(position);

        holder.tvUsername.setText(model.getUserName());
        Picasso.with(mContext).load(model.getVideoThumbnail()).placeholder(R.color.md_black_1000).into(holder.imgVideoThumb);

        holder.imgVideoPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.getInstance().openVideoPlayer(mActivity, model.getVideoUrl());
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoDetailsModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llListItem;
        private LinearLayout llProfileData;
        private ImageView profileImg;
        private TextView tvUsername;
        private ImageView imgVideoThumb;
        private ImageView imgVideoPlay;
        private ImageView ivLike;
        private ImageView ivShare;

        ViewHolder(View view) {
            super(view);
            llListItem = (LinearLayout) view.findViewById(R.id.ll_list_item);
            llProfileData = (LinearLayout) view.findViewById(R.id.ll_profile_data);
            profileImg = (ImageView) view.findViewById(R.id.profile_img);
            tvUsername = (TextView) view.findViewById(R.id.tv_username);
            imgVideoThumb = (ImageView) view.findViewById(R.id.img_video_thumb);
            imgVideoPlay = (ImageView) view.findViewById(R.id.img_video_play);
            ivLike = (ImageView) view.findViewById(R.id.iv_like);
            ivShare = (ImageView) view.findViewById(R.id.iv_share);
        }
    }
}
