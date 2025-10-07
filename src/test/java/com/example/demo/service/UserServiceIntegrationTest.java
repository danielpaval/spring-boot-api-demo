package com.example.demo.service;

import com.example.demo.TestcontainersConfiguration;
import com.example.demo.entity.User;
import com.example.demo.generated.dto.UserDto;
import com.example.demo.repository.UserRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.history.Revision;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//@Import({TestcontainersConfiguration.class, TestSecurityConfig.class})
@Import({TestcontainersConfiguration.class})
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        // Clean up database before each test
        userRepository.deleteAll();
    }

    @Test
    void save_withValidUser_shouldPersistToDatabase() {
        // Given
        UserDto userDto = new UserDto();
        userDto.setName("John Doe");

        // When
        UserDto savedUser = userService.save(userDto);

        // Then
        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertEquals("John Doe", savedUser.getName());
        assertFalse(savedUser.getDeleted());

        // Verify persistence
        Optional<User> userInDb = userRepository.findById(savedUser.getId());
        assertTrue(userInDb.isPresent());
        assertEquals("John Doe", userInDb.get().getName());
    }

    @Test
    void save_withNullName_shouldThrowConstraintViolationException() {
        // Given
        UserDto userDto = new UserDto();
        userDto.setName(null);

        // When & Then
        assertThrows(ConstraintViolationException.class, () -> userService.save(userDto));

        // Verify nothing was persisted
        assertEquals(0, userRepository.count());
    }

    @Test
    void save_withEmptyName_shouldThrowConstraintViolationException() {
        // Given
        UserDto userDto = new UserDto();
        userDto.setName("");

        // When & Then
        assertThrows(ConstraintViolationException.class, () -> userService.save(userDto));

        // Verify nothing was persisted
        assertEquals(0, userRepository.count());
    }

    @Test
    void save_withBlankName_shouldThrowConstraintViolationException() {
        // Given
        UserDto userDto = new UserDto();
        userDto.setName("   ");

        // When & Then
        assertThrows(ConstraintViolationException.class, () -> userService.save(userDto));

        // Verify nothing was persisted
        assertEquals(0, userRepository.count());
    }

    @Test
    void findById_withExistingUser_shouldReturnUser() {
        // Given
        UserDto userDto = new UserDto();
        userDto.setName("Jane Smith");
        UserDto savedUser = userService.save(userDto);

        // When
        Optional<UserDto> foundUser = userService.findById(savedUser.getId());

        // Then
        assertTrue(foundUser.isPresent());
        assertEquals(savedUser.getId(), foundUser.get().getId());
        assertEquals("Jane Smith", foundUser.get().getName());
    }

    @Test
    void findById_withNonExistingUser_shouldReturnEmpty() {
        // When
        Optional<UserDto> foundUser = userService.findById(999L);

        // Then
        assertFalse(foundUser.isPresent());
    }

    @Test
    void findAll_shouldReturnAllUsers() {
        // Given
        UserDto user1 = new UserDto();
        user1.setName("User One");
        userService.save(user1);

        UserDto user2 = new UserDto();
        user2.setName("User Two");
        userService.save(user2);

        UserDto user3 = new UserDto();
        user3.setName("User Three");
        userService.save(user3);

        // When
        List<UserDto> allUsers = userService.findAll();

        // Then
        assertThat(allUsers).hasSize(3);
        assertThat(allUsers)
            .extracting(UserDto::getName)
            .containsExactlyInAnyOrder("User One", "User Two", "User Three");
    }

    @Test
    void update_withValidData_shouldUpdateUser() {
        // Given
        UserDto userDto = new UserDto();
        userDto.setName("Original Name");
        UserDto savedUser = userService.save(userDto);

        // When
        savedUser.setName("Updated Name");
        UserDto updatedUser = userService.update(savedUser.getId(), savedUser);

        // Then
        assertNotNull(updatedUser);
        assertEquals(savedUser.getId(), updatedUser.getId());
        assertEquals("Updated Name", updatedUser.getName());

        // Verify in database
        Optional<User> userInDb = userRepository.findById(savedUser.getId());
        assertTrue(userInDb.isPresent());
        assertEquals("Updated Name", userInDb.get().getName());
    }

    @Test
    void delete_shouldMarkUserAsDeleted() {
        // Given
        UserDto userDto = new UserDto();
        userDto.setName("To Be Deleted");
        UserDto savedUser = userService.save(userDto);

        // When
        userService.deleteById(savedUser.getId());

        // Then
        Optional<User> userInDb = userRepository.findById(savedUser.getId());
        assertTrue(userInDb.isPresent());
        assertTrue(userInDb.get().isDeleted());
    }

    @Test
    void save_multipleUsers_shouldMaintainDataIntegrity() {
        // Given & When
        UserDto user1 = new UserDto();
        user1.setName("Alice");
        UserDto savedUser1 = userService.save(user1);

        UserDto user2 = new UserDto();
        user2.setName("Bob");
        UserDto savedUser2 = userService.save(user2);

        UserDto user3 = new UserDto();
        user3.setName("Charlie");
        UserDto savedUser3 = userService.save(user3);

        // Then
        assertThat(savedUser1.getId()).isNotEqualTo(savedUser2.getId());
        assertThat(savedUser2.getId()).isNotEqualTo(savedUser3.getId());
        assertThat(savedUser1.getId()).isNotEqualTo(savedUser3.getId());

        List<UserDto> allUsers = userService.findAll();
        assertThat(allUsers).hasSize(3);
    }

    @Test
    void findUserRevisions_shouldTrackUserChanges() {
        // Given
        UserDto userDto = new UserDto();
        userDto.setName("Initial Name");
        UserDto savedUser = userService.save(userDto);

        // Make an update to create a new revision
        savedUser.setName("Updated Name");
        userService.update(savedUser.getId(), savedUser);

        // When
        Optional<Page<Revision<Integer, UserDto>>> revisions =
            userService.findUserRevisions(savedUser.getId(), PageRequest.of(0, 10));

        // Then
        assertTrue(revisions.isPresent());
        assertThat(revisions.get().getContent()).isNotEmpty();
        assertThat(revisions.get().getTotalElements()).isGreaterThanOrEqualTo(2);
    }

    @Test
    void findLatestUserRevision_shouldReturnMostRecentVersion() {
        // Given
        UserDto userDto = new UserDto();
        userDto.setName("Version 1");
        UserDto savedUser = userService.save(userDto);

        savedUser.setName("Version 2");
        userService.update(savedUser.getId(), savedUser);

        // When
        Optional<Revision<Integer, UserDto>> latestRevision =
            userService.findLatestUserRevision(savedUser.getId());

        // Then
        assertTrue(latestRevision.isPresent());
        assertEquals("Version 2", latestRevision.get().getEntity().getName());
    }

    @Test
    void findUserRevisions_withNonExistentUser_shouldReturnEmpty() {
        // When
        Optional<Page<Revision<Integer, UserDto>>> revisions =
            userService.findUserRevisions(999L, PageRequest.of(0, 10));

        // Then
        assertFalse(revisions.isPresent());
    }

    @Test
    void updateAndRetrieve_shouldMaintainConsistency() {
        // Given
        UserDto userDto = new UserDto();
        userDto.setName("Test User");
        UserDto savedUser = userService.save(userDto);
        Long userId = savedUser.getId();

        // When - Multiple updates
        savedUser.setName("First Update");
        userService.update(userId, savedUser);

        savedUser.setName("Second Update");
        userService.update(userId, savedUser);

        savedUser.setName("Final Update");
        UserDto finalUser = userService.update(userId, savedUser);

        // Then
        Optional<UserDto> retrievedUser = userService.findById(userId);
        assertTrue(retrievedUser.isPresent());
        assertEquals("Final Update", retrievedUser.get().getName());
        assertEquals("Final Update", finalUser.getName());
        assertEquals(userId, retrievedUser.get().getId());
    }
}
