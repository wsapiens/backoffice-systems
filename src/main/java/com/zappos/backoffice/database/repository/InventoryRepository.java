package com.zappos.backoffice.database.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.zappos.backoffice.database.model.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long>, JpaSpecificationExecutor<Inventory>{
    public List<Inventory> findByBrandId(Long brandId);
    public List<Inventory> findByBrandId(Long brandId, Pageable pageable);
    public List<Inventory> findByBrandIdAndReceivedTimeAfterAndReceivedTimeBefore(Long brandId, Date from, Date to);
    public List<Inventory> findByBrandIdAndReceivedTimeAfterAndReceivedTimeBefore(Long brandId, Date from, Date to, Pageable pageable);
}
