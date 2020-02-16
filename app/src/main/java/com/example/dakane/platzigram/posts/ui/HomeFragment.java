package com.example.dakane.platzigram.posts.ui;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dakane.platzigram.R;
import com.example.dakane.platzigram.adapter.PictureAdapterRecyclerView;
import com.example.dakane.platzigram.adapter.PostAdapterRecyclerView;
import com.example.dakane.platzigram.api.PlatzigramClient;
import com.example.dakane.platzigram.api.PlatzigramFirebaseService;
import com.example.dakane.platzigram.api.PostResponse;
import com.example.dakane.platzigram.model.Picture;
import com.example.dakane.platzigram.model.Post;
import com.example.dakane.platzigram.view.fragments.NewPostFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    RecyclerView picturesRecycler ;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Post> posts;
    PostAdapterRecyclerView postAdapterRecyclerView;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        showToolbar(getResources().getString(R.string.tab_home), false, view);

        posts = new ArrayList<>();
        populateData();

        picturesRecycler = (RecyclerView) view.findViewById(R.id.pictureRecycler);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        picturesRecycler.setLayoutManager(linearLayoutManager);

        postAdapterRecyclerView
                = new PostAdapterRecyclerView(posts, R.layout.cardview_picture, getActivity());

        picturesRecycler.setAdapter(postAdapterRecyclerView);

        FloatingActionButton fab =  (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewPostFragment newPostFragment = new NewPostFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, newPostFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        populateData();
    }

    private void populateData() {
        PlatzigramFirebaseService service = (new PlatzigramClient()).getService();
        Call<PostResponse> postListCall = service.getPostList();
        postListCall.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful()){
                    PostResponse result = response.body();

                    posts.clear();
                    posts.addAll(result.getPostList());

                    postAdapterRecyclerView.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public ArrayList<Picture> buildPictures(){
        ArrayList<Picture> pictures = new ArrayList<>();
        pictures.add(new Picture("https://i.imgur.com/ajEEL5k.jpg",
                "Daniel sanchez", "4 me gusta", "3 dias"));
        pictures.add(new Picture("http://i.imgur.com/DvpvklR.png",
                "Oscar perez", "2 me gusta", "1 dias"));
        pictures.add(new Picture("http://i.imgur.com/DvpvklR.png",
                "Ramiro Cortez", "5 me gusta", "3 dias"));

        return pictures;

    }

    public void showToolbar(String tittle, boolean upButton, View view){
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(tittle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }

}
