package com.webuilding.config;

import com.webuilding.common.CommonConfig;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
public class GlobalConfig {

    //第三方接口调试设置
    @Value("${api.debug}")
    private boolean apiDebug;
    //令令接口配置
    @Value("${lingling.url}")
    private String lingUrl;
    @Value("${lingling.signature}")
    private String lingSignature;
    @Value("${lingling.token}")
    private String lingToken;
    @Value("${lingling.openToken}")
    private String lingOpenToken;

    //微信后台接口配置
    @Value("${weixin.url}")
    private String weixinUrl;

    //api token
    @Value("${token_secret}")
    private String tokenSecret;//加密密钥
    @Value("${token_key}")
    private String tokenKey;//请求token参数名
    @Value("${token_expire_time}")
    private long tokenExpireTime;//token过期时间


    @Value("${project.maxpagesize}")
    private int maxPagesize;//最大分页页数
   // @Value("${project.public.Key}")
    private String publicKeyValue;//"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCMKErPe45RXl+PohxaiUUDflwU7dIqVH9C2p5XRJWr18OwTuZ9m94AmYTiLXxgQz8dcpX9zDV4nl3woZbicIzhRCDiQRm2s9Nqhg0DzM70E2iq71FiDa9r1iA/q9rHoNKxS5NCUw4RaESmjSWr1civwAk0kfgKkjqghvBfnDRzwIDAQAB";//
  //  @Value("${project.private.Key}")
    private String privateKeyValue;//

    private  KeyPair pair;

    @Bean(name = "commonConfig")
    public CommonConfig commonConfig() {
        CommonConfig commonConfig = new CommonConfig();
        commonConfig.setApiDebug(apiDebug);
        commonConfig.setLingUrl(lingUrl);
        commonConfig.setLingSignature(lingSignature);
        commonConfig.setLingToken(lingToken);
        commonConfig.setLingOpenToken(lingOpenToken);
        commonConfig.setWeixinUrl(weixinUrl);

        commonConfig.setTokenSecret(tokenSecret);
        //commonConfig.setTokenSecret(tokenSecret);
        commonConfig.setTokenKey(tokenKey);
        commonConfig.setTokenExpireTime(tokenExpireTime*1000);
        commonConfig.setMaxPagesize(maxPagesize);
        return commonConfig;
    }
    @PostConstruct
    private void getpublicKey(){
        InputStream is = null;
        BufferedReader br=null;
        InputStreamReader isr=null;
        try {
            Resource resource = new ClassPathResource("rsa_public_key.pem");
            is = resource.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String data = null;
            StringBuilder sb= new StringBuilder();
            while((data = br.readLine()) != null) {
                System.out.println(data);
                sb.append(data);
            }
            publicKeyValue=sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                br.close();
                isr.close();
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
    @PostConstruct
    private void getprivateKey(){
        InputStream is = null;
        BufferedReader br=null;
        InputStreamReader isr=null;
        try {
            Resource resource = new ClassPathResource("rsa_private_key_pkcs8.pem");
            is = resource.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String data = null;
            StringBuilder sb= new StringBuilder();
            while((data = br.readLine()) != null) {
                System.out.println(data);
                sb.append(data);
            }
            privateKeyValue=sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                br.close();
                isr.close();
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }
    @PostConstruct
    private void getkey() throws NoSuchAlgorithmException {

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        KeyPair  pair = keyGen.generateKeyPair();

        byte[] publicBytes = pair.getPublic().getEncoded();
        byte[] privateBytes = pair.getPrivate().getEncoded();

       String publicKey= Base64.encodeBase64String(publicBytes);
        String privateKey= Base64.encodeBase64String(privateBytes);

    }

    public String getPublicKey(){
        return publicKeyValue;
    }
    public KeyPair getPair(){
        return pair;
    }
    public String getPrivateKey(){
        return privateKeyValue;
    }

    private Map<String,TokenSecret> tokenMaps=new HashMap<String,TokenSecret>();

    public Map<String, TokenSecret> getTokenMaps() {
        return tokenMaps;
    }

    public void add(String key,TokenSecret value) {
        tokenMaps.put(key,value);
    }

}
