package com.example.e_commerce.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.e_commerce.HorizontalProductScrollAdapter;
import com.example.e_commerce.ProductDetailsActivity;
import com.example.e_commerce.R;
import com.example.e_commerce.SliderAdapter;
import com.example.e_commerce.ViewAllActivity;
import com.example.lib.Model.HomePageModel;
import com.example.lib.Model.HorizontalProductScrollModel;
import com.example.lib.Model.SliderModel;
import com.example.lib.Model.WishlistModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageAdapter extends RecyclerView.Adapter {

    private List<HomePageModel> homePageModels;
    private RecyclerView.RecycledViewPool recycledViewPool;
    private int lastPos=-1;

    public HomePageAdapter(List<HomePageModel> homePageModels) {
        this.homePageModels = homePageModels;
        recycledViewPool=new RecyclerView.RecycledViewPool();
    }

    @Override
    public int getItemViewType(int position) {

        switch (homePageModels.get(position).getType()){
            case 0:
                return HomePageModel.BANNER_SLIDER;
            case 1:
                return HomePageModel.HORIZONTAL_PRODUCT_VIEW;
            case 2:
                return HomePageModel.GRID_PRODUCT_VIEW;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case HomePageModel.BANNER_SLIDER:
                View BannerSliderview= LayoutInflater.from(parent.getContext()).inflate(R.layout.sliding_ad_layout,parent,false);
                return new BannerSliderViewHolder(BannerSliderview);
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                View horizontalproductview= LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_layout,parent,false);
                return new HorizontalProductViewHolder(horizontalproductview);
            case HomePageModel.GRID_PRODUCT_VIEW:
                View gridproductview= LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_product_layout,parent,false);
                return new GridProductViewHolder(gridproductview);
            default:
                return null;

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        switch (homePageModels.get(position).getType()){
            case HomePageModel.BANNER_SLIDER:
                List<SliderModel> sliderModelList=homePageModels.get(position).getSliderModelList();
                ((BannerSliderViewHolder) holder).setBannerSliderViewPager(sliderModelList);
                break;
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                String title=homePageModels.get(position).getTitle();
                String colorr=homePageModels.get(position).getBackgroundColor();
                List<HorizontalProductScrollModel> horizontalProductScrollModelList=homePageModels.get(position).getHorizontalProductScrollModelList();
                List<WishlistModel> viewAllProductList=homePageModels.get(position).getViewAllProductList();
                ((HorizontalProductViewHolder) holder).setHorizontalProductLayout(horizontalProductScrollModelList,title,colorr,viewAllProductList);
                break;
            case HomePageModel.GRID_PRODUCT_VIEW:
                String gridcolorr=homePageModels.get(position).getBackgroundColor();
                String gridtitle=homePageModels.get(position).getTitle();
                List<HorizontalProductScrollModel> gridProductScrollModelList=homePageModels.get(position).getHorizontalProductScrollModelList();
                ((GridProductViewHolder) holder).setGridProductLayout(gridProductScrollModelList,gridtitle,gridcolorr);
                break;

            default:
                return;
        }
        if(lastPos<position) {
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_in);
            holder.itemView.setAnimation(animation);
            lastPos=position;
        }
    }

    @Override
    public int getItemCount() {
        return homePageModels.size();
    }


    public class BannerSliderViewHolder extends RecyclerView.ViewHolder {

        private ViewPager bannerSliderViewPager;
        private int currentPage;
        private Timer timer;
        final private long DELAY_TIME=3000;
        final private long PERIOD_TIME=3000;
        private List<SliderModel> arrangedList;


        public BannerSliderViewHolder(@NonNull View itemView) {
            super(itemView);

            bannerSliderViewPager=itemView.findViewById(R.id.banner_slider_viewpager);

        }

        public void setBannerSliderViewPager(final List<SliderModel> sliderModelList){
            currentPage=2;
            if(timer!=null){
                timer.cancel();
            }
            arrangedList=new ArrayList<>();
            for(int x=0;x<sliderModelList.size();x++){
                arrangedList.add(x,sliderModelList.get(x));
            }
            arrangedList.add(0,sliderModelList.get(sliderModelList.size()-2));
            arrangedList.add(1,sliderModelList.get(sliderModelList.size()-1));
            arrangedList.add(sliderModelList.get(0));
            arrangedList.add(sliderModelList.get(1));

            SliderAdapter sliderAdapter=new SliderAdapter(arrangedList);
            bannerSliderViewPager.setAdapter(sliderAdapter);
            bannerSliderViewPager.setClipToPadding(false);
            bannerSliderViewPager.setPageMargin(20);
            bannerSliderViewPager.setCurrentItem(currentPage);

            ViewPager.OnPageChangeListener onPageChangeListener=new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    currentPage=position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if(state==ViewPager.SCROLL_STATE_IDLE){
                        pageLooper(arrangedList);
                    }
                }
            };
            bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);

            startBannerSlideShow(arrangedList);

            bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    pageLooper(arrangedList);
                    stopBannerSlideShow();

                    if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                        startBannerSlideShow(arrangedList);
                    }
                    return false;
                }
            });

        }

        private void pageLooper(List<SliderModel> sliderModelList){
            if(currentPage==sliderModelList.size()-2){
                currentPage=2;
                bannerSliderViewPager.setCurrentItem(currentPage,false);
            }

            if(currentPage==1){
                currentPage=sliderModelList.size()-3;
                bannerSliderViewPager.setCurrentItem(currentPage,false);
            }
        }

        private void startBannerSlideShow(final List<SliderModel> sliderModelList){
            final Handler handler=new Handler();
            final Runnable update=new Runnable() {
                @Override
                public void run() {
                    if(currentPage>=sliderModelList.size()){
                        currentPage=1;
                    }
                    bannerSliderViewPager.setCurrentItem(currentPage++,true);
                }
            };

            timer=new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            },DELAY_TIME,PERIOD_TIME);
        }

        private void stopBannerSlideShow(){
            timer.cancel();
        }

    }

    public class HorizontalProductViewHolder extends RecyclerView.ViewHolder{
        private ConstraintLayout container;
        private TextView horizontallayoutTitle;
        private Button horizontalviewAllBtn;
        private RecyclerView horizontalRecyclerView;

        public HorizontalProductViewHolder(@NonNull View itemView) {
            super(itemView);
            container=itemView.findViewById(R.id.container);
            horizontalRecyclerView=itemView.findViewById(R.id.recycler_horizontal_scroll_layout);
            horizontalviewAllBtn=itemView.findViewById(R.id.btn_horizontal_scroll_layout);
            horizontallayoutTitle=itemView.findViewById(R.id.horizontal_scroll_layout_title);
            horizontalRecyclerView.setRecycledViewPool(recycledViewPool);
        }

        private void setHorizontalProductLayout(List<HorizontalProductScrollModel> horizontalProductScrollModelList, String title, String color, List<WishlistModel> viewAllProductList){
            container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            horizontallayoutTitle.setText(title);
            if(horizontalProductScrollModelList.size() >8){
                horizontalviewAllBtn.setVisibility(View.VISIBLE);
                horizontalviewAllBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ViewAllActivity.wishlistModelList=viewAllProductList;
                        itemView.getContext().startActivity(new Intent(itemView.getContext(),ViewAllActivity.class)
                                .putExtra("layout",0)
                                .putExtra("title",title)
                        );
                    }
                });
            }
            else {
                horizontalviewAllBtn.setVisibility(View.INVISIBLE);
            }
            HorizontalProductScrollAdapter horizontalProductScrollAdapter=new HorizontalProductScrollAdapter(horizontalProductScrollModelList);
            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(itemView.getContext());
            linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontalRecyclerView.setLayoutManager(linearLayoutManager1);
            horizontalRecyclerView.setAdapter(horizontalProductScrollAdapter);
            horizontalProductScrollAdapter.notifyDataSetChanged();

        }
    }

    public class GridProductViewHolder extends RecyclerView.ViewHolder{
        private ConstraintLayout container;
        private TextView gridLayoutTitle;
        private Button gridLayoutButton;
        private GridLayout gridProductLayout;

        public GridProductViewHolder(@NonNull final View itemView) {
            super(itemView);
            container=itemView.findViewById(R.id.container);
            gridLayoutTitle=itemView.findViewById(R.id.grid_product_layout_title);
            gridLayoutButton=itemView.findViewById(R.id.grid_product_layout_viewall_button);
            gridProductLayout=itemView.findViewById(R.id.gird_product_layout_gridview);

        }
        private void setGridProductLayout(final List<HorizontalProductScrollModel> horizontalProductScrollModelList, final String title,String color) {
            container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            gridLayoutTitle.setText(title);

            for(int x=0;x<4;x++){
                ImageView productImage=gridProductLayout.getChildAt(x).findViewById(R.id.horizontal_product_img);
                TextView productTitle=gridProductLayout.getChildAt(x).findViewById(R.id.horizontal_product_name);
                TextView productDesc=gridProductLayout.getChildAt(x).findViewById(R.id.product_description);
                TextView productPrice=gridProductLayout.getChildAt(x).findViewById(R.id.horizontal_product_price);

                Glide.with(itemView.getContext()).load(horizontalProductScrollModelList.get(x).getProductImage()).apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background)).into(productImage);
                productTitle.setText(horizontalProductScrollModelList.get(x).getProductTitle());
                productDesc.setText(horizontalProductScrollModelList.get(x).getProductDesc());
                productPrice.setText(horizontalProductScrollModelList.get(x).getProductPrice());

                gridProductLayout.getChildAt(x).setBackgroundColor(Color.WHITE);
                if(!title.equals("")){
                    final int finalX = x;
                    gridProductLayout.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // gọi ProductDetailsActivity
                            itemView.getContext().startActivity(new Intent(itemView.getContext(), ProductDetailsActivity.class).putExtra("PRODUCT_ID",horizontalProductScrollModelList.get(finalX).getProductID()));
                        }
                    });
                }
            }

            if(!title.equals("")) {
                gridLayoutButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ViewAllActivity.horizontalProductScrollModelList = horizontalProductScrollModelList;
                        itemView.getContext().startActivity(new Intent(itemView.getContext(), ViewAllActivity.class)
                                .putExtra("layout", 1)
                                .putExtra("title", title)
                        );

                    }
                });
            }
        }
    }

}
