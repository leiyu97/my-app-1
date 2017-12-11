package com.larinia.app;

//import com.aspose.words.*;

import javax.print.Doc;

/**
 * Hello world!
 *
 */
public class App 
{
    static final String dataDir = "/data/case_download/";

    public static void main( String[] args )
    {
        try {
      //      Document doc = testShapeRender();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

  /*  public static Document testShapeRender() throws Exception {

        ImageSaveOptions imageOptions = new ImageSaveOptions(SaveFormat.JPEG);
        imageOptions.setScale(20.06f);
        imageOptions.setResolution(10.45f);


        Document doc = new Document(dataDir + "test.docx");

        Shape shape = new Shape(doc, ShapeType.IMAGE);


      //  ShapeRenderer renderer = new ShapeRenderer(shape);

      //  String outFile = dataDir + "testOut.jpg";

        // write it to disk
        //renderer.save(outFile, imageOptions);

        RenderShapeToDisk(dataDir, shape);

        // Document docNew = new Document(dataDir+"TestOut.jpg");


        //doc.save(outFile, imageOptions);

        // can we display it in the html page too?

       /* FileOutputStream stream = new FileOutputStream(dataDir + "TestOut.jpg");

        // Save the rendered image to the stream using different options.
        renderer.save(stream, imageOptions);



        stream.close();***

        return doc;
    }

    public static void RenderShapeToDisk(String dataDir, Shape shape) throws Exception
    {
        ShapeRenderer r = shape.getShapeRenderer();

        // Define custom options which control how the image is rendered. Render the shape to the JPEG raster format.
        ImageSaveOptions imageOptions = new ImageSaveOptions(SaveFormat.JPEG);

        imageOptions.setScale(1.5f);

        // Save the rendered image to disk.
        r.save(dataDir + "TestFile.RenderToDisk Out.jpg", imageOptions);

        System.out.println("Shape rendered to disk successfully.");
    }*/
}
