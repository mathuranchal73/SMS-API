package com.sms.redis;

import com.sms.model.Student;

public interface StudentCacheManager {

	void cacheStudentDetails(Student students);

	boolean checkEmpty();

}
