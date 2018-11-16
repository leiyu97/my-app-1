package com.larinia.app;

//import com.aspose.words.*;
//import com.aspose.words.Shape;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
//import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.TransactionManager;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class MyFirstServlet extends HttpServlet {
    private String message;
    static final String dataDir = "/data/case_download/";
    @Resource(name = "java:/TransactionManager")
    private TransactionManager transactionManager;
    // private org.apache.log4j.Logger log4jlogger = org.apache.log4j.Logger.getLogger(this.getClass());

    private static final Logger logger = Logger.getLogger(MyFirstServlet.class);

    public void init() throws ServletException {
        // Do required initialization
        message = "Hello World";
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        // Set response content type
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        logger.info("MyFirstServlet.doGet: time start is " + formatter.format(System.currentTimeMillis()));
        System.out.println("MyFirstServlet.doGet: thread sleep: start ");

       /* int DELAY= 60000;
        boolean RANDOMIZE = false;
        //    Document docNew = null;
        if (DELAY > 0){
            int delay;
            if (RANDOMIZE){

                delay = (int) Math.random();
            } else {
                delay = DELAY;
            }
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e1) {
                logger.error(e1);
            }
        }*/

        //  System.out.println("MyFirstServlet.doGet: thread sleep: stop "+formatter.format(System.currentTimeMillis()));
        response.setContentType("text/html");

        // request.setCharacterEncoding("UTF-8");

        System.out.println("MyFirstServlet.doGet: request URI " + request.getRequestURI());
        System.out.println("MyFirstServlet.doGet: reuqest URL" + request.getRequestURL());


        System.out.println("MyFirstServlet.doGet: reuqest get parameter: " + request.getParameter("NOTIZ1"));


        logger.warn("test the method to see if it works");

        logger.error("MyFirstServlet.doGet:log4j error test");
        logger.error("test the method to see if it works error test");
        logger.error("log4j implement title: " + logger.getClass().getPackage().getImplementationTitle());
        logger.error("log4j implement vendor: " + logger.getClass().getPackage().getImplementationVendor());
        logger.error("log4j implement version: " + logger.getClass().getPackage().getImplementationVersion());


        System.out.println("MyFirstServlet.doGet: *************** in here**********************");

        System.out.println(logger.getClass().getPackage().getImplementationTitle());
        System.out.println(logger.getClass().getPackage().getImplementationVendor());
        System.out.println(logger.getClass().getPackage().getImplementationVersion());

       /* String lStrNameMarathi1 = request.getParameter("txtNameInMarathi");
        logger.info("lStrNameMarathi1 from request:"+lStrNameMarathi1);
        byte[] bytes = lStrNameMarathi1.getBytes("ISO-8859-1");
        String lStrNameMarathi = new String(bytes, "UTF-8");

        logger.info("lStrNameMarathi gayathri from bytes:"+lStrNameMarathi);​*/
        // Actual logic goes here.


        PrintWriter out = response.getWriter();

        //
        try {
            //convertImageToPdf("/home/lyu/Downloads/images_totoro.jpg","/data/case_download/image_totoro.pdf");
            //       docNew = testShapeRender();
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.println("<h1> My First Sevlet </h1>");

        out.println("<b>Testing shape Render at the moment</b><br/>");
        out.println("<b>System properties: protocol is </b> " + System.getProperty("https.protocols") + "<br>");
        out.println("<b>System properties: jdk.tls is </b> " + System.getProperty("jdk.tls.client.protocols") + "<br>");
        //    out.println(docNew);
        out.println("<b>The words in url is </b> " + request.getParameter("NOTIZ1"));

        logger.info("MyFirstServlet.doGet: time end is " + formatter.format(System.currentTimeMillis()));
        out.close();
    }

    public void destroy() {
        // do nothing.

    }

   /* static {
        try {
            testShapeRender();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

  /*  public static Document testShapeRender() throws Exception {



        ImageSaveOptions imageOptions = new ImageSaveOptions(SaveFormat.PDF);
        imageOptions.setScale(20.06f);
        imageOptions.setResolution(10.45f);


        Document doc = new Document(dataDir + "test.docx");

        Shape shape = new Shape(doc, ShapeType.IMAGE);


        ShapeRenderer renderer = new ShapeRenderer(shape);

        String outFile = dataDir + "testOut.jpg";

        // write it to disk
        renderer.save(outFile, imageOptions);

       // Document docNew = new Document(dataDir+"TestOut.jpg");


        //doc.save(outFile, imageOptions);

        // can we display it in the html page too?

       /* FileOutputStream stream = new FileOutputStream(dataDir + "TestOut.jpg");

        // Save the rendered image to the stream using different options.
        renderer.save(stream, imageOptions);



        stream.close();

        return doc;
    }*/

    public static String getDataDir(Class c) {
        File dir = new File(System.getProperty("user.dir"));
        dir = new File(dir, "src");
        dir = new File(dir, "main");
        dir = new File(dir, "resources");

        for (String s : c.getName().split("\\.")) {
            dir = new File(dir, s);
            if (dir.isDirectory() == false)
                dir.mkdir();
        }
        System.out.println("Using data directory: " + dir.toString());
        return dir.toString() + File.separator;
    }

    /**
     * Converts an image to PDF using Aspose.Words for Java.
     *
     * @param inputFileName  File name of input image file.
     * @param outputFileName Output PDF file name.
     */
  /*  public static void convertImageToPdf(String inputFileName, String outputFileName) throws Exception {
        // Create Aspose.Words.Document and DocumentBuilder.
        // The builder makes it simple to add content to the document.
        System.out.println("MyFirstServlet.convertImageToPdf: ###################### in here ########################");
        System.out.println("MyFirstServlet.convertImageToPdf: input file name is " + inputFileName);
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);


        // Load images from the disk using the approriate reader.
        // The file formats that can be loaded depends on the image readers available on the machine.
        ImageInputStream iis = ImageIO.createImageInputStream(new File(inputFileName));
        ImageReader reader = ImageIO.getImageReaders(iis).next();
        reader.setInput(iis, false);

        try {
            // Get the number of frames in the image.
            int framesCount = reader.getNumImages(true);

            // Loop through all frames.
            for (int frameIdx = 0; frameIdx < framesCount; frameIdx++) {
                // Insert a section break before each new page, in case of a multi-frame image.
                if (frameIdx != 0)
                    builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);

                // Select active frame.
                BufferedImage image = reader.read(frameIdx);

                // We want the size of the page to be the same as the size of the image.
                // Convert pixels to points to size the page to the actual image size.
                PageSetup ps = builder.getPageSetup();

                ps.setPageWidth(ConvertUtil.pixelToPoint(image.getWidth()));
                ps.setPageHeight(ConvertUtil.pixelToPoint(image.getHeight()));

                // Insert the image into the document and position it at the top left corner of the page.
                builder.insertImage(
                        image,
                        RelativeHorizontalPosition.PAGE,
                        0,
                        RelativeVerticalPosition.PAGE,
                        0,
                        ps.getPageWidth(),
                        ps.getPageHeight(),
                        WrapType.NONE);
            }
        } finally {
            if (iis != null) {
                iis.close();
                reader.dispose();
            }
        }

        // Save the document to PDF.
        doc.save(outputFileName);
    }*/
    public void callRemoteURL() throws IOException {

        HttpHost proxy = new HttpHost("127.0.0.1", 8080, "https");

        // DefaultHttpClient httpclient = new SystemDefaultHttpClient();
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {

            httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

            HttpHost target = new HttpHost("issues.apache.org", 443, "https");
            HttpGet req = new HttpGet("/");

            System.out.println("executing request to " + target + " via " + proxy);
            HttpResponse rsp = httpclient.execute(target, req);
            HttpEntity entity = rsp.getEntity();

            System.out.println("----------------------------------------");
            System.out.println(rsp.getStatusLine());
            Header[] headers = rsp.getAllHeaders();
            for (int i = 0; i < headers.length; i++) {
                System.out.println(headers[i]);
            }
            System.out.println("----------------------------------------");

            if (entity != null) {
                System.out.println(EntityUtils.toString(entity));
            }

        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
    }
}





