package com.kafka.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kafka.producer.CustomLocalDateSerializer;

public class Employee {
	
	@JsonProperty("employee_id")
	private String employeeId;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("birth_date")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	private LocalDate birthdate;
	

	public Employee(String employeeId, String name, LocalDate birthdate) {
		super();
		this.employeeId = employeeId;
		this.name = name;
		this.birthdate = birthdate;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", name=" + name + ", birthdate=" + birthdate + "]";
	}
	
}
