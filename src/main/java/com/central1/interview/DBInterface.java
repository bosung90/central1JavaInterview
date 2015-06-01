package com.central1.interview;

import java.util.List;

/**
 * Created by Eric on 2015-05-30.
 */
public interface DBInterface {
    List<Database.Post> getAllPosts();
    void addPost( String email, String password, String message );
    void addUser( String email, String password );
}
