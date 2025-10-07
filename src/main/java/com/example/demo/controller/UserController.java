package com.example.demo.controller;

import com.example.demo.dto.UserPatchDto;
import com.example.demo.generated.api.UsersApi;
import com.example.demo.generated.dto.UserDto;
import com.example.demo.generated.dto.UserPageDto;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.history.Revision;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController implements UsersApi {

    private final UserService userService;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> saveUser(UserDto userDto) {
        UserDto createdUser = userService.save(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @Override
    public ResponseEntity<UserDto> findUserById(Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<UserPageDto> findUsersByCriteria() {
        List<UserDto> users = userService.findAll();
        return ResponseEntity.ok(
                UserPageDto.builder()
                        .items(users)
                        .number(0)
                        .size(users.size())
                        .count((long) users.size())
                        .build()
        );
    }

    @Override
    public ResponseEntity<UserDto> updateUser(Long id, UserDto userDto) {
        return ResponseEntity.ok(userService.update(id, userDto));
    }

    @Override
    public ResponseEntity<UserDto> patchUser(Long id, UserPatchDto userPatchDto) {
        return ResponseEntity.ok(userService.patch(id, userPatchDto));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Audit endpoints
    @GetMapping("/api/users/{id}/revisions")
    public ResponseEntity<Page<Revision<Integer, UserDto>>> getUserRevisions(
            @PathVariable Long id, Pageable pageable) {
        return userService.findUserRevisions(id, pageable)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/api/users/{id}/revisions/{revisionNumber}")
    public ResponseEntity<Revision<Integer, UserDto>> getUserRevision(
            @PathVariable Long id, @PathVariable Integer revisionNumber) {
        return userService.findUserRevision(id, revisionNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/api/users/{id}/revisions/latest")
    public ResponseEntity<Revision<Integer, UserDto>> getLatestUserRevision(@PathVariable Long id) {
        return userService.findLatestUserRevision(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
