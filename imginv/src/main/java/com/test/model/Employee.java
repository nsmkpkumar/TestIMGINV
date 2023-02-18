package com.test.model;

import java.util.Arrays;
import java.util.Date;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;



@Entity
@Table(name = "Employee")
public class Employee {
	
	@Id
	@Column(name="employee_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long empId;
	
	@Column(name="first_name")
	@Size(min = 3, max = 50)
	@NotBlank(message = "First Name is mandatory")
	   private String firstName;
	
	@Column(name="last_name")
	@Size(min = 1, max = 50)
	@NotBlank(message = "Last Name is mandatory")
	   private String lastName;
	
	@Column(name="email")
	@Email(message="Please enter a Valid mail ID")
	@NotBlank(message = "Email is mandatory")
	   private String email;
	
	@Column(name="phone_number")
	@NotNull
	@NotBlank(message = "Phone Number is mandatory")
	   private String[] phoneNumber;
	
	@Column(name="doj")
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	@NotBlank(message = "DOJ is mandatory")
	private Date doj;
	
	@Column(name="salary")
	@NotNull(message= "Salary positive number value is required")
	@Min(value=0, message="Positive number, min 0 is required")
//	@NotEmpty(message="Salary should not be Empty")
	private double salary;


	
	private double yearSalary;
	
	public double getYearSalary() {
		return yearSalary;
	}

	public void setYearSalary(double yearSalary) {
		this.yearSalary = yearSalary;
	}

	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", phoneNumber=" + Arrays.toString(phoneNumber) + ", doj=" + doj + ", salary=" + salary
				+ ", yearSalary=" + yearSalary + ", tax=" + tax + ", cess=" + cess + "]";
	}

	private double tax;
	private double cess;
	public double getCess() {
		return cess;
	}

	public void setCess(double cess) {
		this.cess = cess;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public long getEmpId() {
		return empId;
	}

	public void setEmpId(long empId) {
		this.empId = empId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String[] getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String[] phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getDoj() {
		return doj;
	}

	public void setDoj(Date doj) {
		this.doj = doj;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	
}
