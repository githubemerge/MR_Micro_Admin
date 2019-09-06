package emerge.project.mrsolution_admin_micro.ui.adapters.assing_products;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import emerge.project.mrsolution_admin_micro.R;
import emerge.project.mrsolution_admin_micro.ui.activity.products.assign_products.AssignProductsPresenter;
import emerge.project.mrsolution_admin_micro.ui.activity.products.assign_products.AssignProductsPresenterImpli;
import emerge.project.mrsolution_admin_micro.ui.activity.products.assign_products.AssignProductsView;
import emerge.project.mrsolution_admin_micro.utils.entittes.Products;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class AssigendProductByUserAdapter extends RecyclerView.Adapter<AssigendProductByUserAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<Products> productsItems;

    AssignProductsPresenter assignProductsPresenter;

    public AssigendProductByUserAdapter(Context mContext, ArrayList<Products> item, AssignProductsView assignProductsView) {
        this.mContext = mContext;
        this.productsItems = item;

        assignProductsPresenter = new AssignProductsPresenterImpli(assignProductsView);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_user_assigned_products, parent, false);
        return new MyViewHolder(itemView);


    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Products pro  = productsItems.get(position);





        holder.textviewProduct.setText(pro.getName());
        holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productsItems.remove(position);
                notifyDataSetChanged();
                assignProductsPresenter.editAddedProduct(productsItems);

            }
        });



        holder.relativeLayoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }


    @Override
    public int getItemCount() {
        return productsItems.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder  {

        @BindView(R.id.card_view)
        CardView cardView;

        @BindView(R.id.relativeLayout_header_main)
        RelativeLayout relativeLayoutMain;


        @BindView(R.id.textview_product)
        TextView textviewProduct;


        @BindView(R.id.imageView_delete)
        ImageView imageViewDelete;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }


}
