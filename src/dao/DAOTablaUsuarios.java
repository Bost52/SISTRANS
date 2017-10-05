package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Usuario;

public class DAOTablaUsuarios {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAORestaurantes
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaUsuarios() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Metodo que cierra todos los recursos que estan enel arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	/**
	 * Metodo que inicializa la connection del DAO a la base de datos con la conexión que entra como parametro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}
	
	public ArrayList<Usuario> darUsuarios() throws SQLException, Exception {
	ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

	String sql = "SELECT * FROM USUARIO";

	PreparedStatement prepStmt = conn.prepareStatement(sql);
	recursos.add(prepStmt);
	ResultSet rs = prepStmt.executeQuery();

	while (rs.next()) {
		String nombre = rs.getString("NOMBRE");
		int cedula = rs.getInt("CEDULA");
		String email = rs.getString("EMAIL");
		String rol = rs.getString("ROL");
		usuarios.add(new Usuario(cedula, nombre, email, rol));
	}
	return usuarios;
	}
	
	public Usuario buscarUsuarioPorCedula(int cedula) throws SQLException, Exception 
	{
		Usuario usuario = null;

		String sql = "SELECT * FROM USUARIO WHERE CEDULA =" + cedula;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			String nombre = rs.getString("NOMBRE");
			int id = rs.getInt("CEDULA");
			String email = rs.getString("EMAIL");
			String rol = rs.getString("ROL");
			
			usuario = new Usuario(id, nombre, email, rol);
		}

		return usuario;
	}
	
	
	public void addUsuario(Usuario usuario) throws SQLException, Exception {

		String sql = "insert into USUARIO (CEDULA, ROL, EMAIL, NOMBRE) values ("+usuario.getCedula()+", '"+usuario.getRol()+"', '"+usuario.getEmail()+"', '"+usuario.getNombre()+"')";
		System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	public void addPreferencia(int id,int cedula, int idCategoria, double maximo, double minimo, int idZona) throws SQLException
	{
		String sql = "insert into PREFERENCIAS (IDPREFERENCIA, CEDULA, IDCATEGORIA, MAXIMO, MINIMO,IDZONA) values ("+id+","+cedula+", "+idCategoria+", "+maximo+", "+minimo+", "+idZona+")";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void updatePreferencia(int id,int cedula, int idCategoria, double maximo, double minimo, int idZona) throws SQLException,Exception
	{
		String sql = "update PREFERENCIAS set IDCATEGORIA="+idCategoria+", MAXIMO="+maximo+", MINIMO="+minimo+",IDZONA="+idZona+" WHERE IDPREFERENCIA="+id;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void deletePreferencia(int id) throws SQLException,Exception
	{
		String sql = "delete from PREFERENCIAS  WHERE IDPREFERENCIA="+id;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
