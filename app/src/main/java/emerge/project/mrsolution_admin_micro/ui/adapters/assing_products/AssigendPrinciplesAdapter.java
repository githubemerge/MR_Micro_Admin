package emerge.project.mrsolution_admin_micro.ui.adapters.assing_products;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import emerge.project.mrsolution_admin_micro.R;
import emerge.project.mrsolution_admin_micro.ui.activity.products.assign_products.AssignProductsPresenter;
import emerge.project.mrsolution_admin_micro.ui.activity.products.assign_products.AssignProductsPresenterImpli;
import emerge.project.mrsolution_admin_micro.ui.activity.products.assign_products.AssignProductsView;
import emerge.project.mrsolution_admin_micro.utils.entittes.Principles;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class AssigendPrinciplesAdapter extends RecyclerView.Adapter<AssigendPrinciplesAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<Principles> principlesItems;
    AssignProductsPresenter assignProductsPresenter;

    public AssigendPrinciplesAdapter(Context mContext, ArrayList<Principles> item, AssignProductsView assignProductsView) {
        this.mContext = mContext;
        this.principlesItems = item;
        assignProductsPresenter = new AssignProductsPresenterImpli(assignProductsView);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_principles, parent, false);
        return new MyViewHolder(itemView);


    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Principles principles  = principlesItems.get(position);


        if (principles.isSelect()) {
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorgold));
        } else {
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
        }


        holder.textviewPrinciple.setText(principles.getName());


        holder.relativeLayoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Principles items :principlesItems){
                    items.setSelect(false);
                }
                principlesItems.get(position).setSelect(true);
                assignProductsPresenter.getSelectedPrinciple(principles);
                notifyDataSetChanged();

            }
        });


    }


    @Override
    public int getItemCount() {
        return principlesItems.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder  {

        @BindView(R.id.card_view)
        CardView cardView;

        @BindView(R.id.relativeLayout_header_main)
        RelativeLayout relativeLayoutMain;


        @BindView(R.id.textview_principle)
        TextView textviewPrinciple;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }


}
