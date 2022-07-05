package com.code.springmvcgenerator.service;

import com.code.springmvcgenerator.entity.Entity;
import com.code.springmvcgenerator.repository.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntityService {

    private final EntityRepository entityRepository;

    @Autowired
    public EntityService(EntityRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    public Entity save(Entity entity) {
        return entityRepository.save(entity);
    }

    public List<Entity> findAll() {
        return entityRepository.findAll();
    }

    public Entity findById(Long id) {
        return entityRepository.findById(id).orElseThrow();
    }

    public Entity update(Entity entityToUpdate) {
        return save(entityToUpdate);
    }

    public void deleteById(Long id) {
        entityRepository.deleteById(id);
    }

    public List<Entity> findByProjectId(Long id) {
        return entityRepository.findByProjectId(id);
    }
}
