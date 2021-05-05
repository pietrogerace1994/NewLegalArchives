package com.ibm.la.mongo.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

import com.ibm.la.mongo.interfaces.DdsContentTOREMOVE;
import com.ibm.la.mongo.interfaces.DdsDocumentPermissionsTOREMOVE;
import com.ibm.la.mongo.interfaces.DdsPermissionTOREMOVE;

@Service("DdsManager")
public class DdsManagerTOREMOVE {

	private String authAddress;
	private String authAddressToken;
	private String clientId;
	private String clientSecret;
	private String grantType;
	
	
	private static final Logger logger = Logger.getLogger(DdsManagerTOREMOVE.class);


	public static Logger getLogger() {
		return logger;
	}

	public DdsManagerTOREMOVE(){
		logger.info("DdsManager() - Initializiation INIT ");
		InputStream is = null;
		try{
			is = DdsManagerTOREMOVE.class.getResourceAsStream("/config.properties");
			Properties properties = new Properties();
			properties.load(is);
			authAddress = properties.getProperty("keycloak.authAddress");
			authAddressToken = properties.getProperty("keycloak.authAddressToken");
			clientId = properties.getProperty("keycloak.clientId");
			clientSecret = properties.getProperty("keycloak.clientSecret");
			grantType = properties.getProperty("keycloak.grantType");
			
		}catch(Exception e){
			logger.error("DdsManager() - Initializiation ERROR - " + e.getMessage());
			e.printStackTrace();
		}
		logger.info("DdsManager() - Initializiation END ");
	}
	
	public String getToken() throws MalformedURLException, IOException {
		logger.debug("ddsManager.getToken() - INIT ");
		
		final JSONObject jsonToken = getDdsToken(authAddress + "/" + authAddressToken + "",
				"grant_type="+grantType+"&client_id="+clientId+"&client_secret=" + clientSecret + "");
		
		String token = jsonToken.get("access_token").toString();
		
		logger.info("Returning ddsToken: BearerTokenPlaceholder");
		logger.debug("ddsManager.getToken() - END ");
		return token;
	}
	
	private JSONObject getDdsToken(final String url, final String formrequest)
			throws MalformedURLException, IOException {

		InputStream is = null;
		try {
			final HttpsURLConnection conn = (HttpsURLConnection) new URL(null, url, new sun.net.www.protocol.https.Handler()).openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("charset", "utf-8");
			conn.setRequestProperty("Content-Length", Integer.toString(formrequest.length()));
			conn.setUseCaches(false);
			conn.setDoOutput(true);

			try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
				wr.writeBytes(formrequest);
			}
			is = conn.getInputStream();
			final BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			final String jsonText = readAll(rd);
			JSONObject jsonToken = new JSONObject(jsonText);
			return jsonToken;
			// return jsonText;
		} catch (final Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}
	
	public String insertDocument(final String objectStore, final String docTitle, final Map<String, Object> metadata, final String docClass,
								 final DdsDocumentPermissionsTOREMOVE perm, final InputStream is) throws IOException, JSONException {

		logger.info("ddsManager.insertDocument() - INIT ");
		
		
		DdsContentTOREMOVE ddsContent = new DdsContentTOREMOVE();
		ddsContent.setContent(is);
		ddsContent.setFileName(docTitle);
		
		final JSONObject jprop = getInsertPropertyJSON(metadata);
		
		jprop.put("OS", objectStore);
		jprop.put("documentalClass", docClass);
		jprop.put("createFolderIfNotExist", false);

		if (perm != null) {
			final JSONArray arrperm = getJSONPerm(perm);
			jprop.put("customPermissions", arrperm);
		}
		final String jsonstr = jprop.toString();

		logger.info("ddsManager.insertDocument() - jsonString: " + jsonstr);
		final String addr = getDdsEnvVal(DdsEnum.UPSERTDOC);
		String jsonText = null;
		if (ddsContent.getFileName() != null && ddsContent.getFileName().length() > 0) {
			jsonText = sendRequestMulti(addr + "/insertDocument", jsonstr, ddsContent);
		} else {
			jprop.put("folders", new JSONArray());
			jprop.put("folderClass", "");
			//jsonText = sendRequestAppJSON(addr + "/createDocument", jprop.toString(), token);
		}
		
		logger.info("Response from MongoLayer: " + jsonText);
		logger.info("ddsManager.insertDocument() - END ");
		return jsonText;
	}
	
