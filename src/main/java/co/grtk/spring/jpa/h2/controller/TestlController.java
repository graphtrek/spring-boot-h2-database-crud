package co.grtk.spring.jpa.h2.controller;

import co.grtk.spring.jpa.h2.entity.TestEntity;
import co.grtk.spring.jpa.h2.repository.TestEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TestlController {

	private final TestEntityRepository testEntityRepository;

	@GetMapping("/test")
	public ResponseEntity<List<TestEntity>> getAllTestEntity(@RequestParam(required = false) String title) {
		try {
			List<TestEntity> testEntities;
			testEntities = new ArrayList<TestEntity>();

			if (title == null)
				testEntities.addAll(testEntityRepository.findAll());
			else
				testEntities.addAll(testEntityRepository.findByTitleContaining(title));

			if (testEntities.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(testEntities, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/test/{id}")
	public ResponseEntity<TestEntity> getTestEntityById(@PathVariable("id") long id) {
		Optional<TestEntity> tutorialData = testEntityRepository.findById(id);

		if (tutorialData.isPresent()) {
			return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/test")
	public ResponseEntity<TestEntity> createTestEntity(@RequestBody TestEntity testEntity) {
		try {
			TestEntity testEntity1 = TestEntity.builder()
					.title(testEntity.getTitle())
					.description(testEntity.getDescription())
					.published(testEntity.isPublished())
					.build();
			TestEntity _testEntity = testEntityRepository.save(testEntity1);
			return new ResponseEntity<>(_testEntity, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/test/{id}")
	public ResponseEntity<TestEntity> updateTestEntity(@PathVariable("id") long id, @RequestBody TestEntity testEntity) {
		Optional<TestEntity> tutorialData = testEntityRepository.findById(id);

		if (tutorialData.isPresent()) {
			TestEntity _testEntity = tutorialData.get();
			_testEntity.setTitle(testEntity.getTitle());
			_testEntity.setDescription(testEntity.getDescription());
			_testEntity.setPublished(testEntity.isPublished());
			return new ResponseEntity<>(testEntityRepository.save(_testEntity), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/test/{id}")
	public ResponseEntity<HttpStatus> deleteTestEntity(@PathVariable("id") long id) {
		try {
			testEntityRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/test")
	public ResponseEntity<HttpStatus> deleteAllTestEntity() {
		try {
			testEntityRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/test/published")
	public ResponseEntity<List<TestEntity>> findByPublished() {
		try {
			List<TestEntity> testEntities = testEntityRepository.findByPublished(true);

			if (testEntities.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(testEntities, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
