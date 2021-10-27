package com.example.restapi.demo.controller;

import com.example.restapi.demo.payload.PostDto;
import com.example.restapi.demo.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //create post
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    //get all posts
    @GetMapping
    public List<PostDto> getAllPosts(){
        return postService.getAllPosts();
    }

    //get posts by ID
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostsById(@PathVariable(name="id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }
    //update posts by Id
   @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable(name="id") long id){
        PostDto postResponse=postService.updatePost(postDto,id);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }
    //delete posts by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostsById(@PathVariable(name="id") long id){
       postService.deletePostById(id);
        return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);

    }




}
