package com.masanz.controller;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class GestorFTP {
    private FTPClient ftpClient;
    private  static final String SERVER = "localhost";
    private static final int PUERTO = 21;
    private static final String USER = "alumno";
    private static final String PASSWORD = "prueba123";

    public GestorFTP(){ftpClient = new FTPClient();}

    public void conectar() throws SocketException, IOException{
        ftpClient.connect(SERVER,PUERTO);
        int codRespuesta = ftpClient.getReplyCode();
        if(!FTPReply.isPositiveCompletion(codRespuesta)){
            throw new IOException("No se pudo conectar con el servidor FTP, respuesta: "+ codRespuesta);
        }
        if(!ftpClient.login(USER,PASSWORD)){
            throw new IOException("No se pudo iniciar sesión con las credenciales indicadas");
        };
        ftpClient.setFileType(FTP.ASCII_FILE_TYPE);
    }
    public void desconectar() throws IOException{
        if(ftpClient.isConnected()){
            ftpClient.logout();
            ftpClient.disconnect();
        }
    }
    public boolean subirArchivo(String rutaLocal, String rutaRemota) throws IOException{
        try {
            File localFile = new File(rutaLocal);
            if(!localFile.exists() || !localFile.isFile()){
                throw new IOException("El archivo local no existe o no es válido");
            }
            try (InputStream inputStream = new FileInputStream(localFile)){
                return ftpClient.storeFile(rutaRemota,inputStream);
            }
        } catch (IOException e) {
            throw new IOException("Error al subir el archivo: "+e.getMessage(),e);
        }
    }
    public boolean descargarArchivo(String rutaRemota, String rutaLocal)throws IOException{
        try (InputStream inputStream = ftpClient.retrieveFileStream(rutaRemota)){
            if (inputStream == null){
                throw new IOException("No se pudo obtener el archivo remoto: " + rutaRemota);
            }
            File localFile = new File(rutaLocal);
            Files.copy(inputStream, localFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return ftpClient.completePendingCommand();
        } catch (Exception e) {
            throw new IOException("Error al descargar el archivo: " + e.getMessage(),e);
        }
    }
}
