package cl.inacap.samuelvasquez.unidad3.tarea1.dataaccess;

import java.util.ArrayList;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import cl.inacap.samuelvasquez.unidad3.tarea1.datamodel.Cliente;
import cl.inacap.samuelvasquez.unidad3.tarea1.datamodel.Producto;

public class DAOProducto {
	protected SQLiteDatabase database;
    private DBHelper dbHelper;
    private Context mContext;
	private String[] columnas = new String[]{"ID", "NOMBRE", "ES_ACTIVO"} ;
	    
	public DAOProducto(Context context)
	{
		this.mContext = context;
        dbHelper = DBHelper.getHelper(mContext);
        open();
	}
	
	public void open() throws SQLException {
        if(dbHelper == null)
            dbHelper = DBHelper.getHelper(mContext);
        database = dbHelper.getWritableDatabase();
    }
	
	
	private Producto cursorToProducto(Cursor cursor) 
	{
		Producto producto = new Producto();
		producto.id = cursor.getInt(0);
		producto.nombre = cursor.getString(1);
		producto.es_activo = (cursor.getInt(2) == 1);
		return producto;
	}
	
	public ArrayList<Producto> getAllProductos() 
	{
		Log.i("daoproducto", "getAllProductos");
		
		ArrayList<Producto> productos = new ArrayList<Producto>();
		String where = "ES_ACTIVO = 1 "; // the condition for the row(s) you want returned.
		Cursor cursor = database.query("PRODUCTO", columnas, where, null, null, null, null);
		while (cursor.moveToNext()) {
			Producto producto = cursorToProducto(cursor);
			productos.add(producto);
		}
		// make sure to close the cursor
		cursor.close();
		
		Log.i("daoproducto", "return " + String.valueOf(productos.size()));
		
		return productos;
	}
	
	public Producto getProducto(long id) {
		Producto producto = null;
 
        String where = "ES_ACTIVO = 1 AND ID = ? "; // the condition for the row(s) you want returned.
		String[] whereArgs = new String[] { // The value of the column specified above for the rows to be included in the response
				String.valueOf(id)
		    };
		Cursor cursor = database.query("PRODUCTO", columnas, where, whereArgs, null, null, null);
		
        if (cursor.moveToNext()) {
        	producto = cursorToProducto(cursor);
	    }
        
        return producto;
    }
	
}
