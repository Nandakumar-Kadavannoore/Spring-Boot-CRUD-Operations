/**
 * User Repository Definition
 * @author Nandakumar.K
 */
package com.sample.code.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sample.code.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

	List<User> findAll(Pageable pageable);
	
	List<User> findAll(Specification<User> specification, Pageable pageRequest);

}
