package com.arbeit.bachelor.repository;

import com.arbeit.bachelor.model.Anwender;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnwenderRepository extends JpaRepository<Anwender, String> {

}
