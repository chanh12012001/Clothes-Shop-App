package com.example.clothersshopapp;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    private RecyclerView categoryRecyclerView;
    private RecyclerView testing;
    private CategoryAdapter categoryAdapter;
    private List<CategoryModel> categoryModelList;
    private FirebaseFirestore firebaseFirestore;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        categoryRecyclerView = view .findViewById(R.id.category_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager((getActivity()));
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

        categoryModelList = new ArrayList<CategoryModel>();
        categoryAdapter = new CategoryAdapter(categoryModelList);
        categoryRecyclerView.setAdapter(categoryAdapter);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                categoryModelList.add(new CategoryModel(documentSnapshot.get("icon").toString(),documentSnapshot.get("categoryName").toString()));
                            }
                            categoryAdapter.notifyDataSetChanged();
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(getContext(),error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //------------------Banner slider-----------------
        List<SliderModel> sliderModelList = new ArrayList<SliderModel>();

        sliderModelList.add(new SliderModel(R.drawable.img_banner_1,"#FFADDFF6"));
        sliderModelList.add(new SliderModel(R.drawable.ic_logo_shop,"#FFADDFF6"));
        sliderModelList.add(new SliderModel(R.drawable.ic_about_us,"#FFADDFF6"));
        sliderModelList.add(new SliderModel(R.drawable.ic_menu_camera,"#FFADDFF6"));
        sliderModelList.add(new SliderModel(R.drawable.img_horizontal_item1,"#FFADDFF6"));
        sliderModelList.add(new SliderModel(R.drawable.img_strip_ad_1,"#FFADDFF6"));
        sliderModelList.add(new SliderModel(R.drawable.img_locate,"#FFADDFF6"));
        sliderModelList.add(new SliderModel(R.drawable.ic_free_ship,"#FFADDFF6"));

        //-------------------Banner slider---------------------

        //-------------------Horizontal Product Layout---------
        List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.img_horizontal_item1,"Áo thun", "100000d","TP. Hồ Chí Minh"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.img_horizontal_item1,"Áo Sơ mi", "200000d","TP. Hà Nội"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.img_horizontal_item1,"Áo khoác nam", "400000d","Bình Định"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.img_horizontal_item1,"Áo thun", "100000d","TP. Hồ Chí Minh"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.img_horizontal_item1,"Áo thun", "100000d","TP. Hồ Chí Minh"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.img_horizontal_item1,"Áo thun", "100000d","TP. Hồ Chí Minh"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.img_horizontal_item1,"Áo thun", "100000d","TP. Hồ Chí Minh"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.img_horizontal_item1,"Áo thun", "100000d","TP. Hồ Chí Minh"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.img_horizontal_item1,"Áo thun", "100000d","TP. Hồ Chí Minh"));

        //-------------------Horizontal Product Layout---------

        ////////////////////TESTING//////////////////////////
        testing = view.findViewById(R.id.home_page_recyclerview);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        testing.setLayoutManager(testingLayoutManager);

        List<HomePageModel> homePageModelList = new ArrayList<>();
        homePageModelList.add(new HomePageModel(0,sliderModelList));
        homePageModelList.add(new HomePageModel(categoryModelList,4));
        homePageModelList.add(new HomePageModel(1,R.drawable.img_strip_ad_1,"#000000"));
        homePageModelList.add(new HomePageModel(2,getString(R.string.deal_of_the_day),horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(3,getString(R.string.deal_of_the_day),horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(0,sliderModelList));
        homePageModelList.add(new HomePageModel(1,R.drawable.ic_wishlist,"#ffff00"));
        homePageModelList.add(new HomePageModel(0,sliderModelList));
        homePageModelList.add(new HomePageModel(1,R.drawable.img_horizontal_item1,"#ff0000"));
        homePageModelList.add(new HomePageModel(2,getString(R.string.deal_of_the_day),horizontalProductScrollModelList));

        HomePageAdapter adapter = new HomePageAdapter(homePageModelList);
        testing.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        ////////////////////TESTING//////////////////////////

        return view;
    }
}