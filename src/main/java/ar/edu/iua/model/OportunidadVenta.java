package ar.edu.iua.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "oportunidades_de_venta")
public class OportunidadVenta {
	
	public String checkBasicData() {
		if(getCantidad()<=0)
			return "El atributo 'cantidad' debe ser mayor a 0";
		
		if(getClientePotencial()==null || getClientePotencial().trim().length()==0)
			return "El atributo 'clientePotencial' es obligatorio";
		

		if(getMailCliente()==null || getMailCliente().trim().length()==0)
			return "El atributo 'mailCliente' es obligatorio";

		if(getProducto()==null)
			return "El atributo 'producto' es obligatorio";
		
		if(getProducto().getNombre()==null || getProducto().getNombre().trim().length()==0)
			return "El atributo 'producto.nombre' es obligatorio";
		
		if(getProducto().getCodigoExterno()==null || getProducto().getCodigoExterno().trim().length()==0)
			return "El atributo 'producto.codigoExterno' es obligatorio";

		return null;
	}

	@Column(length = 256)
	private String clientePotencial;

	@Column(length = 256)
	private String mailCliente;

	@Column(columnDefinition = "DATETIME NOT NULL")
	private Date fechaHora;

	@ManyToOne
	@JoinColumn(name = "id_producto")
	private Producto producto;

	@JsonProperty(required = true)
	private int cantidad;

	@Column(length = 256, nullable = true)
	private String comentarios;

	@Column(columnDefinition = "TINYINT NOT NULL DEFAULT 0")
	private boolean concretada;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientePotencial() {
		return clientePotencial;
	}

	public void setClientePotencial(String clientePotencial) {
		this.clientePotencial = clientePotencial;
	}

	public String getMailCliente() {
		return mailCliente;
	}

	public void setMailCliente(String mailCliente) {
		this.mailCliente = mailCliente;
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public boolean isConcretada() {
		return concretada;
	}

	public void setConcretada(boolean concretada) {
		this.concretada = concretada;
	}

//GETTERS SETTTERS

}
