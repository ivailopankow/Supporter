package supporter.services.comment;

import supporter.models.Comment;

/**
 * Created by Ivaylo on 11-Dec-16.
 */
public interface CommentService{
    void create(Comment comment);

    void delete(Long commentId);
}
