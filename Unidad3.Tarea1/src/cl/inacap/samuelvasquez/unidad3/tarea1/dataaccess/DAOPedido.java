package cl.inacap.samuelvasquez.unidad3.tarea1.dataaccess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import cl.inacap.samuelvasquez.unidad3.tarea1.datamodel.Cliente;
import cl.inacap.samuelvasquez.unidad3.tarea1.datamodel.DetallePedido;
import cl.inacap.samuelvasquez.unidad3.tarea1.datamodel.Pedido;
import cl.inacap.samuelvasquez.unidad3.tarea1.datamodel.Producto;
import cl.inacap.samuelvasquez.unidad3.tarea1.datamodel.ResumenCaja;

public class DAOPedido {
	protected SQLiteDatabase database;
    private DBHelper dbHelper;
    private Context mContext;
	    
    private String tabla = "PEDIDO";
	private String[] columnas = new String[]{"ID", "ID_VENDEDOR", "ID_CLIENTE", "FECHA_ENTREGA", "ENTREGADO", "ES_ACTIVO"} ;
	    
	private String tablaDetalle = "PEDIDO_DETALLE";
	private String[] columnasDetalle = new String[]{"ID", "ID_PEDIDO", "ID_PRODUCTO", "CANTIDAD", "PRECIO_UNITARIO", "ES_ACTIVO"} ;
		
	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
 
	private DAOCliente daoCliente;
	private DAOProducto daoProducto;
	
	public DAOPedido(Context context)
	{
		this.mContext = context;
		daoCliente = new DAOCliente(context);
		daoProducto = new DAOProducto(context);
        dbHelper = DBHelper.getHelper(mContext);
        open();
	}
	
	public void open() throws SQLException {
        if(dbHelper == null)
            dbHelper = DBHelper.getHelper(mContext);
        database = dbHelper.getWritableDatabase();
    }
	
	public long update(Pedido pedido) {
        ContentValues values = new ContentValues();
        values.put("ID_CLIENTE", pedido.id_cliente);
        values.put("FECHA_ENTREGA", formatter.format(pedido.fecha_entrega));
        values.put("ENTREGADO",pedido.entregado);
     
        long result = database.update(tabla, values,
        		"ID = ? ",
                new String[] { String.valueOf(pedido.id) });
        Log.d("Update Result:", "=" + result);
        
        for (int i = 0; i < pedido.detalles.size(); i++) 
        { 
        	if(pedido.detalles.get(i).id == 0)
        	{
	        	ContentValues valuesDetalle = new ContentValues();
	        	valuesDetalle.put("ID_PEDIDO", pedido.id);
	        	valuesDetalle.put("ID_PRODUCTO", pedido.detalles.get(i).id_producto);
	        	valuesDetalle.put("CANTIDAD", pedido.detalles.get(i).cantidad);
	        	valuesDetalle.put("PRECIO_UNITARIO", pedido.detalles.get(i).precio);
	           	database.insert(tablaDetalle, null, valuesDetalle);
        	}
        }
        
        return result;
    }
	
	public long save(Pedido pedido) {
        ContentValues values = new ContentValues();
        values.put("ID_VENDEDOR", pedido.id_vendedor);
        values.put("ID_CLIENTE", pedido.id_cliente);
        values.put("FECHA_ENTREGA", formatter.format(pedido.fecha_entrega));
        values.put("ENTREGADO",pedido.entregado);
        long id = database.insert(tabla, null, values);
        
        for (int i = 0; i < pedido.detalles.size(); i++) 
        { 
        	ContentValues valuesDetalle = new ContentValues();
        	valuesDetalle.put("ID_PEDIDO", id);
        	valuesDetalle.put("ID_PRODUCTO", pedido.detalles.get(i).id_producto);
        	valuesDetalle.put("CANTIDAD", pedido.detalles.get(i).cantidad);
        	valuesDetalle.put("PRECIO_UNITARIO", pedido.detalles.get(i).precio);
           	database.insert(tablaDetalle, null, valuesDetalle);
        }
        
        return id;
    }
	
	public long delete(Pedido pedido) {
		ContentValues values = new ContentValues();
        values.put("ES_ACTIVO", "0");
     
        long result = database.update(tabla, values,
        		"ID = ? ",
                new String[] { String.valueOf(pedido.id) });
        Log.d("Delete Result:", "=" + result);
        return result;
    }
	
	private Pedido cursorToPedido(Cursor cursor) 
	{
		Pedido pedido = new Pedido();
		pedido.id = cursor.getInt(0);
		pedido.id_vendedor = cursor.getInt(1);
		pedido.id_cliente = cursor.getInt(2);
		try {
			pedido.fecha_entrega = formatter.parse(cursor.getString(3));
        } catch (ParseException e) {
        	pedido.fecha_entrega = null;
        }
		pedido.entregado = (cursor.getInt(4) == 1);
		pedido.es_activo = (cursor.getInt(5) == 1);
		
		Cliente cliente = daoCliente.getCliente(pedido.id_cliente);
		if(cliente != null)
		{
			pedido.nombre_cliente = cliente.nombre;
		}
		
		return pedido;
	}
	
