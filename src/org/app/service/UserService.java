package org.app.service;

import java.util.List;
import java.util.Map;

import com.fast.annotation.Service;
import com.fast.core.base.BaseModel;
import com.fast.core.base.FastService;

@Service
public class UserService extends FastService {

	public void addRecord(String test) {
		System.out.println("====addRecordMethod Invoke Success=======");
	}

	@Override
	public int save(BaseModel obj) {
		// TODO Auto-generated method stub
		return super.save(obj);
	}

	@Override
	public List<Map<String, Object>> query(BaseModel obj) {
		return super.query(obj);
	}

}
