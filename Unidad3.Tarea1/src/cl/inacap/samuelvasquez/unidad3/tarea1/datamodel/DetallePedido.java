package cl.inacap.samuelvasquez.unidad3.tarea1.datamodel;

public class DetallePedido
{
	// Propiedades
	public int id;
	public int id_pedido;
	public int id_producto;
	public int cantidad;
	public int precio;
	public Boolean es_activo;
	public String nombre_producto;
	
	// Constructor de las instancias
	public DetallePedido(){}
	
	// Sobreescribe metodo toString() de la clase
	public String toString()
	{
		return String.valueOf(this.id);
	}
	
	public int total()
	{
		return cantidad * precio;
	}
}