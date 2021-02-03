package com.grpc.blog.client;

import com.proto.blog.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.proto.blog.BlogServiceGrpc.BlogServiceBlockingStub;

public class BlogClient {

    public static void main(String[] args) {
        System.out.println("Hello i'm a gRPC client");

        BlogClient main = new BlogClient();
        main.run();
    }

    private void run() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext().build();

        BlogServiceBlockingStub blogClient = BlogServiceGrpc.newBlockingStub(channel);

        //created blog
        CreateBlogResponse createBlogResponse = createBlog(blogClient);
        System.out.println("Received create blog response");
        System.out.println(createBlogResponse.toString());

        String blogId = createBlogResponse.getBlog().getId();

        //reading blog
        //readBlog(blogClient, blogId);

        //update blog
        //updateBlog(blogClient, blogId);

        //delete blog
        //deleteBlog(blogClient, blogId);


        //list blogs
        listBlog(blogClient);

    }

    private CreateBlogResponse createBlog(BlogServiceBlockingStub blogClient) {
        Blog blog = Blog.newBuilder()
                .setAuthorId("petronila")
                .setTitle("hope 2")
                .setContent("Hello world this is my first article blog!")
                .build();

        return blogClient.createBlog(
                CreateBlogRequest.newBuilder()
                        .setBlog(blog)
                        .build()
        );
    }

    private void readBlog(BlogServiceBlockingStub blogClient, String blogId) {
        System.out.println("Reading blog...");
        ReadBlogResponse readBlogResponse = blogClient.readBlog(ReadBlogRequest.newBuilder()
                .setBlogId(blogId)
                .build());
        System.out.println(readBlogResponse.toString());
    }

    private void updateBlog(BlogServiceBlockingStub blogClient, String blogId) {
        Blog newBlog = Blog.newBuilder()
                .setId(blogId)
                .setAuthorId("petra")
                .setTitle("Hope!")
                .setContent("Hello world this is my first blog! I've added some more content")
                .build();

        System.out.println("Updating blog...");
        UpdateBlogResponse updateBlogResponse = blogClient.updateBlog(
                UpdateBlogRequest.newBuilder().setBlog(newBlog).build());

        System.out.println("Updated blog");
        System.out.println(updateBlogResponse.toString());
    }

    private void deleteBlog(BlogServiceBlockingStub blogClient, String blogId) {
        System.out.println("Deleting blog");
        blogClient.deleteBlog(
                DeleteBlogRequest.newBuilder().setBlogId(blogId).build()
        );

        System.out.println("Deleted blog");
    }

    private void listBlog(BlogServiceBlockingStub blogClient) {
        blogClient.listBlog(ListBlogRequest.newBuilder().build()).forEachRemaining(
                listBlogResponse -> System.out.println(listBlogResponse.getBlog().toString())
        );
    }
}
