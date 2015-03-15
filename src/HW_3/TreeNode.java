package HW_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TreeNode{
	public String attributeName;
	public ArrayList<Probability> classProbability; 
	public String prevAttributeValue;
	public Map<String,TreeNode> children=new HashMap<String, TreeNode>();
}

