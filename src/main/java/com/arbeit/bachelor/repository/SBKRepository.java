package com.arbeit.bachelor.repository;

import com.arbeit.bachelor.model.SBK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SBKRepository extends JpaRepository<SBK, String> {
    List<SBK> findByBewirtschafter_Name(String bewirtschafterName);
    List<SBK> findAll();
}

