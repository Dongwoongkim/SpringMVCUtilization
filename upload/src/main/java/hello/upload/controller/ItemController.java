package hello.upload.controller;

import hello.upload.domain.Item;
import hello.upload.domain.ItemForm;
import hello.upload.domain.ItemRepository;
import hello.upload.domain.UploadFile;
import hello.upload.file.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.UriUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemRepository itemRepository;
    private final FileStore fileStore;

    // Item 등록 폼 Get
    @GetMapping("/items/new")
    public String newItem(@ModelAttribute ItemForm form)
    {
        return "item-form";
    }

    // Item 등록 폼을 통해 POST
    @PostMapping("/items/new")
    public String saveItem(@ModelAttribute ItemForm form, RedirectAttributes redirectAttributes) throws IOException {

        // Form으로부터 Post된 파일을 스토어에 저장하고 UploadFile 리턴 -> 리포지토리에 저장
        UploadFile uploadFile = fileStore.storeFile(form.getAttachFile());

        // Form으로부터 Post된 파일리스트를 스토어에 저장하고 UploadFile List 리턴 ->  리포지토리에 저장
        List<UploadFile> uploadFiles = fileStore.storeFiles(form.getImageFiles());

        // 리포지토리에 저장
        Item item = new Item();
        item.setItemName(form.getItemName());
        item.setAttachFile(uploadFile);
        item.setImageFiles(uploadFiles);
        itemRepository.save(item);

        redirectAttributes.addAttribute("itemId",item.getItemId());

        return "redirect:/items/{itemId}";
    }

    // Item 뷰
    @GetMapping("/items/{itemId}")
    public String itemView(@PathVariable Long itemId, Model model)
    {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "item-view";
    }


    // 파일
    @ResponseBody
    @GetMapping("/images/{fileName}")
    public Resource downloadImage(@PathVariable String fileName) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(fileName));
    }


    // 다운로드
    @GetMapping("/attach/{itemId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long itemId) throws MalformedURLException {
        Item item = itemRepository.findById(itemId);
        String uploadFileName = item.getAttachFile().getUploadFileName();
        String storeFileName = item.getAttachFile().getStoreFileName();

        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));

        log.info("uploadFileName={}",uploadFileName);

        String encode = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);

        // 다운로드 하기 위해서 attachment; filename="다운받을 파일이름"을 Header CONTENT_DISPOSITION에 설정
        String contentDisposition = "attachment; filename=\"" + encode + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,contentDisposition)
                .body(resource); // resource 다운
    }
}
