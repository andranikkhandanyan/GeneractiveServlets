package am.aca.generactive.repository;

import am.aca.generactive.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String username);

    @Query("select u from User u " +
            " join fetch u.authorities" +
            " where u.username = :username")
    Optional<User> findByUsernameEager(@Param("username") String username);
}
