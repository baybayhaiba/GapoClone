package com.example.gapoclone.Main.Fragment.Home.Viewpager;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.gapoclone.Adapter.AdapterNewfeeds;
import com.example.gapoclone.Adapter.AdapterPost;
import com.example.gapoclone.Adapter.AdapterTrend;
import com.example.gapoclone.Application.PersonAPI;
import com.example.gapoclone.CreatePostActivity;
import com.example.gapoclone.Main.Fragment.Home.fragment_home;
import com.example.gapoclone.Main.MainActivity;
import com.example.gapoclone.Model.Content;
import com.example.gapoclone.Model.NewsFeed;
import com.example.gapoclone.Model.Post;
import com.example.gapoclone.Model.Trend;
import com.example.gapoclone.R;
import com.example.gapoclone.Utilities.ImageSample;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_home_discover#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_home_discover extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_home_discover() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_home_discover.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_home_discover newInstance(String param1, String param2) {
        fragment_home_discover fragment = new fragment_home_discover();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private RecyclerView rvNewsFeed, rvTrends, rvPosts;
    private AdapterNewfeeds adapterNewfeeds;
    private ArrayList<NewsFeed> newsFeeds;
    private AdapterTrend adapterTrend;
    private ArrayList<Trend> trends;
    private AdapterPost adapterPost;
    private ArrayList<Post> posts;

    private SwipeRefreshLayout swipeRefreshLayout;
    private CardView cvChoosePost;

    private FirebaseDatabase firebaseDatabase;
    private NestedScrollView nestedScrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_discover, container, false);
        initUi(view);
        testData();
        initAdapter();
        eventPullToRefresh();
        //TestFirebase();
        ScrollToTop();
        return view;
    }

    private void initAdapter() {

        adapterNewfeeds = new AdapterNewfeeds(getActivity(), newsFeeds);
        rvNewsFeed.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        rvNewsFeed.setAdapter(adapterNewfeeds);

        adapterTrend = new AdapterTrend(getActivity(), trends);
        rvTrends.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        rvTrends.setAdapter(adapterTrend);

        adapterPost = new AdapterPost(getActivity(), posts);

        rvPosts.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPosts.setAdapter(adapterPost);
        rvPosts.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        InterceptRecyclerView(rvNewsFeed);
        InterceptRecyclerView(rvTrends);
    }

    private void initUi(View view) {
        newsFeeds = new ArrayList<>();
        trends = new ArrayList<>();
        posts = new ArrayList<>();
        rvNewsFeed = view.findViewById(R.id.recyclerViewNewsFeed);
        rvTrends = view.findViewById(R.id.recyclerViewTrends);
        rvPosts = view.findViewById(R.id.recyclerViewPosts);
        cvChoosePost = view.findViewById(R.id.cv_choose_status);
        cvChoosePost.setOnClickListener(this);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        firebaseDatabase = FirebaseDatabase.getInstance();
        nestedScrollView = view.findViewById(R.id.nested_scroll_view);
    }


    private void InterceptRecyclerView(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                recyclerView.getParent().requestDisallowInterceptTouchEvent(true);
            }
        });


    }

    private void ScrollToTop() {
        MainActivity.listenerScroll = () -> {
            //WRONG !
            //https://stackoverflow.com/questions/26875061/scroll-recyclerview-to-show-selected-item-on-top
            //https://www.youtube.com/watch?v=mZrddvW0b_g&ab_channel=TinCoder
            //linearLayoutManager.scrollToPositionWithOffset(2, 20);


            //RIGHT
            //https://stackoverflow.com/questions/4119441/how-to-scroll-to-top-of-long-scrollview-layout
            nestedScrollView.fullScroll(ScrollView.FOCUS_UP);
        };
    }


    private void testData() {

        NewsFeed newsFeed = new NewsFeed.Builder()
                .image_news_feed("https://images2.alphacoders.com/516/516664.jpg")
                .image_owner("https://images.alphacoders.com/595/595884.png")
                .name_owner("")
                .build();

        NewsFeed newsFeed1 = new NewsFeed.Builder()
                .image_news_feed("https://images5.alphacoders.com/605/605588.jpg")
                .image_owner("https://images4.alphacoders.com/673/673536.jpg")
                .name_owner("Phạm Thanh Huy")
                .build();

        NewsFeed newsFeed2 = new NewsFeed.Builder()
                .image_news_feed("https://images.wallpaperscraft.com/image/river_mountains_ice_199112_1280x720.jpg")
                .image_owner("https://images4.alphacoders.com/673/673536.jpg")
                .name_owner("Phạm Thanh Huy")
                .build();

        NewsFeed newsFeed3 = new NewsFeed.Builder()
                .image_news_feed("https://images.wallpaperscraft.com/image/waterfall_rock_cave_199129_1280x720.jpg")
                .image_owner("https://images4.alphacoders.com/673/673536.jpg")
                .name_owner("Phạm Thanh Huy")
                .build();

        Trend trend = new Trend("Chống dịch", "https://image-5.gapo.vn/images/a0257398-28d2-4ddf-a0d3-9d7474b9a76f.jpeg");
        Trend trend1 = new Trend("Tâm sự", "https://image-5.gapo.vn/images/fc58fe4b-fe1c-4bc5-a7ae-28b9fddb5eb1.jpeg");
        Trend trend2 = new Trend("Xem phim", "https://image-5.gapo.vn/images/7d63f9ca-8026-48e7-92cb-c9b164ca0f63.jpeg");


        trends.add(trend);
        trends.add(trend1);
        trends.add(trend2);

        newsFeeds.add(newsFeed);
        newsFeeds.add(newsFeed1);
        newsFeeds.add(newsFeed2);
        newsFeeds.add(newsFeed3);

        firebaseDatabase.getReference("AllPost")
                .child(PersonAPI.getInstance().getPersonId())
                .orderByChild("postAgo")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            posts.add(postSnapshot.getValue(Post.class));
                        }
                        adapterPost.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    public void eventPullToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            adapterTrend.DeleteAll();
            adapterNewfeeds.DeleteAll();
            adapterPost.DeleteAll();
            new Handler(Looper.myLooper()).postDelayed(() -> {
                testData();
                adapterTrend.notifyDataSetChanged();
                adapterNewfeeds.notifyDataSetChanged();
                adapterPost.notifyDataSetChanged();
                Toast.makeText(getContext(), "Data finished", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }, 100);
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cv_choose_status:
                getActivity().startActivity(new Intent(getActivity(), CreatePostActivity.class));
                break;
        }
    }
}