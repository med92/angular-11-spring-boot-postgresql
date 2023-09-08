package com.cagip.tools.mscontact.repository;

import com.cagip.tools.mscontact.model.Namespace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NamespaceRepository extends JpaRepository<Namespace, Long> {
  List<Namespace> findByLabelContaining(String label);
}
