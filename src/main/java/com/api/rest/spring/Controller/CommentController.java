package com.api.rest.spring.Controller;

import com.api.rest.spring.Entity.Comment;
import com.api.rest.spring.Entity.Dto.CommentDto;
import com.api.rest.spring.handlers.CommentHandler;
import com.api.rest.spring.handlers.UserHandler;
import com.api.rest.spring.handlers.exceptions.ValidationException;
import com.api.rest.spring.repository.CommentRepository;
import com.api.rest.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.AuthenticationException;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;


    @PostMapping("/add")
    public HttpStatus addComment(@RequestParam Integer userId, @RequestParam CommentDto commentDto) throws ValidationException, AuthenticationException{
        try {
            CommentHandler commentHandler = new CommentHandler(commentRepository);
            commentHandler.addComment(userId, commentDto);
            return HttpStatus.OK;
        } catch (ValidationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getErrorReason());
        } catch (AuthenticationException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/list")
    public List<CommentDto> getComments(@RequestParam Integer userId, @RequestParam Integer taskId) throws ValidationException, AuthenticationException{
        try {
            CommentHandler commentHandler = new CommentHandler(commentRepository);
            List<CommentDto> commentList = commentHandler.getCommentsLists(userId, taskId);
            return commentList;
        } catch (ValidationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getErrorReason());
        } catch (AuthenticationException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
