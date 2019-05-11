package com.zappos.backoffice.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zappos.backoffice.database.model.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    public Brand findByName(String name);
}
