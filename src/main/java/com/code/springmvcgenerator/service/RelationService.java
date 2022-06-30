package com.code.springmvcgenerator.service;

import com.code.springmvcgenerator.entity.Relation;
import com.code.springmvcgenerator.repository.RelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelationService {

    private final RelationRepository relationRepository;

    @Autowired
    public RelationService(RelationRepository relationRepository) {
        this.relationRepository = relationRepository;
    }

    public Relation save(Relation relation) {
        return relationRepository.save(relation);
    }

    public Relation findById(Long id) {
        return relationRepository.findById(id).orElseThrow();
    }
}