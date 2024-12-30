package com.arbeit.bachelor.repository;

import com.arbeit.bachelor.model.Bewirtschafter;
import com.arbeit.bachelor.model.Organisationseinheit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganisationseinheitRepository extends JpaRepository<Organisationseinheit, String> {
    List<Bewirtschafter> findBewirtschaftersByName(String organisationseinheitName);
}
