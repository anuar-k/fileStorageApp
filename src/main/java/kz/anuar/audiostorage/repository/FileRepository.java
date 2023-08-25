package kz.anuar.audiostorage.repository;

import kz.anuar.audiostorage.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long>, JpaSpecificationExecutor<FileEntity> {
//    @Query("select f from FileEntity f where f.fileName = ?1")
    Optional<FileEntity> findByFileName(String name);
}