	private JSONObject getInsertPropertyJSON(final Map<String, Object> metadata) throws JSONException {
		final JSONObject jprop = new JSONObject();
		final JSONArray jproparr = new JSONArray();
		getJSONFromMetadata(metadata, jprop, jproparr);
		if (jproparr.length() > 0) {
			jprop.put("customAttributes", jproparr);
		}
		return jprop;

	}
	
	private void getJSONFromMetadata(final Map<String, Object> metadata, final JSONObject jprop,
			final JSONArray jproparr) throws JSONException {
		if(metadata != null){
			for (final String key : metadata.keySet()) {
				if (getSystemMetadataNames().contains(key)) {
					if (jprop != null) {
						jprop.put(key, metadata.get(key).toString());
					}
				} else {
					final JSONObject item = new JSONObject();
					item.put("key", key);
					item.put("value", metadata.get(key).toString());
					jproparr.put(item);
				}
			}
		}
	}
	
	private JSONArray getJSONPerm(final DdsDocumentPermissionsTOREMOVE perm) throws JSONException {
		final JSONArray arrperm = new JSONArray();
		final Map<String, DdsPermissionTOREMOVE> pmap = perm.getUserPermission();
		for (final String key : pmap.keySet()) {
			final DdsPermissionTOREMOVE uperm = pmap.get(key);
			final JSONObject juperm = new JSONObject();
			juperm.put("id", key);
			juperm.put("type", "user");
			final JSONObject rwdp = new JSONObject();
			rwdp.put("r", uperm.getR());
			rwdp.put("w", uperm.getW());
			rwdp.put("d", uperm.getD());
			rwdp.put("p", uperm.getP());
			juperm.put("permissionList", rwdp);
			arrperm.put(juperm);
		}
		return arrperm;
	}
	
