package com.book.admin.user;

import com.book.common.entity.Role;
import com.book.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests {
    @Autowired
    private UserRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUser(){
        Role roleAdmin = entityManager.find(Role.class,1);
        User userHieuNguyen = new User("hieunv2111@gmail.com", "hieunv2111", "Hieu", "Nguyen Van");
        userHieuNguyen.addRole(roleAdmin);
        User savedUser = repository.save(userHieuNguyen);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }
}