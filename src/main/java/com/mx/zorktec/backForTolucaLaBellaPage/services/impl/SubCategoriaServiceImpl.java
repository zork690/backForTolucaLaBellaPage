package com.mx.zorktec.backForTolucaLaBellaPage.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mx.zorktec.backForTolucaLaBellaPage.daos.SubCategoriaDao;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Categoria;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.SubCategoria;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.CategoriaSubCategoriaVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.SubCategoriaVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.SubCategoria_Vo;
import com.mx.zorktec.backForTolucaLaBellaPage.services.SubCategoriaService;

@Service
public class SubCategoriaServiceImpl implements SubCategoriaService {
	
	@Autowired
	private SubCategoriaDao subcategoriaDao;

	@Override
	public List<SubCategoriaVo> getSubCategorias() {
		List<SubCategoria> subcategoriasList = this.subcategoriaDao.findAll("SubCategoria"
				, SubCategoria.class).orElse(null);
		List<SubCategoriaVo> subcategoriaListVo = new ArrayList<SubCategoriaVo>();
		if(subcategoriasList != null) {
			subcategoriasList.forEach((subcategoria)->{
				SubCategoriaVo subCategoriaVo = new SubCategoriaVo();
				subCategoriaVo.setId(subcategoria.getId());
				subCategoriaVo.setNombre(subcategoria.getSubcategoria());
				subCategoriaVo.setValid(subcategoria.isValid());
				
				CategoriaSubCategoriaVo csVo = new CategoriaSubCategoriaVo();
				csVo.setId(subcategoria.getCategoria().getId());
				csVo.setNombre(subcategoria.getCategoria().getCategoria());
				subCategoriaVo.setCategoria(csVo);
				subcategoriaListVo.add(subCategoriaVo);
			});
			
			return subcategoriaListVo;
		}
		
		return null;
	}

	@Override
	public void insertarSubCategoria(SubCategoria_Vo subcategoriaVo) {
		SubCategoria subcategoria = new SubCategoria();
		subcategoria.setSubcategoria(subcategoriaVo.getSubcategoria());
		subcategoria.setId(subcategoriaVo.getId());
		
		Categoria categoria = new Categoria();
		categoria.setId(subcategoriaVo.getCategoria());
		subcategoria.setCategoria(categoria);
		
		subcategoria.setValid(subcategoriaVo.isValid());
		
		this.subcategoriaDao.saveOrUpdate(subcategoria);
	}

}
