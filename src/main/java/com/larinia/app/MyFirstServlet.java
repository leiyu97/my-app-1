package com.larinia.app;

//import com.aspose.words.*;
//import com.aspose.words.Shape;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;


public class MyFirstServlet extends HttpServlet {
    private String message;
    static final String dataDir = "/data/case_download/";

    // private org.apache.log4j.Logger log4jlogger = org.apache.log4j.Logger.getLogger(this.getClass());

    //private Logger log = LoggerFactory.getLogger(this.getClass());
    public void init() throws ServletException {
        // Do required initialization
        message = "Hello World";
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        // Set response content type
        response.setContentType("text/html");
        System.out.println("MyFirstServlet.doGet: *************** in here**********************");

        // Actual logic goes here.
        PrintWriter out = response.getWriter();
    //    Document docNew = null;

        //
       try {
           //convertImageToPdf("/home/lyu/Downloads/images_totoro.jpg","/data/case_download/image_totoro.pdf");
    //       docNew = testShapeRender();
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.println("<h1> My First Sevlet </h1>");

        out.println("Testing shape Render at the moment\r\n");
    //    out.println(docNew);
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
}





