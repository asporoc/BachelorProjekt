package com.arbeit.bachelor.repository;

import com.arbeit.bachelor.model.Bewirtschafter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BewirtschafterRepository extends JpaRepository<Bewirtschafter, String> {
}