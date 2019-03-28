package com.gabbarstalk.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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


    public static int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public AgendaListAdapter(Context context, List<AgendaDetailsModel> AgendaDetailModelList) {
        this.mAgendaDetailModelList = AgendaDetailModelList;
        this.mContext = context;
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

        holder.llAgendaListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.VOTER_MODEL, model);
                ((HomeScreenActivity) mContext).setFragment(new VoterDetailsFragment(), bundle);*/

                Intent loginIntent = new Intent(view.getContext(), AgendaWithVideosActivity.class);
                loginIntent.putExtra(Constants.AGENDA_MODEL,model);
                view.getContext().startActivity(loginIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mAgendaDetailModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
//        private final RecyclerView rvChild;
        private LinearLayout llAgendaListItem;
        private TextView txtAgendaTitle;

        ViewHolder(View itemView) {
            super(itemView);
            txtAgendaTitle = (TextView) itemView.findViewById(R.id.tv_agenda_title);
            llAgendaListItem = (LinearLayout) itemView.findViewById(R.id.ll_agenda_list_item);
//            rvChild = (RecyclerView) itemView.findViewById(R.id.rv_child);
        }
    }
}
