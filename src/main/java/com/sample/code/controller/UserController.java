/**
 * Implementation of User Controller
 * @author Nandakumar.K
 */
package com.sample.code.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sample.code.entity.User;
import com.sample.code.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
	
   @Autowired
   UserService userService;

   @PostMapping
   public ResponseEntity<User> addUser(@RequestBody User user) { 
	return new ResponseEntity<>(userService.addUser(user),
            HttpStatus.CREATED);
   }
   
   @PostMapping("/all")
   public ResponseEntity<List<User>> addListOfUser(@RequestBody List<User> user) { 
	return new ResponseEntity<>(userService.addListOfUser(user),
            HttpStatus.CREATED);   
   }
   
   @GetMapping("/all")
   public ResponseEntity<List<User>> getAllUsers(
		   @RequestParam (value = "pageNumber", required = false) String pageNumber,
		   @RequestParam (value = "pageSize", required = false) String pageSize,
		   @RequestParam (value = "sortOrder", required = false) String sortOrder,
		   @RequestParam (value = "sortKey", required = false) String sortKey,
		   @RequestParam (value = "keyword", required = false) String keyword) { 
	return new ResponseEntity<>(userService.getAllUsers(pageNumber, pageSize, sortOrder, sortKey, keyword),
            HttpStatus.OK);     
   }
}
