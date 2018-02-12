package com.base.utils.libaray.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.base.utils.libaray.vo.Node;
 
/**
 * 多叉树类
*/
public class NoteUtil {
	 public static void main(String[] args) {
		 // 读取层次数据结果集列表 
		 List dataList = VirtualDataGenerator.getVirtualResult(); 
		  
		 // 节点列表（散列表，用于临时存储节点对象）
		 HashMap nodeList = new HashMap();
		 // 根节点
		 Node root = null;
		 List<Node> rootlist=ListToNote(dataList);
		 System.out.println(rootlist);
		 for(Node node:rootlist){
			 System.out.println(node.toString());
		 }
	  
	  
	 }
	 public static List<Node> ListToNote(List dataList ){
		 // 节点列表（散列表，用于临时存储节点对象）
		 HashMap nodeList = new HashMap();
		 // 根节点
		 Node root = null;
		 List<Node> rootlist=new ArrayList<Node>();
		 // 根据结果集构造节点列表（存入散列表）
		 for (Iterator it = dataList.iterator(); it.hasNext();) {
			  Map dataRecord = (Map) it.next();
			  Node node = new Node();
			  node.id = (Long) dataRecord.get("id");
			  node.nameCn = (String) dataRecord.get("nameCn");
			  node.nameEn = (String) dataRecord.get("nameEn");
			  node.parentId = (Long) dataRecord.get("parentId");
			  nodeList.put(node.id, node);
		 }
		 // 构造无序的多叉树
		 Set entrySet = nodeList.entrySet();
		 for (Iterator it = entrySet.iterator(); it.hasNext();) {
			  Node node = (Node) ((Map.Entry) it.next()).getValue();
			  if (node.parentId == null || node.parentId.equals("")) {
				  root = node;
				  root.sortChildren();
				  rootlist.add(root);
			  } else {
				  Node node_t=((Node) nodeList.get(node.parentId));
				  if(node_t!=null ){
					  node_t.addChild(node);
				  }
				 
			  }
		 }
		 return rootlist;
	}
   
}
 

 
/**
 * 构造虚拟的层次数据
 */
class VirtualDataGenerator {
 // 构造无序的结果集列表，实际应用中，该数据应该从数据库中查询获得；
 public static List getVirtualResult() {  
 List dataList = new ArrayList();
  
 HashMap dataRecord1 = new HashMap();
 dataRecord1.put("id", 112000l);
 dataRecord1.put("nameCn", "廊坊银行解放道支行");
 dataRecord1.put("nameEn", "asdfsdf");
 dataRecord1.put("parentId", 110000l);
  
 HashMap dataRecord2 = new HashMap();
 dataRecord2.put("id", 112200l);
 dataRecord2.put("nameCn", "廊坊银行三大街支行");
 dataRecord2.put("nameEn", "asdfsdf");
 dataRecord2.put("parentId", 112000l);
  
 HashMap dataRecord3 = new HashMap();
 dataRecord3.put("id", 112100l);
 dataRecord3.put("nameCn", "廊坊银行广阳道支行");
 dataRecord3.put("nameEn", "asdfsdf");
 dataRecord3.put("parentId", 112000l);
    
 HashMap dataRecord4 = new HashMap();
 dataRecord4.put("id", 113000l);
 dataRecord4.put("nameCn", "廊坊银行开发区支行");
 dataRecord4.put("nameEn", "asdfsdf");
 dataRecord4.put("parentId", 110000l);
    
 HashMap dataRecord5 = new HashMap();
 dataRecord5.put("id", 100000l);
 dataRecord5.put("nameCn", "廊坊银行总行");
 dataRecord5.put("nameEn", "asdfsdf");
 dataRecord5.put("parentId", null);
 
  
 HashMap dataRecord6 = new HashMap();
 dataRecord6.put("id", 110000l);
 dataRecord6.put("nameCn", "廊坊分行");
 dataRecord6.put("parentId", 100000l);
 dataRecord6.put("nameEn", "asdfsdf");
  
 HashMap dataRecord7 = new HashMap();
 dataRecord7.put("id", 111000l);
 dataRecord7.put("nameCn", "廊坊银行金光道支行");
 dataRecord7.put("nameEn", "asdfsdf");
 dataRecord7.put("parentId", 110000l); 
 
 HashMap dataRecord8 = new HashMap();
 dataRecord8.put("id", 200000l);
 dataRecord8.put("nameCn", "厦门银行总行");
 dataRecord8.put("nameEn", "asdfsdf");
 dataRecord8.put("parentId", null);
 
 HashMap dataRecord9 = new HashMap();
 dataRecord9.put("id", 210000l);
 dataRecord9.put("nameCn", "厦门银行思明支行");
 dataRecord9.put("nameEn", "asdfsdf");
 dataRecord9.put("parentId", 200000l);
   
 dataList.add(dataRecord1);
 dataList.add(dataRecord2);
 dataList.add(dataRecord3);
 dataList.add(dataRecord4);
 dataList.add(dataRecord5);
 dataList.add(dataRecord6);
 dataList.add(dataRecord7);
 dataList.add(dataRecord8);
 dataList.add(dataRecord9);
  
 return dataList;
 } 
}
