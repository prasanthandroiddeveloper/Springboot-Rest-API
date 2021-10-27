package com.example.restapi.demo.service.impl;

import com.example.restapi.demo.entity.Post;
import com.example.restapi.demo.exception.ResourceNotFoundException;
import com.example.restapi.demo.payload.PostDto;
import com.example.restapi.demo.repository.PostRepository;
import com.example.restapi.demo.service.PostService;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {


    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        //convert dto to entity

      Post post=mapToEntity(postDto);

      Post newPost=postRepository.save(post);

      PostDto postResponse=mapToDTO(newPost);

      return postResponse;
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts =postRepository.findAll();
        return  posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

    }

    @Override
    public PostDto getPostById(Long id) {
       Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("post","id",id));
       return mapToDTO(post);

    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
       Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("post","id",id));
       post.setTitle(postDto.getTitle());
       post.setDescription(postDto.getDescription());
       post.setContent(postDto.getContent());

       Post updatePost=postRepository.save(post);
       return mapToDTO(updatePost);
    }

    @Override
    public void deletePostById(Long id) {
        Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("post","id",id));
        postRepository.delete(post);
    }

    private PostDto mapToDTO(Post post){
        //convert entity to dto
        PostDto postDto=new PostDto();
        postDto.setTitle(post.getTitle());
        postDto.setId(post.getId());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());

        return postDto;
    }

    private Post mapToEntity(PostDto postDto){
        //convert dto to entity
        Post post=new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }
}
