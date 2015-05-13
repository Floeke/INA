package com.floeke.connection;

import java.io.PrintWriter;

import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class INA1_P1_A1_DOMEcho {
	
	PrintWriter out;
	
	public INA1_P1_A1_DOMEcho(PrintWriter pw)
	{
		this.out = pw;
	}
	
	public void echo(Node n)
	{
		int type = n.getNodeType();
		switch(type)
		{
		case Node.ATTRIBUTE_NODE:
			out.print("ATTR: ");
			printlnCommon(n);
			break;
			
		case Node.DOCUMENT_TYPE_NODE:
			out.print("DOC_TYPE: ");
			printlnCommon(n);
			NamedNodeMap nodeMap = ((DocumentType)n).getEntities();
			for(int i=0; i<nodeMap.getLength(); i++)
			{
				echo(nodeMap.item(i));
			}
			break;
			
		case Node.ELEMENT_NODE:
			out.print("ELEM: ");
			printlnCommon(n);
			NamedNodeMap atts = n.getAttributes();
			for(int i=0; i<atts.getLength(); i++)
			{
				echo(atts.item(i));
			}
			break;
			
		case Node.TEXT_NODE:
			out.print("TEXT:");
			printlnCommon(n);
			break;
			
		default:
			out.print("UNSUPPORTED NODE: " + type);
			printlnCommon(n);
			break;
		}
		
		for(Node child = n.getFirstChild(); child != null; child = child.getNextSibling())
			echo(child);
	}
	
	private void printlnCommon(Node n) {
		
		out.print(" nodeName=\"" + n.getNodeName() + "\"");
		String val = n.getNamespaceURI();
		if (val != null)
			out.print(" uri=\"" + val + "\"");
		val = n.getPrefix();
		if (val != null)
			out.print(" pre=\"" + val + "\"");
		val = n.getLocalName();
		if (val != null)
			out.print(" local=\"" + val + "\"");
		val = n.getNodeValue();
		if (val != null) {
			out.print(" nodeValue=");
			if (val.trim().equals("")) {
				// war nur Whitespace
				out.print("[WS]");
			} else {
				out.print("\"" + n.getNodeValue() + "\"");
			}
		}
		out.println();
	}

}
