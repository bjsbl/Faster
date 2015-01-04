package org.app.service;

import com.fast.annotation.Service;
import com.fast.annotation.Transactional;
import com.fast.core.db.tx.Transaction;

@Service
public class BaseService {

	@Transactional(Transaction.TRANSACTION_REPEATABLE_READ)
	public void doSomeThings() {
		System.out.println("====Do SomeThings====");
	}
}
