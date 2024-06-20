package com.bookstore.repository;

import com.bookstore.entity.Category;
import com.bookstore.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


public interface IUserRepository extends JpaRepository<User, Integer> {
    @Query ("SELECT u FROM User u WHERE u.username = ?1")
    User findByUsername(String username);

    //Thêm quyền cho người dùng
    @Modifying
    @Transactional
    @Query (value = "INSERT INTO user_role (user_id, role_id) VALUE (?1, ?2)", nativeQuery = true)
    void addRoleToUser (Long userId, Long roleId);
    //Lấy ID của User bằng Username
    @Query("SELECT u.id FROM User u WHERE u.username = ?1")
    Long getUserIdByUsername (String username);
    //Lấy danh sách quyền từ User Id
    @Query(value = "SELECT r.name FROM role r INNER JOIN user_role ur ON r.id = ur.role_id WHERE ur.user_id = ?1",
            nativeQuery = true)
    String[] getRoleOfUser (Long userId);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    public User getUserByUsername(@Param("username") String username);
}
