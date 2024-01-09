package com.sondev.authservice.controller;

import com.sondev.authservice.dto.request.UserRequest;
import com.sondev.authservice.service.UserService;
import com.sondev.common.response.ResponseMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RequestMapping("/users")
@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/current")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ResponseMessage> getCurrentUser(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok().body(new ResponseMessage("OK", "Get current user successful !!!", userService.getCurrentUser(token)) );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ResponseMessage> getById(@PathVariable String id){
        return ResponseEntity.ok().body(new ResponseMessage("OK", "Get user successful !!!", userService.getUserById(id)) );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<ResponseMessage> updateUserInfo(@RequestBody Map<String, Object> fields, @PathVariable String id){
        return ResponseEntity.ok().body(new ResponseMessage("OK", "Update user information successful !!!", userService.updateUser(fields,id)) );
    }

    @PutMapping("/avatar/{id}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<ResponseMessage> updateUserAvatar(@RequestParam MultipartFile file, @PathVariable String id){
        return ResponseEntity.ok().body(new ResponseMessage("OK", "Update user avatar successful !!!", userService.updateAvatar(file,id)) );
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseMessage> getUserWithCondition(@RequestParam(name = "searchText") String searchText,
                                                                @RequestParam(name = "offset") Integer offset,
                                                                @RequestParam(name = "pageSize") Integer pageSize,
                                                                @RequestParam(name = "sortStr") String sortStr){
        return ResponseEntity.ok().body(new ResponseMessage("OK", "Get users successful !!!", userService.getUsers(searchText, offset, pageSize, sortStr)) );
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseMessage> getAllUsers(){
        return ResponseEntity.ok().body(new ResponseMessage("OK", "Get all users successful !!!", userService.getAllUser()) );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseMessage> deleteUser(@PathVariable String id){
        return ResponseEntity.ok().body(new ResponseMessage("OK", "Delete user successful !!!", userService.deleteUser(id)) );
    }
}
