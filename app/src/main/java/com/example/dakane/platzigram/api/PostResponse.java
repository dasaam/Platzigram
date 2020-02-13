package com.example.dakane.platzigram.api;

import com.example.dakane.platzigram.model.Post;

import java.util.ArrayList;

/**
 * Created by dakane on 13/02/20.
 */

public class PostResponse {
    ArrayList<Post> postList = new ArrayList<>();
    public void setPostList(ArrayList<Post>  postList){
        this.postList = postList;
    }

    public ArrayList<Post> getPostList(){
        return  postList;
    }
}
