package br.udesc.doo2.cantina.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utilitária responsável por transformar a senha capturada na View (como
 * char[], vinda de um JPasswordField) no hash que efetivamente é
 * persistido/comparado no banco (como String).
 *
 * Usa SHA-256 nativo do Java SE (java.security.MessageDigest), sem
 * dependências externas. Sem salt.
 */
public final class SenhaUtils {

    private SenhaUtils() {
        // Impede instanciamento - classe é apenas um agrupador de métodos estáticos
    }

    /**
     * Gera o hash SHA-256 (em hexadecimal) a partir da senha em char[].
     */
    public static String gerarHash(char[] senha) {
        if (senha == null || senha.length == 0) {
            throw new IllegalArgumentException("Senha não pode ser nula ou vazia.");
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Convertemos char[] -> byte[] manualmente (evitamos criar uma
            // String intermediária imutável com a senha em texto puro).
            byte[] senhaBytes = new byte[senha.length];
            for (int i = 0; i < senha.length; i++) {
                senhaBytes[i] = (byte) senha[i];
            }

            byte[] hashBytes = digest.digest(senhaBytes);

            // Limpa o array intermediário da memória assim que possível
            java.util.Arrays.fill(senhaBytes, (byte) 0);

            return bytesParaHex(hashBytes);

        } catch (NoSuchAlgorithmException e) {
            // SHA-256 é algoritmo padrão obrigatório da JVM - nunca deveria cair aqui
            throw new IllegalStateException("Algoritmo SHA-256 indisponível na JVM.", e);
        }
    }

    /**
     * Compara uma senha em texto puro (vinda da tela de login)
     * com um hash já armazenado no banco.
     */
    public static boolean validar(char[] senhaDigitada, String hashArmazenado) {
        if (hashArmazenado == null) {
            return false;
        }
        String hashDigitado = gerarHash(senhaDigitada);
        return hashDigitado.equalsIgnoreCase(hashArmazenado);
    }

    private static String bytesParaHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}