package org.larinia.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class ChangeAlbumName {

    // class for change the album name for ogg files

    String listCommand = "/usr/bin/vorbiscomment -l ";
    String changeCommand = "/usr/bin/vorbiscomment -w ";
    private static String correctAlbumName;


    public static void main(String[] args) {

        ChangeAlbumName can = new ChangeAlbumName();

        //list the directory, pass in as an args

        String directoryPath = args[0];
        System.out.println("ChangeAlbumName.main: direcotyr is " + directoryPath);

        if (directoryPath == null || directoryPath.isEmpty()) {
            System.out.println("$0 Directory must not be empty");
            // throw RuntimeException("First args directory must not be empty");
        }



        File directory = new File(directoryPath);

        correctAlbumName = directory.getName();
        System.out.println("ChangeAlbumName.main:  album name is "+correctAlbumName);
        File[] contents = directory.listFiles();

        if (contents != null && contents.length != 0) {
            for (File f : contents) {
                // System.out.println(f.getAbsolutePath());
                try {
                    //list Ogg comment file by file, capture the comments
                    StringBuilder sb = can.listOggComment(f.getName(), directory);
                    System.out.println("ChangeAlbumName.main: sb is ");
                    System.out.println(sb.toString());




                    // change the album description.
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

   // list and correct the description
    public StringBuilder listOggComment(String oggFileName, File workingFolder) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        Runtime r = Runtime.getRuntime();


        // Process p = r.exec(listCommand + oggFileName);
        String finalName = "\""+oggFileName+"\"";

        System.out.println(">>>>>> just in " + finalName);

        ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", "/usr/bin/vorbiscomment -l " + finalName);
        pb.directory(workingFolder);


        pb.redirectError();
        Process proc = pb.start();

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

        // read the output from the command
        System.out.println("Here is the standard output of the command:\n");
        String s = null;
        while ((s = stdInput.readLine()) != null) {
            System.out.println("before change "+s);

            //compare the directory name with the album name, if different, then loga message then
            if (!s.isEmpty() && s.contains("ALBUM=")) {
                if (!s.equals(correctAlbumName) ) {
                    s = "ALBUM="+correctAlbumName;
                }
            }
            System.out.println("s is now >>> "+s);
            stringBuilder.append(s);
            stringBuilder.append("\n");
        }

        System.out.println("ChangeAlbumName.listOggComment end");

        return stringBuilder;
    }

    // need to get the comment files in as well
    public void changeOggComment(String oggFileName, File workingFolder, StringBuilder commentTxt) throws IOException {
        Runtime r = Runtime.getRuntime();
        //    Process p = r.exec(changeCommand + oggFileName);

//        StringBuilder stringBuilder = new StringBuilder();

        File commentFile = new File (commentTxt.toString());
        ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", "/usr/bin/vorbiscomment -w -f " + commentFile);

        pb.directory(workingFolder);


        pb.redirectError();
        Process proc = pb.start();

    }

}
