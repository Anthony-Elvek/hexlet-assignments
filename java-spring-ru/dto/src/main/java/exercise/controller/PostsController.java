package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

// BEGIN
@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping(path = "")
    public List<PostDTO> show() {
        var postList = postRepository.findAll();
        List<PostDTO> postDTOS = new ArrayList<>();

        for (Post post : postList) {
            var comments = commentRepository.findByPostId(post.getId()).stream()
                    .map(this::toCommentDTO)
                    .toList();

            postDTOS.add(toPostDto(post, comments));
        }

        return postDTOS;
    }

    @GetMapping(path = "/{id}")
    public PostDTO showPost(@PathVariable Long id) {
        var post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Post with id %d not found", id)
        ));

        var comments = commentRepository.findByPostId(post.getId()).stream()
                .map(this::toCommentDTO)
                .toList();

        return toPostDto(post, comments);
    }

    private CommentDTO toCommentDTO(Comment comment) {
        var commentDto = new CommentDTO();
        commentDto.setId(comment.getId());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }

    private PostDTO toPostDto(Post post, List<CommentDTO> comments) {
        var postDto = new PostDTO();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setBody(post.getBody());
        postDto.setComments(comments);
        return postDto;
    }
}
// END
