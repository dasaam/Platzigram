package com.example.dakane.platzigram.posts.ui;

import com.example.dakane.platzigram.model.Post;

import java.util.List;

/**
 * Created by dakane on 16/02/20.
 */

public interface PostsView {
    void showPosts(List<Post> posts);
    void showAddNewPostView();

}
