package com.library.component;


import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import net.sf.jasperreports.engine.*;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author Shahidul Hasan
 * @developer Shahidul Hasan
 * class LibraryJrxmlHelper
 * Helper
 */
public class LibraryJrxmlHelper {

	protected Map<String, Object> getBasicProperty(){
		Map<String, Object> params = new HashMap<String, Object>();
		String resourceDir = String.valueOf(getClass().getClassLoader().getResource(""));
		params.put("resourceDir", resourceDir);
		return params;
	}
	protected JasperPrint getReturnProperty(String fileName, Map<String, Object> params)throws ColumnBuilderException, JRException {
		return JasperFillManager.fillReport(JasperCompileManager.compileReport(Thread.currentThread().getContextClassLoader().
				getResourceAsStream(fileName)), params, new JREmptyDataSource());
	}
	protected void getListProperty(Map<String, Object> params, String paramName, Object objectList){
		JRBeanCollectionDataSource list = new JRBeanCollectionDataSource((Collection<?>) objectList);
		params.put(paramName, list);
	}
}