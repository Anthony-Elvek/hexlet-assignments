package exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import exercise.model.Post;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    void deletePostById(Long id);
}