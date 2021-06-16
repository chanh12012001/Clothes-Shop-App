package com.example.clothersshopapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {

    private List<WishlistModel> wishlistModelList;
    private Boolean wishlist;

    public WishlistAdapter(List<WishlistModel> wishlistModelList, Boolean wishlist) {
        this.wishlistModelList = wishlistModelList;
        this.wishlist = wishlist;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wishlist_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        String resource = wishlistModelList.get(position).getProductImage();
        String title = wishlistModelList.get(position).getProductTitle();
        long freeCoupens = wishlistModelList.get(position).getFreeCoupens();
        String rating = wishlistModelList.get(position).getRating();
        long totalRatings = wishlistModelList.get(position).getTotalRatings();
        String productPrice = wishlistModelList.get(position).getProductPrice();
        String cuttedPrice = wishlistModelList.get(position).getCuttedPrice();
        boolean paymentMethod = wishlistModelList.get(position).isCOD();

        viewHolder.setData(resource, title, freeCoupens, rating, totalRatings, productPrice, cuttedPrice, paymentMethod);
    }

    @Override
    public int getItemCount() {
        return wishlistModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView productTitle;
        private TextView freeCoupens;
        private ImageView coupenIcon;
        private TextView rating;
        private TextView totalRatings;
        private View priceCut;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView paymentMethod;
        private ImageView deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.iv_product_wishlist_item);
            productTitle = itemView.findViewById(R.id.tv_product_title_wishlist_item);
            freeCoupens = itemView.findViewById(R.id.tv_free_coupon_wishlist_item);
            coupenIcon = itemView.findViewById(R.id.iv_icon_coupon_wishlist_item);
            rating = itemView.findViewById(R.id.tv_product_rating_miniview_wishlist_item);
            totalRatings = itemView.findViewById(R.id.tv_total_rating_wishlist_item);
            priceCut = itemView.findViewById(R.id.tv_cutted_price_wishlist_item);
            productPrice = itemView.findViewById(R.id.tv_product_price_wishlist_item);
            cuttedPrice = itemView.findViewById(R.id.tv_cutted_price_wishlist_item);
            paymentMethod = itemView.findViewById(R.id.tv_payment_method);
            deleteBtn = itemView.findViewById(R.id.btn_remove_wishlist_item);
        }

        private void setData(String resource, String title, long freeCoupensNo, String averageRate, long totalRatingsNo, String price, String cuttedPriceValue, boolean COD) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.ic_home)).into(productImage);
            productTitle.setText(title);
            if (freeCoupensNo != 0) {
                coupenIcon.setVisibility(View.VISIBLE);
                if (freeCoupensNo == 1) {
                    freeCoupens.setText("Free " + freeCoupensNo + " coupen");
                } else {
                    freeCoupens.setText("Free " + freeCoupensNo + " coupens");
                }
            } else {
                coupenIcon.setVisibility(View.INVISIBLE);
                freeCoupens.setVisibility(View.INVISIBLE);
            }
            rating.setText(averageRate);
            totalRatings.setText("(" + totalRatingsNo + " ratings)" );
            productPrice.setText(price);
            cuttedPrice.setText(cuttedPriceValue);
            if (COD) {
                paymentMethod.setVisibility(View.VISIBLE);
            } else {
                paymentMethod.setVisibility(View.INVISIBLE);
            }

            if(wishlist){
                deleteBtn.setVisibility(View.VISIBLE);
            }
            else
            {
                deleteBtn.setVisibility(View.GONE);
            }

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(),"delete",Toast.LENGTH_SHORT).show();
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailsIntent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                    itemView.getContext().startActivity(productDetailsIntent);
                }
            });
        }
    }
}
