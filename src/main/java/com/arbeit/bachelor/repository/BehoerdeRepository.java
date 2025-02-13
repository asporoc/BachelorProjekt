package com.arbeit.bachelor.repository;

import com.arbeit.bachelor.model.Behoerde;
import com.arbeit.bachelor.model.Bewirtschafter;
import com.arbeit.bachelor.model.Organisationseinheit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BehoerdeRepository extends JpaRepository<Behoerde, String> {
    List<Organisationseinheit> findOrganisationseinheitenByName(String behoerdeName);
    List<Bewirtschafter> findBewirtschaftersByName(String behoerdeName);
}
