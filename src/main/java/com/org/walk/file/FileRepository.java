package com.org.walk.file;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

    FileEntity getFileByCourseId(Long courseId);

    FileEntity findByCourseId(Long courseId);

}
