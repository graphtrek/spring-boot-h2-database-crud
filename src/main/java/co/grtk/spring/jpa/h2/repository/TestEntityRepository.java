package co.grtk.spring.jpa.h2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.grtk.spring.jpa.h2.entity.TestEntity;

public interface TestEntityRepository extends JpaRepository<TestEntity, Long> {
  List<TestEntity> findByPublished(boolean published);

  List<TestEntity> findByTitleContaining(String title);
}
