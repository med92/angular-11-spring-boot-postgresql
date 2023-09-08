package com.cagip.tools.mscontact.controller;

import com.cagip.tools.mscontact.model.Namespace;
import com.cagip.tools.mscontact.repository.NamespaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class NamespacesController {

	@Autowired
	NamespaceRepository namespaceRepository;

	@GetMapping("/namespaces")
	public ResponseEntity<List<Namespace>> getAllNamespaces(@RequestParam(required = false) String label) {
		try {
			List<Namespace> Namespaces = new ArrayList<Namespace>();

			if (label == null)
				namespaceRepository.findAll().forEach(Namespaces::add);
			else
				namespaceRepository.findByLabelContaining(label).forEach(Namespaces::add);

			if (Namespaces.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(Namespaces, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/namespaces/{id}")
	public ResponseEntity<Namespace> getNamespaceById(@PathVariable("id") long id) {
		Optional<Namespace> NamespaceData = namespaceRepository.findById(id);

		if (NamespaceData.isPresent()) {
			return new ResponseEntity<>(NamespaceData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/namespaces")
	public ResponseEntity<Namespace> createNamespace(@RequestBody Namespace namespace) {
		try {
			Namespace _Namespace = namespaceRepository
					.save(namespace);
			return new ResponseEntity<>(_Namespace, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/namespaces/{id}")
	public ResponseEntity<Namespace> updateNamespace(@PathVariable("id") long id, @RequestBody Namespace Namespace) {
		Optional<Namespace> NamespaceData = namespaceRepository.findById(id);

		if (NamespaceData.isPresent()) {
			Namespace _Namespace = NamespaceData.get();
			_Namespace.setLabel(Namespace.getLabel());
			_Namespace.setBalGenerique(Namespace.getBalGenerique());
			return new ResponseEntity<>(namespaceRepository.save(_Namespace), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/namespaces/{id}")
	public ResponseEntity<HttpStatus> deleteNamespace(@PathVariable("id") long id) {
		try {
			namespaceRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/namespaces")
	public ResponseEntity<HttpStatus> deleteAllNamespaces() {
		try {
			namespaceRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}


	@PostMapping("/namespaces/published/batch/create")
	public ResponseEntity<List<Namespace>> createAllNamespacesBatch(@RequestBody List<Namespace> namespaces) {
		try {
			List<Namespace> savedNamespaces = namespaceRepository.saveAll(namespaces);
			return new ResponseEntity<>(savedNamespaces, HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
