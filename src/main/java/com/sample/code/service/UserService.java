/**
 * Service Implementation of User
 * @author Nandakumar.K
 */
package com.sample.code.service;

import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sample.code.entity.User;
import com.sample.code.repository.UserRepository;

@Service
@CacheConfig(cacheNames = "users")
public class UserService {
	
	@Autowired
	UserRepository userRepo;

	public User addUser(User user) {
		 return userRepo.save(user);
	}

	public List<User> addListOfUser(List<User> user) {
		return (List<User>) userRepo.saveAll(user);	
	}
	
	@Cacheable()
	public List<User> getAllUsers(String pgeNumber, String pgeSize, String sortOrder, String sortKey, String keyword) {
		int pageNumber = StringUtils.isEmpty(pgeNumber) ? 0 : Integer.parseInt(pgeNumber);
		int pageSize = StringUtils.isEmpty(pgeSize) ? Integer.MAX_VALUE : Integer.parseInt(pgeSize);
		sortKey = StringUtils.isEmpty(sortKey) ? "id" : sortKey;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize,
				new Sort("asc".equals(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC, sortKey));

		return getResultbasedOnRequest(pageable, keyword);
	}

	private List<User> getResultbasedOnRequest(Pageable pageable, String keyword) {
		if (StringUtils.isEmpty(keyword)) {
			return userRepo.findAll(pageable);
		}
		// else Search by keyword
		return userRepo.findAll(containsText(keyword), pageable);
	}

	/**
	 * Used to Search from database based on keyword as query parameter
	 * @param keyword
	 * @return Specification<User>
	 */
	private Specification<User> containsText(String keyword) {
		return (root, query, builder) -> {
			String finalText = keyword.toLowerCase();
			if (!finalText.contains("%")) {
				finalText = "%" + finalText + "%";
			}
			Predicate firstNameExp = builder.like(builder.lower(root.get("firstName")), finalText);

			return builder.or(firstNameExp);
		};
	}

}
