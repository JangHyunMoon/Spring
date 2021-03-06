package com.kh.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.dao.DomoDao;
import com.kh.spring.model.vo.Dev;

@Service
public class DomoServiceImpl implements DomoService {

	@Autowired
	private DomoDao dao;
	
	@Override
	public void print() {
		System.out.println("Service");
		dao.print();
	}

	// Spring이 알아서 commit rollback 트랜잭션 처리
	@Override
	public int insert(Dev dev) {
		// TODO Auto-generated method stub
		return dao.insert(dev);
	}

	@Override
	public Dev select(int num) {
		return dao.select(num);
	}

	@Override
	public List<Dev> selectList() {
		return dao.selectList();
	}
	
	
}