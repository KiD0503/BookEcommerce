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

// Code about Unit Test to know about Test more and more.
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests {
    @Autowired
    private UserRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateNewUserWithOneRole(){
        Role roleAdmin = entityManager.find(Role.class,1);
        User userHieuNguyen = new User("hieunv2111@gmail.com", "hieunv2111", "Hieu", "Nguyen Van");
        userHieuNguyen.addRole(roleAdmin);
         User savedUser = repository.save(userHieuNguyen);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateNewUserWithTwoRoles(){
        User userKid = new User("kid@gmail.com", "kid1999", "Kid", "Nguyen");
        Role roleEditor = new Role(3);
        Role roleAssistant = new Role(5);

        userKid.addRole(roleEditor);
        userKid.addRole(roleAssistant);

        User savedUser = repository.save(userKid);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers(){
        Iterable<User> listUsers = repository.findAll();
        listUsers.forEach(user -> System.out.println(user));
    }


    @Test
    public void testGetUserById(){
        User userHieuNguyen = repository.findById(1).get();//get user have id 1 and show in console
        System.out.println(userHieuNguyen);
        assertThat(userHieuNguyen).isNotNull();
    }

    @Test
    public void testUpdateUserDetails(){
        User userHieuNguyen = repository.findById(1).get();
        Role roleAdmin = new Role(1);
        userHieuNguyen.setEnabled(true);//Update enabled
        userHieuNguyen.setEmail("hieunv2111@gmail.com");//Update email
        userHieuNguyen.addRole(roleAdmin);// Add role Admin for userHieuNguyen
        repository.save(userHieuNguyen); // Save in db
    }

    @Test
    public void testGetUserByEmail(){//Check duplicate email
        String email = "hieunv2111@gmail.com";
        User user = repository.getUserByEmail(email);
        assertThat(user).isNotNull();
    }
}