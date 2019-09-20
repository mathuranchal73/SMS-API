package com.sms.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

import com.sms.model.audit.UserDateAudit;

@Entity
@Table(name="student", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "registrationNo"
            }),
            @UniqueConstraint(columnNames = {
                "rollNo"
            }),
            @UniqueConstraint(columnNames = {
                    "uuid"
                })
    })
@SequenceGenerator(name="seq", initialValue=2000, allocationSize=100)
public class Student extends UserDateAudit {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private String doa;
	
	private String registrationNo;
	
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
	private String rollNo;
	
	private String academicSessions;
	
	private boolean enabled;
	
	@NaturalId
    @Size(max = 40)
    @Email
    private String studentEmail;
	
	@NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
    private String parentEmail;
	
	@NotBlank
    @Size(max = 100)
    private String uuid;
	
	
	

	public Student() {
		
	}

	public Student(String firstName, String lastName, String doa, 
			String academicSessions,@Size(max = 40) @Email String studentEmail,
			@NotBlank @Size(max = 40) @Email String parentEmail, @NotBlank @Size(max = 100) String uuid, Boolean enabled) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.doa = doa;
		this.academicSessions = academicSessions;
		this.studentEmail = studentEmail;
		this.parentEmail = parentEmail;
		this.uuid = uuid;
		this.enabled=enabled;
	}

	
	public Student(String firstName, String lastName, String registrationNo,
			@Size(max = 40) @Email String studentEmail, @NotBlank @Size(max = 40) @Email String parentEmail) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.registrationNo = registrationNo;
		this.studentEmail = studentEmail;
		this.parentEmail = parentEmail;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getDoa() {
		return doa;
	}

	public void setDoa(String doa) {
		this.doa = doa;
	}

	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String string) {
		this.rollNo = string;
	}

	public String getAcademicSessions() {
		return academicSessions;
	}

	public void setAcademicSessions(String academicSessions) {
		this.academicSessions = academicSessions;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getStudentEmail() {
		return studentEmail;
	}

	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}

	public String getParentEmail() {
		return parentEmail;
	}

	public void setParentEmail(String parentEmail) {
		this.parentEmail = parentEmail;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}	

}
