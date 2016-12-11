package supporter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import supporter.models.Comment;

/**
 * Created by Ivaylo on 11-Dec-16.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
