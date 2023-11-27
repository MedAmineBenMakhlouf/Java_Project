package com.artisana.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artisana.models.Role;
import com.artisana.repositories.RoleRepository;

@Service
public class RoleService {
	// === CRUD ===

	@Autowired
	private RoleRepository RoleRepo;

	// READ ALL
	public List<Role> allRoles() {
		return RoleRepo.findAll();
	}

	// READ ONE
	public Role findRole(Long id) {

		Optional<Role> maybeRole = RoleRepo.findById(id);
		if (maybeRole.isPresent()) {
			return maybeRole.get();
		} else {
			return null;
		}
	}

	public Role findByName(String name) {
		return RoleRepo.findByName(name);
	}

	// UPDATE
	public Role updateRole(Role R) {
		return RoleRepo.save(R);
	}

	// DELETE
	public void deleteRole(Long id) {
		RoleRepo.deleteById(id);
	}
}
