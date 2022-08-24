package innovitics.azimut.configproperties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
@Component
@Configuration
@ConstructorBinding
@ConfigurationProperties

public class ConfigProperties {

    @Value("${blob.connection.string}")
    private String blobConnectionString;


    @Value("${blob.account.name}")
    private String blobAccountName;
    
    @Value("${blob.container.name.profile-pictures}")
    private String blobProfilePicturePath;
    
    @Value("${blob.container.name.signed-pdfs}")
    private String blobSignedPdfPath;

    @Value("${blob.container.name.unsigned-pdf}")
    private String blobUnsignedPdfPath;
    
    @Value("${blob.container.name.unsigned-pdf.subDirectory}")
    private String blobUnsignedPdfPathSubDirectory;

    @Value("${blob.phoneNumberChangeDocument.name}")
    private String phoneNumberChangeDocumentName;

    @Value("${blob.container.name.kyc.documents}")
    private String blobKYCDocuments;

    @Value("${blob.container.name.kyc.documents.temp}")
    private String blobKYCDocumentsTemp;
    
    @Value("${blob.container.name.kyc.documents.container}")
    private String blobKYCDocumentsContainer;
    
    @Value("${blob.temp.deletion.hours}")
    private String blobTempDeletionInHours;
    
    
    @Value("${blob.generate.sas.token}")
    private boolean generateBlobSasToken;
    
    @Value("${blob.sas.token.duration.minutes}")
    private String sasTokenDurationInMinutes;
    
    @Value("${blob.sas.document.token.duration.minutes}")
    private String sasDocumentTokenDurationInMinutes;
    
    
    @Value("${blob.container.url}")
    private String blobContainerUrl;
    
    @Value("${token.validity.minutes}")
    private String jwTokenDurationInMinutes;

    @Value("${token.key}")
    private String jwTokenKey;
    
    @Value("${token.encryption.key}")
    private String jwTokenEncryptionKey;

    @Value("${token.encryption.init.vector}")
    private String jwTokenInitVector;

    @Value("${profile.picture.max.size.bytes}")
    private String profilePictureMaximumSizeInBytes;

    @Value("${phone.number.pdf.max.size.bytes}")
    private String phoneNumberMaximumSizeInBytes;

    @Value("${azimut.url}")
    private String azimutUrl;

    @Value("${tea.computers.url}")
    private String teaComputersUrl;
    
    @Value("${tea.computers.key}")
    private String teaComputersKey;

    @Value("${tea.computers.username}")
    private String teaComputersUsername;
 
    @Value("${tea.computers.password}")
    private String teaComputersPassword;
    
    @Value("${valify.url}")
    private String valifyUrl;
    
    @Value("${valify.username}")
    private String valifyUsername;
 
    @Value("${valify.password}")
    private String valifyPassword;
    
    @Value("${valify.client.id}")
    private String valifyClientId;
 
    @Value("${valify.client.secret}")
    private String valifyClientSecret;

    @Value("${valify.bundle.key}")
    private String valifyBundleKey;
    
    @Value("${valify.number.of.trials}")
    private String valifyTrialCount;
    
    
    @Value("${log.file.path}")
    private String logFilePath;
    
    
	public String getBlobConnectionString() {
		return blobConnectionString;
	}
	public String getBlobAccountName() {
		return blobAccountName;
	}
	public String getBlobProfilePicturePath() {
		return blobProfilePicturePath;
	}
	public String getBlobSignedPdfPath() {
		return blobSignedPdfPath;
	}

	public String getBlobUnsignedPdfPath() {
		return blobUnsignedPdfPath;
	}
	
	public String getBlobUnsignedPdfPathSubDirectory() {
		return blobUnsignedPdfPathSubDirectory;
	}
	public String getPhoneNumberChangeDocumentName() {
		return phoneNumberChangeDocumentName;
	}
	
	public String getBlobKYCDocuments() {
		return blobKYCDocuments;
	}
	
	public String getBlobKYCDocumentsTemp() {
		return blobKYCDocumentsTemp;
	}
	
	public String getBlobKYCDocumentsContainer() {
		return blobKYCDocumentsContainer;
	}
	public String getBlobTempDeletionInHours() {
		return blobTempDeletionInHours;
	}
	public boolean isGenerateBlobSasToken() {
		return generateBlobSasToken;
	}

	public String getSasTokenDurationInMinutes() {
		return sasTokenDurationInMinutes;
	}
	public String getBlobContainerUrl() {
		return blobContainerUrl;
	}

	public String getJwTokenDurationInMinutes() {
		return jwTokenDurationInMinutes;
	}
	public String getJwTokenKey() {
		return jwTokenKey;
	}
	
	public String getJwTokenEncryptionKey() {
		return jwTokenEncryptionKey;
	}
	public String getJwTokenInitVector() {
		return jwTokenInitVector;
	}
	public String getProfilePictureMaximumSizeInBytes() {
		return profilePictureMaximumSizeInBytes;
	}
	public String getPhoneNumberMaximumSizeInBytes() {
		return phoneNumberMaximumSizeInBytes;
	}
	public String getAzimutUrl() {
		return azimutUrl;
	}
	public String getTeaComputersUrl() {
		return teaComputersUrl;
	}
	public String getTeaComputersKey() {
		return teaComputersKey;
	}
	public String getTeaComputersUsername() {
		return teaComputersUsername;
	}
	public String getTeaComputersPassword() {
		return teaComputersPassword;
	}
	public String getValifyUrl() {
		return valifyUrl;
	}
	public String getValifyUsername() {
		return valifyUsername;
	}
	public String getValifyPassword() {
		return valifyPassword;
	}
	public String getValifyClientId() {
		return valifyClientId;
	}
	public String getValifyClientSecret() {
		return valifyClientSecret;
	}
	public String getValifyBundleKey() {
		return valifyBundleKey;
	}
	public String getSasDocumentTokenDurationInMinutes() {
		return sasDocumentTokenDurationInMinutes;
	}
	public String getValifyTrialCount() {
		return valifyTrialCount;
	}
	public String getLogFilePath() {
		return logFilePath;
	}
    
    

}