	private DetallePedido cursorToDetallePedido(Cursor cursor) 
	{
		//private String[] columnasDetalle = new String[]{"ID", "ID_PEDIDO", "ID_PRODUCTO", "CANTIDAD", "PRECIO_UNITARIO", "ES_ACTIVO"} ;
		
		DetallePedido detalle = new DetallePedido();
		detalle.id = cursor.getInt(0);
		detalle.id_pedido = cursor.getInt(1);
		detalle.id_producto = cursor.getInt(2);
		detalle.cantidad = cursor.getInt(3);
		detalle.precio = cursor.getInt(4);
		detalle.es_activo = (cursor.getInt(5) == 1);
		
		Producto producto = daoProducto.getProducto(detalle.id_producto);
		if(producto != null)
		{
			detalle.nombre_producto = producto.nombre;
		}
		
		return detalle;
	}
	
	public ArrayList<Pedido> getPedidosByIdVendedor(int id_vendedor) 
	{
		Log.i("daopedido", "getPedidosByIdVendedor " + String.valueOf(id_vendedor));
		  
		ArrayList<Pedido> pedidos = new ArrayList<Pedido>();
		String where = "ES_ACTIVO = 1 AND ID_VENDEDOR=?"; // the condition for the row(s) you want returned.
		String[] whereArgs = new String[] { // The value of the column specified above for the rows to be included in the response
				String.valueOf(id_vendedor)
		    };
		Cursor cursor = database.query(tabla, columnas, where, whereArgs, null, null, null);
		while (cursor.moveToNext()) {
			Pedido pedido = cursorToPedido(cursor);
			pedidos.add(pedido);
		}
		// make sure to close the cursor
		cursor.close();
		
		Log.i("daocliente", "return " + String.valueOf(pedidos.size()));
		
		return pedidos;
	}
	
	public Pedido getPedido(long id) {
        Pedido pedido = null;
 
        String where = "ES_ACTIVO = 1 AND ID = ? "; // the condition for the row(s) you want returned.
		String[] whereArgs = new String[] { // The value of the column specified above for the rows to be included in the response
				String.valueOf(id)
		    };
		Cursor cursor = database.query(tabla, columnas, where, whereArgs, null, null, null);
		
        if (cursor.moveToNext()) {
        	pedido = cursorToPedido(cursor);
	    }
        
        // Detalles
        String whereDetalle = "ES_ACTIVO = 1 AND ID_PEDIDO = ? "; // the condition for the row(s) you want returned.
  		String[] whereArgsDetalle = new String[] { // The value of the column specified above for the rows to be included in the response
  				String.valueOf(id)
  		    };
  		Cursor cursorDetalle = database.query(tablaDetalle, columnasDetalle, whereDetalle, whereArgsDetalle, null, null, null);
  		while (cursorDetalle.moveToNext()) {
			DetallePedido detalle = cursorToDetallePedido(cursorDetalle);
			pedido.detalles.add(detalle);
		}
		// make sure to close the cursor
  		cursorDetalle.close();
  		cursor.close();
        
        return pedido;
    }
	
	public ResumenCaja getResumenByIdVendedor(int id_vendedor)
	{
		ResumenCaja resumen = new ResumenCaja();
		
		String[] whereArgs = new String[] { // The value of the column specified above for the rows to be included in the response
				String.valueOf(id_vendedor)
		    };
		
		Cursor cursorPedidos = database.rawQuery(
			    "SELECT COUNT(ID) FROM PEDIDO WHERE ES_ACTIVO = 1 AND ID_VENDEDOR=? ", whereArgs);
		if(cursorPedidos.moveToFirst()) {
		    resumen.total_pedidos = cursorPedidos.getInt(0);
		}
		
		Cursor cursorEntregas = database.rawQuery(
			    "SELECT COUNT(ID) FROM PEDIDO WHERE ES_ACTIVO = 1 AND ENTREGADO = 1 AND ID_VENDEDOR=?", whereArgs);
		if(cursorEntregas.moveToFirst()) {
		    resumen.total_entregas = cursorEntregas.getInt(0);
		}
		
		Cursor cursorSaldos = database.rawQuery(
			    "SELECT SUM(PEDIDO_DETALLE.CANTIDAD * PEDIDO_DETALLE.PRECIO_UNITARIO) "
			    + "FROM PEDIDO, PEDIDO_DETALLE "
			    + "WHERE PEDIDO.ID = PEDIDO_DETALLE.ID_PEDIDO "
			    + "AND PEDIDO.ES_ACTIVO = 1 AND PEDIDO.ENTREGADO = 1 AND PEDIDO.ID_VENDEDOR=? "
			    + "AND PEDIDO_DETALLE.ES_ACTIVO = 1 ", whereArgs);
		if(cursorSaldos.moveToFirst()) {
		    resumen.saldo = cursorSaldos.getInt(0);
		}
		
		return resumen;
	}
}
