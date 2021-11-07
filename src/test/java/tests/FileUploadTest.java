package tests;

import base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FileUploadTest extends BaseTest {

    @Test
    @DisplayName("Testing the functionality of uploading an image")
    public void uploadImage() {
        var file = getReader().get("file-upload.file-path").toString();
        var expectedMessage = getReader().get("file-upload.expected-message").toString();
        homePage
                .navigateToFileUploadPage()
                .selectFile(System.getProperty("user.dir") + "/" + file)
                .uploadFile()
                .assertOnPageTitle(expectedMessage);
    }
}
