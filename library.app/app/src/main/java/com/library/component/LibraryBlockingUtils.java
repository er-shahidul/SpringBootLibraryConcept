package com.library.component;


import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Arrays;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.io.FilenameFilter;
import lombok.extern.slf4j.Slf4j;
import java.nio.file.StandardOpenOption;
import java.nio.channels.AsynchronousFileChannel;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @author Shahidul Hasan
 * @developer Shahidul Hasan
 * class LibraryBlockingUtils
 * Service
 */
@Slf4j
public class LibraryBlockingUtils {
    /**
     * @param request example can be obtained from parameter injection
     * @return the URL
     */
    public static String baseUrl(ServerHttpRequest request){
        URI uri = request.getURI();
        String port = "";
        if(uri.getPort()!=80){
            port = String.format(":%s", uri.getPort());
        }
        return String.format("%s://%s%s", uri.getScheme(), uri.getHost(), port);
    }

    /**
     * @param filePart example can be obtained from @RequestPart("file") FilePart
     * @param folder example :
     *               ./folder : parent folder,
     *               /folder : to system root folder
     * @return true if succeed false otherwise
     */
    public static Boolean createFile(FilePart filePart, String folder, String fileName) {
        try{
            String fullPath = folder+"/"+fileName;
            Path path = Files.createFile(Paths.get(fullPath).toAbsolutePath().normalize());
            AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE );
            DataBufferUtils.write(filePart.content(), channel, 0)
                    .doOnComplete(() -> {
                        try {
                            channel.close();
                        } catch ( Exception e ) {
                            e.printStackTrace();
                        }
                    })
                    .subscribe()
            ;
            return true;
        }catch (IOException e){
            log.error("ERROR CreateFile: {}", e.getMessage());
        }
        return false;
    }

    public static String docsDirectoryCreate(String docRootDirectory, String institute, String merchant) {

        if(institute.length()>4){
            institute = institute.substring(1, 5);
        }

        String docLoc = "";
        String MMS_DOC = docRootDirectory;
        writeDirectory(MMS_DOC);

        String instituteDocLocation = docRootDirectory+"/"+institute;
        writeDirectory(instituteDocLocation);

        File instituteDocs = new File(instituteDocLocation);
        String[] instituteDocLocations = getStrings(instituteDocs);
        Integer instituteDocLocationMaxValue;
        List instituteDocList = null;

        if(instituteDocLocations.length < 1 ){
            writeDirectory(instituteDocLocation+"/1");
            instituteDocLocationMaxValue = 1;
        }else {
            instituteDocList = Arrays.asList(instituteDocLocations);
            instituteDocLocationMaxValue = Integer.valueOf(String.valueOf(Collections.max(instituteDocList)));
        }

        File instituteDocMaxLocationsFiles = new File(instituteDocLocation+"/"+instituteDocLocationMaxValue);
        String[] maxLocationFiles = getStrings(instituteDocMaxLocationsFiles);

        if(maxLocationFiles.length>2000||maxLocationFiles.length==2000) {
            Integer instituteDocLocationNextValue = instituteDocLocationMaxValue+1;
            docLoc = writeDirectory(instituteDocLocation + "/" + instituteDocLocationNextValue);
        }else {
            docLoc = instituteDocLocation+"/"+String.valueOf(instituteDocLocationMaxValue);
        }

        return writeDirectory(docLoc+"/"+merchant);
    }

    public static String[] getStrings(File file1) {
        String[] directories = file1.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        return directories;
    }

    public static String writeDirectory(String location) {
        File directory = new File(location);
        if (!directory.exists()) {
            directory.mkdir();
        }

        return directory.getPath();
    }
}