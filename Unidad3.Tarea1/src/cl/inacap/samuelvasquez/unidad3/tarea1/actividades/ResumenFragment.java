package cl.inacap.samuelvasquez.unidad3.tarea1.actividades;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cl.inacap.samuelvasquez.unidad3.tarea1.dataaccess.DAOPedido;
import cl.inacap.samuelvasquez.unidad3.tarea1.datamodel.ResumenCaja;

public class ResumenFragment extends  Fragment {
	public static final String ARG_ITEM_ID = "resumen";
	public int id_vendedor;
	
	Activity activity;
    DAOPedido pedidoDAO;
	
	public ResumenFragment(){}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        pedidoDAO = new DAOPedido(activity);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.fragment_resumen, container, false);
        Bundle bundle = this.getArguments();
        id_vendedor = bundle.getInt("id_vendedor", 0);
        
        ResumenCaja resumen = pedidoDAO.getResumenByIdVendedor(id_vendedor);
        
        TextView lbl_valor_total_entregas = (TextView) rootView.findViewById(R.id.lbl_valor_total_entregas);
        TextView lbl_valor_total_pedidos = (TextView) rootView.findViewById(R.id.lbl_valor_total_pedidos);
        TextView lbl_valor_saldo = (TextView) rootView.findViewById(R.id.lbl_valor_saldo);
              
        lbl_valor_total_entregas.setText(String.valueOf(resumen.total_entregas));
        lbl_valor_total_pedidos.setText(String.valueOf(resumen.total_pedidos));
        lbl_valor_saldo.setText(String.valueOf(resumen.saldo));
        
        return rootView;
    }
	
    @Override
    public void onResume() {
        getActivity().setTitle(R.string.title_fragment_resumen);
        getActivity().getActionBar().setTitle(R.string.title_fragment_resumen);
        super.onResume();
    }
}
