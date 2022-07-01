package com.code.springmvcgenerator.service;

import com.code.springmvcgenerator.entity.EntityDetail;
import com.code.springmvcgenerator.repository.EntityDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntityDetailService {

    private final EntityDetailRepository entityDetailRepository;

    @Autowired
    public EntityDetailService(EntityDetailRepository entityDetailRepository) {
        this.entityDetailRepository = entityDetailRepository;
    }

    public EntityDetail save(EntityDetail entityDetail) {
        return entityDetailRepository.save(entityDetail);
    }

    public List<EntityDetail> findAll() {
        return entityDetailRepository.findAll();
    }

    public EntityDetail findById(Long id) {
        return entityDetailRepository.findById(id).orElseThrow();
    }

    public EntityDetail update(EntityDetail entityToUpdate) {
        return save(entityToUpdate);
    }

    public void deleteById(Long id) {
        entityDetailRepository.deleteById(id);
    }
}
