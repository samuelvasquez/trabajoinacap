package cl.inacap.samuelvasquez.unidad3.tarea1.dataaccess;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import cl.inacap.samuelvasquez.unidad3.tarea1.actividades.R;
import cl.inacap.samuelvasquez.unidad3.tarea1.datamodel.Producto;

public class ProductoListAdapter extends  ArrayAdapter<Producto>{
	private Context context;
    private List<Producto> listaProductos;
	 
    public ProductoListAdapter(Context _context, List<Producto> _listaProductos) 
	 {
		 super(_context, R.layout.list_item_producto, _listaProductos);
		 context = _context;
		 listaProductos = _listaProductos;
	 }
		 
	 static class ViewHolder {
		 TextView txt_producto_id;
		 TextView txt_producto_nombre;
	 }

	 public int getCount() {
		 return listaProductos.size();
	 }

	 public Producto getItem(int position) {
		 return listaProductos.get(position);
	 }

	 public long getItemId(int position) {
		 return position;
	 }

	 public View getView(int position, View convertView, ViewGroup parent) 
	 {
		 ViewHolder holder;
		 if (convertView == null) {
			 LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_item_producto, null);
		 	 holder = new ViewHolder();
		 	 holder.txt_producto_id = (TextView) convertView.findViewById(R.id.txt_producto_id);
		 	 holder.txt_producto_nombre = (TextView) convertView.findViewById(R.id.txt_producto_nombre);
		 	 convertView.setTag(holder);
		 } else {
			 holder = (ViewHolder) convertView.getTag();
		 }
		 Producto producto = (Producto)getItem(position);
		 holder.txt_producto_id.setText(String.valueOf(producto.id));
		 holder.txt_producto_nombre.setText(producto.nombre);
	    return convertView;
	 }
	 
	 @Override
	    public void add(Producto producto) {
		 listaProductos.add(producto);
	        notifyDataSetChanged();
	        super.add(producto);
	    }
	 
	    @Override
	    public void remove(Producto producto) {
	    	listaProductos.remove(producto);
	        notifyDataSetChanged();
	        super.remove(producto);
	    }
}