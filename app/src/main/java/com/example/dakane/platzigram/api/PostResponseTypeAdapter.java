package com.example.dakane.platzigram.api;

import android.util.Log;
import android.widget.Toast;

import com.example.dakane.platzigram.model.Post;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by dakane on 13/02/20.
 */

public class PostResponseTypeAdapter  extends TypeAdapter{
    @Override
    public void write(JsonWriter out, Object value) throws IOException {

    }

    @Override
    public PostResponse read(JsonReader in) throws IOException {
        final PostResponse response = new PostResponse();
        ArrayList<Post> postList = new ArrayList<Post>();

        in.beginObject();
        while (in.hasNext()){
            Post post = null;
            try {
                post = readPost(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            postList.add(post);
        }

        in.endObject();
        response.setPostList(postList);
        return response;

    }

    public Post readPost(JsonReader reader) throws Exception{
        Post post = new Post();
        reader.nextName();
        reader.beginObject();
        while (reader.hasNext()){
            String next = reader.nextName();
            switch (next){
                case "author":
                    post.setAuthor(reader.nextString());
                    break;
                case "imageUrl":
                    post.setImageUrl(reader.nextString());
                    break;
                case "timestampCreated":
                    post.setTimestampCreated(reader.nextDouble());
                    break;

            }
        }
        reader.endObject();
        return post;
    }
}
