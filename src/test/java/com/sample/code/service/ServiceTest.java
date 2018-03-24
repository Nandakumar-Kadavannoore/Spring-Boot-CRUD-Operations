/**
 * Unit Test for User Service
 * @author Nandakumar.K
 */
package com.sample.code.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.sample.code.entity.User;
import com.sample.code.repository.UserRepository;
import com.sample.code.service.UserService;

@SpringBootTest
public class ServiceTest {

	@InjectMocks
	private UserService userService;
	
	@Mock
	private UserRepository userRepo;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	private static int PAGE_NUMBER = 0;
	private static String SORT_KEY = "emailId";
	private static int PAGE_SIZE = Integer.MAX_VALUE;

	@Test
	public void addUser() {
		User user = getMockUser();
		Mockito.when(userRepo.save(user)).thenReturn(user);
		User userResponse = userService.addUser(user);
		assertEquals(userResponse.getEmailId(), user.getEmailId());
	}
	
	@Test
	public void addListOfUser() {
		List<User> userList = new ArrayList<>();
		userList.add(getMockUser());
		Mockito.when(userRepo.saveAll(userList)).thenReturn(userList);
		List<User> userListResponse = userService.addListOfUser(userList);
		assertEquals(userListResponse.size(), userList.size());
	}
	
	@Test
	public void getAllUsersWithoutKeyword() {
		List<User> userList = new ArrayList<>();
		userList.add(getMockUser());

		Pageable pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE,
				new Sort("asc".equals(null) ? Sort.Direction.ASC : Sort.Direction.DESC, SORT_KEY));

		Mockito.when(userRepo.findAll(pageable)).thenReturn(userList);
		List<User> userListResponse = userService.getAllUsers(Integer.toString(PAGE_NUMBER),
				Integer.toString(PAGE_SIZE), null, SORT_KEY, null);
		assertEquals(userListResponse.size(), userList.size());
	}

	@Test
	public void getAllUsersWithKeyword() {
		List<User> userList = new ArrayList<>();
		userList.add(getMockUser());

		Pageable pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE,
				new Sort("asc".equals(null) ? Sort.Direction.ASC : Sort.Direction.DESC, SORT_KEY));

		Mockito.when(userRepo.findAll(null, pageable)).thenReturn(userList);
		List<User> userListResponse = userService.getAllUsers(Integer.toString(PAGE_NUMBER),
				Integer.toString(PAGE_SIZE), null, SORT_KEY, "new");
		assertEquals(userListResponse.size(), userList.size());
	}

	private User getMockUser() {
		User user = new User();
		user.setEmailId("test@test.com");
		user.setFirstName("Test");
		return user;
	}
	
}
