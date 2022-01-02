package com.example.jsd_assignment_3.entities;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;
@Entity
@Data
@Table(name="setting")
public class Setting {
@Id
private long id;
@Column(name="max_file_size")
private long maxFileSize;
@Column(name = "item_per_page")
private long itemPerPage;
@Column(name="mime_type_allowed")
private String mimeTypeAllowed;
@Column(name="last_update_time")
private LocalDateTime lastUpdatedTime;




}
