package beys_dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import beys_dao.DAO;
import model.Beys;
import service.BeysService;


public class beyDAO extends DAO {	
	public beyDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	
	public boolean insert(Beys beys) {
		BeysService serve = new BeysService();
		boolean status = false;
		try {
			String sql = "INSERT INTO beys (id, nome, tipo, descricao, preco) "
		               + "VALUES ("+ serve.FazerId() +", '" + beys.getNome() + "', '"
		               + beys.getTipo() + "', '" + beys.getDescricao() + "', " + beys.getPreco() + ");";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public Beys get(int id) {
		Beys beys = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM beys WHERE id="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 beys = new Beys(rs.getInt("id"), rs.getString("nome"), rs.getString("tipo"), 
	        			               rs.getString("descricao"),
	        			               (float)rs.getDouble("preco"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return beys;
	}
	
	
	public List<Beys> get() {
		return get("");
	}

	
	public List<Beys> getOrderByID() {
		return get("id");		
	}
	
	
	public List<Beys> getOrderByDescricao() {
		return get("descricao");		
	}
	
	
	public List<Beys> getOrderByPreco() {
		return get("preco");		
	}
	
	public List<Beys> getOrderByNome() {
		return get("nome");		
	}
	
	public List<Beys> getOrderByTipo() {
		return get("tipo");		
	}
	
	
	private List<Beys> get(String orderBy) {
		List<Beys> beyss = new ArrayList<Beys>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM beys" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Beys p = new Beys(rs.getInt("id"), rs.getString("nome"), rs.getString("tipo"), 
	        				rs.getString("descricao"),
	        				(float)rs.getDouble("preco"));
	            beyss.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return beyss;
	}
	
	
	public boolean update(Beys beys) {
		boolean status = false;
		try {  
			String sql = "UPDATE beys SET descricao = '" + beys.getDescricao() + "', "
					   + "preco = " + beys.getPreco() + ", " 
					   + "nome = '"+ beys.getNome() + "', " 
					   + "tipo = '" + beys.getTipo() + "' WHERE id = " + beys.getID();
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean delete(int id) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM beys WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
}