package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

import rest.UsuarioService;
import vos.Pedido;
import vos.PedidoMesa;
import vos.PedidoProducto;
import vos.Restaurante;
import vos.ServirPedidoProducto;
import vos.TipoDeComida;
import vos.Usuario;
import vos.Zona;

public class DAOTablaPedido {

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
	public DAOTablaPedido() {
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

	public ArrayList<Pedido> darPedidos() throws SQLException, Exception {

		DAOTablaUsuarios daoUsuario= new DAOTablaUsuarios();
		daoUsuario.setConn(conn);
		ArrayList<Pedido> pedidos = new ArrayList<Pedido>();

		String sql = "SELECT * FROM PEDIDO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int idPago = rs.getInt("IDPAGO");
			int id = rs.getInt("IDPEDIDO");
			Date fecha = rs.getDate("FECHAYHORA");
			Usuario cliente = daoUsuario.buscarUsuarioPorCedula((rs.getInt("CEDULA")));
			String servido=rs.getString("SERVIDO");

			pedidos.add(new Pedido(null,fecha.toString(), id, idPago, cliente,servido));
		}
		return pedidos;
	}
	public void addPedido(Pedido pedido, double totalValue) throws SQLException, Exception {

		String sql = "insert into PEDIDO (IDPEDIDO, FECHAYHORA, PRECIOPEDIDO) values ("+pedido.getIdPedido()+",SYSDATE, "+totalValue+")";

		if(pedido.getCliente()!=null)
		{
			sql = "insert into PEDIDO (IDPEDIDO, FECHAYHORA, CEDULA, PRECIOPEDIDO) values ("+pedido.getIdPedido()+",SYSDATE , "+pedido.getCliente().getCedula()+", "+totalValue+")";
		}
		if(pedido.getIdMesa()>0)
		{
			sql = "insert into PEDIDO (IDPEDIDO, FECHAYHORA, PRECIOPEDIDO, IDMESA) values ("+pedido.getIdPedido()+",SYSDATE, "+totalValue+", "+pedido.getIdMesa()+")";
		}
		if(pedido.getCliente()!=null && pedido.getIdMesa()>0)
		{
			sql = "insert into PEDIDO (IDPEDIDO, FECHAYHORA, CEDULA, PRECIOPEDIDO, IDMESA) values ("+pedido.getIdPedido()+",SYSDATE , "+pedido.getCliente().getCedula()+", "+totalValue+", "+pedido.getIdMesa()+")";
		}


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public void addProductoPedido(PedidoProducto pedido) throws SQLException, Exception {

		String sql = "insert into PEDIDOPRODUCTO (IDPEDIDO, LOCAL, IDPRODUCTO) values ("+pedido.getIdPedido()+", "+pedido.getLocal()+", "+pedido.getIdProducto()+")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

		for(int i=0;i<pedido.getEquivalencias().size();i++) {
			String sql2 = "insert into PEDIDO_EQUIVALENCIAPRODUCTO (IDPEDIDO, IDPROD1, IDPROD2, IDMENU) values ("+pedido.getIdPedido()+", "+pedido.getIdProducto()+", "+pedido.getEquivalencias().get(i)+", 0)";
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			prepStmt2.executeQuery();
		}
	}

	public void addMenuPedido(PedidoProducto pedido) throws SQLException, Exception {

		String sql = "insert into PEDIDOMENU (IDPEDIDO, IDMENU) values ("+pedido.getIdPedido()+", "+pedido.getIdMenu()+")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public void pedidoServidoProducto(ServirPedidoProducto pedido) throws Exception
	{

		String sql = "SELECT LOCAL FROM PEDIDOPRODUCTO WHERE IDPEDIDO="+pedido.getIdPedido()+" AND IDPRODUCTO=" +pedido.getIdProducto();

		PreparedStatement prepStmt= conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs=prepStmt.executeQuery();

		int local = 0;
		if(rs.next())
		{
			local=rs.getInt("LOCAL");
		}
		else {
			throw new NoSuchElementException("Este pedido no contiene el producto dado");
		}

		String sql2 = "UPDATE OFRECEPRODUCTO SET CANTIDAD=CANTIDAD-1 WHERE LOCAL="+local+" AND IDPRODUCTO="+pedido.getIdProducto();

		PreparedStatement prepStmt2= conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		prepStmt2.executeQuery();



		String sql3 = "UPDATE PEDIDOPRODUCTO SET SERVIDO='T' WHERE IDPEDIDO="+pedido.getIdPedido()+" AND IDPRODUCTO="+pedido.getIdProducto();

		PreparedStatement prepStmt3= conn.prepareStatement(sql3);
		recursos.add(prepStmt3);
		prepStmt3.executeQuery();
	}

	public void pedidoServidoMenu(ServirPedidoProducto pedido) throws Exception
	{

		String sql2 = "UPDATE MENU SET CANTIDAD=CANTIDAD-1 WHERE IDMENU="+pedido.getIdMenu();

		PreparedStatement prepStmt2= conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		prepStmt2.executeQuery();



		String sql3 = "UPDATE PEDIDOMENU SET SERVIDO='T' WHERE IDPEDIDO="+pedido.getIdPedido()+" AND IDMENU="+pedido.getIdMenu();

		PreparedStatement prepStmt3= conn.prepareStatement(sql3);
		recursos.add(prepStmt3);
		prepStmt3.executeQuery();
	}

	public Pedido buscarPedidoById(int id) throws SQLException, Exception 
	{
		DAOTablaUsuarios daoUsuario= new DAOTablaUsuarios();
		daoUsuario.setConn(conn);
		Pedido usuario = null;

		String sql = "SELECT * FROM PEDIDO WHERE IDPEDIDO =" + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			int idPedido = rs.getInt("IDPEDIDO");
			int idPago = rs.getInt("IDPAGO");
			Usuario cliente=daoUsuario.buscarUsuarioPorCedula(rs.getInt("CEDULA"));
			String servido= rs.getString("SERVIDO");
			String date = rs.getString("FECHAYHORA");


			usuario = new Pedido(null,date, idPedido, idPago, cliente, servido);
		}

		return usuario;
	}

	public void pedidoServido(Pedido pedido) throws Exception
	{
		DAOTablaUsuarios daoUsuario= new DAOTablaUsuarios();
		daoUsuario.setConn(conn);

		String sql = "SELECT IDMENU FROM PEDIDOMENU P1 WHERE IDPEDIDO="+pedido.getIdPedido();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			ServirPedidoProducto ped =new ServirPedidoProducto(pedido.getIdPedido(), 0, rs.getInt("IDMENU"));
			pedidoServidoMenu(ped);
		}

		String sql2 = "SELECT IDPRODUCTO FROM PEDIDOPRODUCTO P1 WHERE IDPEDIDO="+pedido.getIdPedido();

		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		ResultSet rs2 = prepStmt2.executeQuery();

		while (rs2.next()) {
			ServirPedidoProducto ped =new ServirPedidoProducto(pedido.getIdPedido(), rs2.getInt("IDPRODUCTO"), 0);
			pedidoServidoProducto(ped);
		}

		String sql3 = "UPDATE PEDIDO SET SERVIDO='T' WHERE IDPEDIDO="+pedido.getIdPedido();

		PreparedStatement prepStmt3= conn.prepareStatement(sql3);
		recursos.add(prepStmt3);
		prepStmt3.executeQuery();
	}

