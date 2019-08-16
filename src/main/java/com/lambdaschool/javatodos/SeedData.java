package com.lambdaschool.javatodos;

// Vivek Vishwanath

import com.lambdaschool.javatodos.model.Todo;
import com.lambdaschool.javatodos.model.Role;
import com.lambdaschool.javatodos.model.User;
import com.lambdaschool.javatodos.model.UserRoles;
import com.lambdaschool.javatodos.repository.TodoRepository;
import com.lambdaschool.javatodos.repository.RoleRepository;
import com.lambdaschool.javatodos.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional
@Component
public class SeedData implements CommandLineRunner
{

    RoleRepository rolerepos;
    UserRepository userrepos;
    TodoRepository todorepos;

    public SeedData(RoleRepository rolerepos, UserRepository userrepos, TodoRepository todorepos) {
        this.rolerepos = rolerepos;
        this.userrepos = userrepos;
        this.todorepos = todorepos;
    }

    @Override
    public void run(String[] args) throws Exception {
        Role r1 = new Role("admin");
        Role r2 = new Role("user");

        rolerepos.save(r1);
        rolerepos.save(r2);

        ArrayList<UserRoles> users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u1 = new User("barnbarn", "ILuvM4th!", users);
        u1.getTodos().add(new Todo("Live long and prosper", u1));
        u1.getTodos().add(new Todo("The enemy of my enemy is the enemy I kill last", u1));
        u1.getTodos().add(new Todo("Beam me up", u1));
        userrepos.save(u1);

        ArrayList<UserRoles> admins = new ArrayList<>();
        admins.add(new UserRoles(new User(), r1));
        admins.add(new UserRoles(new User(), r2));
        User u2 = new User("admin", "password", admins);
        u2.getTodos().add(new Todo("A creative man is motivated by the desire to achieve, not by the desire to beat others", u2));
        u2.getTodos().add(new Todo("The question isn't who is going to let me; it's who is going to stop me.", u2));
        userrepos.save(u2);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u3 = new User("Bob", "password", users);
        userrepos.save(u3);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u4 = new User("Jane", "password", users);
        userrepos.save(u4);
    }
}