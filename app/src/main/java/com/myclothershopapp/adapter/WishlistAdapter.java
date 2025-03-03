package com.myclothershopapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.myclothershopapp.DBqueries;
import com.myclothershopapp.PrefManager;
import com.myclothershopapp.R;
import com.myclothershopapp.activities.ProductDetailsActivity;
import com.myclothershopapp.model.WishlistModel;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {

    private boolean fromSearch;
    private List<WishlistModel> wishlistModelList;
    private Boolean wishlist;
    private int lastPosition = -1;

    public boolean isFromSearch() {
        return fromSearch;
    }

    public void setFromSearch(boolean fromSearch) {
        this.fromSearch = fromSearch;
    }
    PrefManager prefManager;
    public WishlistAdapter(List<WishlistModel> wishlistModelList, Boolean wishlist, Context context) {
        this.wishlistModelList = wishlistModelList;
        this.wishlist = wishlist;
        prefManager = new PrefManager(context);
    }

    public List<WishlistModel> getWishlistModelList() {
        return wishlistModelList;
    }

    public void setWishlistModelList(List<WishlistModel> wishlistModelList) {
        this.wishlistModelList = wishlistModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wishlist_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        String productId = wishlistModelList.get(position).getProductId();
        String resource = wishlistModelList.get(position).getProductImage();
        String title = wishlistModelList.get(position).getProductTitle();
        long freeCoupens =wishlistModelList.get(position).getFreecoupens();
        String rating = wishlistModelList.get(position).getRating();
        long totalRatings = wishlistModelList.get(position).getTotalRatings();
        String productPrice = wishlistModelList.get(position).getProductPrice();
        String cuttedPrice = wishlistModelList.get(position).getCuttedPrice();
        boolean paymentMethod = wishlistModelList.get(position).isCOD();
        boolean inStock = wishlistModelList.get(position).isInStock();
        viewHolder.setData(productId,resource,title,freeCoupens,rating,totalRatings,productPrice,cuttedPrice,paymentMethod,position,inStock);

        if (lastPosition < position) {
            Animation animation = AnimationUtils.loadAnimation(viewHolder.itemView.getContext(), R.anim.fade_in);
            viewHolder.itemView.setAnimation(animation);
            lastPosition = position;
        }
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
        private  TextView paymentMethod,discountedtextview;
        private ImageButton deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productTitle= itemView.findViewById(R.id.product_title);
            freeCoupens = itemView.findViewById(R.id.free_coupen);
            coupenIcon = itemView.findViewById(R.id.coupen_icon);
            rating = itemView.findViewById(R.id.tv_product_rating_miniview);
            totalRatings = itemView.findViewById(R.id.total_ratings);
            priceCut = itemView.findViewById(R.id.price_cut);
            productPrice = itemView.findViewById(R.id.product_price);
            cuttedPrice = itemView.findViewById(R.id.cutted_price);
            paymentMethod = itemView.findViewById(R.id.payment_mehod);
            deleteBtn = itemView.findViewById(R.id.delete_btn);
       //     discountedtextview = itemView.findViewById(R.id.discountTextView);
        }
        private void setData(final String productId, String resource, String title, long freeCoupensNo, String averageRate, long totalRatingsNo, String price, String cuttedPriceValue, boolean COD, final int index,boolean inStock){
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.placeholder_image)).into(productImage);
            productTitle.setText(title);
            if (freeCoupensNo != 0 && inStock){
            }else{
            }
            LinearLayout linearLayout = (LinearLayout) rating.getParent();
            if (inStock){
                rating.setVisibility(View.VISIBLE);
                totalRatings.setVisibility(View.VISIBLE);
                productPrice.setTextColor(Color.parseColor("#000000"));
                cuttedPrice.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.VISIBLE);
                rating.setText(averageRate);
                totalRatings.setText("("+totalRatingsNo+") đánh giá");


                if (prefManager.getDiscountAvailable()){
                    cuttedPrice.setVisibility(View.GONE);
                    int percentage =  Integer.parseInt(prefManager.getPercentageValue());
                    cuttedPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    cuttedPrice.setText(price+" đồng");

                    Double productpricecal = (double) percentage / (double) 100.00;
                    if (!price.equals("")) {
                        Double productrealprice = (Double) (productpricecal * Integer.parseInt(price));
                        int realpriceint = (int)Math.round(productrealprice);

                        productPrice.setText(( Integer.parseInt(price) - realpriceint) + " đồng");
                        Double p = Integer.parseInt(price) - productrealprice;
                        Double dif = Integer.parseInt(price) - p;
                        Double div = (double) dif / (double) Integer.parseInt(price);
                        int percentoff = (int) (div * 100);
                     //   discountedtextview.setText(percentoff + "% off");
                     //   discountedtextview.setVisibility(View.GONE);
                    }
                }else {
                    cuttedPrice.setVisibility(View.GONE);
                   // discountedtextview.setVisibility(View.GONE);
                    productPrice.setText(price+" đồng");

                }

                /// new code

                if (COD){
                    paymentMethod.setVisibility(View.VISIBLE);
                }else {
                    paymentMethod.setVisibility(View.INVISIBLE);

                }
            }else {
                linearLayout.setVisibility(View.INVISIBLE);
                rating.setVisibility(View.INVISIBLE);
                totalRatings.setVisibility(View.INVISIBLE);
                productPrice.setText("Hết hàng");
                productPrice.setTextColor(itemView.getContext().getResources().getColor(R.color.unsuccessred));
                cuttedPrice.setVisibility(View.INVISIBLE);
                paymentMethod.setVisibility(View.INVISIBLE);
            }

            if (wishlist){
                deleteBtn.setVisibility(View.VISIBLE);
            }else {
                deleteBtn.setVisibility(View.GONE);
            }
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!ProductDetailsActivity.running_wishlist_query) {
                        ProductDetailsActivity.running_wishlist_query = true;
                        DBqueries.removeFromWishlist(index, itemView.getContext());
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (fromSearch){
                      ProductDetailsActivity.fromSearch = true;
                    }
                    Intent productDetailsIntent = new Intent(itemView.getContext(),ProductDetailsActivity.class);
                    productDetailsIntent.putExtra("PRODUCT_ID",productId);
                    itemView.getContext().startActivity(productDetailsIntent);
                }
            });
        }
    }
}