package com.sms.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.sms.config.RedisUtil;
import com.sms.model.Student;

@Configuration
public class StudentCacheManagerImpl implements StudentCacheManager {
      public static final String TABLE_STUDENT = "TABLE_STUDENT";
      public static final String STUDENT = "STUDENT_";
      private RedisUtil<Student> redisUtilStudent;
      @Autowired
      public StudentCacheManagerImpl(RedisUtil<Student> redisUtilStudent) {
          this.redisUtilStudent = redisUtilStudent;
      }
      @Override
      public void cacheStudentDetails(Student students){
          redisUtilStudent.putMap(TABLE_STUDENT,STUDENT+students.getId(),students);
          redisUtilStudent.setExpire(TABLE_STUDENT,1,TimeUnit.DAYS);
      }
	@Override
	public boolean checkEmpty() {
		if(redisUtilStudent.equals(null))
		{
			return true;
		}
		return false;
	}
}