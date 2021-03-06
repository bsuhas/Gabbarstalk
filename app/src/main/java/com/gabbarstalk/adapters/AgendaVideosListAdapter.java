package com.gabbarstalk.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gabbarstalk.R;
import com.gabbarstalk.interfaces.RESTClientResponse;
import com.gabbarstalk.models.EmptyResponse;
import com.gabbarstalk.models.LikeData;
import com.gabbarstalk.models.UserData;
import com.gabbarstalk.models.VideoDetailsModel;
import com.gabbarstalk.utils.UserPreferences;
import com.gabbarstalk.utils.Utils;
import com.gabbarstalk.webservices.LikeService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SUHAS
 */
public class AgendaVideosListAdapter extends RecyclerView.Adapter<AgendaVideosListAdapter.ViewHolder> {

    private static int position = -1;
    private List<VideoDetailsModel> videoDetailsModelList;
    private final Context mContext;
    private Activity mActivity;
    private  String agendaTitle;

    public static int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public AgendaVideosListAdapter(Activity activity, Context context, List<VideoDetailsModel> videoDetailsModelList, String agendaTitle) {
        this.videoDetailsModelList = videoDetailsModelList;
        this.mContext = context;
        this.mActivity = activity;
        this.agendaTitle = agendaTitle;
    }

    public void refreshAdapter(List<VideoDetailsModel> videoDetailsModelList) {
        this.videoDetailsModelList = new ArrayList<>(videoDetailsModelList);
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

//        holder.tvAgendaName.setText(model.getAgendaTitle());
        holder.tvUsername.setText(String.format("@%s", model.getUserName()));
        holder.tvVideoCaption.setText(model.getCaption());
        holder.tvLikeCount.setText(model.getLikeCount() + " likes");
        if (!TextUtils.isEmpty(model.getVideoThumbnail()))
            Picasso.with(mContext).load(model.getVideoThumbnail()).placeholder(R.color.md_black_1000).into(holder.imgVideoThumb);

        if (!TextUtils.isEmpty(model.getProfileImage()))
            Picasso.with(mContext).load(model.getProfileImage()).placeholder(R.color.md_black_1000).into(holder.profileImg);

        holder.imgVideoPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.getInstance().openVideoPlayer(mActivity, model.getVideoUrl());
            }
        });

        holder.ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserData userData = UserPreferences.getInstance(mContext).getUserNameInfo();
                LikeData likeData = new LikeData();
                likeData.setUserId(userData.getUserId());
                likeData.setVideoId(model.getVideoId());
                likeVideo(likeData,position);
            }
        });

        holder.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.getInstance().shareUrl(view.getContext(),model.getShareUrl());
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
        private TextView tvVideoCaption;
        private TextView tvLikeCount;
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
            tvLikeCount = (TextView) view.findViewById(R.id.tv_like_count);
            tvVideoCaption = (TextView) view.findViewById(R.id.tv_video_caption);
            imgVideoThumb = (ImageView) view.findViewById(R.id.img_video_thumb);
            imgVideoPlay = (ImageView) view.findViewById(R.id.img_video_play);
            ivLike = (ImageView) view.findViewById(R.id.iv_like);
            ivShare = (ImageView) view.findViewById(R.id.iv_share);
        }
    }

    private void likeVideo(final LikeData likeData, final int position) {
        Log.e("TAG", "Request:" + likeData.toString());
        Utils.getInstance().showProgressDialog(mActivity);

        new LikeService().likeService(mActivity, likeData, new RESTClientResponse() {
            @Override
            public void onSuccess(Object response, int statusCode) {
                if (statusCode == 201) {
                    Utils.getInstance().hideProgressDialog();
                    EmptyResponse model = (EmptyResponse) response;
                    Log.e("TAG", "Response:" + model.toString());
                    Utils.getInstance().showToast(mActivity, model.getErrorMsg());

                    VideoDetailsModel videoDetailsModel = videoDetailsModelList.get(position);
                    int count = videoDetailsModel.getLikeCount() + 1;
                    videoDetailsModel.setLikeCount(count);
                    notifyDataSetChanged();
                } else {
                    Utils.getInstance().showToast(mContext, mContext.getString(R.string.somthing_went_wrong));
                    Utils.getInstance().hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Object errorResponse) {

            }
        });

    }

}
