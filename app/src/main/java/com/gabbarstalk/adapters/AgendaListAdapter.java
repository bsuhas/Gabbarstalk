package com.gabbarstalk.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gabbarstalk.R;
import com.gabbarstalk.activity.AgendaWithVideosActivity;
import com.gabbarstalk.models.AgendaDetailsModel;
import com.gabbarstalk.utils.Constants;

import java.util.List;

/**
 * Created by SUHAS
 */
public class AgendaListAdapter extends RecyclerView.Adapter<AgendaListAdapter.ViewHolder> {

    private static int position = -1;
    private List<AgendaDetailsModel> mAgendaDetailModelList;
    private final Context mContext;
    private Activity mActivity;

    public static int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public AgendaListAdapter(Activity activity, Context context, List<AgendaDetailsModel> AgendaDetailModelList) {
        this.mAgendaDetailModelList = AgendaDetailModelList;
        this.mContext = context;
        this.mActivity = activity;
    }

    public void refreshAdapter(List<AgendaDetailsModel> VoterDetailModelList) {
        this.mAgendaDetailModelList = VoterDetailModelList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.agenda_list_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final AgendaDetailsModel model = mAgendaDetailModelList.get(position);

        holder.txtAgendaTitle.setText(model.getAgendaTitle());

        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.rvChildView.setHasFixedSize(true);
        holder.rvChildView.setLayoutManager(llm);

        if (mAgendaDetailModelList.get(position).getVideoDetailsModelList().size() > 0) {
            ChildVideoListAdapter childVideoListAdapter = new ChildVideoListAdapter(mActivity, mContext, mAgendaDetailModelList.get(position));
            holder.rvChildView.setAdapter(childVideoListAdapter);
            holder.rvChildView.setVisibility(View.VISIBLE);
            holder.txtEmpty.setVisibility(View.GONE);
        } else {
            holder.rvChildView.setVisibility(View.GONE);
            holder.txtEmpty.setVisibility(View.VISIBLE);
        }


        holder.txtAgendaTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(view.getContext(), AgendaWithVideosActivity.class);
                loginIntent.putExtra(Constants.AGENDA_MODEL, model);
                view.getContext().startActivity(loginIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAgendaDetailModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final RecyclerView rvChildView;
        private TextView txtAgendaTitle;
        private TextView txtEmpty;

        ViewHolder(View itemView) {
            super(itemView);
            txtEmpty = (TextView) itemView.findViewById(R.id.tv_empty);
            txtAgendaTitle = (TextView) itemView.findViewById(R.id.tv_agenda_title);
            rvChildView = (RecyclerView) itemView.findViewById(R.id.rv_child);
        }
    }
}
