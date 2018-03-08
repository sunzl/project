package com.nami.sonar.rule.checks;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.nami.sonar.rule.FileUtils;
/**
 * 检查SQL是否符合要求
 * 默认检查：
 * 1、检查 SELECT * 
 * 2、检查 有where条件否则一定要有limit条件
 * 3、检查是否有DORP操作(create,drop,alter)
 * 4、检查更新语句中不能出现表关联
 * 5、检查更新语句条件中如有主键且不能判断条件中
 */
@Rule(key="SqlCheckRule")
public class SqlCheckRule extends AbstractXmlCheck{
	
	private Logger logger = LoggerFactory.getLogger(SqlCheckRule.class);
	
	@RuleProperty(key = "matchContent", 
			description = "自定义输入匹配条件", 
			type = "TEXT")
	private String matchContent;
	
	@RuleProperty(key = "stopCheckNo", 
			description = "指定要停止检查序号，多个用英文\",\"隔开", 
			type = "TEXT")
	private String stopCheckNo;
	
	@Override
	public void validate(XmlSourceCode xmlSourceCode) {
		try {
			setWebSourceCode(xmlSourceCode);
			//System.out.println(xmlSourceCode.createInputStream().available());
			//LineCountParser lineCountParser = new LineCountParser(xmlSourceCode.getContents(), Charset.forName("UTF-8"));
			//System.out.println(lineCountParser.getLineCountData().linesNumber());
			handleSqlCheck();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * sql检查
	 * @throws Exception
	 */
	private void handleSqlCheck() throws Exception {
		//Document document = getWebSourceCode().getDocument(false);
		Document document =  getDocument(getWebSourceCode().createInputStream());
		DocumentType doctype = document.getDoctype();
		//处理是mybatis映射xml文件
		if (doctype!=null 
				&& doctype.getPublicId()!=null
				&& doctype.getPublicId().contains("mybatis")) {
			//System.out.println(doctype.getPublicId());//-//mybatis.org//DTD Mapper 3.0//EN
			//System.out.println(doctype.getSystemId());
			//long startTime = System.currentTimeMillis();
			//获取xml所有行内容列表
			List<String> xmlLineContents = xmlLineContents();
			//获取xml中sql标签节点
			NodeList sqlNodeList = document.getElementsByTagName("sql");
			//过滤某些检查规则
			StopFlag sf = new StopFlag();
			if (stopCheckNo!=null && !"".equals(stopCheckNo.trim())) {
				String[] nos = stopCheckNo.split(",");
				for(String no : nos) {
					if ("1".equals(no)) {
						sf.setNo1(false);
					}else if("2".equals(no)) {
						sf.setNo2(false);
					}else if("3".equals(no)) {
						sf.setNo3(false);
					}else if("4".equals(no)) {
						sf.setNo4(false);
					}else if("5".equals(no)) {
						sf.setNo5(false);
					}
				}
			}
			//select规则检查
			handleBySqlType(document,xmlLineContents,sqlNodeList,"select",sf);
			//save规则检查
			handleBySqlType(document,xmlLineContents,sqlNodeList,"save",sf);
			//delete规则检查
			handleBySqlType(document,xmlLineContents,sqlNodeList,"delete",sf);
			//update规则检查
			handleBySqlType(document,xmlLineContents,sqlNodeList,"update",sf);
			//long endTme = System.currentTimeMillis();
			//logger.info("分析此文档所花费的时间(毫秒)："+(endTme-startTime));
		}
		
	}
	
	/**
	 * 根据sql类型按规则处理
	 * @param document 文档
	 * @param xmlLineContents 文档行内容
	 * @param sqlNodeList 节点sql列表
	 * @param sqlType sql类型
	 * @throws IOException
	 */
	private void handleBySqlType(Document document,List<String> xmlLineContents,NodeList sqlNodeList,String sqlType,StopFlag sf) throws IOException{
		NodeList nodeList = getNodeListByTagName(document,sqlType);
		//主键字段
		String key = null;
		if ("update".equals(sqlType)) {
			//获取xml中的resultMap标签节点
			key = getKey(document);
		}
		if (nodeList!=null && nodeList.getLength()>0) {
			//String idAtt = null;
			List<Node> refList = null;
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node sqlNode = nodeList.item(i);
				String sqlContent = sqlNode.getTextContent();
				if (sqlContent!=null) {
					final String idAtt = sqlNode.getAttributes().getNamedItem("id").getTextContent();
					refList = new ArrayList<>();
					//getFirstLevelNode(sqlNodeList,refList,sqlNode);
					getAllNode(sqlNodeList, refList, sqlNode);
					sqlContent = sqlContent.toLowerCase().trim();
					//1、默认检查语句不能有 SELECT * 
					if (sf.isNo1()) {
						checkSql(sqlContent, "*", null,xmlLineContents, idAtt, refList, "select语句 id=["+idAtt+"] 中出现查询所有列操作 (select * )");
					}
					
					//2、默认检查语句 必须带where条件否则一定要有limit条件
					if (sf.isNo2()) {
						if (!"save".equals(sqlType)
								&& !isExistMatchData(sqlContent,"where") 
								&& !isExistMatchData(sqlContent,"limit") ) {
							if (isExistWhereOrLimit(refList)) {
								recordFindMatchInfo(xmlLineContents, idAtt, sqlType+"语句属性id=["+idAtt+"] 中即没有\"where\"条件也没有\"limit\"条件");
							}
						}
					}
					
					//3、默认检查所有语句不能出现DDL操作(create、drop、alter)
					if (sf.isNo3()) {
						checkSql(sqlContent, "table", null,xmlLineContents, idAtt, refList, sqlType+"句属性id=["+idAtt+"] 中出现DDL操作(create,drop,alter)");
					}

					if("update".equals(sqlType)) {
						//4、update里不能出现表关联检查
						if (sf.isNo4()) {
							String subStr = "";
							if (sqlContent.length()>0) {
								subStr = getSubStr(sqlContent);
							}
							checkSql(subStr,"join", "join",xmlLineContents, idAtt, refList, "update语句 id=["+idAtt+"] 中出现表关联更新");
						}
						//5、检查更新语句条件中如有主键且不能判断语句中
						if (sf.isNo5()) {
							if (!doCheck(sqlNode, xmlLineContents, idAtt,key)) {
								final String k = key;
								refList.forEach(node->{
									if (!"set".equals(node.getParentNode().getNodeName())) {
										doCheck(node, xmlLineContents, idAtt,k);
									}
								});
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 获取主键
	 * @param document
	 * @return
	 */
	private String getKey(Document document) {
		String key = null;
		NodeList rMapNodeList = document.getElementsByTagName("resultMap");
		if (rMapNodeList!=null) {
			for(int i=0;i<rMapNodeList.getLength();i++) {
				NodeList childNodes = rMapNodeList.item(i).getChildNodes();
				for(int j=0;j<childNodes.getLength();j++) {
					if("id".equals(childNodes.item(j).getNodeName())) {
						key = childNodes.item(j).getAttributes().getNamedItem("column").getTextContent();
						break;
					}
				}
			}
		}
		return key;
	}
	
	/**
	 * 检查主键是否存在where条件中
	 * @param node 节点
	 * @param xmlLineContents 文档行内容
	 * @param idAtt 主节点id属性值
	 * @return
	 */
	private boolean doCheck(Node node,List<String> xmlLineContents,String idAtt,String key) {
		NamedNodeMap attributes = node.getAttributes();
		if (attributes!=null && attributes.getNamedItem("test")!=null) {
			String reg = "[\\s\\S]*"+(key==null?"id":key)+"[\\s]*=[\\s]*#[\\s\\S]*";
			if(node.getTextContent().matches(reg) && !node.getTextContent().contains(",")) {
				checkSql("",reg, "id",xmlLineContents, idAtt, null, "update语句 id=["+idAtt+"] 中存在主键且在判断语句中");
				return true;
			}
		}
		return false;	
	}
	
	/**
	 * 根据条件检查sql
	 * @param sqlContent sql文本内容 
	 * @param match 匹配的条件
	 * @param flag 特殊处理标记
	 * @param xmlLineContents 文档行内容
	 * @param idAtt 主节点id属性值（select、insert、update、delete）
	 * @param refList 节点sql列表
	 * @param msg 消息
	 */
	private void checkSql(String sqlContent,String match,String flag,List<String> xmlLineContents,String idAtt,List<Node> refList,String msg) {
		if (isExistMatchData(sqlContent, match)) {
			recordFindMatchInfo(xmlLineContents, idAtt, msg);
		}else {
			if ("join".equals(flag)) {
				for(Node n:refList) {
					if (n.getTextContent()!=null 
							&& !"".equals(n.getTextContent().trim())) {
						if(isExistMatchData(getSubStr(n.getTextContent()),match)) {
							recordFindMatchInfo(xmlLineContents, idAtt, msg);
							break;
						}
					}
				}
			}else if("id".equals(flag)){
				recordFindMatchInfo(xmlLineContents, idAtt, msg);
			}else {
				for(Node n:refList) {
					if (n.getTextContent()!=null 
							&& !"".equals(n.getTextContent().trim()) 
							&& isExistMatchData(n.getTextContent().toLowerCase(), match)) {
						recordFindMatchInfo(xmlLineContents, idAtt, msg);
						break;
					}
				}
			}
		}
	}
	
	/**
	 * 判断是否存在有匹配的内容
	 * @param sqlContent sql内容
	 * @param match 匹配内容
	 * @return
	 */
	private boolean isExistMatchData(String sqlContent,String match) {
		if (sqlContent!=null && "".equals(sqlContent.trim())) {
			return false;
		}
		String[] splits = sqlContent.trim().split("\\s+");
		for(String s : splits) {
			if (match.equals(s)) {
				return true;
			}
		}
		return false;
	}
	
	private String getSubStr(String content) {
		String lowerC = content.toLowerCase();
		if (lowerC.contains("set")) {
			return lowerC.substring(0, lowerC.indexOf("set"));
		}else {
			return lowerC.substring(0, content.length());
		}
	}
	
	/**
	 * 记录输出找到的符合条件的内容
	 * @param xmlLineContents 文档行内容
	 * @param idAtt 主节点id属性值
	 * @param msg 消息
	 */
	private void recordFindMatchInfo(List<String> xmlLineContents,String idAtt,String msg) {
		int line = findMatchLine(xmlLineContents,idAtt);
		if (logger.isDebugEnabled()) {
			logger.debug("line="+line+",msg="+msg);
		}
		createViolation(line, msg);
	}
	
	/**
	 * 获取当前节点下的所有节点
	 * @param sqlNodeList
	 * @param refList
	 * @param sqlNode
	 */
	private void getAllNode(NodeList sqlNodeList,List<Node> refList,Node sqlNode) {
		NodeList nodes = sqlNode.getChildNodes();
		if (sqlNodeList.getLength()>0) {
			for(int j=0;j<nodes.getLength();j++) {
				if (!"#text".equals(nodes.item(j).getNodeName()) 
						&& !"#comment".equals(nodes.item(j).getNodeName())) {
					if("include".equals(nodes.item(j).getNodeName())) {
						String refid = nodes.item(j).getAttributes().getNamedItem("refid").getTextContent();
						for(int c=0;c<sqlNodeList.getLength();c++) {
							if(refid.equals(sqlNodeList.item(c).getAttributes().getNamedItem("id").getTextContent())) {
								refList.add(sqlNodeList.item(c));
								getAllNode(sqlNodeList,refList,sqlNodeList.item(c));
							}
						}
					}else {
						refList.add(nodes.item(j));
						getAllNode(sqlNodeList,refList,nodes.item(j));
					}
				}
			}
		}else {
			for(int j=0;j<nodes.getLength();j++) {
				if (!"#text".equals(nodes.item(j).getNodeName()) 
						&& !"#comment".equals(nodes.item(j).getNodeName())) {
					refList.add(nodes.item(j));
					getAllNode(sqlNodeList,refList,nodes.item(j));
				}
			}
		}
	}
	
	/**
	 * 判断是否存在where、limit
	 * @param refList 节点列表
	 * @return
	 */
	private boolean isExistWhereOrLimit(List<Node> refList) {
		boolean flag = true;
		for(Node n:refList) {
			//是where节点
			if ("where".equals(n.getNodeName())) {
				flag = false;
				break;
			}
			//检查内容是否有where、limit条件
			if(n.getTextContent()!=null && !"".equals(n.getTextContent().trim()) 
					&& (isExistMatchData(n.getTextContent().trim().toLowerCase(), "where")
							|| isExistMatchData(n.getTextContent().trim().toLowerCase(), "limit"))) {
				flag = false;
				break;
			}
			//检查是trim节点且是where条件
			if(n.getAttributes().getNamedItem("prefix")!=null 
					&& "where".equals(n.getAttributes().getNamedItem("prefix").getTextContent())) {
				flag = false;
				break;
			}
			//检查是否有子节点并寻找是否有where节点
			/*if(n.hasChildNodes()) {
				NodeList nodes = n.getChildNodes();
				for(int j=0;j<nodes.getLength();j++) {
					if(nodes.item(j).getNodeName().equals("where")) {
						flag = false;
						break;
					}
				}
			}*/
		}
		return flag;
	}
	
	//根据标签名称获取节点列表
	private NodeList getNodeListByTagName(Document document,String tagName) {
		return document.getElementsByTagName(tagName);
	}

	/**
	 * 文档行内容
	 * @return
	 * @throws IOException
	 */
	private List<String> xmlLineContents() throws IOException{
		List<String> readLines = FileUtils.readLines(getWebSourceCode().getXmlFile().getInputFile());
		if (readLines!=null && !readLines.isEmpty()) {
			if (matchContent!=null 
					&& matchContent.trim().length()>0) {
				for(int i=0;i<readLines.size();i++) {
					if (readLines.get(i).toLowerCase().contains(matchContent.trim().toLowerCase())) {
						createViolation(i+1, "该行中出现匹配的内容 , 即 matchContent=["+matchContent+"]");
					}
				}
			}
		}
		return readLines;
	}
	//寻找符合属性id的节点所在行
	private int findMatchLine(List<String> lines,String match) {
		for(int i=0;i<lines.size();i++) {
			if (lines.get(i).contains("id=\""+match+"\"")) {
				return i+1;
			}
		}
		//没有匹配时定位在第一行
		return 1;
	}
	
	private Document getDocument(InputStream inputStream) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();   
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(inputStream);
	}
	
	
	public String getMatchContent() {
		return matchContent;
	}

	public void setMatchContent(String matchContent) {
		this.matchContent = matchContent;
	}

	public String getStopCheckNo() {
		return stopCheckNo;
	}

	public void setStopCheckNo(String stopCheckNo) {
		this.stopCheckNo = stopCheckNo;
	}
	
	/**
	 * 停止序号
	 */
	class StopFlag{
		private boolean no1 = true;
		private boolean no2 = true;
		private boolean no3 = true;
		private boolean no4 = true;
		private boolean no5 = true;
		
		public boolean isNo1() {
			return no1;
		}
		public void setNo1(boolean no1) {
			this.no1 = no1;
		}
		public boolean isNo2() {
			return no2;
		}
		public void setNo2(boolean no2) {
			this.no2 = no2;
		}
		public boolean isNo3() {
			return no3;
		}
		public void setNo3(boolean no3) {
			this.no3 = no3;
		}
		public boolean isNo4() {
			return no4;
		}
		public void setNo4(boolean no4) {
			this.no4 = no4;
		}
		public boolean isNo5() {
			return no5;
		}
		public void setNo5(boolean no5) {
			this.no5 = no5;
		}
		
		
	}

}
