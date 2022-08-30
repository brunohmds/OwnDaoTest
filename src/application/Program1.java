package application;

import java.util.ArrayList;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program1 {

	public static void main(String[] args) {

		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		System.out.println("Test 1: Find by Id");
		
		Department dep = new Department();
		dep = departmentDao.findById(2);
		
		System.out.println(dep);
		
		System.out.println("\nTest 2: FindAll");
		
		List<Department> allDeps = new ArrayList<>();
		allDeps = departmentDao.findAll();
		
		for (Department d : allDeps) {
			System.out.println(d);
		}
		
		/*
		System.out.println("\nTest 3: Insert");
		Department dep1 = new Department("Music", null);
		departmentDao.insert(dep1);
		System.out.println("New department inserted! New Id is: " + dep1.getId());
		
		
		System.out.println("\nTest 4: Update department");
		dep.setName("Video Games");
		departmentDao.update(dep);
		System.out.println("Department info updated!");
		*/
		
		/*
		System.out.println("\nTest 5: Delete department");
		departmentDao.deleteById(2);
		System.out.println("Department deleted!");
		*/
	}

}
