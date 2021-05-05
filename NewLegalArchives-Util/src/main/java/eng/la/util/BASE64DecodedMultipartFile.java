package eng.la.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public class BASE64DecodedMultipartFile implements MultipartFile {

	private final byte[] imgContent;
	private String originalFileName;
	private String name;
	private String contentType;
	
	

    public BASE64DecodedMultipartFile(byte[] imgContent)
    {
       this.imgContent = imgContent;
        }
    
    

    public BASE64DecodedMultipartFile(byte[] imgContent, String originalFileName, String name, String contentType) {
		super();
		this.imgContent = imgContent;
		this.originalFileName = originalFileName;
		this.name = name;
		this.contentType = contentType;
	}



	@Override
        public String getName()
    {
           // TODO - implementation depends on your requirements 
               return name;
    }

    @Override
        public String getOriginalFilename()
    {
        // TODO - implementation depends on your requirements
            return originalFileName;
    }

    @Override
    public String getContentType()
    {
        // TODO - implementation depends on your requirements
        return contentType;
    }

    @Override
    public boolean isEmpty()
    {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize()
    {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException
    {
        return imgContent;
    }

    @Override
    public InputStream getInputStream() throws IOException
    {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException
    { 
        new FileOutputStream(dest).write(imgContent);
    }

}
