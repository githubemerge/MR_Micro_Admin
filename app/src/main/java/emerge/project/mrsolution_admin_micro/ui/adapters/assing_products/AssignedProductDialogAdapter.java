package emerge.project.mrsolution_admin_micro.ui.adapters.assing_products;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import emerge.project.mrsolution_admin_micro.utils.entittes.Products;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class AssignedProductDialogAdapter extends RecyclerView.Adapter<AssignedProductDialogAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<Products> productsItems;



    public AssignedProductDialogAdapter(Context mContext, ArrayList<Products> item) {
        this.mContext = mContext;
        this.productsItems = item;



    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_dialog_assigned_products, parent, false);

        return new MyViewHolder(itemView);


    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        Products products =productsItems.get(position);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_product_defult_small);
        requestOptions.error(R.drawable.ic_product_defult_small);


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


        holder.textviewProductname.setText(products.getName());
        holder.textviewPrincipale.setText(products.getPrinciple());

        if(products.getImageUrl()==null){

        }else {
            Glide.with(mContext)
                    .asBitmap()
                    .load(products.getImageUrl())
                    .apply(requestOptions)
                    .listener(requestListener)
                    .into(holder.imgProduct);
        }



    }

    @Override
    public int getItemCount() {
        return productsItems.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textview_productname)
        TextView textviewProductname;

        @BindView(R.id.textview_principale)
        TextView textviewPrincipale;


        @BindView(R.id.img_product)
        ImageView imgProduct;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }


}
