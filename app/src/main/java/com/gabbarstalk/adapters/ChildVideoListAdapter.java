package com.gabbarstalk.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gabbarstalk.R;
import com.gabbarstalk.activity.AgendaWithVideosActivity;
import com.gabbarstalk.models.AgendaDetailsModel;
import com.gabbarstalk.models.VideoDetailsModel;
import com.gabbarstalk.utils.Constants;
import com.gabbarstalk.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by SUHAS on 31/03/2019.
 */

public class ChildVideoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static int position = -1;
    private List<VideoDetailsModel> videoDetailsModelList;
    private final Context mContext;
    private Activity mActivity;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 2;
    private AgendaDetailsModel mAgendaDetailsModel ;
    public static int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ChildVideoListAdapter(Activity activity, Context context, AgendaDetailsModel agendaDetailsModel) {
        mAgendaDetailsModel = agendaDetailsModel;
        this.videoDetailsModelList = mAgendaDetailsModel.getVideoDetailsModelList();
        this.mContext = context;
        this.mActivity = activity;
    }

    public void refreshAdapter(List<VideoDetailsModel> videoDetailsModelList) {
        this.videoDetailsModelList = videoDetailsModelList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ITEM) {
            //Inflating recycle view item layout
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.child_video_thumb_list_item, viewGroup, false);
            return new ItemViewHolder(itemView);
        } else if (viewType == TYPE_FOOTER) {
            //Inflating footer view
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_footer, viewGroup, false);
            return new FooterViewHolder(itemView);
        } else return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ItemViewHolder) {
            final VideoDetailsModel model = videoDetailsModelList.get(position);
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            if (!TextUtils.isEmpty(model.getVideoThumbnail()))
                Picasso.with(mContext).load(model.getVideoThumbnail()).placeholder(R.color.md_black_1000).into(itemViewHolder.imgVideoThumb);

            itemViewHolder.imgVideoPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.getInstance().openVideoPlayer(mActivity, model.getVideoUrl());
                }
            });
        } else if (holder instanceof FooterViewHolder) {

            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.tvLoadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), AgendaWithVideosActivity.class);
                    intent.putExtra(Constants.AGENDA_MODEL, mAgendaDetailsModel);
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return videoDetailsModelList.size() +1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == videoDetailsModelList.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgVideoThumb;
        private ImageView imgVideoPlay;

        ItemViewHolder(View view) {
            super(view);
            imgVideoThumb = (ImageView) view.findViewById(R.id.img_video_thumb);
            imgVideoPlay = (ImageView) view.findViewById(R.id.img_video_play);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

        private TextView tvLoadMore;

        FooterViewHolder(View view) {
            super(view);
            tvLoadMore = (TextView) view.findViewById(R.id.tv_load_more);
        }
    }
}

