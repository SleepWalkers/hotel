package com.yaoxingyu.hotel.utils;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class LoadFlashDatas extends HttpServlet{
	private static final long serialVersionUID = -3463259207600030632L;
	
	private Map<String, LoadDatas> loadDatasHandlerMap = new HashMap<String, LoadDatas>();

//	private static final Logger LOG = Logger.getLogger(LoadFlashDatas.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {
		super.init();
		
		try {
			Date start = new Date();
			SAXReader reader = new SAXReader();
	        Document document = reader.read(LoadFlashDatas.class.getResourceAsStream("/load-data.xml"));
	        Element root=document.getRootElement();
	        List<Element> elsemens = root.elements();
	        Date readFileDate = new Date();
	        int i=0;
	        for(Element e : elsemens){
	        	i++;
	        	loadDatasHandlerMap.put(e.attributeValue("method"), (LoadDatas)(Class.forName(e.attributeValue("class")).newInstance()));
	        }
	        Date end = new Date();
	  //      LOG.warn("LOAD DATAS SERVLERT INIT: read file and parse time: " + (readFileDate.getTime()-start.getTime()) + "\t class new instance(" + i + ") time: " + (end.getTime()-start.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		//	LOG.error("init LoadFlashDatas Servlet error!", e);
		}      
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String method = req.getParameter("method");
		
		LoadDatas loadDataHandler = loadDatasHandlerMap.get(method);
		if(loadDataHandler!=null)
			loadDataHandler.loadDatas(req, resp);
		else{
	//		LOG.error("domainUser-->" + req.getAttribute("Ark:User").toString()+"<--    �Ҳ�����Ӧ�Ĵ��?��");
		}
	}
	
//	public static void main(String[] args) {
		//System.out.println(new String("�ҵĹ��ﳵ".getBytes(), "ISO8859-1"));
//	}
}
