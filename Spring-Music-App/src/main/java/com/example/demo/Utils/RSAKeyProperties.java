//package com.example.demo.Utils;
//
//import org.springframework.stereotype.Component;
//
//import java.security.KeyPair;
//import java.security.interfaces.RSAPrivateKey;
//import java.security.interfaces.RSAPublicKey;
//
//@Component
//public class RSAKeyProperties {
//
//    private RSAPublicKey publicKey;
//    private RSAPrivateKey privateKey;
//
//
//
//    public RSAKeyProperties() {
//        KeyPair keyPair = KeyGeneratorUtility.generateRsaKey();
//        this.publicKey = (RSAPublicKey) keyPair.getPublic();
//        this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
//    }
//
//    public RSAPrivateKey getPrivateKey() {
//        return privateKey;
//    }
//
//    public void setPrivateKey(RSAPrivateKey privateKey) {
//        this.privateKey = privateKey;
//    }
//
//    public RSAPublicKey getPublicKey() {
//        return publicKey;
//    }
//
//    public void setPublicKey(RSAPublicKey publicKey) {
//        this.publicKey = publicKey;
//    }
//}
