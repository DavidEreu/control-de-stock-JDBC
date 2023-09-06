package com.ereu.jdbc.controller;

import com.ereu.jdbc.dao.CategoriaDAO;
import com.ereu.jdbc.factory.ConnectionFactory;
import com.ereu.jdbc.modelo.Categoria;

import java.util.List;

public class CategoriaController {

    private CategoriaDAO categoriaDAO;

    public CategoriaController(){
        var factory = new ConnectionFactory();
        this.categoriaDAO = new CategoriaDAO(factory.recuperaConexion());
    }
    public List<Categoria> listar() {
        return categoriaDAO.listar();
    }

    public List<Categoria> cargaReporte() {
        return this.categoriaDAO.listarConProductos();
    }

}
