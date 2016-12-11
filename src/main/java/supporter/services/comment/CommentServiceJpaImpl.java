package supporter.services.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import supporter.models.Comment;
import supporter.repositories.CommentRepository;

/**
 * Created by Ivaylo on 11-Dec-16.
 */
@Service
public class CommentServiceJpaImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void create(Comment comment) {
        this.commentRepository.saveAndFlush(comment);
    }
}
