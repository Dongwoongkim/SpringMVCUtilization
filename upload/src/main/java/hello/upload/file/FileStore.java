package hello.upload.file;

import hello.upload.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    // 업로드 되는 경로 + 파일이름
    public String getFullPath(String fileName)
    {
        return fileDir + fileName;
    }


    // 업로드 파일(오리지널 파일이름, 스토어에 올라갈 파일이름)
    // Form으로 부터 POST된 MultipartFile을 서버에 저장하고 UploadFile(오리지널파일이름, 스토어에 올라갈 파일이름) 리턴
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {

        if(multipartFile.isEmpty())
        {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFilename = createStoreFileName(originalFilename);

        // transferTo를 통해 파일 서버에 저장 (서버에 저장 할 때는 UUID+.+확장자 )
        multipartFile.transferTo(new File(getFullPath(storeFilename)));

        return new UploadFile(originalFilename,storeFilename);
    }
    private String createStoreFileName(String originalFilename) {

        int pos = originalFilename.lastIndexOf(".");
        String uuid = UUID.randomUUID().toString();
        String ext = originalFilename.substring(pos+1);
        return uuid + "." + ext; 
    }


    // ItemForm으로부터 POST 된 파일 리스트로부터 하나씩 iterate 해서 서버에 저장하고 Item List에 추가하고 리턴
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty())
            {
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }
}
