package emerge.project.mrsolution_admin_micro.ui.adapters.assing_products;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import emerge.project.mrsolution_admin_micro.R;
import emerge.project.mrsolution_admin_micro.ui.activity.products.assign_products.AssignProductsPresenter;
import emerge.project.mrsolution_admin_micro.ui.activity.products.assign_products.AssignProductsPresenterImpli;
import emerge.project.mrsolution_admin_micro.ui.activity.products.assign_products.AssignProductsView;
import emerge.project.mrsolution_admin_micro.utils.entittes.Rep;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class AssigendRepsAdapter extends RecyclerView.Adapter<AssigendRepsAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<Rep> repItems;
    AssignProductsPresenter assignProductsPresenter;

    public AssigendRepsAdapter(Context mContext, ArrayList<Rep> item, AssignProductsView assignProductsView) {
        this.mContext = mContext;
        this.repItems = item;
        assignProductsPresenter = new AssignProductsPresenterImpli(assignProductsView);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_reps, parent, false);
        return new MyViewHolder(itemView);


    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Rep rep = repItems.get(position);


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_profile_users);
        requestOptions.error(R.drawable.ic_profile_users);


        if (rep.isSelect()) {
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorgold));
        } else {
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
        }


        RequestListener<Bitmap> requestListener = new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        };

        if(rep.getImageUrl()==null){

        }else {
            Glide.with(mContext)
                    .asBitmap()
                    .load(rep.getImageUrl())
                    .apply(requestOptions)
                    .listener(requestListener)
                    .into(holder.imgRep);
        }

        holder.textviewName.setText(rep.getName());


        holder.relativeLayoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Rep items :repItems){
                    items.setSelect(false);
                }
                repItems.get(position).setSelect(true);
                notifyDataSetChanged();
                assignProductsPresenter.getSelectedReps(rep);
            }
        });


    }


    @Override
    public int getItemCount() {
        return repItems.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder  {


        @BindView(R.id.card_view)
        CardView cardView;

        @BindView(R.id.relativeLayout_main)
        RelativeLayout relativeLayoutMain;

        @BindView(R.id.img_rep)
        ImageView imgRep;

        @BindView(R.id.textview_rep_name)
        TextView textviewName;






        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }


}
