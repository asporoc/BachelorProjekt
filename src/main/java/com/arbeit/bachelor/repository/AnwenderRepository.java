package com.arbeit.bachelor.repository;

import com.arbeit.bachelor.model.Anwender;
import com.arbeit.bachelor.model.SBK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnwenderRepository extends JpaRepository<Anwender, String> {
}
