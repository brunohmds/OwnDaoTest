package application;

import java.util.ArrayList;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.ProductDao;
import model.entities.Department;
import model.entities.Product;

public class Program2 {

	public static void main(String[] args) {

		ProductDao productDao = DaoFactory.createProductDao();
		
		System.out.println("Test 1: findById");
		
		Product product = productDao.findById(3);
		
		System.out.println(product);
		
		System.out.println("\nTest 2: findByDepartment");
		Department dep = new Department(null, 5);
		
		List<Product> productsByDep = new ArrayList<>();
		productsByDep = productDao.findByDepartment(dep);
		
		for (Product p : productsByDep) {
			System.out.println(p);
		}
		
		System.out.println("\nTest 3: findAll");
		List<Product> allProducts = productDao.findAll();
		
		for (Product p : allProducts) {
			System.out.println(p);
		}
		
		/**
		System.out.println("\nTest 4: Insert");
		Product newItem = new Product(null, "Dell", "Alienware", 10000.00, 2, dep);
		
		Product newItem2 = new Product(null, "Apple", "iPhone 12", 4500.00, 8, dep);
		
		productDao.insert(newItem2);
		
		
		System.out.println("\nTest 5: Update product");
		
		Product updatedItem = productDao.findById(4);
		
		updatedItem.setQuantity(7);
		updatedItem.setPrice(6139.0);
		
		productDao.updade(updatedItem);
		
		
		System.out.println("\nTest 6: Delete product");
		
		productDao.deleteById(7);
		**/		
	}
}
