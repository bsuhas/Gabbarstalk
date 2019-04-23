package com.gabbarstalk.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.gabbarstalk.activity.AgendaWithVideosActivity;
import com.gabbarstalk.interfaces.RESTClientResponse;
import com.gabbarstalk.models.AgendaDetailsModel;
import com.gabbarstalk.models.EmptyResponse;
import com.gabbarstalk.models.LikeData;
import com.gabbarstalk.models.UserData;
import com.gabbarstalk.models.VideoDetailsModel;
import com.gabbarstalk.utils.CircularImageView;
import com.gabbarstalk.utils.Constants;
import com.gabbarstalk.utils.UserPreferences;
import com.gabbarstalk.utils.Utils;
import com.gabbarstalk.webservices.LikeService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SUHAS
 */
public class RecentVideosListAdapter extends RecyclerView.Adapter<RecentVideosListAdapter.ViewHolder> {

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

    public RecentVideosListAdapter(Activity activity, Context context, List<VideoDetailsModel> videoDetailsModelList) {
        this.videoDetailsModelList = videoDetailsModelList;
        this.mContext = context;
        this.mActivity = activity;
    }

    public void refreshAdapter(List<VideoDetailsModel> videoDetailsModelList) {
        this.videoDetailsModelList = new ArrayList<>(videoDetailsModelList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.recent_videos_list_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final VideoDetailsModel model = videoDetailsModelList.get(position);
        String myString = model.getAgendaTitle();
        String upperString = myString.substring(0, 1).toUpperCase() + myString.substring(1);
        holder.tvAgendaTitle.setText(upperString);

        holder.tvUsername.setText(String.format("@%s", model.getUserName()));
        holder.tvVideoCaption.setText(model.getCaption());
        holder.tvLikeCount.setText(model.getLikeCount() + " likes");
        if (!TextUtils.isEmpty(model.getVideoThumbnail()))
            Picasso.with(mContext).load(model.getVideoThumbnail()).placeholder(R.color.md_black_1000).into(holder.imgVideoThumb);

        if (!TextUtils.isEmpty(model.getProfileImage()))
            Picasso.with(mContext).load(model.getProfileImage()).placeholder(R.color.md_black_1000).into(holder.profileImg);

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

        holder.imgVideoPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.getInstance().openVideoPlayer(mActivity, model.getVideoUrl());
//                Utils.getInstance().showToast(mContext, "Coming Soon");
            }
        });
        holder.tvAgendaTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgendaDetailsModel agendaDetailsModel = new AgendaDetailsModel();
                agendaDetailsModel.setAgendaId(Integer.parseInt(model.getAgendaId()));
                agendaDetailsModel.setAgendaTitle(model.getAgendaTitle());
                agendaDetailsModel.setAgendaDetails(model.getAgendaDetails());
                Intent intent = new Intent(view.getContext(), AgendaWithVideosActivity.class);
                intent.putExtra(Constants.AGENDA_MODEL, agendaDetailsModel);
                view.getContext().startActivity(intent);
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
        private CircularImageView profileImg;
        private TextView tvAgendaTitle;
        private TextView tvUsername;
        private TextView tvLikeCount;
        private TextView tvVideoCaption;
        private ImageView imgVideoThumb;
        private ImageView imgVideoPlay;
        private ImageView ivLike;
        private ImageView ivShare;

        ViewHolder(View view) {
            super(view);
            llListItem = (LinearLayout) view.findViewById(R.id.ll_list_item);
            profileImg = (CircularImageView) view.findViewById(R.id.profile_img);
            tvVideoCaption = (TextView) view.findViewById(R.id.tv_video_caption);
            tvLikeCount = (TextView) view.findViewById(R.id.tv_like_count);
            tvAgendaTitle = (TextView) view.findViewById(R.id.tv_agenda_title);
            tvUsername = (TextView) view.findViewById(R.id.tv_username);
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
