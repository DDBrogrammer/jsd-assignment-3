package com.example.jsd_assignment_3.repositories;

import com.example.jsd_assignment_3.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;
public interface FileRepository extends JpaRepository<File,Long> {
}
