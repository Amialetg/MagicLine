package com.obrasocialsjd.magicline.data.models.posts;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Response {

	@SerializedName("posts")
	private List<PostsItem> posts;

	public void setPosts(List<PostsItem> posts){
		this.posts = posts;
	}

	public List<PostsItem> getPosts(){
		return posts;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"posts = '" + posts + '\'' + 
			"}";
		}
}