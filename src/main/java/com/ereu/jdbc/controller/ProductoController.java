package com.ereu.jdbc.controller;

import com.ereu.jdbc.factory.ConnectionFactory;
import com.ereu.jdbc.modelo.Categoria;
import com.ereu.jdbc.modelo.Producto;
import com.ereu.jdbc.dao.ProductoDAO;

import java.util.List;


public class ProductoController {

    private ProductoDAO productoDAO;

    public ProductoController(){
        this.productoDAO = new ProductoDAO(new ConnectionFactory().recuperaConexion());
    }
    public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) {
        return productoDAO.modificar(nombre, descripcion, cantidad, id);
    }

    public int eliminar(Integer id) {
        return productoDAO.eliminar(id);
    }

    public List<Producto> listar() {
        return productoDAO.listar();
    }

    public List<Producto> listar(Categoria categoria){
        return productoDAO.listar(categoria.getId());
    }

    public void guardar(Producto producto, Integer categoriaId)  {
        producto.setCategoriaId(categoriaId);
        productoDAO.guardar(producto);
    }

}