	public void cancelarPedidoProducto(Pedido pedido) throws SQLException {
		cancelarPedidoEquivalencias(pedido);
		String sql = "DELETE FROM PEDIDOPRODUCTO WHERE IDPEDIDO="+pedido.getIdPedido();

		PreparedStatement prepStmt= conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs=prepStmt.executeQuery();
	}

	public void cancelarPedidoEquivalencias(Pedido pedido) throws SQLException {
		String sql = "DELETE FROM PEDIDO_EQUIVALENCIAPRODUCTO WHERE IDPEDIDO="+pedido.getIdPedido();

		PreparedStatement prepStmt= conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs=prepStmt.executeQuery();
	}

	public void cancelarPedidoMenu(Pedido pedido) throws SQLException {
		String sql = "DELETE FROM PEDIDOMENU WHERE IDPEDIDO="+pedido.getIdPedido();

		PreparedStatement prepStmt= conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs=prepStmt.executeQuery();
	}

	public void cancelarPedido(Pedido pedido) throws SQLException {
		cancelarPedidoProducto(pedido);
		cancelarPedidoMenu(pedido);
		String sql = "DELETE FROM PEDIDO WHERE IDPEDIDO="+pedido.getIdPedido();

		PreparedStatement prepStmt= conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs=prepStmt.executeQuery();
	}

	public void cancelarPedidoProducto(int id, int id2) throws SQLException {
		cancelarPedidoEquivalencias(id,id2);
		String sql = "DELETE FROM PEDIDOPRODUCTO WHERE IDPEDIDO="+id+" AND IDPRODUCTO=" +id2;

		PreparedStatement prepStmt= conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs=prepStmt.executeQuery();		
	}

	private void cancelarPedidoEquivalencias(int id, int id2) throws SQLException {
		String sql = "DELETE FROM PEDIDO_EQUIVALENCIAPRODUCTO WHERE IDPEDIDO="+id+" AND IDPROD1="+id2;

		PreparedStatement prepStmt= conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs=prepStmt.executeQuery();
	}

	public double precioPedido(int idPedido) throws SQLException {
		String sql = "SELECT PRECIOPEDIDO FROM PEDIDO WHERE IDPEDIDO="+idPedido;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if(rs.next())
		{
			return rs.getDouble("PRECIOPEDIDO");
		}
		return 0;	
	}

	public void addPedidoMesa(PedidoMesa pedido) throws SQLException {
		String sql = "insert into PEDIDOMESA (IDMESA, PRECIOTOTAL) values ("+pedido.getIdMesa()+", "+0+")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public void setPrecioMesa(double totalValue, int id) throws SQLException {
		String sql3 = "UPDATE PEDIDOMESA SET PRECIOTOTAL="+totalValue+" WHERE IDMESA="+id;

		PreparedStatement prepStmt3= conn.prepareStatement(sql3);
		recursos.add(prepStmt3);
		prepStmt3.executeQuery();		
	}
	
	
}
