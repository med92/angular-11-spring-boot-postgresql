package com.cagip.tools.mscontact.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "namespace")
public class Namespace {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "label")
	private String label;

	@Column(name = "balGenerique")
	private String balGenerique;

	@Column(name = "microservice")
	private String microService;

	@Column(name = "tribu")
	private String tribu;

}
