package com.example.sae_mobile_api.sae_mobile_api.services;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.sae_mobile_api.sae_mobile_api.models.CustomJsonString;
import com.example.sae_mobile_api.sae_mobile_api.models.Personne;
import com.example.sae_mobile_api.sae_mobile_api.repositorys.PersonneRepository;

@Service
public class PasswordResetService {
    
    @Autowired
    private PersonneRepository personneRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired 
    private MailSenderService mailSenderService;

    @Autowired
    private RSAService rsaService;
    // @Autowired
    // private JavaMailSenderImpl javaMailSender;
    // @Value("${publicKey64}")
    // private String publicKey;
    // @Value("{${privateKey64}")
    // private String privateKey;

    

    public boolean resetPassword(String email, String newPassword,String tokenOriginal,String tokenClient){
        System.out.println("section resetPassword : ");
        System.out.println(email+" "+newPassword+" "+tokenClient+" "+tokenOriginal);
        if (!tokenIsValide(tokenOriginal, tokenClient)) {
            System.out.println("token invalide");
            return false;
        }

        Personne p = personneRepository.findByEmail(email);
        if(p==null){  
            throw new UsernameNotFoundException("Utilisateur introuvable dans la base de données, vérifier l'email");
        }

        String encodedPassword=passwordEncoder.encode(newPassword);
        p.setPassword(encodedPassword);
        personneRepository.save(p);
        return true;
    }

    /**
     * 
     * @param email
     * @return
     * envoie un code à 6 chiffre à l'email passer en param pour reset sont mdp
     */
    public ResponseEntity<CustomJsonString> sendPasswordResetRequest(String email){
        //String resetLink=String.format("http://%s:8080/reset-password", getServerIpAddress());
        String codeReset=getSixChiffreRandom();
        //mailSenderService.sendEmail("abeuhkclefeuh@gmail.com", "CODE REINITIALISATION MOT DE PASSE",codeReset); // tmp
        System.out.println("email : "+email+" "+codeReset);
        mailSenderService.sendEmail(email, "CODE REINITIALISATION MOT DE PASSE",codeReset);
        try {
            String token=generateResetToken(email,codeReset,getExpireDate());
            return new ResponseEntity<>(new CustomJsonString(token),HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("erreur generation token pour "+email);
            return new ResponseEntity<>(new CustomJsonString("erreur"),HttpStatus.BAD_REQUEST);
        }
        
    }

    private String getExpireDate(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime dateTimeWithExtraHours = currentDateTime.plusHours(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTimeWithExtraHours.format(formatter);
    }



    private boolean tokenIsValide(String tokenOrignal,String tokenClient){
        System.out.println("token originale : "+tokenOrignal);
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String tokenOriginalDecrypted=rsaService.decrypt(tokenOrignal);
            String splittedTokenOriginale[]=tokenOriginalDecrypted.split("\\|");
            
            String emailOriginal=splittedTokenOriginale[0];
            String codeOriginal=splittedTokenOriginale[1];
    
            
            String splittedTokenClient[]=tokenClient.split("\\|");
            String emailClient=splittedTokenClient[0];
            String codeClient=splittedTokenClient[1];
            LocalDateTime dateExpireClient = LocalDateTime.parse(splittedTokenClient[2], formatter);
            LocalDateTime dateExpireOriginale = LocalDateTime.parse(splittedTokenOriginale[2], formatter);

            System.out.println("token recue : ");
            System.out.println("email client : "+emailClient);
            System.out.println("token client : "+tokenClient);
            
            return emailClient.equals(emailOriginal) && codeClient.equals(codeOriginal) && dateExpireClient.isBefore(dateExpireOriginale);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    private String getServerIpAddress() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null; 
        }
    }

    private String getSixChiffreRandom(){
        Random random = new Random();
        return String.valueOf(100000 + random.nextInt(999999));
    }

    private String generateResetToken(String email,String code6chiffres,String dateExpire) throws Exception{
        String msg=String.format("%s|%s|%s", email,code6chiffres,dateExpire);
        //System.out.println("message en clair :" +msg);
        String encryptedMsg=rsaService.encrypt(msg);
        //System.out.println("message chiffré :" +encryptedMsg);
        //System.out.println("message déchiffré :" +rsaService.decrypt(encryptedMsg));
        //tokenOriginale
        return encryptedMsg;
    }
}
