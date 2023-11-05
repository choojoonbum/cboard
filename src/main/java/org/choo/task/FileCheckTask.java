package org.choo.task;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.choo.domain.BoardAttachVO;
import org.choo.mapper.BoardAttachMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Log4j
@Component
@AllArgsConstructor
public class FileCheckTask {
    private BoardAttachMapper attachMapper;

    @Scheduled(cron = "0 0 2 * * *")
    public void checkFiles() throws Exception {
        log.warn("File Check Task run.................");
        log.warn(new Date());

        List<BoardAttachVO> fileList = attachMapper.getOldFiles();

        List<Path> fileListPaths = fileList.stream()
                .map(vo -> Paths.get("C:\\Temp\\upload", vo.getUploadPath(), vo.getUuid() + "_" + vo.getFileName()))
                .collect(Collectors.toList());

        fileList.stream().filter(vo -> vo.isFileType())
                .map(vo -> Paths.get("C:\\Temp\\upload", vo.getUploadPath(), "s_" + vo.getUuid() + "_" + vo.getFileName()))
                .forEach(fileListPaths::add);

        log.warn("==================================");
        fileListPaths.forEach(log::warn);

        File targetDir = Paths.get("C:\\Temp\\upload", getFolderYesterDay()).toFile();

        File[] removeFiles = targetDir.listFiles(file -> fileListPaths.contains(file.toPath()) == false);
        log.warn("---------------------------------");
        for (File file : removeFiles) {
            log.warn(file.getAbsolutePath());
            file.delete();
        }
    }

    private String getFolderYesterDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String str = sdf.format(cal.getTime());
        log.warn(str);
        return str.replace("-", File.separator);
    }
}
