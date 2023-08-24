package kz.anuar.audiostorage.repository.specification;

import jakarta.persistence.criteria.*;
import kz.anuar.audiostorage.model.FileDto;
import kz.anuar.audiostorage.model.FileEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serial;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
public class FileSpecification implements Specification<FileEntity> {

    @Serial
    private static final long serialVersionUID = 2076826106831858494L;

    private final FileDto fileDto;

    @Override
    public Predicate toPredicate(Root<FileEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (nonNull(fileDto.getFileName())) {
            predicates.add(cb.equal(root.get("fileName"), fileDto.getFileName()));
        }
        if (nonNull(fileDto.getFileDate())) {
            predicates.add(cb.equal(root.get("fileDate"), Timestamp.valueOf(fileDto.getFileDate().toString())));
        }
        if (nonNull(fileDto.getFilePath())) {
            predicates.add(cb.equal(root.get("filePath"), fileDto.getFilePath()));
        }

        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}