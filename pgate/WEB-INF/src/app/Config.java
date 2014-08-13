package app;

import java.io.File;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Config {

	private static HashMap<String, String> config = new HashMap<String, String>();

	public static void setAllConfig() {

		String path = "/var/www/pg_keys/config.xml"; // Live
		path = "/home/dipak.mali/icici-key/config.xml";// Development Server

		try {
			File file = new File(path);

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			NodeList nodeLst = doc.getElementsByTagName("payment_gateway");

			for (int s = 0; s < nodeLst.getLength(); s++) {

				Node fstNode = nodeLst.item(s);

				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

					Element fstElmnt = (Element) fstNode;

					/************************ DB Config **************************************/
					NodeList db_host = fstElmnt.getElementsByTagName("db_host");
					Element dbHostElmnt = (Element) db_host.item(0);
					NodeList dbHostVal = dbHostElmnt.getChildNodes();
					String DB_HOST = (dbHostVal.item(0)).getNodeValue();
					// System.out.println("DB_HOST : " + DB_HOST);// /

					NodeList db_port = fstElmnt.getElementsByTagName("db_port");
					Element dbPortElmnt = (Element) db_port.item(0);
					NodeList dbPortVal = dbPortElmnt.getChildNodes();
					String DB_PORT = (dbPortVal.item(0)).getNodeValue();
					// System.out.println("DB_PORT : " + DB_PORT);// /

					NodeList db_user = fstElmnt.getElementsByTagName("db_user");
					Element dbUserElmnt = (Element) db_user.item(0);
					NodeList dbUserVal = dbUserElmnt.getChildNodes();
					String DB_USER = (dbUserVal.item(0)).getNodeValue();
					// System.out.println("DB_USER : " + DB_USER);// /

					NodeList db_password = fstElmnt
							.getElementsByTagName("db_password");
					Element passWdElmnt = (Element) db_password.item(0);
					NodeList passWdElmntVal = passWdElmnt.getChildNodes();
					String DB_PASSWORD = (passWdElmntVal.item(0))
							.getNodeValue();
					// System.out.println("DB_PASSWORD : " + DB_PASSWORD);// /

					NodeList db_name = fstElmnt.getElementsByTagName("db_name");
					Element dbNameElmnt = (Element) db_name.item(0);
					NodeList dbNameVal = dbNameElmnt.getChildNodes();
					String DB_NAME = (dbNameVal.item(0)).getNodeValue();
					// System.out.println("DB_NAME : " + DB_NAME);// /
					/************************ DB Config **************************************/

					/************************ EServe **************************************/
					NodeList eserve_resource = fstElmnt
							.getElementsByTagName("eserve_resource_file");
					Element eServerElmnt = (Element) eserve_resource.item(0);
					NodeList eServerVal = eServerElmnt.getChildNodes();
					String ESERVE_RESOURCE = (eServerVal.item(0))
							.getNodeValue();

					NodeList eserve_action = fstElmnt
							.getElementsByTagName("eserve_action");
					Element actionElmnt = (Element) eserve_action.item(0);
					NodeList actionVal = actionElmnt.getChildNodes();
					String ESERVE_ACTION = (actionVal.item(0))
							.getNodeValue();

					NodeList eserve_alias = fstElmnt
							.getElementsByTagName("eserve_alias");
					Element aliasElmnt = (Element) eserve_alias.item(0);
					NodeList aliasVal = aliasElmnt.getChildNodes();
					String ESERVE_ALIAS = (aliasVal.item(0)).getNodeValue();
					/************************ EServe **************************************/

					
					 NodeList log_path = fstElmnt.getElementsByTagName("log_path"); Element
					 logPathElmnt = (Element) log_path.item(0); NodeList
					 logPathVal = logPathElmnt.getChildNodes(); 
					 String LOG_PATH = (logPathVal.item(0)).getNodeValue(); 

					/************************ LOG PATH **************************************/
					 
					NodeList site_path = fstElmnt.getElementsByTagName("site_path");
					Element sitePathElmnt = (Element) site_path.item(0);
					NodeList sitePathVal = sitePathElmnt.getChildNodes();
					String SITE_PATH = (sitePathVal.item(0)).getNodeValue();
					 //System.out.println("SITE_PATH : " + SITE_PATH);// /
					/************************ SITE PATH **************************************/

					/************************ ICICI **************************************/
					NodeList icici_key_dir_path = fstElmnt
							.getElementsByTagName("icici_key_dir_path");
					Element iciciKeyElmnt = (Element) icici_key_dir_path
							.item(0);
					NodeList iciciKeyVal = iciciKeyElmnt.getChildNodes();
					String ICICI_KEY_PATH = (iciciKeyVal.item(0))
							.getNodeValue();
					// System.out.println("ICICI KEY PATH : " +
					// ICICI_KEY_PATH);// /
					/************************ ICICI **************************************/
					
					/************************ ICICI **************************************/
					NodeList billDeskURLElem = fstElmnt.getElementsByTagName("billDeskURL");
					Element billDeskURLElement = (Element) billDeskURLElem.item(0);
					NodeList billDeskURLVal = billDeskURLElement.getChildNodes();
					String billDeskURL = (billDeskURLVal.item(0)).getNodeValue();
					// System.out.println("ICICI KEY PATH : " +
					// ICICI_KEY_PATH);// /
					/************************ ICICI **************************************/
					
					

					config.put("DB_HOST", DB_HOST);
					config.put("DB_PORT", DB_PORT);
					config.put("DB_USER", DB_USER);
					config.put("DB_PASSWORD", DB_PASSWORD);
					config.put("DB_NAME", DB_NAME);

					config.put("ESERVE_RESOURCE", ESERVE_RESOURCE);
					config.put("ESERVE_ACTION", ESERVE_ACTION);
					config.put("ESERVE_ALIAS", ESERVE_ALIAS);

					config.put("BILLDESK_URL", billDeskURL);
					
					config.put("LOG_PATH", LOG_PATH);
					config.put("SITE_PATH", SITE_PATH);
					config.put("ICICI_KEY_DIR_PATH", ICICI_KEY_PATH);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String get(String key) {
		String returnVal = config.get(key);
		return returnVal;
	}

	public static HashMap<String, String> getAll() {
		return config;
	}
}
