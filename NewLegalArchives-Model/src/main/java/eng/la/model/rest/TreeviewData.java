package eng.la.model.rest;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TreeviewData {

	private String nodeid;
	private String text;
	private List<String> tags;
	private String icon;
	private List<TreeviewData> nodes;
	private String type;
	private String href;
	private String isPenale;
	private String nodeState;
	public TreeviewData() {
	
	}
	
	
	
	public String isPenale() {
		return isPenale;
	}



	public void setPenale(String isPenale) {
		this.isPenale = isPenale;
	}
	
	public TreeviewData(String id, String text, String tags, String icon, List<TreeviewData> nodes, String type, String href, String nodeState) {
		super();
		this.href = href;
		this.nodeid = id;
		this.nodeid = this.nodeid.replaceAll("\\{", "").replaceAll("\\}","");
		this.text = text;
		if( tags != null ){
			this.tags = new ArrayList<String>();
			this.tags.add(tags);
		}
		this.icon = icon;
		this.nodeState = nodeState;
		this.nodes = nodes;
		this.type=type;
		
	}



	public TreeviewData(String id, String text, String tags, String icon, List<TreeviewData> nodes, String type, String href, boolean isPenale) {
		super();
		this.href = href;
		this.nodeid = id;
		this.nodeid = this.nodeid.replaceAll("\\{", "").replaceAll("\\}","");
		this.text = text;
		if( tags != null ){
			this.tags = new ArrayList<String>();
			this.tags.add(tags);
		}
		this.icon = icon;
		this.nodes = nodes;
		this.type=type;
		
		if(isPenale)
			this.isPenale="1";
		else this.isPenale="0";
	}

	public String getHref() {
		return href != null ? href : "javascript:$('#containerLiAzioni"+type+nodeid+"').toggle();caricaAzioniNodoContenuto('"+type+"','"+nodeid+"', '"+isPenale+"')";
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getNodeId() {
		return nodeid;
	}

	public void setNodeId(String id) {
		this.nodeid = id;
		this.nodeid = this.nodeid.replaceAll("\\{", "").replaceAll("\\}","");
	}

	public String getText() {
		String result = "";
		
		result = "<span class=\'text\'>"+text+"</span>"
		+"<div class=\"pull-left\" style=\"left: 163px;position: relative;top: 20px;\">"
		+"<ul class=\"actions\">"
			+"<li class=\"dropdown\">"
				+"<a href=\"\" data-toggle=\"dropdown\" aria-expanded=\"false\" style=\"display:none\">"
					+"<i class=\"zmdi zmdi-more-vert\"></i>"
				+"</a>"
				+"<ul data-idoggetto=\""+nodeid+"\"  data-tipooggetto=\""+type+"\" class=\"dropdown-menu dropdown-menu-left\" id=\"containerLiAzioni"+type+nodeid+"\">"
				+"<li><a href=#>Loading....</a></li>"
				+"</ul>"
			+"</li>"
		+"</ul>"
		+ "</div>";
		
		if(nodeState != null){
			result += "<div class=\'pull-left\' style=\'right:5%; position: absolute; top:27%;\'>";
			result += "<span class=\'badge\' style=\'background-color:orange\'>"+nodeState+"</span>";
			result += "</div>";
		}
		return result;
		
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<String> getTags() {
		if (this.tags == null && this.nodes != null ){
			this.tags = new ArrayList<String>();
			this.tags.add(nodes.size()+"");
			return this.tags;
			
		}
		if( this.tags != null ){
			return this.tags;
		}
		
		return null;
		
			
	}

	public void setTags(List<String> tags) {
		this.tags = tags;  
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<TreeviewData> getNodes() {
		return nodes;
	}

	public void setNodes(List<TreeviewData> nodes) {
		this.nodes = nodes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}



	public String getNodeState() {
		return nodeState;
	}



	public void setNodeState(String nodeState) {
		this.nodeState = nodeState;
	}

}
