package com.example.jsd_assignment_3.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Entity
@Data
@Table(name="file")
public class File {
@Id
private long id;
@Column(name ="name")
private String name;
    @Column(name ="path")
private String path;
    @Column(name ="file_size")
private long fileSize;
    @Column(name ="mime")
private String mime;
    @Column(name ="number_of_download")
private long numberOfDownload;
    @Column(name ="version")
private long version;
    @Column(name ="status")
private String status;
    @Column(name ="create_date_time")
private Date createdDateTime;
    @Column(name ="version_ids")
private String versionIds;




}
