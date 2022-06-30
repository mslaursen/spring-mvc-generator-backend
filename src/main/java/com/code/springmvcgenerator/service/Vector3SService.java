package com.code.springmvcgenerator.service;

import com.code.springmvcgenerator.entity.Vector3S;
import com.code.springmvcgenerator.repository.Vector3SRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Vector3SService {

    private final Vector3SRepository vector3SRepository;

    @Autowired
    public Vector3SService(Vector3SRepository vector3SRepository) {
        this.vector3SRepository = vector3SRepository;
    }

    public Vector3S save(Vector3S vector) {
        return vector3SRepository.save(vector);
    }
}
