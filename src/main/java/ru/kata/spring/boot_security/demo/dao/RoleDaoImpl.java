package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class RoleDaoImpl implements RoleDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role getByName(String name) {
        return entityManager.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class)
                .setParameter("name", name).getSingleResult();
    }

    @Override
    public List<Role> getAll() {
        return entityManager.createQuery("from Role").getResultList();
    }

    @Override
    public void add(Role role) {
        entityManager.persist(role);

    }

    @Override
    public Role getById(int id) {
        return entityManager.find(Role.class, id);
    }

    @Override
//    public Set<Role> getRolesByName(Set<Role> roles) {
//        Set<Role> userRoles = new HashSet<>();
//        for (Role role : roles){
//            userRoles.add(getByName(role.getName()));
//        }
//        return userRoles;
//    }
    public Set<Role> getRolesByName(Set<Role> roles) {
        //Set<Role> userRoles = new HashSet<>();
        List<String> names = roles.stream().map(field -> field.getName()).collect(Collectors.toList());
        List<Role> roleList = entityManager.createQuery("SELECT r FROM Role r WHERE r.name IN :names",Role.class)
                .setParameter("names", names)
                .getResultList();
        return roleList.stream().collect(Collectors.toSet());
    }
}
