/*
 * Copyright (c) 2007 Allianz Global Risks
 *
 * This software is confidential and proprietary information of Allianz.
 * You shall not disclose such confidential information and shall use it only
 * in accordance with the terms of the agreements you entered into with Allianz.
 */
package com.larinia.app;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
//import org.jboss.logging.Logger;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Upload of SOV Spreadsheet.
 * 
 * @author amerk
 * @version $Id$
 * 
 */
public class UploadServlet extends HttpServlet {

	/***/
	private static final long serialVersionUID = 8441842423826297368L;

	/** Maximum size of uploads: 10 MB. */
	private static final long MAX_REQUEST_SIZE = 10 * 1024 * 1024;

	/** the default logger. */
	private static Logger logger = Logger.getLogger(UploadServlet.class);

	/** Error constant for storing error messages in session. */
	public static final String SESSION_ERROR = "LDM_UPLOAD_ERROR";

	/** A often used string in this file. */
	private static final String EXCEPTION = "Exception in Front Office!";

	/** parameter encoding. */
	public static final String PARAM_ENC = "UTF-8";

	/**
	 * Constructor of the object.
	 */
	public UploadServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy();
	}

	/**
	 * The doGet method of the servlet. <br>
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StringBuffer paramValue = new StringBuffer();
		String originalFileName = null;
		String fileName = null;
		System.out.println("debug1");

		// detect the temporary file folder
		File tempDir = (File) getServletContext().getAttribute(
				"javax.servlet.context.tempdir");
		System.out.println("debug2");

		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(tempDir);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		System.out.println("debug3");

		// Set overall request size constraint
		upload.setSizeMax(MAX_REQUEST_SIZE);

		// reset errors
		setError(request, null);
		System.out.println("debug4");

		File tempFile = null;
		System.out.println("debug5");
		try {

			// Parse the request
			/*Enumeration<String> parameterNames = request.getParameterNames();
	        while (parameterNames.hasMoreElements()) {
	            String paramName = parameterNames.nextElement();
	            System.out.println(paramName);
	            System.out.println("n");
	            String[] paramValues = request.getParameterValues(paramName);
	            for (int i = 0; i < paramValues.length; i++) {
	                String paramValue1 = paramValues[i];
	                System.out.println("t" + paramValue1);
	                System.out.println("n");
	            }
	        }*/
			/*Map<String, List<FileItem>> itemsMap = upload.parseParameterMap(request);*/
            List<FileItem> items = upload.parseRequest(request);
           	System.out.println("" +items);
			System.out.println("debug6");
			System.out.println("debug7");
			for(FileItem item : items)
			 {
					if (item.isFormField()) {
					paramValue.append('&').append(item.getFieldName()).append(
							'=').append(
							URLEncoder.encode(item.getString(), PARAM_ENC));
							
				} else {
					/*if (item.getSize() > 0) {*/
						if (item.getSize() > 0) {

						// uplaod file to document management
						byte[] excelFile = item.get();
						String name = item.getName();
						System.out.println("debug8");

						// IE returns always the complete location path of the
						// file.
						// Only the filename is required.
						if (name.contains("\\")) {
							int lastIndex = name.lastIndexOf("\\");
							name = name.substring(lastIndex + 1);
						}

						// COR-6641
						checkFileName(name);
						System.out.println("debug9");

						//ProgramVersion pv = (ProgramVersion) Component
						//		.getInstance("programVersion");
					//	String userName = Identity.instance().getUsername();
						System.out.println("debug10");
						// create temp file
						tempFile = File.createTempFile("locmgmt.", ".upl",
								tempDir);
						System.out.println("debug11");
						if (!(item.getName().toLowerCase().endsWith(".xls") || item.getName().toLowerCase().endsWith(".xlsx"))) {
							setError(request, "No excel file selected: '"
									+ originalFileName + "'");
							break;
						} else {
							item.write(tempFile);
						}
                     //LAY comment out
					//	DocumentManagementHandler dmh = (DocumentManagementHandler) Component
					//			.getInstance("documentmanagementhandler");
						originalFileName = name;

						// mockup
					/*	if (userName.equalsIgnoreCase("test")) {
							logger
									.info("Using mockup upload, skipping document management...");
							dmh.setFileURL(tempFile.getName(), tempFile
									.getAbsolutePath());
						} else {
							// upload to document management
							try {
								fileName = dmh.sendFile(pv
										.getProgramVersionID(), name, userName,
										excelFile);
							} catch (FOException foe) {
								setError(request, EXCEPTION + " Error: '"
										+ foe.getCause().getMessage() + "'");
							} catch (Exception e) {
								logger.error(EXCEPTION, e);
								setError(request,
										"Excel file could not be send! Error: '"
												+ e.getMessage() + "'");

							}
						}*/

						// set file to keep working: COR-6641
						fileName = tempFile.getAbsolutePath();

						/*
						 * tempFile = File.createTempFile("locmgmt.", ".upl",
						 * tempDir); fileName = tempFile.getAbsolutePath(); if
						 * (item.getFieldName().equals(UPLOAD_FIELD_NAME)) {
						 * originalFileName = item.getName(); if
						 * (!item.getName().endsWith(".xls")) {
						 * setError(request, "No excel file selected: '" +
						 * originalFileName + "'"); break; } }
						 * item.write(tempFile);
						 */
					} else {
						setError(request, "No file selected");
						break;
					}
				}
			}
		} catch (ServletException se) {
			// error
			logger.error(se.getMessage());
			setError(request, "Cannot upload file: " + se.getMessage());
		} catch (FileUploadException fue) {
			// error
			logger.error(fue.getMessage());
//			setError(request, "Cannot upload file: " + fue.getMessage());
		} catch (Exception e) {
			logger.error("Error uploading file", e);
			//String msg = e.getMessage();

			// error
			/*if (msg == null || msg.equals("")) {
				logger.error(e.getCause().getMessage());
			} else {
				logger.error(msg);
			}*/

			setError(request, "Error uploading file: " + e.getMessage());
		}

		// return the result file
		List<String> fileParameters = new ArrayList<String>();
		if (fileName != null) {
			fileParameters.add("fileName="
					+ URLEncoder.encode(fileName, PARAM_ENC));
		}
		if (originalFileName != null) {
			fileParameters.add("originalFileName="
					+ URLEncoder.encode(originalFileName, PARAM_ENC));
		}
		if (tempFile != null) {
			fileParameters.add("tempFile="
					+ URLEncoder.encode(tempFile.getAbsolutePath(), PARAM_ENC));
		}
		response.sendRedirect(request.getContextPath()
				+ "/importsov/importSoV.seam?"
				+ StringUtils.join(fileParameters.iterator(), "&")
				+ paramValue.toString());
	}

	private void checkFileName(String fileName) throws ServletException {

		logger.error("Checking upload file: " + fileName);

		// chars to be checked for
		char[] searchChars = { '#', '%', '&', '*', ':', '<', '>', '?', '/',
				'{', '|', '}' };

		// check SOV file name for forbidden characters
		if (StringUtils.containsAny(fileName, searchChars)) {
			throw new ServletException(
					"due to technical restrictions, the file name may not contain the following characters: '#' '%' '&' '*' ':' '<' '>' '?' '/' '{' '|' '}'");
		}
	}

	/**
	 * Sets a error occurred while loading a file.
	 * 
	 * @param request
	 *            the request
	 * @param msg
	 *            the message
	 */
	private void setError(HttpServletRequest request, String msg) {
		HttpSession session = request.getSession(true);
		session.setAttribute(SESSION_ERROR, msg);
	}
}
