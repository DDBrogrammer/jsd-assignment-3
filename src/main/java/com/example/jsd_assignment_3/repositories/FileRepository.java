package com.example.jsd_assignment_3.repositories;

import com.example.jsd_assignment_3.entities.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FileRepository extends JpaRepository<File,Long> {
    @Query("SELECT f FROM File f where f.status =1 ")
    public Page<File> findActiveFiles( Pageable pageable);
}
