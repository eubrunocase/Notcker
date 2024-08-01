package interfaces;

import entityes.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<User.Role, Long> {
    User.Role findByName(User.ERole name);
}