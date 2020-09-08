package ar.edu.iua.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Producto", description = "Modelo de producto de ventas")
@Entity
@Table(name="produtos")
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class Producto implements Serializable {

	private static final long serialVersionUID = 5081791146397214235L;
	
	@ApiModelProperty(notes = "Identificador del producto, clave autogenerada", required = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ApiModelProperty(notes = "Nombre del producto", required = true)
	@Column(length = 100, nullable = false)
	private String nombre;
	
	@ApiModelProperty(notes = "Descripción extendida del producto", required = true)
	@Column(length = 200)
	private String descripcion;
	
	@ApiModelProperty(notes = "Precio actual del producto", required = true)
	private double precioLista;
	
	
	@ApiModelProperty(notes = "Indica si el producto se encuentra actualmente en stock", required = true)
	@Column(columnDefinition = "TINYINT DEFAULT 0")
	private boolean enStock;
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecioLista() {
		return precioLista;
	}

	public void setPrecioLista(double precioLista) {
		this.precioLista = precioLista;
	}

	public boolean isEnStock() {
		return enStock;
	}

	public void setEnStock(boolean enStock) {
		this.enStock = enStock;
	}


	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getFotoMimeType() {
		return fotoMimeType;
	}

	public void setFotoMimeType(String fotoMimeType) {
		this.fotoMimeType = fotoMimeType;
	}


	@Type(type="json")
	@Column(columnDefinition = "JSON null")
	private String comentarios;
	
	@JsonIgnore
	@Lob
	private byte[] foto;
	
	@JsonIgnore
	@Column(length = 50)
	private String fotoMimeType;
	
	

}
