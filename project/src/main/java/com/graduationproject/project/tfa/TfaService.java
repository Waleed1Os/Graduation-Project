package com.graduationproject.project.tfa;



import org.springframework.stereotype.Service;


import dev.samstevens.totp.code.CodeGenerator;
import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.code.HashingAlgorithm;
import dev.samstevens.totp.exceptions.CodeGenerationException;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import dev.samstevens.totp.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TfaService {

public String generateNewSecret(){
    return new DefaultSecretGenerator().generate();
}

public String generateQrCodeImageUri(String secret){
final QrData data = new QrData.Builder()
.label("Qr 2fa")
.secret(secret)
.issuer("AI correction")
.algorithm(HashingAlgorithm.SHA1)
.digits(6)
.period(60)
.build();
final QrGenerator generator = new ZxingPngQrGenerator();
byte[] imageBytes = new byte[0];
try {
    imageBytes = generator.generate(data);
} catch (QrGenerationException e) {
    e.printStackTrace();
    log.error("Error while generating QR image");
}
return Utils.getDataUriForImage(imageBytes,generator.getImageMimeType());
}

public boolean isOtpValid(String secret,String code){
    final TimeProvider timeProvider = new SystemTimeProvider();
    final CodeGenerator codeGenerator = new DefaultCodeGenerator();
    final CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator,timeProvider);
    return verifier.isValidCode(secret, code);
}

public boolean isOtpNotValid(String secret,String code){
    return !isOtpValid(secret, code);
}
public String generateTextOTP(String secret) throws CodeGenerationException {
    final long counter = System.currentTimeMillis() / 1000 / 30; // Number of time steps since the epoch 'int timeStepInSeconds = 30;'
    final CodeGenerator codeGenerator = new DefaultCodeGenerator();
    final String code = codeGenerator.generate(secret, counter);
    return code;
}
}
