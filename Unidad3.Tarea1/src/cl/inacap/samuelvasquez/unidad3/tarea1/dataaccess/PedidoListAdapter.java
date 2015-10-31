package cl.inacap.samuelvasquez.unidad3.tarea1.dataaccess;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import cl.inacap.samuelvasquez.unidad3.tarea1.actividades.R;
import cl.inacap.samuelvasquez.unidad3.tarea1.datamodel.Pedido;

public class PedidoListAdapter  extends  ArrayAdapter<Pedido>{
	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
	 
	private Context context;
    private List<Pedido> listaPedidos;
	
	 public PedidoListAdapter(Context _context, List<Pedido> _listaPedidos) {
		 super(_context, R.layout.list_item_pedido, _listaPedidos);
		 context = _context;
		 listaPedidos = _listaPedidos;
	 }
	 
	 class ViewHolder {
		 TextView txt_pedido_id;
		 TextView txt_pedido_cliente;
		 TextView txt_pedido_fecha;
		 TextView txt_pedido_estado;
	 }

	 public int getCount() {
		 return listaPedidos.size();
	 }

	 public Pedido getItem(int position) {
		 return listaPedidos.get(position);
	 }	

	 public long getItemId(int position) {
		 return 0;
	 } 
	 
	 public View getView(int position, View convertView, ViewGroup parent) {
		 ViewHolder holder;
		 if (convertView == null) {
			 LayoutInflater inflater = (LayoutInflater) context
	                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	            
			 convertView = inflater.inflate(R.layout.list_item_pedido, null);
			 holder = new ViewHolder();
			 holder.txt_pedido_id = (TextView)convertView.findViewById(R.id.txt_pedido_id);
			 holder.txt_pedido_cliente = (TextView)convertView.findViewById(R.id.txt_pedido_cliente);
			 holder.txt_pedido_fecha = (TextView)convertView.findViewById(R.id.txt_pedido_fecha);
			 holder.txt_pedido_estado = (TextView)convertView.findViewById(R.id.txt_pedido_estado);

			 convertView.setTag(holder);
		 } else {
			 holder = (ViewHolder) convertView.getTag();
		 }
		 Pedido pedido = (Pedido)getItem(position);
	       
		 holder.txt_pedido_id.setText(String.valueOf(pedido.id));
		 
		 
		 holder.txt_pedido_cliente.setText(pedido.nombre_cliente);
		 holder.txt_pedido_fecha.setText(formatter.format(pedido.fecha_entrega));
		 holder.txt_pedido_estado.setText(pedido.entregado ? "Entregado" : "Pendiente");

		 return convertView;
	 }

	@Override
    public void add(Pedido pedido) {
		listaPedidos.add(pedido);
        notifyDataSetChanged();
        super.add(pedido);
    }
 
    @Override
    public void remove(Pedido pedido) {
    	listaPedidos.remove(pedido);
        notifyDataSetChanged();
        super.remove(pedido);
    }
	 
}