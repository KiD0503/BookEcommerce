package com.book.admin.user;

import com.book.common.entity.Role;
import com.sun.tools.javac.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {
    @Autowired
    private RoleRepository repository;

    @Test
    public void testCreateFirstRole() {
        Role roleAdmin = new Role("Admin", "manageEverything");
        Role savedRole = repository.save(roleAdmin);
        assertThat(savedRole.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateRestRoles() {
        Role roleSalesperson = new Role("SalesPerson", "manage product price, "
                + "customer, shipping, orders and sales report");
        Role roleEditor = new Role("Editor", "manage categories, brands, "
                + "products, articles and menus");
        Role roleShipper = new Role("Shipper", "view products, view orders, "
                + " and update order status");
        Role roleAssistant = new Role("Assistant", "manage questions and reviews");

        repository.saveAll(List.of(roleSalesperson, roleEditor, roleShipper, roleAssistant));
    }
}
