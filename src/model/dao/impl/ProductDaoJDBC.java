package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.ProductDao;
import model.entities.Department;
import model.entities.Product;

public class ProductDaoJDBC implements ProductDao{
	
private Connection conn;

	public ProductDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Product product) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"Insert Into product "
					+ "(Brand, Name, Price, Quantity, TotalPrice, DepartmentId)"
					+ "Values "
					+ "(?,?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, product.getBrand());
			st.setString(2, product.getName());
			st.setDouble(3, product.getPrice());
			st.setInt(4, product.getQuantity());
			st.setDouble(5, product.getTotalPrice());
			st.setInt(6, product.getDepartment().getId());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					product.setId(id);
				}
				DB.closeResultSet(rs);
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void updade(Product product) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"Update product "
					+ "Set Brand = ?, Name = ?, Price = ?, Quantity = ?, TotalPrice = ?, DepartmentId = ? "
					+ "Where product.Id = ?");
			
			st.setString(1, product.getBrand());
			st.setString(2, product.getName());
			st.setDouble(3, product.getPrice());
			st.setInt(4, product.getQuantity());
			st.setDouble(5, product.getTotalPrice());
			st.setInt(6, product.getDepartment().getId());
			st.setInt(7, product.getId());
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("Delete From product Where product.Id = ?");
			
			st.setInt(1, id);
			
			st.executeUpdate();			
		}
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Product findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"Select product.*, department.Name as DepName "
					+ "From product Inner Join department "
					+ "On product.DepartmentId = department.Id "
					+ "Where product.Id = ?");
			
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			if (rs.next()){
				Department dep = instantiateDepartment(rs);
				Product product = instantiateProduct(rs, dep);
				return product;
			}
			return null;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}
	
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setName(rs.getString("DepName"));
		dep.setId(rs.getInt("DepartmentId"));
		return dep;
	}
	
	private Product instantiateProduct(ResultSet rs, Department dep) throws SQLException {
		Product product = new Product();
		product.setId(rs.getInt("Id"));
		product.setBrand(rs.getString("Brand"));
		product.setName(rs.getString("Name"));
		product.setPrice(rs.getDouble("Price"));
		product.setQuantity(rs.getInt("Quantity"));
		product.setDepartment(dep);
		return product;
	}

	@Override
	public List<Product> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"Select product.*, department.Name as DepName "
					+ "From product Inner Join department "
					+ "On product.DepartmentId = department.Id "
					+ "Order by Name");
			
			rs = st.executeQuery();
			
			List<Product> allProducts = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			while (rs.next()) {
				Department department = map.get(rs.getInt("DepartmentId"));
				if (department == null) {
					department = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), department);
				}
				Product product = instantiateProduct(rs, department);
				allProducts.add(product);
			}
			return allProducts;			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Product> findByDepartment(Department dep) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"Select product.*, department.Name as DepName "
					+ "From product Inner Join department "
					+ "On product.DepartmentId = department.Id "
					+ "Where product.DepartmentId = ? "
					+ "Order By Name");
			
			st.setInt(1, dep.getId());
			
			rs = st.executeQuery();
			
			List<Product> productsByDep = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			while (rs.next()) {
				Department department = map.get(rs.getInt("DepartmentId"));
				if (department == null) {
					department = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), department);
				}
				Product product = instantiateProduct(rs, department);
				productsByDep.add(product);
			}
			
			if (productsByDep.size() == 0) {
				throw new DbException("No products at this department!");
			}
			
			return productsByDep;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

}
