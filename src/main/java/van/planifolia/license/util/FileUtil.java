package van.planifolia.license.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class FileUtil {

    /**
     * 读文件成为字符串
     *
     * @param path 被读的路径
     * @return 读出来的字符串
     */
    public static String readFileAsString(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(path)), StandardCharsets.UTF_8));
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line.trim());
        }
        reader.close();
        return sb.toString();
    }

    /**
     * 写文件到指定路径
     *
     * @param path    被操作的绝对路径
     * @param content 被写入的正文
     */
    public static void writeStringToFile(String path, String content) throws IOException {
        Path jPath = Paths.get(path);
        try (OutputStreamWriter writer = new OutputStreamWriter(
                Files.newOutputStream(jPath), StandardCharsets.UTF_8)) {
            log.info("文件输出成功，路径为:{}", jPath.toAbsolutePath());
            writer.write(content);
        }
    }
}