	public String sendRequestMulti(final String url, final String jsonString, final DdsContentTOREMOVE content)
			throws IOException, JSONException {

		final String token = getToken();
		final InputStream is = null;
		try {
			final HttpHeaders headers = new HttpHeaders();
			// for (String key: hmap.keySet()) {
			// headers.add(key, hmap.get(key));
			// }
			headers.add("Authorization", "Bearer " + token);
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);

			final RestTemplate restTemplate = new RestTemplate();
			final MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

			final HttpHeaders parts = new HttpHeaders();
			MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
			if (content.getContentMimeType() != null && content.getContentMimeType().trim().length() > 0) {
				mediaType = MediaType.parseMediaType(content.getContentMimeType());
			}
			parts.setContentType(mediaType);
			// parts.setContentType(contentType);
			// String fileName = "test.txt";
			// byte[] fileContent = "this is file content".getBytes();
			byte[] bytes = IOUtils.toByteArray(content.getContent());
			final ByteArrayResource byteArrayResource = new ByteArrayResource(bytes) {
				@Override
				public String getFilename() {
					return content.getFileName();
				}
			};
			final HttpEntity<ByteArrayResource> partsEntity = new HttpEntity<>(byteArrayResource, parts);

			body.add("document", jsonString);
			if (content.getFileName() != null && content.getFileName().length() > 0) {
				body.add("file", partsEntity);
			}
			final HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

			final ResponseEntity<String> ret = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
			
			
			return ret.toString();
		} catch (final Exception e) {
			logger.error(e.getMessage());
			throw(e);
		} finally {
			if (is != null) {
				is.close();
			}
			return null;
		}
		
	}
	
	/*
	public String sendRequestAppJSON(final String url, final String jsonString, final String token) throws IOException, JSONException {
		final HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		return sendRequest(url, jsonString, headers);
	}
	
	public String sendRequest(final String url, final String jsonString, final Map<String, String> hmap, final String token)
			throws IOException, JSONException {
		return sendRequest(url, jsonString, hmap, true, true);
	}
	
	public String sendRequest(final String url, final String jsonString, final Map<String, String> hmap,
			final boolean post, final boolean errflag, final String token) throws IOException, JSONException {

		
			final HttpHeaders headers = new HttpHeaders();
			for (final String key : hmap.keySet()) {
				headers.add(key, hmap.get(key));
			}
			headers.add("Authorization", "Bearer " + accTocken);
			final HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);
			final HttpMethod httpmethod = post ? HttpMethod.POST : HttpMethod.GET;
			final ResponseEntity<String> ret = restTemplate.exchange(url, httpmethod, entity, String.class);
			return ret.getBody();
		} catch (final ResourceAccessException e) {
			 if (errflag && e.getCause() instanceof NedRestException
					 	&& "Authentication failed".equals(((NedRestException)
				 e.getCause()).getDetails())) {
				 checkToken();
				 return sendRequest(url, jsonString, hmap, post, false);
			 }
			 throw e;
		}
	}*/
	
	
	private List<String> getSystemMetadataNames() {
		return Arrays.asList("name", "documentTitle", "id");
	}
	
	private String getDdsEnvVal(final DdsEnum varenum) {

		final String envval = System.getenv().getOrDefault(getDdsVarEnv(varenum), getDdsDefault(varenum));
		return envval;

	}
	
	public enum DdsEnum {
		OBJECT_STORE, AUTH_ADDRESS, DELETECUST, DELETEDOC, DELETEFOLDER, GETCUST, GETDOC, GETFOLDER, SECURMNG,
		UPSERTCUST, UPSERTDOC, UPSERTFOLDER, USERMNG, VERSIONDOC
	}
	
	String getDdsDefault(final DdsEnum addr) {
		switch (addr) {
			case OBJECT_STORE:
				return "GNL";
			case AUTH_ADDRESS:
				return "https://sso-ocp1-gc.snam.it";
			case DELETECUST:
				return "https://dds-deletecust-1550-gs.ocp1-q.dds-api.snam.it";
			case DELETEDOC:
				return "https://dds-deletedoc-1550-gs.ocp1-q.dds-api.snam.it";
			case DELETEFOLDER:
				return "https://dds-deletefolder-1550-gs.ocp1-q.dds-api.snam.it";
			case GETCUST:
				return "https://dds-getcust-1550-gs.ocp1-q.dds-api.snam.it";
			case GETDOC:
				return "https://dds-getdoc-1550-gs.ocp1-q.dds-api.snam.it";
			case GETFOLDER:
				return "https://dds-getfolder-1550-gs.ocp1-q.dds-api.snam.it";
			case SECURMNG:
				return "https://dds-securmng-1550-gs.ocp1-q.dds-api.snam.it";
			case UPSERTCUST:
				return "https://dds-upsertcust-1550-gs.ocp1-q.dds-api.snam.it";
			case UPSERTDOC:
				return "https://dds-upsertdoc-1550-gs.ocp1-q.dds-api.snam.it";
			case UPSERTFOLDER:
				return "https://dds-upsertfolder-1550-gs.ocp1-q.dds-api.snam.it";
			case USERMNG:
				return "https://dds-usermng-1550-gs.ocp1-q.dds-api.snam.it";
			case VERSIONDOC:
				return "https://dds-versiondoc-1550-gs.ocp1-q.dds-api.snam.it";
			default:
				return null;
		}
	}

	String getDdsVarEnv(final DdsEnum addr) {
		switch (addr) {
			case OBJECT_STORE:
				return "DDS_OBJECT_STORE";
			case AUTH_ADDRESS:
				return "DDS_AUTHORIZATION";
			case DELETECUST:
				return "DDS_DELETECUST";
			case DELETEDOC:
				return "DDS_DELETEDOC";
			case DELETEFOLDER:
				return "DDS_DELETEFOLDER";
			case GETCUST:
				return "DDS_GETCUST";
			case GETDOC:
				return "DDS_GETDOC";
			case GETFOLDER:
				return "DDS_GETFOLDER";
			case SECURMNG:
				return "DDS_SECURMNG";
			case UPSERTCUST:
				return "DDS_UPSERTCUST";
			case UPSERTDOC:
				return "DDS_UPSERTDOC";
			case UPSERTFOLDER:
				return "DDS_UPSERTFOLDER";
			case USERMNG:
				return "DDS_USERMNG";
			case VERSIONDOC:
				return "DDS_VERSIONDOC";
			default:
				return null;
		}
	}
	
	
	private static String readAll(final Reader rd) throws IOException {
		final StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
}
