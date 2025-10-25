package pro.lopushok.ui.net;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.time.Duration;
import java.util.UUID;

public final class HttpMultipartUploader {
    public static final String DEFAULT_UPLOAD_URL = "http://217.144.185.104:8010/upload.php";

    private HttpMultipartUploader() {}

    public static String uploadFile(String url, File file, String fieldName) throws IOException, InterruptedException {
        byte[] data = Files.readAllBytes(file.toPath());

        String boundary = "----lopushok-" + UUID.randomUUID();
        String filename = file.getName();

        StringBuilder sb = new StringBuilder();
        sb.append("--").append(boundary).append("\r\n");
        sb.append("Content-Disposition: form-data; name=\"").append(fieldName)
                .append("\"; filename=\"").append(filename).append("\"\r\n");
        sb.append("Content-Type: application/octet-stream\r\n\r\n");

        byte[] header = sb.toString().getBytes();
        byte[] footer = ("\r\n--" + boundary + "--\r\n").getBytes();

        byte[] body = new byte[header.length + data.length + footer.length];
        System.arraycopy(header, 0, body, 0, header.length);
        System.arraycopy(data, 0, body, header.length, data.length);
        System.arraycopy(footer, 0, body, header.length + data.length, footer.length);

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();

        HttpRequest req = HttpRequest.newBuilder(URI.create(url))
                .timeout(Duration.ofSeconds(30))
                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                .header("User-Agent", "LopushokDesktop/1.0")
                .POST(HttpRequest.BodyPublishers.ofByteArray(body))
                .build();

        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        int sc = resp.statusCode();
        String bodyStr = resp.body() == null ? "" : resp.body().trim();
        if (sc >= 200 && sc < 300) {
            return bodyStr;
        }
        String tail = bodyStr.length() > 200 ? bodyStr.substring(0, 200) + "..." : bodyStr;
        throw new IOException("Upload failed: HTTP " + sc + (tail.isEmpty() ? "" : " — " + tail));
    }
}
