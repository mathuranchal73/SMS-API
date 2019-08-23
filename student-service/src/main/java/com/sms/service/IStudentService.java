package com.sms.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sms.model.Student;

public interface IStudentService {

	 List<Student> readStudentsFromCSV(MultipartFile File) throws IOException;
}
